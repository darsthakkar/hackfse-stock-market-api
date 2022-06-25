package com.hackfse.stock.market.service;

import com.hackfse.stock.market.data.Stock;
import com.hackfse.stock.market.data.Trade;
import com.hackfse.stock.market.exception.TradeException;

import java.util.List;

public interface TradeService  {

    /**
     * Adds a trade against a stock
     * @param stockSymbol the {@link String} stock to add against.
     * @param trade the {@link Trade} to add.
     */
    void tradeStock(String stockSymbol, Trade trade) throws TradeException;

    /**
     * Returns all the trades against a stock Symbol
     * @param stock the {@link Stock} to get stock for.
     * @return the {@link List} of valid trades.
     */
    List<Trade> getTrades(Stock stock);

    /**
     * Returns all the trades in the fifteen minutes window.
     * @param stock the {@link Stock} to get trades for.
     * @return the {@link List} of trades.
     */
    List<Trade> getLatestTrades(Stock stock);
}
