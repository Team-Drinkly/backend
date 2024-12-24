package com.drinkhere.domainrds.store.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;

    @Column(name = "store_name", nullable = false)
    private String storeName;

    @Column(nullable = true)
    private String description;

    @Lob @Column(name = "opening_hour", nullable = false)
    private String openingHour;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String storeNo;

    @Column(nullable = true)
    private String instagram;

    @Column(name = "available_beverage", nullable = false)
    private String availableBeverage;

    @Builder
    public Store(String storeName, String description, String openingHour, String address, String storeNo, String instagram, String availableBeverage) {
        this.storeName = storeName;
        this.description = description;
        this.openingHour = openingHour;
        this.address = address;
        this.storeNo = storeNo;
        this.instagram = instagram;
        this.availableBeverage = availableBeverage;
    }
}
