package org.tripmonkey.mongo.data;

import io.quarkus.runtime.annotations.RegisterForReflection;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.tripmonkey.rest.domain.data.CommentDTO;
import org.tripmonkey.rest.domain.data.LocationDTO;
import org.tripmonkey.rest.domain.data.LocationListDTO;
import org.tripmonkey.rest.domain.data.LocationMetadataDTO;
import org.tripmonkey.rest.domain.data.UserDTO;
import org.tripmonkey.rest.patch.fields.value.CommentView;
import org.tripmonkey.rest.patch.fields.value.LocationListView;
import org.tripmonkey.rest.patch.fields.value.LocationMetadataView;
import org.tripmonkey.rest.patch.fields.value.LocationView;
import org.tripmonkey.rest.patch.fields.value.UserView;
import org.tripmonkey.rest.patch.fields.value.ValueType;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RegisterForReflection
public class ValueDB implements UserView, CommentView,
        LocationView, LocationListView, LocationMetadataView {

    @BsonProperty public String comment;
    @BsonProperty public String place_id;
    @BsonProperty public String name;
    @BsonProperty public List<String> locations;
    @BsonProperty public String description;
    @BsonProperty public List<String> tags;
    @BsonProperty public String user_id;

    public ValueDB(){}

    public static ValueDB from(
            @BsonProperty String comment,
            @BsonProperty String place_id,
            @BsonProperty String name,
            @BsonProperty List<String> locations,
            @BsonProperty String description,
            @BsonProperty List<String> tags,
            @BsonProperty String user_id
    ) {
        ValueDB vdb = new ValueDB();
        vdb.comment = comment;
        vdb.place_id = place_id;
        vdb.name = name;
        vdb.locations = locations;
        vdb.description = description;
        vdb.tags = tags;
        vdb.user_id = user_id;
        return vdb;
    }

    @BsonIgnore
    @Override
    public CommentDTO asComment() {
        return CommentDTO.from(comment);
    }

    @BsonIgnore
    @Override
    public boolean isComment() {
        return comment != null && place_id == null && name == null && locations == null && description == null
                && tags == null && user_id == null;
    }

    @BsonIgnore
    @Override
    public LocationListDTO asLocationList() {
        return LocationListDTO.fromStringList(name, locations);
    }

    @BsonIgnore
    @Override
    public boolean isLocationList() {
        return comment == null && place_id == null && name != null && locations != null && description == null
                && tags == null && user_id == null;
    }

    @BsonIgnore
    @Override
    public LocationMetadataDTO asLocationMetadata() {
        return LocationMetadataDTO.from(description,tags);
    }

    @BsonIgnore
    @Override
    public boolean isLocationMetadata() {
        return comment == null && place_id == null && name == null && locations == null && description != null
                && tags != null && user_id == null;
    }

    @BsonIgnore
    @Override
    public LocationDTO asLocation() {
        return LocationDTO.from(place_id);
    }

    @BsonIgnore
    @Override
    public boolean isLocation() {
        return comment == null && place_id != null && name == null && locations == null && description == null
                && tags == null && user_id == null;
    }

    @BsonIgnore
    @Override
    public Optional<UserDTO> asUser() {
        return UserDTO.from(user_id);
    }

    @BsonIgnore
    @Override
    public boolean isUser() {
        return comment == null && place_id == null && name == null && locations == null && description == null
                && tags == null && user_id != null;
    }

    @BsonIgnore
    public ValueType getType(){
        if(isComment())
            return ValueType.COMMENT;
        if(isUser())
            return ValueType.USER;
        if(isLocation())
            return ValueType.LOCATION;
        if(isLocationList())
            return  ValueType.LOCATION;
        if(isLocationMetadata())
            return ValueType.LOC_META;
        return ValueType.INVALID;
    }

    public static ValueDB from(CommentDTO comment) {
        return from(comment.getComment(),null,null,null,null,null,null);
    }

    public static ValueDB from(LocationDTO location) {
        return from(null, location.getPlace_id(), null,null,null,null,null);
    }

    public static ValueDB from(LocationListDTO ll) {
        return from(null,null, ll.getName(), ll.getLocations()
                .stream()
                .map(LocationDTO::getPlace_id).toList(),
                null,null,null);
    }

    public static ValueDB from(LocationMetadataDTO llm) {
        return from(null,null,null,null, llm.getDescription(), llm.getTags(),null);
    }

    public static ValueDB from(UserDTO user) {
        return from(null,null,null,null,null,null,user.toString());
    }

    @BsonIgnore
    public String toString() {
        StringBuilder stb = new StringBuilder().append("{");
        switch (getType()) {
            default -> {
            }
            case COMMENT -> {
                stb.append(String.format("\"comment\":\"%s\"", comment));
            }
            case LOCATION -> {
                stb.append(String.format("\"place_id\":\"%s\"", place_id));
            }
            case LOC_LIST -> {
                if (name != null)
                    stb.append(String.format("\"name\":\"%s\"", name));
                if (locations != null)
                    stb.append(String.format("\"locations\":\"[%s]\"",
                            String.join(",", locations.stream().map(s ->
                                    String.format("{\"place_id\":\"%s\"}", s)).toList())));
            }
            case LOC_META -> {
                if (description != null)
                    stb.append(String.format("\"description\":\"%s\"", description));
                if (tags != null)
                    stb.append(String.format("\"tags\":\"[%s]\"",
                            String.join(",", tags)));
            }
            case USER -> {
                stb.append(String.format("\"user_id\":\"%s\"", user_id));
            }
        }
        return stb.append("}").toString();
    }

}
