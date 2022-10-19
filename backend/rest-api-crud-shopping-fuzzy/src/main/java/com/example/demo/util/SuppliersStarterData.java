package com.example.demo.util;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.entities.Supplier;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SuppliersStarterData {
	private static String data = """
[
    {
        "name": "Mercadinho do zé",
        "latitude": 20,
        "longitude": 30
    },
    {
        "name": "Mercearia da maria",
        "latitude": 40,
        "longitude": 40
    },
    {
        "name": "Mercadão do Tonhão",
        "latitude": 5,
        "longitude": 60
    }
]
			""";
	
	public static List<Supplier> getData() {
		ObjectMapper mapper = new ObjectMapper();
		List<Supplier> supplier = new ArrayList<>();
		try {
			supplier = mapper.readValue(data, new TypeReference<List<Supplier>>(){});
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return supplier;
	}
}
