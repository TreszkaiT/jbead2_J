package com.tt.jbead.security.login.config;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Név alapján ki tud kereni egy felhasználót a DB-ből, és azt egy UserDetails objektumba visszaadja, hogy a Spring Security automatikusan tuja ezt kezelni
 */
@Service
public class MyUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new User("foo", "foo", new ArrayList<>());
    }
}

//    @Autowired
//    private UserEntityRepository userEntityRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<UserEntity> userEntity = userEntityRepository.findByUsername(username);
//
//        if (userEntity.isPresent()) {
//            return new AppUserDetails(userEntity.get());
//        }
//
//        throw new UsernameNotFoundException("Not good mate!");
//    }