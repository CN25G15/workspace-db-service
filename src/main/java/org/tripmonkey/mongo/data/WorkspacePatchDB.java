package org.tripmonkey.mongo.data;

import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.tripmonkey.domain.patch.PatchVisitor;
import org.tripmonkey.rest.domain.data.CommentDTO;
import org.tripmonkey.rest.domain.data.LocationDTO;
import org.tripmonkey.rest.domain.data.LocationListDTO;
import org.tripmonkey.rest.domain.data.LocationMetadataDTO;
import org.tripmonkey.rest.domain.data.UserDTO;

public class WorkspacePatchDB {

     @BsonProperty public String wid;
     @BsonProperty public String user_id;
     @BsonProperty public String op;
     @BsonProperty public String path;
     @BsonProperty public ValueDB value;

     public WorkspacePatchDB(){}

     @BsonIgnore
    public static WorkspacePatchDB from(String wid, String user_id, String op, String path, Object vwdb){
        WorkspacePatchDB wpdb = new WorkspacePatchDB();
        wpdb.wid = wid;
        wpdb.user_id = user_id;
        wpdb.op = op;
        wpdb.path = path;
        wpdb.value = switch (vwdb){
            case CommentDTO c -> ValueDB.from(c);
            case UserDTO u -> ValueDB.from(u);
            case LocationDTO l -> ValueDB.from(l);
            case LocationListDTO ll -> ValueDB.from(ll);
            case LocationMetadataDTO mmd -> ValueDB.from(mmd);
            default -> null;
        };
        return wpdb;
    }
}
