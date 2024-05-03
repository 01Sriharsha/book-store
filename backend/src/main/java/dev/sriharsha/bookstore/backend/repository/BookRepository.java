package dev.sriharsha.bookstore.backend.repository;

import dev.sriharsha.bookstore.backend.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    boolean existsByTitle(String title);

    //Fetch all books except the books of current authenticated user
    Page<Book> findByOwnerIdNotAndShareableIsTrue(Integer ownerId, Pageable pageable);

    //fetch books of specific user
    Page<Book> findByOwnerId(Integer ownerId, Pageable pageable);
}
