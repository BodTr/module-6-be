package com.codegym.music_project.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "singers")
public class Singer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String avatarLinkString;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "singers")

    private Set<Song> songs = new HashSet<>();

}
