package com.example.demo.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.example.demo.bean.StockAtratividade;
import com.example.demo.bean.UserPreferences;
import com.example.demo.entities.Stock;
import com.example.demo.entities.Supplier;

//Classe de logica fuzzy que busca os melhores itens de acordo com as especificações do usuario
public class FuzzyLogicNearestSupplier {
	
	public static List<StockAtratividade> fuzzyfy(UserPreferences preferences,
			List<Stock> stocks){
		
		ArrayList<StockAtratividade> stocksFuzzyficados = new ArrayList<>();
		
		for(Stock stock : stocks) {
			Supplier supplier = stock.getSupplier();
			
			CoeficienteDistancia cd = DistanciaSupplierUser.calcular(
					preferences.getLatitude(),
					preferences.getLongitude(),
					supplier
				);
			
			stocksFuzzyficados.add(new StockAtratividade(
					stock,
					cd.getWeight(),
					cd.toString()
				));
		}
		
		Collections.sort(stocksFuzzyficados, Comparator.comparing(StockAtratividade::getAtratividade).reversed()
				.thenComparing(StockAtratividade::getPrice));
		
		return stocksFuzzyficados;
	}
	
}
