package com.codegym.case_module5.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "singers")
@Data
public class Singer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String avatarLinkString;

    @ManyToMany(mappedBy = "singers")

    private Set<Song> songs = new HashSet<>();

}
