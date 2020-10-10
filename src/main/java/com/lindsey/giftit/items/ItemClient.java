//package com.lindsey.giftit.items;
//
//import lombok.Value;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//@Service
//public class ItemClient {
//
//    public class AccountClient {
//        private final String url;
//        private final RestTemplate restTemplate;
//
//        public AccountClient(@Value("${urls.items}") String url, RestTemplate restTemplate) {
//            this.url = url;
//            this.restTemplate = restTemplate;
//        }
//
//        public ItemDTO getItemById(final long id) {
//            ResponseEntity<ItemDTO> response = restTemplate.getForEntity(url + id, ItemDTO.class);
//            return response.getBody();
//        }
//    }
//}
