package com.lindsey.giftit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lindsey.giftit.items.ItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class GiftitApplication {

//	@Autowired
//	private RestTemplate restTemplate;

	public static void main(String[] args) {
		SpringApplication.run(GiftitApplication.class, args);
	}

//	@Bean
//	public RestTemplate restTemplate(RestTemplateBuilder builder){
//		return builder.build();
//	}
//
//	public ItemDTO getItemFromJson(String json) throws Exception{
//		ObjectMapper objectMapper = new ObjectMapper();
//		ItemDTO itemDTO = objectMapper.readValue(json, ItemDTO.class);
//		ItemDTO item = restTemplate.getForObject("http://iframe.ly/api/oembed?url=https%3A%2F%2Fvimeo.com%2F141567420&api_key=1a3f50a29205bf7ec89c4f", ItemDTO.class);
//
//		return item;
//	}

}
