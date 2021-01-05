package com.example.demo.security.services;


import com.example.demo.model.User;
import com.example.demo.repo.UserRepo;
import com.example.demo.security.configs.MyUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo usersRepo;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = usersRepo.findByEmailContainingIgnoreCase(userName);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + userName);
        }
//        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
//                new ArrayList<>());
        return new MyUserDetail(user)  ;
    }

}
