package com.example.demo.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.example.demo.bean.DefaultReturn;
import com.example.demo.bean.StockAtratividade;
import com.example.demo.bean.UserPreferences;
import com.example.demo.entities.Stock;
import com.example.demo.entities.Supplier;

//Classe de logica fuzzy que busca os melhores itens de acordo com as especificações do usuario
public class FuzzyLogicBestItems {
	
	public static DefaultReturn<List<StockAtratividade>> fuzzyfy(UserPreferences preferences,
			List<Stock> stocks){
		
		ArrayList<StockAtratividade> stocksFuzzyficados = new ArrayList<>();
		
		for(Stock stock : stocks) {
			Supplier supplier = stock.getSupplier();
			
			CoeficienteDistancia cd = calcularDiferencaSupplierUser(
					preferences.getLatitude(),
					preferences.getLongitude(),
					supplier
				);
			
			CoeficientePreco cp = CoeficientePreco.retornaCoeficientePreco(stock.getPrice());
			
			CoeficienteAtratividade ca = CoeficienteAtratividade.retornaCoeficienteAtratividade(cd, cp);
			
			stocksFuzzyficados.add(new StockAtratividade(
					stock,
					ca.getWeight(),
					ca.toString()
				));
		}
		
		Collections.sort(stocksFuzzyficados, Comparator.comparing(StockAtratividade::getAtratividade).reversed()
				.thenComparing(StockAtratividade::getPrice));
		
		return new DefaultReturn<>("Sucesso", stocksFuzzyficados);
	}
	
	private static CoeficienteDistancia calcularDiferencaSupplierUser(
			Double latitude, Double longitude, Supplier supplier) {
		Double x1 = latitude;
		Double y1 = longitude;
		Double x2 = supplier.getLatitude();
		Double y2 = supplier.getLongitude();
		
		Double distancia = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y1 - y2, 2));
		return CoeficienteDistancia.retornaCoeficienteReal(distancia);
	}
}
