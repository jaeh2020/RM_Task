package hello.rm_task.web.book.form;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class BookUpdateForm {


    private Long id;

    // 도서명 : 도서의 제목이나 이름
    private String bookName;

    // 도서의 코드명 : 각 도서에 할당된 고유한 코드
    private String bookCode;

    private int bookQuantity;
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

    public int getBookQuantity() {
        return bookQuantity;
    }

    public void setBookQuantity(int bookQuantity) {
        this.bookQuantity = bookQuantity;
    }
}