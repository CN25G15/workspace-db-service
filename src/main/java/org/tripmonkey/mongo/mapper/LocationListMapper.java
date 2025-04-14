package org.tripmonkey.mongo.mapper;

import org.tripmonkey.mongo.data.LocationDB;
import org.tripmonkey.mongo.data.LocationListDB;
import org.tripmonkey.rest.domain.data.LocationDTO;
import org.tripmonkey.rest.domain.data.LocationListDTO;

public class LocationListMapper {

    public static LocationListDB from(LocationListDTO lldto) {
        return LocationListDB.of(lldto.getName(), lldto.getLocations()
                .stream().map(LocationDTO::getPlace_id).map(LocationDB::of).toList());
    }

    public static LocationListDTO fromLocationListDB(LocationListDB lldb) {
        return LocationListDTO.fromStringList(lldb.getName(),
                lldb.getLocations().stream().map(LocationDB::getPlaceId).toList());
    }

}
