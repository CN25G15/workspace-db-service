package org.tripmonkey.mongo.mapper;

import org.tripmonkey.mongo.data.CommentDB;
import org.tripmonkey.mongo.data.LocationDB;
import org.tripmonkey.mongo.data.LocationListDB;
import org.tripmonkey.mongo.data.LocationMetadataDB;
import org.tripmonkey.mongo.data.UserDB;
import org.tripmonkey.mongo.data.ValueWrapperDB;
import org.tripmonkey.rest.domain.data.CommentDTO;
import org.tripmonkey.rest.domain.data.LocationDTO;
import org.tripmonkey.rest.domain.data.LocationListDTO;
import org.tripmonkey.rest.domain.data.LocationMetadataDTO;
import org.tripmonkey.rest.domain.data.UserDTO;
import org.tripmonkey.rest.domain.value.ValueType;
import org.tripmonkey.rest.domain.value.ValueWrapper;

public class ValueWrapperMapper {

    public static ValueWrapperDB from(ValueWrapper vw) {

        Object o = switch(vw.getValue()){
            case UserDTO u -> UserMapper.from(u);
            case CommentDTO c -> CommentMapper.fromComment(c);
            case LocationDTO l -> LocationMapper.of(l);
            case LocationListDTO ll -> LocationListMapper.from(ll);
            case LocationMetadataDTO lm -> LocationMetadataMapper.fromMetadata(lm);
            default -> null;
        };

        return o != null ? ValueWrapperDB.from(vw.getType().toString(), o) :
        ValueWrapperDB.from(ValueType.INVALID.toString(), o);

    }

    public static ValueWrapper from(ValueWrapperDB vw) {

        Object o = switch(vw.getValue()){
            case UserDB u -> UserMapper.from(u);
            case CommentDB c -> CommentMapper.fromCommentDB(c);
            case LocationDB l -> LocationMapper.of(l);
            case LocationListDB ll -> LocationListMapper.fromLocationListDB(ll);
            case LocationMetadataDB lm -> LocationMetadataMapper.fromMetadataDB(lm);
            default -> null;
        };

        return o != null ? ValueWrapper.from(ValueType.valueOf(vw.getType()), o) :
                ValueWrapper.from(ValueType.INVALID, o);

    }

}
