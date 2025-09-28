package com.odeyalo.sonata.gateway.client;

import com.odeyalo.sonata.gateway.client.dto.TokenMetadataDto;
import reactor.core.publisher.Mono;

public interface AccessTokenClient {

    Mono<TokenMetadataDto> validateToken(String token);

}
