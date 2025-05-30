package org.tripmonkey.mongo.repo;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;
import org.tripmonkey.mongo.data.PlaceDB;
import org.tripmonkey.mongo.data.UserDB;
import org.tripmonkey.mongo.data.UserPlacesDB;

import java.util.List;

@ApplicationScoped
public class UserDataRepository implements PanacheMongoRepository<UserPlacesDB> {

    @Inject
    Logger log;

    public UserPlacesDB findById(String uid){
        return find("user.id", uid).firstResult();
    }

    public void dump(UserDB user, PlaceDB place) {
        UserPlacesDB db = find("user.id", user.getId()).firstResult();
        log.infof("Fetching user data: %s ", db != null ? db.toString() :
                String.format("none found for user: %s", user.getId()));
        if(db != null) {
            db.getPlace().add(place);
            update(db);
        } else {
            db = UserPlacesDB.from(user, List.of(place));
            persist(db);
        }
    }

}
