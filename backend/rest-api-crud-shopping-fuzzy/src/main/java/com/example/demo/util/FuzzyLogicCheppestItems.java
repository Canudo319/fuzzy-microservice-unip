package com.example.demo.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.example.demo.bean.StockAtratividade;
import com.example.demo.bean.UserPreferences;
import com.example.demo.entities.Stock;

//Classe de logica fuzzy que busca os melhores itens de acordo com as especificações do usuario
public class FuzzyLogicCheppestItems {
	
	public static List<StockAtratividade> fuzzyfy(UserPreferences preferences,
			List<Stock> stocks){
		
		ArrayList<StockAtratividade> stocksFuzzyficados = new ArrayList<>();
		
		for(Stock stock : stocks) {
			CoeficientePreco cp = CoeficientePreco.retornaCoeficientePreco(stock.getPrice());
			
			stocksFuzzyficados.add(new StockAtratividade(
					stock,
					cp.getWeight(),
					cp.toString(),
					cp.getColor()
				));
		}
		
		Collections.sort(stocksFuzzyficados, Comparator.comparing(StockAtratividade::getAtratividade).reversed()
				.thenComparing(StockAtratividade::getPrice));
		
		return stocksFuzzyficados;
	}
	
}
