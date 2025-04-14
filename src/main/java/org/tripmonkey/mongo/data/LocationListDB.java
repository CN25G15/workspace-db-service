package org.tripmonkey.mongo.data;

import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.List;

public class LocationListDB {

    @BsonProperty String name;
    @BsonProperty List<LocationDB> locations;

    public String getName() {
        return name;
    }

    public List<LocationDB> getLocations() {
        return locations;
    }

    public static LocationListDB of(String name, List<LocationDB> locations){
        LocationListDB lldb = new LocationListDB();
        lldb.name = name;
        lldb.locations = locations;
        return lldb;
    }

    public static LocationListDB from(String name, List<String> location_ids){
        return of(name, location_ids.stream().map(LocationDB::of).toList());
    }

}
