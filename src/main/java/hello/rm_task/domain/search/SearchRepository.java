package hello.rm_task.domain.search;

import hello.rm_task.domain.book.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface SearchRepository extends JpaRepository<Book, Long> {
    List<Book> findByAuthorContaining(String author);
    List<Book> findByBookNameContaining(String bookName);
    List<Book> findByBookCodeContaining(String bookCode);

    /*12.12*/
   @Query("SELECT b FROM Book b WHERE " +
            "(:#{#searchType.author} IS NULL OR b.author LIKE %:#{#searchType.author}%) AND " +
            "(:#{#searchType.bookName} IS NULL OR b.bookName LIKE %:#{#searchType.bookName}%) AND " +
            "(:#{#searchType.bookCode} IS NULL OR b.bookCode LIKE %:#{#searchType.bookCode}%)")
    Page<Book> searchBooks(@Param("searchType") SearchType searchType, Pageable pageable);

    Page<Book> findByBookNameContainingOrBookCodeContaining(String bookName, String bookCode, Pageable pageable);

    List<Book> findByBookName(String bookName);
}

