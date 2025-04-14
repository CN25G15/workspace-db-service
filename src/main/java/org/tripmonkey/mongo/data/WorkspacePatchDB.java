package org.tripmonkey.mongo.data;

import org.bson.codecs.pojo.annotations.BsonProperty;

public class WorkspacePatchDB {

    @BsonProperty String wid;
    @BsonProperty String user_id;
    @BsonProperty String op;
    @BsonProperty String path;
    @BsonProperty
    ValueWrapperDB value;

    public static WorkspacePatchDB from(String wid, String user_id, String op, String path, ValueWrapperDB vwdb){
        WorkspacePatchDB wpdb = new WorkspacePatchDB();
        wpdb.wid = wid;
        wpdb.user_id = user_id;
        wpdb.op = op;
        wpdb.path = path;
        wpdb.value = vwdb;
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

    public ValueWrapperDB getValue() {
        return value;
    }

    public String getWid() {
        return wid;
    }
}
