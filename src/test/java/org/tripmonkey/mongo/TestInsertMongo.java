package org.tripmonkey.mongo;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.tripmonkey.domain.data.User;
import org.tripmonkey.domain.data.Workspace;
import org.tripmonkey.mongo.data.WorkspaceDB;
import org.tripmonkey.mongo.mapper.WorkspaceMapper;
import org.tripmonkey.mongo.repo.WorkspaceRepository;

import java.util.List;
import java.util.UUID;

@QuarkusTest
public class TestInsertMongo {

    @Inject
    WorkspaceRepository wrp;

    @Inject
    Logger log;

    @Test
    public void testInsert(){
        User u = User.from(UUID.randomUUID());
        UUID wid = UUID.randomUUID();
        Workspace w = Workspace.from(wid.toString(), List.of(u), List.of());

        WorkspaceDB wdb = WorkspaceMapper.from(w);

        wrp.persist(wdb);

        WorkspaceDB fetched = wrp.findById(wid.toString());

        log.infof("%s", fetched.toString());

        Assertions.assertEquals(wdb.getWorkspaceId(),fetched.getWorkspaceId());
    }

}
