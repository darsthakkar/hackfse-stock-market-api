package com.hackfse.stock.market.repository;

import com.hackfse.stock.market.data.Stock;
import com.hackfse.stock.market.data.Trade;
import com.hackfse.stock.market.exception.TradeException;

import java.util.List;
import java.util.Optional;
import java.util.Set;


public interface StockRepository {

    /**
     * Adds a new stock to the system.
     * @param stock the {@link Stock} to add.
     * @return the reference to added stock.
     */
    Stock addStock(Stock stock);

    /**
     * Finds a stock by its symbol.
     * @param symbol the {@link String} symbol to search for.
     * @return to reference to found stock.
     */
    Optional<Stock> findBySymbol(String symbol);

    /**
     * Retrieves all registered stocks.
     * @return the {@link Set} of listed stocks.
     */
    Set<Stock> getAllStocks();

    /**
     * Adds a trade to the listed stock.
     * @param symbol the {@link String} stock to add against.
     * @param trade the {@link Trade} to add.
     * @return flag added to stock.
     */
    boolean addTrade(String symbol, Trade trade) throws TradeException;

    /**
     * Retrieves all the trades happening for the given symbol
     * @param stock the {@link Stock} symbol to match.
     * @return the {@link List} of recorded trades.
     */
    List<Trade> getTrades(final Stock stock);
}
