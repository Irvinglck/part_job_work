package com.lck.repository;


import com.lck.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

@Component
public interface UserRepository extends JpaSpecificationExecutor<User>, JpaRepository<User, Integer> {
    @Query(value = "select id,user_name,password,grade from t_user u where u.user_name =?1",nativeQuery=true)
    User findByUserName(String userName);
}
