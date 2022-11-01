package com.example.demo.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum CoeficienteAtratividade{
	MUITO_ATRATIVO(0.9, "DARK_GREEN"),
	ATRATIVO(0.7, "GREEN"),
	MEDIANO(0.5, "YELLOW"),
	NAO_ATRATIVO(0.3, "ORANGE"),
	NADA_ATRATIVO(0.1, "RED");
	
	@Getter
	private final Double weight;
	@Getter
	private final String color;
	
	public static CoeficienteAtratividade retornaCoeficienteAtratividade(CoeficienteDistancia cd, CoeficientePreco cp) {
		Double cdWeight = cd.getWeight();
		Double cpWeight = cp.getWeight();
		Double realWeight = Math.max(cdWeight, cpWeight);
		
		//Somente é muito atrativo se ambos os coeficientes estiverem no máximo
		if(cd == CoeficienteDistancia.PERTO && cp == CoeficientePreco.MUITO_BARATO) {
			return MUITO_ATRATIVO;
		}else if(cd == CoeficienteDistancia.MUITO_LONGE && cp == CoeficientePreco.MUITO_CARO){
			return NADA_ATRATIVO;
		}
		
		if(realWeight >=MUITO_ATRATIVO.getWeight() ||
				realWeight < MUITO_ATRATIVO.getWeight() && realWeight >= ATRATIVO.getWeight()) {
			return ATRATIVO;
		}else if(realWeight < ATRATIVO.getWeight() && realWeight >= MEDIANO.getWeight()) {
			return MEDIANO;
		}else if(realWeight < MEDIANO.getWeight() && realWeight >= NAO_ATRATIVO.getWeight()) {
			return NAO_ATRATIVO;
		}else {
			return NADA_ATRATIVO;
		}
	}
	
	@Override
	public String toString() {
		return switch (this) {
			case MUITO_ATRATIVO -> "MUITO ATRATIVO";
			case ATRATIVO -> "ATRATIVO";
			case MEDIANO -> "MEDIANO";
			case NADA_ATRATIVO -> "NADA ATRATIVO";
			case NAO_ATRATIVO -> "NÃO ATRATIVO";
		};
	}
}
