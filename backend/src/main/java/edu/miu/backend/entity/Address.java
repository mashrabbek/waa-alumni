package edu.miu.backend.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String state;
    private String city;
    private String zip;

    @OneToOne(mappedBy ="address")
    private User user;

}
