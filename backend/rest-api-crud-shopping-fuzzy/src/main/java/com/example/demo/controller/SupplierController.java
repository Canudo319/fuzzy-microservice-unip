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

import com.example.demo.entities.Supplier;
import com.example.demo.repository.SupplierRepository;

import lombok.AllArgsConstructor;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@AllArgsConstructor
public class SupplierController {
	
	@Autowired
	SupplierRepository repository;
	
	@GetMapping("/supplier")
	public List<Supplier> getAllSupliers(){
		return repository.findAll();
	}
	
	@GetMapping("/supplier/{id}")
	public ResponseEntity<Supplier> getSupplierById(@PathVariable Long id) {
		Optional<Supplier> supplier = repository.findById(id);
		if(!supplier.isPresent()) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(supplier.get(), HttpStatus.OK);
	}
	
	@PostMapping("/suppliers")
	public List<Supplier> saveShoppingItens(@RequestBody List<Supplier> suppliers) {
		List<Supplier> itensSalvos = new ArrayList<>();
		
		suppliers.forEach(supplier ->{
			itensSalvos.add(repository.save(supplier));
		});
		
		return itensSalvos;
	}
	
	@PutMapping("/supplier/{id}")
	public Supplier putShoppingItem(
			@RequestBody Supplier newSupplier,
			@PathVariable Long id) {
		return repository.findById(id)
				.map(supplier -> {
					return updateItem(supplier, newSupplier);
				})
				.orElseGet(() -> {
					newSupplier.setId(id);
					return repository.save(newSupplier);
				});
		
	}
	
	@PatchMapping("/supplier/{id}")
	public ResponseEntity<Supplier> patchShoppingItem(
			@RequestBody Supplier newSupplier,
			@PathVariable Long id) {
		
		Optional<Supplier> optional = repository.findById(id);
		
		if(!optional.isPresent()) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(optional
				.map(supplier -> {
					return updateItem(supplier, newSupplier);
				}).get(),
				HttpStatus.OK);
		
	}
	
	@DeleteMapping("/supplier/{id}")
	public ResponseEntity<Void> deleteShoppingItem(@PathVariable Long id) {
		if(!repository.findById(id).isPresent()) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		repository.deleteById(id);
		return new ResponseEntity<>(null, HttpStatus.OK);
	}
	
	private Supplier updateItem(Supplier supplier, Supplier newSupplier) {
		supplier.setName(newSupplier.getName());
		supplier.setLatitude(newSupplier.getLatitude());
		supplier.setLongitude(newSupplier.getLongitude());
		return repository.save(supplier);
	}
}
