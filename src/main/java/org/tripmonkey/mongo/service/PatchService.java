package org.tripmonkey.mongo.service;

import io.quarkus.grpc.GrpcService;
import io.smallrye.common.annotation.RunOnVirtualThread;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;
import org.tripmonkey.database.service.PatchPersister;
import org.tripmonkey.mongo.data.WorkspaceDB;
import org.tripmonkey.mongo.mapper.WorkspacePatchMapper;
import org.tripmonkey.patch.data.Status;
import org.tripmonkey.patch.data.WorkspacePatch;
import org.tripmonkey.proto.map.ProtoMapper;
import org.tripmonkey.mongo.repo.WorkspaceRepository;
import org.tripmonkey.workspace.service.PatchApplier;


@GrpcService
public class PatchService implements PatchPersister {

    @Inject
    Logger log;

    @Inject
    WorkspaceRepository wrkp;

    @Override
    @RunOnVirtualThread
    public Uni<Status> apply(WorkspacePatch request) {
        return Uni.createFrom().item(ProtoMapper.workspacePatchMapper.deserialize(request))
                .log(String.format("Received patch for workspace %s.", request.getWorkspaceId()))
                .map(WorkspacePatchMapper::from)
                .log("Deserialized patch.")
                .map(workspacePatch -> {
                WorkspaceDB wdb = wrkp.findById(workspacePatch.wid);
                    if(wdb == null) {
                        throw new RuntimeException("Workspace doesn't exist in the database");
                    }
                    wdb.getHistory().forEach(workspacePatchDB -> System.out.println(workspacePatchDB.value.toString()));
                    wdb.getHistory().add(workspacePatch);
                    wrkp.update(wdb);
                    return Status.newBuilder().setStatus(200).build();
                }).log("Successfully persisted patch.")
                .onFailure().recoverWithItem(throwable ->
                        Status.newBuilder().setStatus(500).setMessage(throwable.getMessage()).build())
                .onFailure().invoke(throwable ->  log.errorf("Persistence unsuccessful.", throwable));

    }
}
