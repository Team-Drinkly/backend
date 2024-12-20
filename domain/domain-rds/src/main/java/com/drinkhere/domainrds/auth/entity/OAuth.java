package com.drinkhere.domainrds.auth.entity;

import com.drinkhere.domainrds.auth.enums.Provider;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authId;
    @Enumerated(value = EnumType.STRING)
    private Provider provider;
    private String sub;

    private OAuth(Provider provider, String sub) {
        this.provider = provider;
        this.sub = sub;
    }

    public static OAuth of(Provider provider, String sub) {
        return new OAuth(provider, sub);
    }
}
