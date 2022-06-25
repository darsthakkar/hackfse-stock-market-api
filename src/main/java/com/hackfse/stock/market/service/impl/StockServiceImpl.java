package com.hackfse.stock.market.service.impl;

import com.hackfse.stock.market.data.Stock;
import com.hackfse.stock.market.data.StockType;
import com.hackfse.stock.market.data.Trade;
import com.hackfse.stock.market.exception.StockMarketException;
import com.hackfse.stock.market.repository.StockRepository;
import com.hackfse.stock.market.service.StockService;
import com.hackfse.stock.market.service.TradeService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final TradeService tradeService;

    /**
     * Argument constructor.
     * @param stockRepository the {@link StockRepository} to set.
     * @param tradeService the {@link TradeService} to set.
     */
    @Autowired
    public StockServiceImpl(final StockRepository stockRepository,
                            final TradeService tradeService) {
        this.stockRepository = stockRepository;
        this.tradeService = tradeService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double calculateDividendYield(final Double price, final String symbol) throws StockMarketException {
        if (price <= 0d) {
            throw new StockMarketException("Price is not valid");
        }
        final Stock stock = stockRepository.findBySymbol(symbol)
                .orElseThrow(() -> new StockMarketException("Stock not found for Symbol"));

        return getDividend(stock) / price;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double getPERatio(final Double price, final String symbol) throws StockMarketException {
        if (price <= 0d) {
            throw new StockMarketException("Price is not valid");
        }

        final Stock stock = stockRepository.findBySymbol(symbol)
                .orElseThrow(() -> new StockMarketException("Stock not found for Symbol"));

        final Double dividend = this.getDividend(stock);

        if (dividend > 0) {
            return price / dividend;
        }

        return 0d;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double getVolumeWeightedStock(final String symbol) throws StockMarketException {

        final Stock stock = stockRepository.findBySymbol(symbol)
                .orElseThrow(() -> new StockMarketException("Stock not found for Symbol"));

        final List<Trade> validTrades = tradeService.getLatestTrades(stock);
        int totalQuantity = 0;
        double tradedValue = 0d;

        if (validTrades.size() > 0) {
            for (final Trade trade : validTrades) {
                tradedValue = tradedValue + trade.getQuantity() * trade.getTradePrice();
                totalQuantity = totalQuantity + trade.getQuantity();
            }

            return tradedValue / totalQuantity;
        }

        return 0d;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double calculateGeometricMean() {
        final Set<Stock> indexStocks = stockRepository.getAllStocks();
        final List<Trade> allTrades = indexStocks.stream()
                .map(stockRepository::getTrades).flatMap(List::stream)
                .collect(Collectors.toList());

        if (allTrades != null && allTrades.size() > 0) {
            final Double priceMultiplier = allTrades.stream()
                    .map(Trade::getTradePrice)
                    .reduce(1d, (tradePrice1, tradePrice2) -> tradePrice1 * tradePrice2);

            return Math.pow(priceMultiplier, 1/allTrades.size());
        }

        return 0d;
    }

    /**
     * Calculates the dividend for the given stock.
     * @param stock the {@link Stock} to calculate dividend for
     * @return the {@link Double} dividend value.
     */
    private Double getDividend(final Stock stock) {
        if (stock.getType() == StockType.COMMON) {
            return stock.getLastDividend();
        }

        return (stock.getFixedDividend() / 100) * stock.getParValue();
    }
}
