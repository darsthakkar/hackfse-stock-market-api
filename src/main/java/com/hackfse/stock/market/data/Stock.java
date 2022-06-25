package com.hackfse.stock.market.data;

import java.util.Objects;

public class Stock {

    private String symbol;
    private StockType type;
    private Double fixedDividend;
    private Double lastDividend;
    private Integer parValue;

    /**
     * Argument constructor.
     * @param symbol the {@link String} symbol to set.
     * @param type the {@link String} type to set.
     * @param fixedDividend the {@link String} fixedDividend to set.
     * @param lastDividend the {@link String} lastDividend to set.
     * @param parValue the {@link String} parValue to set.
     */
    public Stock(final String symbol, final String type,
                 final String fixedDividend, final String lastDividend,
                 final String parValue) {

        this.symbol = symbol;
        this.type = StockType.valueOf(type);
        this.fixedDividend = Double.valueOf(fixedDividend);
        this.lastDividend = Double.valueOf(lastDividend);
        this.parValue = Integer.valueOf(parValue);
    }

    /**
     * Getter for symbol
     * @return the {@link String} symbol set.
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Getter for type
     * @return the {@link StockType} type set.
     */
    public StockType getType() {
        return type;
    }

    /**
     * Getter for fixed dividend
     * @return the {@link Double} fixed-dividend set.
     */
    public Double getFixedDividend() {
        return fixedDividend;
    }

    /**
     * Getter for last dividend
     * @return the {@link Double} last-dividend set.
     */
    public Double getLastDividend() {
        return lastDividend;
    }

    /**
     * Getter for par value
     * @return the {@link Integer} par-value set.
     */
    public Integer getParValue() {
        return parValue;
    }

    /**
     * @see #equals(Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Stock stock = (Stock) obj;
        return symbol.equals(stock.symbol);
    }

    /**
     * @see #hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hash(symbol);
    }

    /**
     * @see #toString()
     */
    @Override
    public String toString() {
        return "Stock{" +
                "symbol='" + symbol + '\'' +
                ", type=" + type +
                ", fixedDividend=" + fixedDividend +
                ", lastDividend=" + lastDividend +
                ", parValue=" + parValue +
                '}';
    }
}
