package com.example.demo.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum CoeficientePreco{
	MUITO_BARATO(2.99, 1.0),
	BARATO(5.99, 0.7),
	RAZOAVEL(10.99, 0.5),
	CARO(15.99, 0.3),
	MUITO_CARO(25.99, 0.1);
	
	@Getter
	private final Double preco;
	@Getter
	private final Double weight;
	
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
}
