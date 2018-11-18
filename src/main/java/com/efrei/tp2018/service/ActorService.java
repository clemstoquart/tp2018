package com.efrei.tp2018.service;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import com.efrei.tp2018.dto.Actor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ActorService {
    private final WebClient webClient;

    public ActorService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8081").build();
    }

    public Actor requestActor(String name) {
        return webClient.get()
                .uri("/api/v1/actors/{name}", name).retrieve()
                .bodyToMono(Actor.class)
                .timeout(Duration.of(20, ChronoUnit.SECONDS))
                .retry(2)
                .block();
    }
}
