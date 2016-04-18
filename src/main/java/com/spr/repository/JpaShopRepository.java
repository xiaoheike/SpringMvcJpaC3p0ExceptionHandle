package com.spr.repository;

import javax.annotation.Resource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.spr.model.Shop;

@Repository("shopRepository")
public class JpaShopRepository implements ShopRepository {
    @Resource(name = "autowiringShopJpaRepository")
    AutowiringShopJpaRepository autowiringShopJpaRepository;

    public Iterable<Shop> findAll() {
        return autowiringShopJpaRepository.findAll();
    }

    public Shop save(Shop shop) {
        autowiringShopJpaRepository.save(shop);
        return null;
    }

    public Shop findOne(int id) throws EmptyResultDataAccessException {
        Shop findShop = autowiringShopJpaRepository.findOne(id);
        if (findShop == null) {
            throw new EmptyResultDataAccessException(String.format("No %s entity with id %s exists!", Shop.class, id), 1);
        }
        return null;
    }

    public void delete(Shop deletedShop) {
        autowiringShopJpaRepository.delete(deletedShop);
    }

}
