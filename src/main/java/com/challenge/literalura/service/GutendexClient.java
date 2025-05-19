package com.challenge.literalura.service;

import com.challenge.literalura.dtos.GutendexBookDto;
import com.challenge.literalura.dtos.GutendexResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
public class GutendexClient {

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Optional<GutendexBookDto> getBookJsonByTitle(String title){

        String baseUrl = "https://gutendex.com/books/?search=";
        String requestUrl = baseUrl + URLEncoder.encode(title, StandardCharsets.UTF_8);

        try{
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(requestUrl))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            GutendexResponseDto gutendexResponseDto = objectMapper.readValue(response.body(), GutendexResponseDto.class);

            if(gutendexResponseDto != null && !gutendexResponseDto.results().isEmpty()){
                return Optional.of(gutendexResponseDto.results().getFirst());
            }else{
                return Optional.empty();
            }

        } catch (Exception e) {
            throw new RuntimeException("GutendexClient error: ", e);
        }
    }

}
