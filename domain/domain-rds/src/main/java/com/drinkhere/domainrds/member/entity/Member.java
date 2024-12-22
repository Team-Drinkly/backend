package com.drinkhere.domainrds.member.entity;

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
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;
    private String name;
    private String birthDate;

    @Enumerated(EnumType.STRING)  // Gender 값을 String으로 저장
    @Column(name = "gender")
    private Gender gender;

    @Enumerated(EnumType.STRING)  // NationalInfo 값을 String으로 저장
    @Column(name = "national_info")
    private NationalInfo nationalInfo;

    @Enumerated(EnumType.STRING)  // MobileCo 값을 String으로 저장
    @Column(name = "mobile_co")
    private MobileCo mobileCo;

    private String mobileNo;

    @Builder
    public Member(Long memberId, String name, String birthDate, Gender gender, NationalInfo nationalInfo, MobileCo mobileCo, String mobileNo) {
        this.memberId = memberId;
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.nationalInfo = nationalInfo;
        this.mobileCo = mobileCo;
        this.mobileNo = mobileNo;
    }
}
