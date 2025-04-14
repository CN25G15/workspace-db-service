package org.tripmonkey.mongo.repo;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.tripmonkey.mongo.data.WorkspaceDB;


@ApplicationScoped
public class WorkspaceRepository implements PanacheMongoRepository<WorkspaceDB> {

    public WorkspaceDB findById(String wid){
        return find("wid", wid).firstResult();
    }

}
