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
public class FuzzyLogicBestItems {
	
	public static List<StockAtratividade> fuzzyfy(UserPreferences preferences,
			List<Stock> stocks){
		
		ArrayList<StockAtratividade> stocksFuzzyficados = new ArrayList<>();
		
		Double precoMedio = stocks.stream()
				.map(Stock::getPrice)
				.reduce(0.0, Double::sum) / stocks.size();
		
		for(Stock stock : stocks) {
			Supplier supplier = stock.getSupplier();
			
			CoeficienteDistancia cd = DistanciaSupplierUser.calcular(
					preferences.getLatitude(),
					preferences.getLongitude(),
					supplier
				);
			
			CoeficientePreco cp = CoeficientePreco.retornaCoeficientePreco(stock.getPrice(), precoMedio);
			
			CoeficienteAtratividade ca = CoeficienteAtratividade.retornaCoeficienteAtratividade(cd, cp);
			
			stocksFuzzyficados.add(new StockAtratividade(
					stock,
					ca.getWeight(),
					ca.toString(),
					ca.getColor()
				));
		}
		
		Collections.sort(stocksFuzzyficados, Comparator.comparing(StockAtratividade::getAtratividade).reversed()
				.thenComparing(StockAtratividade::getPrice));
		
		return stocksFuzzyficados;
	}
}
