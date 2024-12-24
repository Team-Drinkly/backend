package com.drinkhere.domainrds.auth.repository;


import com.drinkhere.domainrds.auth.entity.OAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OAuthRepository extends JpaRepository<OAuth, Long> {

    boolean existsBySub(String sub);

    Optional<OAuth> findBySub(String sub);
}
