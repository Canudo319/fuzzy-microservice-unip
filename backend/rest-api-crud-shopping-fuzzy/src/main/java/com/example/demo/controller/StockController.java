package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.bean.DefaultReturn;
import com.example.demo.entities.ShoppingItem;
import com.example.demo.entities.Stock;
import com.example.demo.repository.ShoppingItemRepository;
import com.example.demo.repository.StockRepository;

import lombok.AllArgsConstructor;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@AllArgsConstructor
public class StockController {
	
	@Autowired
	StockRepository repository;
	@Autowired
	ShoppingItemRepository repositoryItens;
	
	@GetMapping("/stock")
	public List<Stock> getAllStocks(){
		return repository.findAll();
	}
	
	@GetMapping("/stock/item/{itemId}")
	public ResponseEntity<List<Stock>> getStockByItem(@PathVariable Long itemId){
		List<Stock> stock = repository.findByItemId(itemId);
		if(stock.isEmpty()) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(stock, HttpStatus.OK);
	}
	
	@GetMapping("/stock/supplier/{supplierId}")
	public ResponseEntity<List<Stock>> getStockBySupplier(@PathVariable Long supplierId){
		List<Stock> stock = repository.findBySupplierId(supplierId);
		if(stock.isEmpty()) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(stock, HttpStatus.OK);
	}
	
	@GetMapping("/stock/buy/{id}")
	public ResponseEntity<DefaultReturn> buyStock(@PathVariable Long id){
		Optional<Stock> stock = repository.findById(id);
		if(!stock.isPresent()) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		if(stock.get().getStock() <= 0) {
			return new ResponseEntity<>(
					new DefaultReturn("Item não disponivel no estoque", null),
					HttpStatus.METHOD_NOT_ALLOWED
				);			
		}
		
		Optional<ShoppingItem> item = repositoryItens.findById(stock.get().getShoppingItemId());
		if(!item.isPresent()) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		
		stock.get().setStock(stock.get().getStock() - 1);
		repository.save(stock.get());
		return new ResponseEntity<>(
				new DefaultReturn("Sucesso", item.get()),
				HttpStatus.OK
			);
	}
	
	@PostMapping("/stocks")
	public List<Stock> saveStocks(@RequestBody List<Stock> suppliers) {
		List<Stock> itensSalvos = new ArrayList<>();
		
		suppliers.forEach(supplier ->{
			Optional<Stock> optional = repository.findDuplicate(
					supplier.getShoppingItemId(),
					supplier.getSupplierId()
				);
			if(optional.isPresent()) {
				optional.get().setStock(optional.get().getStock() + supplier.getStock());
			}else {				
				itensSalvos.add(repository.save(supplier));
			}
		});
		
		return itensSalvos;
	}
}
