package com.example.demo.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum CoeficientePreco{
	MUITO_BARATO(10.0, 1.0, "DARK_GREEN"),
	BARATO(5.0, 0.7, "GREEN"),
	NA_MEDIA(0.0, 0.5, "YELLOW"),
	CARO(-5.0, 0.3, "ORANGE"),
	MUITO_CARO(-10.0, 0.1, "RED");
	
	@Getter
	private final Double porcent;
	@Getter
	private final Double weight;
	@Getter
	private final String color;
	
	public static CoeficientePreco retornaCoeficientePreco(Double preco, Double media) {
		Double diferencaPorcent = ((media - preco) / media) * 100;
		
		if(diferencaPorcent >= MUITO_BARATO.porcent) {
			return MUITO_BARATO;
		}else if(diferencaPorcent < MUITO_BARATO.porcent && diferencaPorcent >= BARATO.porcent) {
			return BARATO;
		}else if(diferencaPorcent < BARATO.porcent && diferencaPorcent >= NA_MEDIA.porcent) {
			return NA_MEDIA;
		}else if(diferencaPorcent < NA_MEDIA.porcent && diferencaPorcent >= CARO.porcent) {
			return CARO;
		}else {
			return MUITO_CARO;
		}
	}
	
	@Override
	public String toString() {
		return switch (this) {
			case MUITO_BARATO -> "MUITO BARATO";
			case BARATO -> "BARATO";
			case NA_MEDIA -> "NA MÉDIA";
			case CARO -> "CARO";
			case MUITO_CARO -> "MUITO CARO";
		};
	}
}
