package com.drinkhere.domainrds.member.repository;

import com.drinkhere.domainrds.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByDi(String di);
}
