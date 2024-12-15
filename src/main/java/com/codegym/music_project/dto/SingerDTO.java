package com.codegym.music_project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SingerDTO {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("avatarLinkString")
    private String avatarLinkString;

    public SingerDTO(Long id, String name, String avatarLinkString) {
        this.id = id;
        this.name = name;
        this.avatarLinkString = avatarLinkString;
    }

}
