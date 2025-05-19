package com.challenge.literalura.dtos;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GutendexBookDto(
        String title,
        List<GutendexAuthorDto> authors,
        List<String> languages,
        @JsonAlias("download_count") int downloadCount
) {
}
