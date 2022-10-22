package com.example.demo.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum CoeficienteDistancia{
	PERTO(15, 1.0),
	MEDIANA(50, 0.7),
	LONGE(75, 0.4),
	MUITO_LONGE(100, 0.1);
	
	@Getter
	private final Integer distance;
	@Getter
	private final Double weight;
	
	public static CoeficienteDistancia retornaCoeficienteReal(Double distancia) {
		if(distancia <= PERTO.getDistance()) {
			return PERTO;
		}else if(distancia > PERTO.getDistance() && distancia <= MEDIANA.getDistance()) {
			return MEDIANA;
		}else if(distancia > MEDIANA.getDistance() && distancia <= LONGE.getDistance()) {
			return LONGE;
		}else if(distancia > LONGE.getDistance() && distancia <= MUITO_LONGE.getDistance()) {
			return LONGE;
		}else {
			return MUITO_LONGE;
		}
	}
}
