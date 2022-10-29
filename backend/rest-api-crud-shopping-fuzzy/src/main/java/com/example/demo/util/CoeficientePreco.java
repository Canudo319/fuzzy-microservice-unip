package com.example.demo.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum CoeficientePreco{
	MUITO_BARATO(2.99, 1.0, "DARK_GREEN"),
	BARATO(5.99, 0.7, "GREEN"),
	RAZOAVEL(10.99, 0.5, "YELLOW"),
	CARO(15.99, 0.3, "ORANGE"),
	MUITO_CARO(25.99, 0.1, "RED");
	
	@Getter
	private final Double preco;
	@Getter
	private final Double weight;
	@Getter
	private final String color;
	
	public static CoeficientePreco retornaCoeficientePreco(Double preco) {
		if(preco <= MUITO_BARATO.preco) {
			return MUITO_BARATO;
		}else if(preco > MUITO_BARATO.preco && preco <= BARATO.preco) {
			return BARATO;
		} else if (preco > BARATO.preco && preco <= RAZOAVEL.preco) {
			return RAZOAVEL;
		} else if (preco > RAZOAVEL.preco && preco <= CARO.preco) {
			return CARO;
		} else {
			return MUITO_CARO;
		}
	}
	
	@Override
	public String toString() {
		return switch (this) {
			case MUITO_BARATO -> "MUITO BARATO";
			case BARATO -> "BARATO";
			case RAZOAVEL -> "RAZOÁVEL";
			case CARO -> "CARO";
			case MUITO_CARO -> "MUITO CARO";
		};
	}
}
