package com.challenge.literalura.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GutendexResponseDto(
        int count,
        List<GutendexBookDto> results
) {
}
