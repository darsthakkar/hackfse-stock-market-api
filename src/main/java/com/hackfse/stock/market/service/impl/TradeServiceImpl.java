package com.hackfse.stock.market.service.impl;

import com.hackfse.stock.market.data.Stock;
import com.hackfse.stock.market.data.Trade;
import com.hackfse.stock.market.exception.TradeException;
import com.hackfse.stock.market.repository.StockRepository;
import com.hackfse.stock.market.service.TradeService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TradeServiceImpl implements TradeService {
    private static final Logger logger = LogManager.getLogger(TradeServiceImpl.class);
    private final StockRepository stockRepository;

    /**
     * Argument constructor.
     * @param stockRepository the {@link StockRepository} to set.
     */
    @Autowired
    public TradeServiceImpl(final StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void tradeStock(final String stockSymbol, final Trade trade) throws  TradeException {
        if(trade == null || trade.getQuantity() <= 0 || trade.getTradePrice() <= 0) {
                throw new TradeException("Request is not valid");
        }
        stockRepository.addTrade(stockSymbol, trade);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Trade> getTrades(final Stock stock) {
        return stockRepository.getTrades(stock);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Trade> getLatestTrades(final Stock stock) {
        final List<Trade> allTrades = this.getTrades(stock);

        if (allTrades != null) {
            final Instant fifteenMinutesBack = Instant.now().minus(15, ChronoUnit.MINUTES);
            return allTrades.stream()
                    .filter(trade -> trade.getTimeStamp().isAfter(fifteenMinutesBack))
                    .collect(Collectors.toList());
        }

        return Collections.emptyList();
    }
}
