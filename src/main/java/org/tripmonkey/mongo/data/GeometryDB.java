package org.tripmonkey.mongo.data;

import org.bson.codecs.pojo.annotations.BsonProperty;

public class GeometryDB {

    @BsonProperty GeoLocationDB location;

    public static GeometryDB from(float lat, float lng) {
        GeometryDB gdb = new GeometryDB();
        gdb.location = GeoLocationDB.from(lat,lng);
        return gdb;
    }

    public GeoLocationDB getLocation() {
        return location;
    }
}
