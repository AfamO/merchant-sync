/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etz.merchanttransactionsync.repository.syncdb;

import com.etz.merchanttransactionsync.model.syncdb.User;
import com.etz.merchanttransactionsync.model.UserModel;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;

/**
 *
 * @author afam.okonkwo
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public User findByUsername(String username);
}

