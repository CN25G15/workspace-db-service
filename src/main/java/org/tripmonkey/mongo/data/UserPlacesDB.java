package org.tripmonkey.mongo.data;

import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.List;

@MongoEntity(collection = "userdata",database = "tripmonkey")
public class UserPlacesDB {

    @BsonProperty UserDB user;
    @BsonProperty List<PlaceDB> places;

    public UserDB getUser() {
        return user;
    }

    public List<PlaceDB> getPlace() {
        return places;
    }

    public static UserPlacesDB from(UserDB user, List<PlaceDB> places) {
        UserPlacesDB updb = new UserPlacesDB();
        updb.user = user;
        updb.places = places;
        return updb;
    }

}
