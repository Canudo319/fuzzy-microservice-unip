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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.bean.DefaultReturn;
import com.example.demo.bean.StockAtratividade;
import com.example.demo.bean.UserPreferences;
import com.example.demo.entities.ShoppingItem;
import com.example.demo.entities.Stock;
import com.example.demo.repository.StockRepository;
import com.example.demo.util.FuzzyLogicBestItems;
import com.example.demo.util.FuzzyLogicCheppestItems;
import com.example.demo.util.FuzzyLogicNearestSupplier;

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
	
	@GetMapping("/stock/bestItens")
	public ResponseEntity<List<StockAtratividade>> getBestItens(
				@RequestParam(value = "item") Long item,
				@RequestParam(value = "latitude", defaultValue = "0.0") Double latitude,
				@RequestParam(value = "longitude", defaultValue = "0.0") Double longitude
			){
		UserPreferences preferences = new UserPreferences(item, latitude, longitude);
		
		ResponseEntity<List<Stock>> stocks = getStockByItem(preferences.getPreferedItem());
		
		if(stocks.getStatusCode() != HttpStatus.OK) {
			return new ResponseEntity<>(null, stocks.getStatusCode());
		}
		
		List<StockAtratividade> stockAtratividade = FuzzyLogicBestItems.fuzzyfy(preferences, stocks.getBody());
		return new ResponseEntity<>(stockAtratividade, HttpStatus.OK);
	}
	
	@GetMapping("/stock/cheapestItems")
	public ResponseEntity<List<StockAtratividade>> getCheapestItems(
			@RequestParam(value = "item") Long item
		){
		UserPreferences preferences = new UserPreferences(item, 0.0, 0.0);
		
		ResponseEntity<List<Stock>> stocks = getStockByItem(preferences.getPreferedItem());
		
		if(stocks.getStatusCode() != HttpStatus.OK) {
			return new ResponseEntity<>(null, stocks.getStatusCode());
		}
		
		List<StockAtratividade> stockAtratividade = FuzzyLogicCheppestItems.fuzzyfy(preferences, stocks.getBody());
		return new ResponseEntity<>(stockAtratividade, HttpStatus.OK);
	}
	
	@GetMapping("/stock/nearestSupplier")
	public ResponseEntity<List<StockAtratividade>> getNearestSupplier(
			@RequestParam(value = "item") Long item,
			@RequestParam(value = "latitude", defaultValue = "0.0") Double latitude,
			@RequestParam(value = "longitude", defaultValue = "0.0") Double longitude
		){
		UserPreferences preferences = new UserPreferences(item, latitude, longitude);
		
		ResponseEntity<List<Stock>> stocks = getStockByItem(preferences.getPreferedItem());
		
		if(stocks.getStatusCode() != HttpStatus.OK) {
			return new ResponseEntity<>(null, stocks.getStatusCode());
		}
		
		List<StockAtratividade> stockAtratividade = FuzzyLogicNearestSupplier.fuzzyfy(preferences, stocks.getBody());
		return new ResponseEntity<>(stockAtratividade, HttpStatus.OK);
	}
	
	@GetMapping("/stock/buy/{id}")
	public ResponseEntity<DefaultReturn<ShoppingItem>> buyStock(@PathVariable Long id){
		Optional<Stock> stock = repository.findById(id);
		if(!stock.isPresent()) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		if(stock.get().getStock() <= 0) {
			return new ResponseEntity<>(
					new DefaultReturn<>("Item não disponivel no estoque", null),
					HttpStatus.METHOD_NOT_ALLOWED
				);			
		}
		
		ShoppingItem item = stock.get().getShoppingItem();
		
		stock.get().setStock(stock.get().getStock() - 1);
		repository.save(stock.get());
		return new ResponseEntity<>(
				new DefaultReturn<>("Sucesso", item),
				HttpStatus.OK
			);
	}
	
	@PostMapping("/stocks")
	public List<Stock> saveStocks(@RequestBody List<Stock> suppliers) {
		List<Stock> itensSalvos = new ArrayList<>();
		
		suppliers.forEach(supplier ->{
			Optional<Stock> optional = repository.findDuplicate(
					supplier.getShoppingItem().getId(),
					supplier.getSupplier().getId()
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
