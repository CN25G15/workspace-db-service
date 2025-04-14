package org.tripmonkey.mongo.data;

import org.bson.codecs.pojo.annotations.BsonProperty;

public class LocationDB {

    @BsonProperty String place_id;

    public String getPlaceId() {
        return place_id;
    }

    public static LocationDB of(String place_id) {
        LocationDB ldb = new LocationDB();
        ldb.place_id = place_id;
        return ldb;
    }
}
