package com.spr.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.spr.model.Shop;

@Repository("autowiringShopJpaRepository")
public interface AutowiringShopJpaRepository extends CrudRepository<Shop, Integer>, JpaSpecificationExecutor<Shop> {

}
