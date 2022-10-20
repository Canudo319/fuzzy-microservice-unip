package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Stock;

public interface StockRepository extends JpaRepository<Stock, Long>{
	default List<Stock> findByItemId(Long itemId){
		return this.findAll().stream()
				.filter(s -> s.getShoppingItemId().equals(itemId)).toList();
	}
	default List<Stock> findBySupplierId(Long supplierId){
		return this.findAll().stream()
				.filter(s -> s.getSupplierId().equals(supplierId)).toList();
	}
	
	default Optional<Stock> findDuplicate(Long itemId, Long supplierId){
		return this.findAll().stream()
				.filter(s -> s.getShoppingItemId().equals(itemId)
						&& s.getSupplierId().equals(supplierId)).findFirst();
	}
}
