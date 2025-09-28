package com.odeyalo.sonata.gateway.client;

import com.odeyalo.sonata.gateway.client.dto.TokenMetadataDto;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public final class WebClientAccessTokenClient implements AccessTokenClient {
    private final WebClient webClient;

    public WebClientAccessTokenClient(final WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Mono<TokenMetadataDto> validateToken(final String token) {
        Assert.notNull(token, "Token cannot be empty!");

        final Map<String, String> body = Map.of("access_token", token);
        return webClient.post().uri("/v1/tokens/access")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .onStatus(status -> !status.is2xxSuccessful(), resp -> {
                    System.out.println(resp.statusCode());
                    return Mono.error(new InvalidAccessTokenException());
                })
                .bodyToMono(TokenMetadataDto.class);
    }
}
