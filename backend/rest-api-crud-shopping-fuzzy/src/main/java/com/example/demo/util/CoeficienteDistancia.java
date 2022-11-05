package com.example.demo.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum CoeficienteDistancia{
	PERTO(30, 1.0, "#00630c"),
	MEDIANA(100, 0.7, "#26c739"),
	LONGE(250, 0.4, "#c1c92a"),
	MUITO_LONGE(500, 0.1, "#bf0000");
	
	@Getter
	private final Integer distance;
	@Getter
	private final Double weight;
	@Getter
	private final String color;
	
	public static CoeficienteDistancia retornaCoeficienteReal(Double distancia) {
		if(distancia <= PERTO.getDistance()) {
			return PERTO;
		}else if(distancia > PERTO.getDistance() && distancia <= MEDIANA.getDistance()) {
			return MEDIANA;
		}else if(distancia > MEDIANA.getDistance() && distancia <= LONGE.getDistance()) {
			return LONGE;
		}else {
			return MUITO_LONGE;
		}
	}
	
	@Override
	public String toString() {
		return switch (this) {
		case PERTO -> "PERTO";
		case MEDIANA -> "MEDIANA";
		case LONGE -> "LONGE";
		case MUITO_LONGE -> "MUITO LONGE";
		};
	}
}
