package com.spr.repository;

import org.springframework.dao.EmptyResultDataAccessException;

import com.spr.model.Shop;

public interface ShopRepository {
    public Iterable<Shop> findAll();

    public Shop save(Shop shop);

    public Shop findOne(int id) throws EmptyResultDataAccessException;

    public void delete(Shop deletedShop);
}
