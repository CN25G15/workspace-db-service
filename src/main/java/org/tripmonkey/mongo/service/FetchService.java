package org.tripmonkey.mongo.service;

import io.quarkus.grpc.GrpcService;
import io.smallrye.common.annotation.RunOnVirtualThread;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import org.tripmonkey.database.service.FetchWorkspace;
import org.tripmonkey.mongo.mapper.WorkspaceMapper;
import org.tripmonkey.proto.ProtoSerde;
import org.tripmonkey.mongo.repo.WorkspaceRepository;
import org.tripmonkey.workspace.service.Workspace;
import org.tripmonkey.workspace.service.WorkspaceRequest;


@GrpcService
public class FetchService implements FetchWorkspace {

    @Inject
    WorkspaceRepository wrkp;

    @Override
    @RunOnVirtualThread
    public Uni<Workspace> fetch(WorkspaceRequest request) {
        return Uni.createFrom().item(request).map(WorkspaceRequest::getWid)
                .map(wrkp::findById)
                .onItem().ifNull().failWith(() -> new RuntimeException("Workspace for given Id doesn't exist"))
                .onItem().ifNotNull().transform(workspaceDB -> WorkspaceMapper.from(workspaceDB))
                .map(ProtoSerde.workspaceMapper::serialize);
    }
}