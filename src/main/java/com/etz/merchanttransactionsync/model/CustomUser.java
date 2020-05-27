/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etz.merchanttransactionsync.model;

import org.springframework.security.core.userdetails.User;

/**
 *
 * @author afam.okonkwo
 */
public class CustomUser extends User {
    private Long id;
    public CustomUser(UserModel userModel) {
        super(userModel.getUsername(), userModel.getPassword(), userModel.getGrantedAuthoritiesList());   
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
       
    }
}
