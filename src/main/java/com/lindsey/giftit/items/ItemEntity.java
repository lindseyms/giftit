package com.lindsey.giftit.items;

import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "items")
public class ItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @URL(message = "Please enter a valid link")
    private String link;

    private String description;

    private double price;

}
