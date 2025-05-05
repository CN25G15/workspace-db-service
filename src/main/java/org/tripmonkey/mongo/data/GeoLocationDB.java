package org.tripmonkey.mongo.data;

import org.bson.codecs.pojo.annotations.BsonProperty;

public class GeoLocationDB {

    @BsonProperty float Lat;
    @BsonProperty float Lng;

    public static GeoLocationDB from(float lat, float lng) {
        GeoLocationDB gldb = new GeoLocationDB();
        gldb.Lat = lat;
        gldb.Lng = lng;
        return gldb;
    }

    public float getLat() {
        return Lat;
    }

    public float getLng() {
        return Lng;
    }
}
