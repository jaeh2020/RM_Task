package hello.rm_task.domain.search;

public class SearchType {
    private String author;
    private String bookName;
    private String bookCode;

    public SearchType(String author, String bookName, String bookCode) {
        this.author = author;
        this.bookName = bookName;
        this.bookCode = bookCode;
    }

    public SearchType() {

    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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
}
