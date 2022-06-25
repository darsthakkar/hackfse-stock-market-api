package com.hackfse.stock.market.service;

import com.hackfse.stock.market.exception.StockMarketException;

public interface StockService {

    /**
     * Calculates the dividend yield for the given stock for the price provided.
     * @param price the {@link Double} price.
     * @param symbol the {@link String} stock symbol.
     * @return the {@link Double} dividend yield value
     * @throws StockMarketException
     */
    Double calculateDividendYield(Double price, String symbol) throws StockMarketException;

    /**
     * Calculates the PE ratio for the given stock and the price provided.
     * @param price the {@link Double} price.
     * @param symbol the {@link String} stock symbol.
     * @return the {@link Double} PE ratio.
     * @throws StockMarketException
     */
    Double getPERatio(Double price, String symbol) throws StockMarketException;

    /**
     * Retrieves the Volume Weighted value for the given stock.
     * @param symbol the {@link String} stock symbol.
     * @return the {@link Double} volume weighted stock.
     */
    Double getVolumeWeightedStock(String symbol) throws StockMarketException;

    /**
     * Calculates the geometric mean for the entire stock index.
     * @return the {@link Double} geometric mean.
     */
    Double calculateGeometricMean();
}
