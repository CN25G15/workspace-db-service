package org.tripmonkey.mongo.mapper;

import org.tripmonkey.mongo.data.UserDB;
import org.tripmonkey.rest.domain.data.UserDTO;

public class UserMapper {

    public static UserDB from(UserDTO u) {
        return UserDB.of(u.toString());
    }

    public static UserDTO from(UserDB u) {
        return UserDTO.from(u.getId()).get();
    }

}
