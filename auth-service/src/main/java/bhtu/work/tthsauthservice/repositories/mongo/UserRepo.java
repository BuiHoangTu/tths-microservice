package bhtu.work.tthsauthservice.repositories.mongo;

import com.mongodb.lang.NonNull;
import org.springframework.data.mongodb.repository.MongoRepository;

import bhtu.work.tthsauthservice.models.User;

import java.util.List;
import java.util.Optional;


public interface UserRepo extends MongoRepository<User, String> {

    // Optional<User> findById(@NonNull String id);

    Boolean existsByUsername(@NonNull String username);

    Optional<User> findByUsername(String username);

    /**
     * Main usage: make sure 1 household only have 1 account
     * @param householdNumber region this user can access (his/her householdNumber)
     * @return if this region(household) already has a user
     */
    Boolean existsByAccessRegion(String householdNumber);

    /**
     * Main usage: retrieve household account
     * @param householdNumber region this user can access (his/her householdNumber)
     * @return List of users who manage this region. Should contain 1 if exists or 0 otherwise with householdNumber as input
     */
    List<User> findByAccessRegion(String householdNumber);
}
