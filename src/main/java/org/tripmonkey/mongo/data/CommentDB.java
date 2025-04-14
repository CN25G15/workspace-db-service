package org.tripmonkey.mongo.data;

import org.bson.codecs.pojo.annotations.BsonProperty;

public class CommentDB {

    @BsonProperty String comment;

    public String getComment() {
        return comment;
    }

    public static CommentDB of(String comment) {
        CommentDB cdb = new CommentDB();
        cdb.comment = comment;
        return cdb;
    }
}
