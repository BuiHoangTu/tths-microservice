package bhtu.work.tthsauthservice.repositories.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import bhtu.work.tthsauthservice.models.UserAcess;


public interface UserAccessRepo extends MongoRepository<UserAcess, Integer>{
}
