package dev.sriharsha.bookstore.backend.repository;

import dev.sriharsha.bookstore.backend.entity.TransactionHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Integer> {
    Page<TransactionHistory> findByUserId(Pageable pageable, Integer userId);

    Page<TransactionHistory> findByBookOwnerId(Pageable pageable, Integer ownerId);

    Boolean existsByBookIdAndUserId(Integer bookId, Integer userId);

    Optional<TransactionHistory> findByBookIdAndUserId(Integer bookId, Integer userId);
}
