package com.lck.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;

import javax.persistence.*;

@Entity
@Table(name="t_user")
@Data
@Accessors(chain = true)
public class User extends JpaRepositoriesAutoConfiguration {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @Column(name="user_name")
    private String userName;
    @Column(name="password")
    private String password;
    @Column(name="grade")
    private String grade;
}
