package com.challenge.checkoutKata.stock;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {
    private final char name;
    private final BigDecimal unitPrice;

    public Product(char name, BigDecimal unitPrice) {
        this.name = name;
        this.unitPrice = unitPrice;
    }

    public char getName() { return name; }
    public BigDecimal getUnitPrice() { return unitPrice; }

    //using getters, and never field references for equals() and hashCode()
        // if using field reference it is very much likely that the id field is lazy loaded
        //meaning one might be zero or null, resulting in incorrect behavior.
    @Override
    public boolean equals(Object o) {
        if(o == null) return false;
        if (this == o) return true;
        if (!(o instanceof Product)) return false;

        Product product = (Product) o;
        return this.getName() == product.getName() &&
                this.getUnitPrice().equals(product.getUnitPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getName(), this.getUnitPrice());   //returns exactly the same hashing as IntelliJ default hashCode
    }
}
