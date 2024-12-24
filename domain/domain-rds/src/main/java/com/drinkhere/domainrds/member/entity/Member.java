package com.drinkhere.domainrds.member.entity;

import com.drinkhere.domainrds.auditing.BaseTimeEntity;
import com.drinkhere.domainrds.member.enums.Gender;
import com.drinkhere.domainrds.member.enums.MobileCo;
import com.drinkhere.domainrds.member.enums.NationalInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "birth_date", nullable = false)
    private String birthDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "national_info", nullable = false)
    private NationalInfo nationalInfo;

    @Enumerated(EnumType.STRING)
    @Column(name = "mobile_co", nullable = false)
    private MobileCo mobileCo;

    @Column(name = "mobile_no", nullable = false)
    private String mobileNo;

    @Column(nullable = false)
    private String di;

    @Builder
    public Member(String name, String birthDate, Gender gender, NationalInfo nationalInfo, MobileCo mobileCo, String mobileNo, String di) {
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.nationalInfo = nationalInfo;
        this.mobileCo = mobileCo;
        this.mobileNo = mobileNo;
        this.di = di;
    }
}
