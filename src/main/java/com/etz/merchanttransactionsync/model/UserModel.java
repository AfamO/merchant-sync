/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etz.merchanttransactionsync.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

/**
 *
 * @author afam.okonkwo
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserModel implements Serializable {
    
    private Long id;
    private String username;
    private String password;
    @Builder.Default
    private Collection<GrantedAuthority> grantedAuthoritiesList = new ArrayList<>();
}
