package com.example.demo.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.demo.bean.DefaultReturn;
import com.example.demo.bean.StockAtratividade;
import com.example.demo.bean.UserPreferences;
import com.example.demo.entities.Stock;
import com.example.demo.entities.Supplier;

//Classe de logica fuzzy que busca os melhores itens de acordo com as especificações do usuario
public class FuzzyLogicBestItems {
	
	public static DefaultReturn<List<StockAtratividade>> fuzzyfy(UserPreferences preferences,
			List<Stock> stocks,
			List<Supplier> suppliers){
		
		ArrayList<StockAtratividade> stocksFuzzyficados = new ArrayList<>();
		
		for(Stock stock : stocks) {
			Optional<Supplier> supplier = suppliers.stream()
					.filter(s -> s.getId().equals(stock.getSupplierId())).findFirst();
			if(!supplier.isPresent()) {
				new DefaultReturn<>("Erro: Supplier não cadastrado", null);
			}
			
			CoeficienteDistancia cd = calcularDiferencaSupplierUser(
					preferences.getLatitude(),
					preferences.getLongitude(),
					supplier.get()
				);
			
			CoeficientePreco cp = CoeficientePreco.retornaCoeficientePreco(stock.getPrice());
			
			CoeficienteAtratividade ca = CoeficienteAtratividade.retornaCoeficienteAtratividade(cd, cp);
			
			stocksFuzzyficados.add(new StockAtratividade(
					stock,
					ca.getWeight(),
					ca.toString()
				));
		}
		
		
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
