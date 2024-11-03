package com.example.webappvolunteer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigInteger;
@Entity
@Table(name = "guide_language")
@AllArgsConstructor
@NoArgsConstructor
public class GuideLanguage implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_language")
    private BigInteger languageId;

    @Getter
    @Setter
    @Column(name = "name_language")
    private String languageName;

}