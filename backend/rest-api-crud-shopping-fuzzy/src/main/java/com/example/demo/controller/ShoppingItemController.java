package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.ShoppingItem;
import com.example.demo.repository.ShoppingItemRepository;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class ShoppingItemController {
	
	@Autowired
	ShoppingItemRepository repository;
	
	@GetMapping("/shoppingItem")
	public List<ShoppingItem> getAllShoppingItens(){
		return repository.findAll();
	}
	
	@GetMapping("/shoppingItem/{id}")
	public ShoppingItem getShoppingItemById(@PathVariable Long id) {
		return repository.findById(id).get();
	}
	
	@PostMapping("/shoppingItem")
	public ShoppingItem saveShoppingItem(@RequestBody ShoppingItem shoppingItem) {
		return repository.save(shoppingItem);
	}
	
	@PutMapping("/shoppingItem/{id}")
	public ShoppingItem putShoppingItem(
			@RequestBody ShoppingItem newShoppingItem,
			@PathVariable Long id) {
		return repository.findById(id)
				.map(shoppingItem -> {
					shoppingItem.setName(newShoppingItem.getName());
					shoppingItem.setBrand(newShoppingItem.getBrand());
					shoppingItem.setType(newShoppingItem.getType());
					shoppingItem.setPrice(newShoppingItem.getPrice());
					return repository.save(shoppingItem);
				})
				.orElseGet(() -> {
					newShoppingItem.setId(id);
					return repository.save(newShoppingItem);
				});
		
	}
	
	@DeleteMapping("/shoppingItem/{id}")
	public void deleteShoppingItem(@PathVariable Long id) {
		repository.deleteById(id);
	}
	
}
