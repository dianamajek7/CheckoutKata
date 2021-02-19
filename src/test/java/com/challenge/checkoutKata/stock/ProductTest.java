package com.challenge.checkoutKata.stock;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ProductTest {
    @Test
    public void validateStockItem_ContainsAUniqueCode() {
        //given
        Product product = new Product('A', new BigDecimal(50));
        Product product2 = new Product('B', new BigDecimal(30));
        //then
        assertNotEquals(product.getName(), product2.getName());
    }

    @Test
    public void validateStockItem_Code() {
        //given
        Product product = new Product('A', new BigDecimal(50));
        //then
        assertEquals('A', product.getName());
    }

    @Test
    public void validateStockItem_UnitPrice() {
        //given
        Product product = new Product('A', new BigDecimal(50));
        //then
        assertEquals(new BigDecimal(50), product.getUnitPrice());
    }

    @Test
    public void validateProductEquals() {
        //given
        Product product = new Product('A', new BigDecimal(50));
        Product product2 = new Product('A', new BigDecimal(50));
        //then
        assertEquals(product2, product);
    }

    @Test
    public void validateProductNotEqual() {
        //given
        Product product = new Product('A', new BigDecimal(50));
        Product product2 = new Product('A', new BigDecimal(80));
        //then
        assertNotEquals(product2, product);
    }

    @Test
    public void validateProductHashCodeEquality() {
        //given
        Product product = new Product('A', new BigDecimal(50));
        Product product2 = new Product('A', new BigDecimal(50));
        //then
        assertEquals(product.hashCode(), product2.hashCode());
    }

    @Test
    public void validateIdenticalProductName_WithUniqueUnitPrice_HashCode() {
        //given
        Product product = new Product('A', new BigDecimal(50));
        Product product2 = new Product('A', new BigDecimal(20));
        //then
        assertNotEquals(product.hashCode(), product2.hashCode());
    }

    @Test
    public void validateDifferentProduct_HashCode() {
        //given
        Product product = new Product('A', new BigDecimal(50));
        Product product2 = new Product('C', new BigDecimal(80));
        //then
        assertNotEquals(product.hashCode(), product2.hashCode());
    }
}