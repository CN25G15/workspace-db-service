package org.tripmonkey.mongo.data;

import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.List;

public class PlaceDB {

    @BsonProperty String place_id;
    @BsonProperty Float rating;
    @BsonProperty List<String> types;
    @BsonProperty GeometryDB geometry;

    public static PlaceDB from(String id, float rating, List<String> types, GeometryDB geoloc) {
        PlaceDB pdb = new PlaceDB();
        pdb.place_id = id;
        pdb.rating = rating;
        pdb.types = types;
        pdb.geometry = geoloc;
        return pdb;
    }

    public GeometryDB getGeometry() {
        return geometry;
    }

    public List<String> getTypes() {
        return types;
    }

    public Float getRating() {
        return rating;
    }

    public String getPlace_id() {
        return place_id;
    }
}
