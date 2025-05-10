package org.tripmonkey.mongo.service;

import io.quarkus.grpc.GrpcService;
import io.smallrye.common.annotation.RunOnVirtualThread;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;
import org.tripmonkey.database.service.FetchWorkspace;
import org.tripmonkey.mongo.mapper.WorkspaceMapper;
import org.tripmonkey.proto.domain.ProtoMapper;
import org.tripmonkey.mongo.repo.WorkspaceRepository;
import org.tripmonkey.workspace.service.WorkspaceRequest;
import org.tripmonkey.workspace.service.WorkspaceResponse;


@GrpcService
public class FetchService implements FetchWorkspace {

    @Inject
    Logger log;

    @Inject
    WorkspaceRepository wrkp;

    @Override
    @RunOnVirtualThread
    public Uni<WorkspaceResponse> fetch(WorkspaceRequest request) {
        return Uni.createFrom().item(request).map(WorkspaceRequest::getWid)
                .map(wrkp::findById)
                .onItem().ifNull().failWith(() -> new RuntimeException("Workspace for given Id doesn't exist"))
                .onItem().ifNotNull()
                .invoke(workspaceDB -> {
                    log.infof("Found workspace with %d", workspaceDB.getWorkspaceId());
                })
                
                .onItem()
                .transform(workspaceDB -> WorkspaceMapper.from(workspaceDB))
                .map(ProtoMapper.workspaceMapper::serialize)
                .map(workspace -> WorkspaceResponse.newBuilder().setWorkspace(workspace).build());
    }
}