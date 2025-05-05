package org.tripmonkey.mongo.repo;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.tripmonkey.mongo.data.PlaceDB;
import org.tripmonkey.mongo.data.UserDB;
import org.tripmonkey.mongo.data.UserPlacesDB;

@ApplicationScoped
public class UserDataRepository implements PanacheMongoRepository<UserPlacesDB> {

    public UserPlacesDB findById(String uid){
        return find("user.id", uid).firstResult();
    }

    public void dump(UserDB user, PlaceDB place) {
        UserPlacesDB db = find("user.id", user.getId()).firstResult();
        db.getPlace().add(place);
        persist(db);
    }

}
