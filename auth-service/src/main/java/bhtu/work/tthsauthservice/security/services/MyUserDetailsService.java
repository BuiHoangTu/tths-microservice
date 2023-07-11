package bhtu.work.tthsauthservice.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bhtu.work.tthsauthservice.models.User;
import bhtu.work.tthsauthservice.repositories.mongo.UserRepo;

@Service
public class MyUserDetailsService implements UserDetailsService {
    UserRepo userRepo;

    @Autowired
    public MyUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return new MyUserDetails(user);
    }
}
