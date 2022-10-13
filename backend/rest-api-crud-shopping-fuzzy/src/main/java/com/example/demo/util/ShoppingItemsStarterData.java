package com.example.demo.util;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.entities.ShoppingItem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ShoppingItemsStarterData {
	private static String data = """
[
	{
		"name": "Bolacha Wafer Avelã",
		"brand": "Bauducco",
		"type": "Alimentação",
		"price": 2.99,
		"image": "https://www.bauducco.com.br/Style%20Library/assets/images/Wafer%20Chocolate%20com%20Avel%C3%A3/wafer_choco_avela.png"
	},
	{
		"name": "Bolhacha Wafer Prestígio",
		"brand": "Bauducco",
		"type": "Alimentação",
		"price": 2.99,
		"image": "https://images.tcdn.com.br/img/img_prod/655345/biscoito_prestigio_wafer_110g_7119_1_922c35ddf8b8b774804db7d12a3a8982.png"
	},
	{
		"name": "Bolhacha Wafer Chocolate",
		"brand": "Marilan",
		"type": "Alimentação",
		"price": 1.99,
		"image": "https://www.marilan.com/wp-content/uploads/2018/10/wafer-chocolate.png"
	},
	{
		"name": "Bolhacha Wafer Chocolate",
		"brand": "Adria",
		"type": "Alimentação",
		"price": 1.99,
		"image": "https://www.adria.com.br/wp-content/uploads/2020/11/Adria-Wafer-Chocolate-100g.png"
	},
	{
		"name": "Bolhacha Wafer Chocolate",
		"brand": "Aymoré",
		"type": "Alimentação",
		"price": 1.87,
		"image": "https://upside.vteximg.com.br/arquivos/ids/725838-1000-1000/38450.png?v=637751978712400000"
	},
	{
		"name": "Arroz Branco 1kg",
		"brand": "Tio João",
		"type": "Alimentação",
		"price": 12.87,
		"image": "https://www.clubeextra.com.br/img/uploads/1/246/596246.png"
	},
	{
		"name": "Arroz Branco 1kg",
		"brand": "Prato Fino",
		"type": "Alimentação",
		"price": 10.99,
		"image": "https://static.clubeextra.com.br/img/uploads/1/1/574001.png"
	},
	{
		"name": "Arroz Branco 1kg",
		"brand": "Broto Legal",
		"type": "Alimentação",
		"price": 9.99,
		"image": "https://trimais.vteximg.com.br/arquivos/ids/1028752-1000-1000/foto_original.jpg?v=637719741869070000"
	},
    {
		"name": "Café instantaneo",
		"brand": "Nescafé",
		"type": "Alimentação",
		"price": 16.99,
		"image": "https://www.clubeextra.com.br/img/uploads/1/119/12355119.jpg"
	},
    {
		"name": "Achocolatado em pó Nescau",
		"brand": "Nestle",
		"type": "Alimentação",
		"price": 11.99,
		"image": "https://static.paodeacucar.com/img/uploads/1/855/675855.jpg"
	}
]
			""";
	
	public static List<ShoppingItem> getData() {
		ObjectMapper mapper = new ObjectMapper();
		List<ShoppingItem> shoppingItens = new ArrayList<>();
		try {
			shoppingItens = mapper.readValue(data, new TypeReference<List<ShoppingItem>>(){});
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return shoppingItens;
	}
}
