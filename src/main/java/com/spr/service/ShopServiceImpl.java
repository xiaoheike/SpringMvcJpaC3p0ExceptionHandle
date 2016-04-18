package com.spr.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spr.exception.ShopNotFound;
import com.spr.model.Shop;
import com.spr.repository.JpaShopRepository;

@Service
public class ShopServiceImpl implements ShopService {

    @Resource
    private JpaShopRepository shopRepository;

    @Override
    @Transactional
    public Shop create(Shop shop) {
        Shop createdShop = shop;
        return shopRepository.save(createdShop);
    }

    @Override
    @Transactional
    public Shop findById(int id) {
        return shopRepository.findOne(id);
    }

    @Override
    @Transactional(rollbackFor = ShopNotFound.class)
    public Shop delete(int id) throws ShopNotFound {
        Shop deletedShop;
        try {
            deletedShop = shopRepository.findOne(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ShopNotFound("id 为" + id + " shop 不存在", e);
        }
        shopRepository.delete(deletedShop);
        return deletedShop;
    }

    @Override
    @Transactional
    public List<Shop> findAll() {
        Iterable<Shop> shopIterable = shopRepository.findAll();
        List<Shop> shopList = new ArrayList<Shop>();
        for (Shop shop : shopIterable) {
            shopList.add(shop);
        }
        return shopList;
    }

    @Override
    @Transactional(rollbackFor = ShopNotFound.class)
    public Shop update(Shop shop) throws ShopNotFound {
        Shop updatedShop;
        try {
            updatedShop = shopRepository.findOne(shop.getId());
        } catch (EmptyResultDataAccessException e) {
            throw new ShopNotFound("shop" + shop + " 不存在", e);
        }

        updatedShop.setName(shop.getName());
        updatedShop.setEmplNumber(shop.getEmplNumber());
        return updatedShop;
    }

}
