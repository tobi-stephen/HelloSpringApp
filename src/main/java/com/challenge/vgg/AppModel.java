package com.challenge.vgg;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;


@Data
@Entity
@Table(name = "appmodels")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppModel {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    protected long id;

    @Id
    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private Date dateOfBirth;

}