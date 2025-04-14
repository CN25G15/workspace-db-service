package org.tripmonkey.mongo.data;

import org.bson.codecs.pojo.annotations.BsonProperty;

public class UserDB {

    @BsonProperty String id;

    public String getId() {
        return id;
    }

    public static UserDB of(String id) {
        UserDB udb = new UserDB();
        udb.id = id;
        return udb;
    }
}
