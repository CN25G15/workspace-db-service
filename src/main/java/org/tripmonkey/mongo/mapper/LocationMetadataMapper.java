package org.tripmonkey.mongo.mapper;

import org.tripmonkey.mongo.data.LocationMetadataDB;
import org.tripmonkey.rest.domain.data.LocationMetadataDTO;

public class LocationMetadataMapper {

    public static LocationMetadataDB fromMetadata(LocationMetadataDTO lmdto){
        return LocationMetadataDB.from(lmdto.getDescription(), lmdto.getTags());
    }

    public static LocationMetadataDTO fromMetadataDB(LocationMetadataDB lmddb){
        return LocationMetadataDTO.from(lmddb.getDescription(), lmddb.getTags());
    }

}
