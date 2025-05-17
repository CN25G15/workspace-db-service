package org.tripmonkey.mongo.data;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.tripmonkey.domain.patch.PatchVisitor;
import org.tripmonkey.rest.domain.data.CommentDTO;
import org.tripmonkey.rest.domain.data.LocationDTO;
import org.tripmonkey.rest.domain.data.LocationListDTO;
import org.tripmonkey.rest.domain.data.LocationMetadataDTO;
import org.tripmonkey.rest.domain.data.UserDTO;

public class WorkspacePatchDB {

    @BsonProperty String wid;
    @BsonProperty String user_id;
    @BsonProperty String op;
    @BsonProperty String path;
    @BsonProperty ValueDB value;

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

    public String getUserId() {
        return user_id;
    }

    public String getOp() {
        return op;
    }

    public String getPath() {
        return path;
    }

    public ValueDB getValue() {
        return value;
    }

    public String getWid() {
        return wid;
    }



}
