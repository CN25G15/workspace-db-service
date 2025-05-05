package org.tripmonkey.mongo.mapper;

import org.tripmonkey.google.places.data.Place;
import org.tripmonkey.mongo.data.PlaceDB;

public class PlaceMapper {

    public static PlaceDB fromPlace(Place p) {
        return PlaceDB.from(p.getPlace_id(),p.getRating(),p.getTypes(),GeometryMapper.fromGeometry(p.getGeometry()));
    }

}
