package org.tripmonkey.mongo.service;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.util.JsonFormat;
import io.quarkus.grpc.GrpcService;
import io.smallrye.common.annotation.RunOnVirtualThread;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;
import org.tripmonkey.database.service.FetchWorkspace;
import org.tripmonkey.mongo.mapper.WorkspaceMapper;
import org.tripmonkey.proto.map.ProtoMapper;
import org.tripmonkey.mongo.repo.WorkspaceRepository;
import org.tripmonkey.workspace.service.WorkspaceRequest;
import org.tripmonkey.workspace.service.WorkspaceResponse;


@GrpcService
public class FetchService implements FetchWorkspace {

    @Inject
    Logger log;

    @Inject
    WorkspaceRepository wrkp;

    private String print(Message m) {
        String s = "";
        try{
            s = JsonFormat.printer().print(m);
        } catch (InvalidProtocolBufferException e) {
        }
        return s;
    }

    @Override
    @RunOnVirtualThread
    public Uni<WorkspaceResponse> fetch(WorkspaceRequest request) {
        return Uni.createFrom().item(request).map(WorkspaceRequest::getWid)
                .invoke(() -> log.info(print(request)))
                .map(wrkp::findById)
                .invoke(workspaceDB -> log.infof("Workspace %s exists: %s", request.getWid(), workspaceDB != null))
                .onItem().ifNull().failWith(() -> new RuntimeException("Workspace for given Id doesn't exist"))
                .onItem().ifNotNull()
                .invoke(workspaceDB -> {
                    log.infof("Found workspace with %d", workspaceDB.getWorkspaceId());
                })
                
                .onItem()
                .transform(WorkspaceMapper::from)
                .map(ProtoMapper.workspaceMapper::serialize)
                .map(workspace -> WorkspaceResponse.newBuilder().setWorkspace(workspace).build())
                .onFailure().invoke(throwable -> log.errorf("\n\nFailed with %s\n\n",throwable.getMessage()));
    }
}