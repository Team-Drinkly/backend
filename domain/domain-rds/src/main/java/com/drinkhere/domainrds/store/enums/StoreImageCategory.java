package com.drinkhere.domainrds.store.enums;

import lombok.RequiredArgsConstructor;

/**
 * 매장 이미지 카테고리
 */
@RequiredArgsConstructor
public enum StoreImageCategory {
    REPRESENTATIVE(0, "대표 이미지"), // 대표 이미지(0)
    MENU(1, "메뉴 이미지");           // 메뉴 이미지(1)

    private final int value;
    private final String description;

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    // 주어진 정수값에 맞는 StoreImageCategory를 반환하는 정적 메서드
    public static StoreImageCategory fromValue(int value) {
        switch (value) {
            case 0: return REPRESENTATIVE;
            case 1: return MENU;
        }
        return MENU;
    }
}
