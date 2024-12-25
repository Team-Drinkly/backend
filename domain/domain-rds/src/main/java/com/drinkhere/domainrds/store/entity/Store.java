package com.drinkhere.domainrds.store.entity;

import com.drinkhere.domainrds.auditing.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// TODO: Soft Delete 적용
public class Store extends BaseTimeEntity {
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

    @Column(nullable = false)
    private String latitude; // 위도 (y)

    @Column(nullable = false)
    private String longitude; // 경도 (x)

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StoreImage> images;


    @Builder
    public Store(String storeName, String description, String openingHour, String address, String storeNo, String instagram, String availableBeverage, String latitude, String longitude, List<StoreImage> images) {
        this.storeName = storeName;
        this.description = description;
        this.openingHour = openingHour;
        this.address = address;
        this.storeNo = storeNo;
        this.instagram = instagram;
        this.availableBeverage = availableBeverage;
        this.latitude = latitude;
        this.longitude = longitude;
        this.images = images;
    }
}
