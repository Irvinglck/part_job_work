package com.lck.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "t_person")
@Data
public class Person {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private  Integer id;

    @Column(length =50,name = "name")
    private  String  name;

    private  String email;

    private  String  gender;

    private  String  address;
}