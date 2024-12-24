package com.drinkhere.domainrds.store.entity;

import com.drinkhere.domainrds.store.enums.StoreImageCategory;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreImage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_image_id")
    private Long id;

    @Column(nullable = false)
    private String storeImagePath;

    @Column(nullable = false)
    private StoreImageCategory storeImageCategory;

    @Column(nullable = false)
    private int storeImageIndex;
}
