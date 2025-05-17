package org.tripmonkey.mongo.data;

import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.types.ObjectId;

import java.util.List;

@MongoEntity(collection = "userdata",database = "tripmonkey")
public class UserPlacesDB {

    ObjectId id;
    UserDB user;
    List<PlaceDB> places;

    public UserDB getUser() {
        return user;
    }

    public List<PlaceDB> getPlace() {
        return places;
    }

    public ObjectId getId() {
        return id;
    }

    public static UserPlacesDB from(UserDB user, List<PlaceDB> places) {
        UserPlacesDB updb = new UserPlacesDB();
        updb.user = user;
        updb.places = places;
        return updb;
    }

}
