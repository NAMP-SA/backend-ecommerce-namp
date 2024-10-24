package com.namp.ecommerce.service.implementation;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.namp.ecommerce.Utils.CustomUserDetails;
import com.namp.ecommerce.model.User;
import com.namp.ecommerce.repository.IUserDAO;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final IUserDAO userDao;

    public CustomUserDetailsService(IUserDAO userDao) {
        this.userDao = userDao;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new CustomUserDetails(user);
    }
}