package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Stock;
import com.example.demo.repository.StockRepository;

import lombok.AllArgsConstructor;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@AllArgsConstructor
public class StockController {
	
	@Autowired
	StockRepository repository;
	
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
	
	
	@PostMapping("/stocks")
	public List<Stock> saveStocks(@RequestBody List<Stock> suppliers) {
		List<Stock> itensSalvos = new ArrayList<>();
		
		suppliers.forEach(supplier ->{
			itensSalvos.add(repository.save(supplier));
		});
		
		return itensSalvos;
	}
}
