package dev.sriharsha.bookstore.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.qos.logback.core.subst.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token , Integer> {
    Optional<Token> findByToken(String token);
}
