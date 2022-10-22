package com.example.demo.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum CoeficienteAtratividade{
	MUITO_ATRATIVO(0.9),
	ATRATIVO(0.7),
	MEDIANO(0.5),
	NAO_ATRATIVO(0.3),
	NADA_ATRATIVO(0.1);
	
	@Getter
	private final Double weight;
	
	public static CoeficienteAtratividade retornaCoeficienteAtratividade(CoeficienteDistancia cd, CoeficientePreco cp) {
		Double cdWeight = cd.getWeight();
		Double cpWeight = cp.getWeight();
		
		Double realWeight = cdWeight <= cpWeight ? cdWeight : cpWeight;
		
		if(realWeight >= MUITO_ATRATIVO.getWeight()) {
			return MUITO_ATRATIVO;
		}else if(realWeight < MUITO_ATRATIVO.getWeight() && realWeight >= ATRATIVO.getWeight()) {
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
