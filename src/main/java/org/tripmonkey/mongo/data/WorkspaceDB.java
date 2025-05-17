package org.tripmonkey.mongo.data;

import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.types.ObjectId;

import java.util.List;

@MongoEntity(collection = "workspaces",database = "tripmonkey")
public class WorkspaceDB {

    public ObjectId id;
    public String wid;
    public List<String> users;
    public List<WorkspacePatchDB> history;

    public String getWorkspaceId() {
        return wid;
    }

    public List<String> getUsers() {
        return users;
    }

    public List<WorkspacePatchDB> getHistory() {
        return history;
    }

    public static org.tripmonkey.mongo.data.WorkspaceDB from(String wid, List<String> userIds, List<WorkspacePatchDB> history) {
        org.tripmonkey.mongo.data.WorkspaceDB wdb = new org.tripmonkey.mongo.data.WorkspaceDB();
        wdb.wid = wid;
        wdb.users = userIds;
        wdb.history = history;
        return wdb;
    }

    public String toString(){ //TODO improve here later forlogger
        return new StringBuilder()
                .append("\n{\n")
                .append("\t\"wid\":")
                .append(wid)
                .append(",\n")
                .append("\t\"collaborators\":[")
                .append(String.join("\t\t\n",users))
                .append("],\n")
                .append("\t\"history\":[")
                .append(String.join("\n",history.stream().map(WorkspacePatchDB::toString).toList()))
                .append("\t]\n}")
                .toString();
    }
}
