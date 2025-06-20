package org.tripmonkey.mongo.service;

import io.quarkus.grpc.GrpcService;
import io.smallrye.common.annotation.RunOnVirtualThread;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;
import org.tripmonkey.database.service.PersistUserData;
import org.tripmonkey.database.service.UserPlace;
import org.tripmonkey.mongo.data.PlaceDB;
import org.tripmonkey.mongo.data.UserDB;
import org.tripmonkey.mongo.mapper.GeometryMapper;
import org.tripmonkey.mongo.repo.UserDataRepository;
import org.tripmonkey.patch.data.Status;
import org.tripmonkey.proto.map.ProtoMapper;

@GrpcService
public class StoreUserData implements PersistUserData {

    @Inject
    Logger log;

    @Inject
    UserDataRepository urdp;

    @Override
    @RunOnVirtualThread
    public Uni<Status> store(UserPlace request) {

        log.infof("Received persist user data request user %s location %s",request.getUser().getUserId(),
                request.getPlace().getPlaceId());

        urdp.dump(UserDB.of(request.getUser().getUserId()),
                PlaceDB.from(request.getPlace().getPlaceId(),
                        request.getPlace().getRating(),
                        request.getPlace().getTypeList().stream().toList(),
                        GeometryMapper.fromGeometry(ProtoMapper.geometryMapper.deserialize(request.getPlace().getGeometry()))));

        return Uni.createFrom().item(Status.newBuilder().setStatus(200).build());
    }
}
