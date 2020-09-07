package com.lindsey.giftit.items;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO implements Serializable {

    private Long id;
    private String username;
    private String link;
    private String title;
    private String description;
    private double price;

}
