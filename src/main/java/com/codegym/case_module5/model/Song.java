package com.codegym.case_module5.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "songs")
@Data
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private String songStringLink;

    @ManyToMany
    @JoinTable(
            name = "song_singer",
            joinColumns = @JoinColumn(name = "song_id"),
            inverseJoinColumns = @JoinColumn(name = "singer_id")
    )
    private Set<Singer> singers = new HashSet<>();


}
