package com.drinkhere.domainrds.store.entity;

import com.drinkhere.domainrds.store.enums.StoreImageCategory;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreImage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_image_id")
    private Long id;

    @Column(name = "store_image_path", nullable = false)
    private String storeImagePath;

    @Column(name = "store_image_category", nullable = false)
    private StoreImageCategory storeImageCategory;

    @Column(name = "store_image_order", nullable = false)
    private int storeImageOrder;

    @Builder
    public StoreImage(String storeImagePath, StoreImageCategory storeImageCategory, int storeImageOrder) {
        this.storeImagePath = storeImagePath;
        this.storeImageCategory = storeImageCategory;
        this.storeImageOrder = storeImageOrder;
    }
}
