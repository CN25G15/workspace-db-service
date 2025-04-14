package org.tripmonkey.mongo.data;

import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.List;

public class LocationMetadataDB {

    @BsonProperty String description;
    @BsonProperty List<String> tags;

    public String getDescription() {
        return description;
    }

    public List<String> getTags() {
        return tags;
    }

    public static LocationMetadataDB from(String description, List<String> tags){
        LocationMetadataDB lmdb = new LocationMetadataDB();
        lmdb.description = description;
        lmdb.tags = tags;
        return lmdb;
    }
}
