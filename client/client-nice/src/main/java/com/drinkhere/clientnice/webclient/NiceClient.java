package com.drinkhere.clientnice.webclient;

import com.drinkhere.clientnice.dto.request.GetCryptoTokenRequest;
import com.drinkhere.clientnice.dto.response.GetCryptoTokenResponse;
import com.drinkhere.clientnice.webclient.config.NiceProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class NiceClient {

    private final WebClient niceWebClient;
    private final NiceProperties niceProperties;

    private static final String NICE_API_URL = "/digital/niceid/api/v1.0/common/crypto/token";

    public GetCryptoTokenResponse requestCryptoToken(String reqDtim, String reqNo) {

        String authorization = generateAuthorizationHeader();

        GetCryptoTokenRequest request = new GetCryptoTokenRequest(
                new GetCryptoTokenRequest.DataHeader("ko"),
                new GetCryptoTokenRequest.DataBody(reqDtim, reqNo, "1")
        );
        System.out.println("authorization = " + authorization);
        System.out.println("request = " + request.dataBody().toString());
        System.out.println("niceProperties.getProductId() = " + niceProperties.getProductId());
        return niceWebClient.post()
                .uri(NICE_API_URL)
                .header("Authorization", authorization)
                .header("ProductID", niceProperties.getProductId())
                .bodyValue(request)
                .retrieve()
                .bodyToMono(GetCryptoTokenResponse.class)
                .block(); // Blocking call for simplicity. Consider using a reactive approach.
    }

    private String generateAuthorizationHeader() {
        long currentTimestamp = System.currentTimeMillis() / 1000;
        String rawAuth = String.format("%s:%d:%s", niceProperties.getOrganizationToken(), currentTimestamp, niceProperties.getClientId());
        return "bearer " + Base64.getEncoder().encodeToString(rawAuth.getBytes(StandardCharsets.UTF_8));
    }
}
