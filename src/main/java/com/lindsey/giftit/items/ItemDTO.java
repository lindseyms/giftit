package com.lindsey.giftit.items;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO implements Serializable {

    private Long id;
    private Long userId;
    private String link;
    private String description;
    private double price;

}
