package com.example.demo.bean;

import com.example.demo.entities.Stock;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockAtratividade {
	Stock stock;
	Double atratividade;
	String atrativadadeDescricao;
	String color;
	
	public Double getPrice() {
		return this.stock.getPrice();
	}
}
