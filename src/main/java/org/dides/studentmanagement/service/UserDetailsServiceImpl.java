package org.dides.studentmanagement.service;

import org.dides.studentmanagement.model.UserPrincipal;
import org.dides.studentmanagement.model.Users;
import org.dides.studentmanagement.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepo.findByEmail(username); // assuming email is the username
        if (user == null) {
            System.out.println("User not found");
            throw new UsernameNotFoundException("Email not found");
        }
        return new UserPrincipal(user); // pass the user to UserPrincipal
    }
}

