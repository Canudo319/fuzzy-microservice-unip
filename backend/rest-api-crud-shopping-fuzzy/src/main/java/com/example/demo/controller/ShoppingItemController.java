package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/shoppingItem")
	public List<ShoppingItem> getAllShoppingItens(){
		return repository.findAll();
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/shoppingItem/{id}")
	public ResponseEntity<ShoppingItem> getShoppingItemById(@PathVariable Long id) {
		Optional<ShoppingItem> shoppingItem = repository.findById(id);
		if(!shoppingItem.isPresent()) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(shoppingItem.get(), HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/shoppingItems")
	public List<ShoppingItem> saveShoppingItens(@RequestBody List<ShoppingItem> shoppingItens) {
		List<ShoppingItem> itensSalvos = new ArrayList<>();
		
		shoppingItens.forEach(shoppingItem ->{
			itensSalvos.add(repository.save(shoppingItem));
		});
		
		return itensSalvos;
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PutMapping("/shoppingItem/{id}")
	public ShoppingItem putShoppingItem(
			@RequestBody ShoppingItem newShoppingItem,
			@PathVariable Long id) {
		return repository.findById(id)
				.map(shoppingItem -> {
					return updateItem(shoppingItem, newShoppingItem);
				})
				.orElseGet(() -> {
					newShoppingItem.setId(id);
					return repository.save(newShoppingItem);
				});
		
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PatchMapping("/shoppingItem/{id}")
	public ResponseEntity<ShoppingItem> patchShoppingItem(
			@RequestBody ShoppingItem newShoppingItem,
			@PathVariable Long id) {
		
		Optional<ShoppingItem> optional = repository.findById(id);
		
		if(!optional.isPresent()) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(optional
				.map(shoppingItem -> {
					return updateItem(shoppingItem, newShoppingItem);
				}).get(),
				HttpStatus.OK);
		
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@DeleteMapping("/shoppingItem/{id}")
	public ResponseEntity<Void> deleteShoppingItem(@PathVariable Long id) {
		if(!repository.findById(id).isPresent()) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		repository.deleteById(id);
		return new ResponseEntity<>(null, HttpStatus.OK);
	}
	
	private ShoppingItem updateItem(ShoppingItem shoppingItem, ShoppingItem newShoppingItem) {
		shoppingItem.setName(newShoppingItem.getName());
		shoppingItem.setBrand(newShoppingItem.getBrand());
		shoppingItem.setType(newShoppingItem.getType());
		shoppingItem.setPrice(newShoppingItem.getPrice());
		return repository.save(shoppingItem);
	}
	
}
