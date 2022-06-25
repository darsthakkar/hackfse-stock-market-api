package com.hackfse.stock.market.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public class Trade {

    @JsonIgnore
    private Instant timeStamp;

    private Integer quantity;
    private Double tradePrice;
    private TradeType type;

    /**
     * Argument constructor.
     * @param quantity the {@link Integer} quantity to set.
     * @param tradePrice the {@link Double} tradePrice to set.
     * @param type the {@link TradeType} type to set.
     */
    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Trade(@JsonProperty("quantity")final Integer quantity,
                 @JsonProperty("tradePrice")final Double tradePrice,
                 @JsonProperty("type")final TradeType type) {

        this.type = type;
        this.quantity = quantity;
        this.tradePrice = tradePrice;
        this.timeStamp = Instant.now();
    }

    /**
     * Getter for time stamp
     * @return the {@link Instant} time-stamp set.
     */
    public Instant getTimeStamp() {
        return timeStamp;
    }

    /**
     * Getter for quantity
     * @return the {@link Integer} quantity set.
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * Getter for trade price
     * @return the {@link Double} trade-price set.
     */
    public Double getTradePrice() {
        return tradePrice;
    }

    /**
     * Getter for trade type
     * @return the {@link TradeType} trade-type set.
     */
    public TradeType getType() {
        return type;
    }

    /**
     * @see #toString()
     */
    @Override
    public String toString() {
        return "Trade{" +
                "timeStamp=" + timeStamp +
                ", quantity=" + quantity +
                ", tradePrice=" + tradePrice +
                ", type=" + type +
                '}';
    }
}
