package dev.sriharsha.bookstore.backend.repository;

import dev.sriharsha.bookstore.backend.entity.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    Page<Feedback> findByBookId(Pageable pageable, Integer bookId);
}
