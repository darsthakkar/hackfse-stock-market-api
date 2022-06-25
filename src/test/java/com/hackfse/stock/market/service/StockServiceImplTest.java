package com.hackfse.stock.market.service;

import com.hackfse.stock.market.data.Stock;
import com.hackfse.stock.market.data.StockType;
import com.hackfse.stock.market.data.Trade;
import com.hackfse.stock.market.data.TradeType;
import com.hackfse.stock.market.exception.StockMarketException;
import com.hackfse.stock.market.exception.TradeException;
import com.hackfse.stock.market.repository.StockRepository;
import com.hackfse.stock.market.service.TradeService;
import com.hackfse.stock.market.service.impl.StockServiceImpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class StockServiceImplTest {

    @Mock
    StockRepository stockRepository;
    @Mock
    TradeService tradeService;

    @InjectMocks
    StockServiceImpl stockService;

    Stock stock;

    @BeforeEach
    public void setup() {
        stock = new Stock("TEA", "COMMON", "10", "2", "100");
    }

    @Test
    public void shouldCalculateDividendYield() throws StockMarketException {
        Mockito.when(stockRepository.findBySymbol("TEA")).thenReturn(Optional.of(stock));

        Double yield = stockService.calculateDividendYield(100d, "TEA");

        Assertions.assertNotNull(yield);
        Assertions.assertEquals(Double.valueOf(0.02), yield);
    }

    @Test
    public void shouldCalculatePERatio() throws StockMarketException {
        Mockito.when(stockRepository.findBySymbol("TEA")).thenReturn(Optional.of(stock));

        Double ratio = stockService.getPERatio(100d, "TEA");

        Assertions.assertNotNull(ratio);
        Assertions.assertEquals(Double.valueOf(50d), ratio);
    }

    @Test
    public void shouldCalculateVolumeWeightedStock() throws StockMarketException {
        Trade trade = new Trade(2, 10D, TradeType.BUY);

        Mockito.when(stockRepository.findBySymbol("TEA")).thenReturn(Optional.of(stock));
        Mockito.when(tradeService.getLatestTrades(stock)).thenReturn(Arrays.asList(trade));

        Double volumeWeight = stockService.getVolumeWeightedStock("TEA");

        Assertions.assertNotNull(volumeWeight);
        Assertions.assertEquals(Double.valueOf(10d), volumeWeight);
    }

    @Test
    public void shouldCalculateAllShareIndex() {
        Trade trade = new Trade(2, 10D, TradeType.BUY);
        Mockito.when(stockRepository.getAllStocks()).thenReturn(new HashSet<>(Arrays.asList(stock)));
        Mockito.when(stockRepository.getTrades(stock)).thenReturn(Arrays.asList(trade));

        Double mean = stockService.calculateGeometricMean();

        Assertions.assertNotNull(mean);
        Assertions.assertEquals(Double.valueOf(10d), mean);
    }
}
