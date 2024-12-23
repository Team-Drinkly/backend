package com.drinkhere.domainrds.member.service;

import com.drinkhere.domainrds.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberQueryService {
    private final MemberRepository memberRepository;

    public boolean existsByDi(String di) {
        return memberRepository.existsByDi(di);
    }
}
