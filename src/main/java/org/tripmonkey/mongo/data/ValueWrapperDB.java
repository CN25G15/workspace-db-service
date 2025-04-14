package org.tripmonkey.mongo.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.List;
import java.util.Map;

public class ValueWrapperDB {

    @BsonProperty String type;
    @BsonProperty Object value;

    public String getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    @JsonCreator
    public static ValueWrapperDB from(
        @BsonProperty("type") String type,
        @BsonProperty("value") Map<String, Object> value
    ) {
        ValueWrapperDB vwdb = new ValueWrapperDB();
        vwdb.type = type;

        if(value.containsKey("comment")){
            vwdb.value = CommentDB.of((String) value.get("comment"));
        } else if(value.containsKey("place_id")){
            vwdb.value = LocationDB.of((String) value.get("place_id"));
        } else if(value.containsKey("name")
                || value.containsKey("locations")){
            String name = value.containsKey("name") ? (String) value.get("name") : null;
            List<String> locations = value.containsKey("locations") ? (List<String>) value.get("locations") : List.of();
            vwdb.value = LocationListDB.from(name,locations);
        } else if(value.containsKey("description")
                || value.containsKey("tags")) {
            String description = value.containsKey("description") ? (String) value.get("description") : null;
            List<String> tags = value.containsKey("tags") ? (List<String>) value.get("tags") : List.of();
            vwdb.value = LocationMetadataDB.from(description,tags);
        } else if(value.containsKey("id")) {
            vwdb.value = UserDB.of((String) value.get("id"));
        } else {
            vwdb.type = "unknown";
            vwdb.value = null;
        }
        return vwdb;
    }

    public static ValueWrapperDB from(String type, Object o){
        ValueWrapperDB vwdb = new ValueWrapperDB();
        vwdb.type = type;
        vwdb.value = o;
        return vwdb;
    }


}
