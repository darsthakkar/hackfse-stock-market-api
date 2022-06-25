package com.hackfse.stock.market.service;

import com.hackfse.stock.market.data.Stock;
import com.hackfse.stock.market.data.Trade;
import com.hackfse.stock.market.data.TradeType;
import com.hackfse.stock.market.exception.TradeException;
import com.hackfse.stock.market.repository.StockRepository;
import com.hackfse.stock.market.service.impl.TradeServiceImpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class TradeServiceImplTest {

    @Mock
    StockRepository stockRepository;
    @InjectMocks
    TradeServiceImpl tradeService;

    @Test
    public void shouldAddTradeToStock() throws TradeException {
        Trade trade = new Trade(2, 10d, TradeType.SELL);
        Mockito.when(stockRepository.addTrade("TEA", trade)).thenReturn(Boolean.TRUE);

        tradeService.tradeStock("TEA", trade);

        Mockito.verify(stockRepository, Mockito.times(1)).addTrade("TEA", trade);
    }

    @Test
    public void shouldReturnAllTradesForStock() throws TradeException {
        Trade trade = new Trade(2, 10d, TradeType.SELL);
        Mockito.when(stockRepository.getTrades(any(Stock.class))).thenReturn(Arrays.asList(trade));

        List<Trade> teaTrades = tradeService.getTrades(mock(Stock.class));

        Assertions.assertNotNull(teaTrades);
        Assertions.assertEquals(1, teaTrades.size());
        Assertions.assertEquals(trade, teaTrades.get(0));
    }

    @Test
    public void shouldReturnLatesTradesForStock() throws TradeException {
        Trade trade = new Trade(2, 10d, TradeType.SELL);
        Mockito.when(stockRepository.getTrades(any(Stock.class))).thenReturn(Arrays.asList(trade));

        List<Trade> teaTrades = tradeService.getLatestTrades(mock(Stock.class));

        Assertions.assertNotNull(teaTrades);
        Assertions.assertEquals(1, teaTrades.size());
        Assertions.assertEquals(trade, teaTrades.get(0));
    }
}
