package com.challenge.literalura.dtos;

import com.fasterxml.jackson.annotation.JsonAlias;

public record GutendexAuthorDto(
        String name,
        @JsonAlias("birth_year") int birthYear,
        @JsonAlias("death_year") int deathYear
) {
}
