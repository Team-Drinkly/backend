package com.drinkhere.domainrds.member.service;

import com.drinkhere.domainrds.member.entity.Member;
import com.drinkhere.domainrds.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberCommandService {
    private final MemberRepository memberRepository;
    public void save(final Member member) {memberRepository.save(member);}
}
