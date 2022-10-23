package com.example.demo.util;

import com.example.demo.entities.Supplier;

public class DistanciaSupplierUser {
	public static CoeficienteDistancia calcular(Double latitude, Double longitude, Supplier supplier) {
		Double x1 = latitude;
		Double y1 = longitude;
		Double x2 = supplier.getLatitude();
		Double y2 = supplier.getLongitude();
		
		Double distancia = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
		return CoeficienteDistancia.retornaCoeficienteReal(distancia);
	}
}
