package org.tripmonkey.mongo.service;

import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import org.tripmonkey.database.service.CreateWorkspace;
import org.tripmonkey.mongo.mapper.WorkspaceMapper;
import org.tripmonkey.mongo.repo.WorkspaceRepository;
import org.tripmonkey.proto.map.ProtoMapper;
import org.tripmonkey.workspace.service.Workspace;
import org.tripmonkey.workspace.service.WorkspaceResponse;

@GrpcService
public class CreateService implements CreateWorkspace {

    @Inject
    WorkspaceRepository wrkp;

    @Override
    public Uni<WorkspaceResponse> create(Workspace request) {
        return Uni.createFrom().item(request)
                .log(String.format("Persisting workspace with id %s", request.getWid()))
                .map(ProtoMapper.workspaceMapper::deserialize)
                .log(String.format("Successfully deserialized workspace with id %s", request.getWid()))
                .map(WorkspaceMapper::from)
                .map(workspaceDB -> {
                    wrkp.persist(workspaceDB);
                    return null;
                }).onItem().transformToUni(o ->
                        Uni.createFrom().item(WorkspaceResponse.newBuilder().setWorkspace(request).build()))
                .onFailure().recoverWithItem(() -> WorkspaceResponse.newBuilder().build());
    }
}
