package com.hackfse.stock.market.exception;

public class StockMarketException extends  Exception {

    /**
     * Argument Constructor.
     * @param message the {@link String} message to set.
     */
    public StockMarketException(final String message) {
        super(message);
    }
}
