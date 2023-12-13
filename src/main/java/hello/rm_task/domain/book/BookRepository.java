package hello.rm_task.domain.book;


import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Slf4j
@Repository
public class BookRepository {

    private final BookService bookService;
    private final LoanHistoryRepository repository;
    @Autowired
    public BookRepository(BookService bookService, LoanHistoryRepository repository) {
        this.bookService = bookService;
        this.repository = repository;
    }

    private static final Map<Long, Book> store = new HashMap<>();

    private static long sequence = 0L;

    public Book save(Book book){
        book.setId(++sequence);
        log.info("save Book={}", book);
        store.put(book.getId(), book);
        return book;
    }

    public List<Book> findAll(){
        return new ArrayList<>(store.values());
    }

    public Book findById(Long id){
        return store.getOrDefault(id, null);
    }

    /* 파일 업데이트 */
    public void update(Long bookId, Book updateParam){
        // ID로 책을 찾습니다
        Optional<Book> optionalBook = Optional.ofNullable(findById(bookId));

        // 주어진 ID로 책을 찾은 경우, 필드를 업데이트합니다
        optionalBook.ifPresent(findBook -> {
            findBook.setBookName(updateParam.getBookName());
            findBook.setBookCode(updateParam.getBookCode());
            findBook.setAuthor(updateParam.getAuthor());
            findBook.setGenre(updateParam.getGenre());
            findBook.setPublisher(updateParam.getPublisher());
            // 필요에 따라 다른 필드를 업데이트합니다

        });
    }

    public void updateProcess(Long bookId, Book updateParam){
        Book findBook = findById(bookId);

        // findById에서 도서를 찾지 못한 경우 처리
        if(findBook == null){
            return;
        }

        update(bookId, updateParam);
    }

    public void clearStore(){
        store.clear();
    }

    // 도서 대출 & 반환 기능
    @Transactional
    public void borrowBook(Long bookId){
        Book book = findById(bookId);

        // Null 체크 추가
        if (book == null) {
            int quantity = book.getBookQuantity();
            throw new IllegalStateException("도서를 찾을 수 없습니다. ID: " + bookId);
        }

        // 대출 가능한 도서 수량 확인
        if(book.getBookQuantity() > 0){
            book.setBookQuantity(book.getBookQuantity() - 1);
        } else {
            throw new IllegalStateException("도서 대출이 불가능 합니다. 대출 가능한 수량이 없습니다.");
        }
    }

    public void returnBook(Long bookId){
        Book book = findById(bookId);

        if (book != null ) {
            book.setBookQuantity(book.getBookQuantity() + 1); // 반납하면 수량을 증가시킴
        }
    }

    public void deleteBook(Long bookId){
        store.remove(bookId);
    }

    public void deleteSelectedBooks(List<Long> bookIds) {
        for (Long bookId : bookIds) {
            deleteBook(bookId);
        }
    }

}
