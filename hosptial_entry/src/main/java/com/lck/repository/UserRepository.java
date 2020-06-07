package com.lck.repository;

import com.lck.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Component;

@Component
public interface UserRepository extends CrudRepository<User, Long> {
    @Query(value = "select id,user_name,password,grade from t_user u where u.user_name =?1",nativeQuery=true)
    User findByUserName(String userName);
}
