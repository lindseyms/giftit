//package com.lindsey.giftit.items;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//@Service
//@JsonIgnoreProperties(true)
//public class ItemClient {
//
//    private final String url1;
//    private final String url2;
//    private final RestTemplate restTemplate;
//
//    public ItemClient(@Value("${urls.url1}") String url1, @Value("$urls.url2") String url2, RestTemplate restTemplate) {
//        this.url1 = url1;
//        this.url2 = url2;
//        this.restTemplate = restTemplate;
//    }
//
//    public ItemDTO getItemByLink(String link) {
//        ResponseEntity<ItemDTO> response = restTemplate.getForEntity(url1 + link + url2, ItemDTO.class);
//        return response.getBody();
//    }
//}
//
