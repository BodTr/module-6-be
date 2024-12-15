package com.codegym.music_project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class SongDTO {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
    @JsonProperty("songStringLink")
    private String songStringLink;
    @JsonProperty("singers")
    private Set<SingerDTO> singers;


}
