package bhtu.work.tths.authservice.repositories.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import bhtu.work.tths.authservice.models.UserAccess;


public interface UserAccessRepo extends MongoRepository<UserAccess, Integer>{
}
