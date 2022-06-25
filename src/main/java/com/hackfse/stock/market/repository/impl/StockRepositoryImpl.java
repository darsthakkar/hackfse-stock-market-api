package com.hackfse.stock.market.repository.impl;

import com.hackfse.stock.market.data.Stock;
import com.hackfse.stock.market.data.Trade;
import com.hackfse.stock.market.exception.TradeException;
import com.hackfse.stock.market.repository.StockRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class StockRepositoryImpl implements StockRepository {

    private Map<Stock, List<Trade>> tradeMap;

    /**
     * Arg-Constructor.
     * @param initialStocks the {@link String} representation of stocks to add.
     */
    @Autowired
    public StockRepositoryImpl(@Value("${hackfse.initial.stocks}") final String initialStocks) {
        tradeMap = new ConcurrentHashMap<>();
        initializeStocks(initialStocks);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stock addStock(final Stock stock) {
        tradeMap.put(stock, new ArrayList<>());
        return stock;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Stock> findBySymbol(final String symbol) {
        return tradeMap.keySet().stream()
                .filter(stock -> stock.getSymbol().equals(symbol.toUpperCase()))
                .findFirst();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Stock> getAllStocks() {
        return tradeMap.keySet();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addTrade(final String symbol, final Trade trade) throws TradeException {

        final Stock tradedStock = this.findBySymbol(symbol)
                .orElseThrow(() -> new TradeException("Stock not found"));

        final List<Trade> stockTrades = tradeMap.get(tradedStock);

        return stockTrades.add(trade);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Trade> getTrades(final Stock stock) {
        return Optional.ofNullable(tradeMap.get(stock)).orElse(Collections.emptyList());
    }

    /**
     * Initializes the stock market with the stocks provided in application.properties.
     * @param stocks the {@link String} representation of stocks to add.
     */
    private void initializeStocks(final String stocks) {
        final String[] allStocks = stocks.split("\\|");
        for (String stockInfo : allStocks) {
            final String[] stockData = stockInfo.split(",");
            final Stock stock = new Stock(stockData[0], stockData[1], stockData[3], stockData[2], stockData[4]);
            this.addStock(stock);
        }
    }
}
