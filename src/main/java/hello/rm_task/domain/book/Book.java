package hello.rm_task.domain.book;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Book {

    // 식별자 정보
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 도서명 : 도서의 제목이나 이름
    private String bookName;

    // 도서의 코드명 : 각 도서에 할당된 고유한 코드
    private String bookCode;

    // 도서의 저자 정보
    private String author;

    // 도서의 출판사 정보
    private String publisher;

    // 도서의 발행일
    private LocalDate publicationDate;

    // 도서의 장르 : 분류정보를 저장
    private String genre;

    //도서의 가격정보
    private BigDecimal price;


    private int bookQuantity;

    public int getBookQuantity() {
        return bookQuantity;
    }

    public void setBookQuantity(int bookQuantity) {
        this.bookQuantity = bookQuantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookCode() {
        return bookCode;
    }

    public void setBookCode(String bookCode) {
        this.bookCode = bookCode;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    // 도서의 재고 수량
    private int stockQuantity;

    // 대출 & 반납
    private int totalQuantity;
    private int availableQuantity;

    @OneToMany(mappedBy = "book")
    private List<LoanHistory> loanHistoryList = new ArrayList<>();
}
