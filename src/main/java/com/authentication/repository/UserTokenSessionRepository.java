package com.authentication.repository;

import com.authentication.model.UserTokenSession;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTokenSessionRepository extends CrudRepository<UserTokenSession, Long> {

    /**
     *  Find  UserTokenSession for the given username
     */
    UserTokenSession findOneByUsername(String username);

}