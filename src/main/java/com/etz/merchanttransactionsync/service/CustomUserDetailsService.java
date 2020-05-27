/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etz.merchanttransactionsync.service;

import com.etz.merchanttransactionsync.model.CustomUser;
import com.etz.merchanttransactionsync.model.syncdb.User;
import com.etz.merchanttransactionsync.model.UserModel;
import com.etz.merchanttransactionsync.repository.syncdb.UserRepository;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author afam.okonkwo
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userDao;
    
    public UserModel getUserDetails(String username) {
    
    Collection<GrantedAuthority> grantedAuthoritiesList = new ArrayList<>();
    User user = userDao.findByUsername(username);
        
       
    if(user != null) {
        UserModel userModel = new UserModel();
        userModel.setUsername(user.getUsername());
        userModel.setPassword(user.getPassword());
        userModel.setId(user.getId());
        grantedAuthoritiesList.add(new SimpleGrantedAuthority("ROLE_SYSTEMADMIN"));
        userModel.setGrantedAuthoritiesList(grantedAuthoritiesList);
        return userModel;
    }
    return null;
}
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       
        UserModel userModel = getUserDetails(username);
        CustomUser customUser = new CustomUser(userModel);
        if(userModel!= null && userModel.getId() != null) {
            customUser.setId(userModel.getId());
            return customUser;
        }
        else {
            throw new UsernameNotFoundException("User " + username + "was not found in the database");
        }
        
    }
    
}
