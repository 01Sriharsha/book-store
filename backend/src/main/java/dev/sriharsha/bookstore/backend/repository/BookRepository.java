package dev.sriharsha.bookstore.backend.repository;

import dev.sriharsha.bookstore.backend.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
}
