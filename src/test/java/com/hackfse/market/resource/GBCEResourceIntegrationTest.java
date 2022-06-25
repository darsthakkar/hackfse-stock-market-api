package com.hackfse.market.resource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.hackfse.stock.market.data.Trade;
import com.hackfse.stock.market.data.TradeType;

import java.net.URI;
import java.net.URISyntaxException;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GBCEResourceIntegrationTest {

    @LocalServerPort
    int randomServerPort;

    @Test
    public void shouldReturnDividendYield() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = getBaseUrl() + "ale/dividend-yield?price=20";
        URI uri = new URI(baseUrl);

        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

        Assertions.assertEquals(200, result.getStatusCodeValue());
        Assertions.assertEquals("1.15", result.getBody());
    }

    @Test
    public void shouldReturnPERatio() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = getBaseUrl() + "ale/pe-ratio?price=20";
        URI uri = new URI(baseUrl);

        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

        Assertions.assertEquals(200, result.getStatusCodeValue());
        Assertions.assertEquals("0.8695652173913043", result.getBody());
    }

    @Test
    public void shouldBeAbleToTradeAStock() throws URISyntaxException {
            RestTemplate restTemplate = new RestTemplate();
            final String baseUrl = getBaseUrl() + "trade/ale";
            URI uri = new URI(baseUrl);
            Trade trade = new Trade(2, 20.5d, TradeType.BUY);
            ResponseEntity<String> result = restTemplate.postForEntity(uri, trade, String.class);

            Assertions.assertEquals(201, result.getStatusCodeValue());
    }

    @Test
    public void shouldRetrieveVolumeWeightedStockPrice() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        final String baseTradeUrl = getBaseUrl() + "trade/ale";
        URI tradeUri = new URI(baseTradeUrl);
        Trade trade = new Trade(2, 20.5d, TradeType.BUY);
        restTemplate.postForEntity(tradeUri, trade, String.class);

        final String baseUrl = getBaseUrl() + "ale/volume-weight-price";
        URI uri = new URI(baseUrl);

        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

        Assertions.assertEquals(200, result.getStatusCodeValue());
        Assertions.assertEquals("20.5", result.getBody());
    }

    @Test
    public void shouldRetrieveAllShareIndex() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        final String baseTradeUrl = getBaseUrl() + "trade/ale";
        URI tradeUri = new URI(baseTradeUrl);
        Trade trade = new Trade(2, 20.5d, TradeType.BUY);
        restTemplate.postForEntity(tradeUri, trade, String.class);

        final String baseUrl = getBaseUrl() + "all-share-index";
        URI uri = new URI(baseUrl);

        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

        Assertions.assertEquals(200, result.getStatusCodeValue());
        Assertions.assertEquals("1.0", result.getBody());
    }

    private String getBaseUrl() {
        return "http://localhost:"+randomServerPort+"/gbce/";
    }
}
