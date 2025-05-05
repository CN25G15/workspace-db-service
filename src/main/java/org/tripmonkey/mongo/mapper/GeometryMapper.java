package org.tripmonkey.mongo.mapper;

import org.tripmonkey.google.places.data.Geometry;
import org.tripmonkey.google.places.data.PlaceLocation;
import org.tripmonkey.mongo.data.GeometryDB;

public class GeometryMapper {

    public static GeometryDB fromGeometry(Geometry g) {
        return GeometryDB.from(g.getLocation().getLat(),g.getLocation().getLng());
    }

    public static Geometry fromGeometryDB(GeometryDB gdb) {
        return Geometry.of(PlaceLocation.from(gdb.getLocation().getLat(),gdb.getLocation().getLng()));
    }

}
