package hello.rm_task.web.book;

import hello.rm_task.domain.book.Book;
import hello.rm_task.domain.book.BookRepository;
import hello.rm_task.domain.book.BookService;
import hello.rm_task.domain.search.SearchRepository;
import hello.rm_task.domain.search.SearchType;
import hello.rm_task.web.book.form.BookSaveForm;
import hello.rm_task.web.book.form.BookUpdateForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookRepository bookRepository;
    private final SearchRepository searchRepository;



    @GetMapping
    public String books(Model model) {
        List<Book> books = bookRepository.findAll();
        System.out.println("Books from findAll " + books);

        model.addAttribute("books", books);
        return "books/books";
    }

    @GetMapping("/{bookId}")
    public String book(@PathVariable long bookId, Model model) {
        Book book = bookRepository.findById(bookId);
        model.addAttribute("book", book);
        return "books/book";
    }

    /* 도서 추가 */
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("book", new Book());
        return "books/addForm";
    }

    @PostMapping("/add")
    public String addBook(@Validated @ModelAttribute("book") BookSaveForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        // 특정 필드 예외가 아닌 전체 예외
        if (form.getBookQuantity() != 0) {
            int bookOk = form.getBookQuantity();
            if (bookOk < 1) {
                bindingResult.rejectValue("bookQuantity", "positive.value", "수량은 1 이상이어야 합니다.");
                return "books/addForm";
            }
        }

        if (bindingResult.hasErrors()) {
            log.info("error={}", bindingResult);
            return "books/addForm";
        }

        Book book = new Book();
        book.setBookName(form.getBookName());
        book.setBookCode(form.getBookCode());
        book.setBookQuantity(form.getBookQuantity());
        book.setGenre(form.getGenre());
        book.setPrice(form.getPrice());
        book.setAuthor(form.getAuthor());
        book.setPrice(form.getPrice());
        book.setPublisher(form.getPublisher());
        Book savedBook = bookRepository.save(book);

        redirectAttributes.addAttribute("bookId", savedBook.getId());
        redirectAttributes.addFlashAttribute("status", true);
        return "redirect:/books/{bookId}";
    }

    @GetMapping("/{bookId}/edit")
    public String editForm(@PathVariable Long bookId, Model model){
        Book book = bookRepository.findById(bookId);
        BookUpdateForm form = new BookUpdateForm();

        form.setId(book.getId());

        model.addAttribute("book", book);
        return "books/editForm";
    }


    @PostMapping("/{bookId}/edit")
    public String edit(@PathVariable Long bookId, @Validated @ModelAttribute("book") BookUpdateForm form, BindingResult bindingResult){

        //특정 필드 예외가 아닌 전체 예외
        if (form.getBookQuantity() < 0) {
            bindingResult.reject("Cant Rent Book");
        }

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "books/editForm";
        }

        Book bookParam = new Book();
        bookParam.setBookName(form.getBookName());
        bookParam.setBookCode(form.getBookCode());
        bookParam.setBookQuantity(form.getBookQuantity());
        bookParam.setGenre(form.getGenre());
        bookParam.setAuthor(form.getAuthor());
        bookParam.setPrice(form.getPrice());
        bookParam.setPublisher(form.getPublisher());

        log.info(bookParam + "<- bookParam");

        bookRepository.update(bookId, bookParam);

        return "redirect:/books/{bookId}";
    }

    /**
     * 도서 대출 / 반납
     */
    @PostMapping("/{bookId}/borrow")
    public String borrowBook(@PathVariable Long bookId, Model model){
        try {
            bookRepository.borrowBook(bookId);
            return "redirect:/books";
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "redirect:/books/error";
        }
    }


    @PostMapping("/{bookId}/return")
    public String returnBook(@PathVariable Long bookId){
        bookRepository.returnBook(bookId);
        return "redirect:/books";
    }

    /* 선택항목 삭제 */
    @PostMapping("/delete-selected")
    public String deleteSeletedBooks(@RequestParam("selectedBooks") List<Long> selectedBooks){
        bookRepository.deleteSelectedBooks(selectedBooks);
        return "redirect:/books";
    }

    /*검색 테스트*/
    @GetMapping("/search")
    @ResponseBody
    public ResponseEntity<Page<Book>> searchBooks(
            @RequestParam(required = false) String searchType,
            @RequestParam(required = false) String keyword,
            @PageableDefault(size = 10, sort = {"bookName"}, direction = Sort.Direction.DESC) Pageable pageable){
        System.out.println("검색 요청 도달 - searchType: " + searchType + ", keyword: " + keyword);
        SearchType searchCriteria = createSearchType(searchType, keyword);

        Page<Book> books = searchRepository.searchBooks(searchCriteria, pageable);
        return ResponseEntity.ok(books);
    }

    private SearchType createSearchType(String searchType, String keyword) {
        SearchType searchCriteria = new SearchType();
        switch (searchType){
            case "author":
                searchCriteria.setAuthor(keyword);
                break;
            case "bookName":
                searchCriteria.setBookName(keyword);
                break;
            case "bookCode":
                searchCriteria.setBookCode(keyword);
                break;
                // 여러 검색 조건이 추가될 경우에 대한 처리
                // case "다른조건":
                //    searchCriteria.set다른조건(keyword);
                //    break;

            default:
                // 기본적으로 아무 동작도 수행하지 않음
        }
        return searchCriteria;
    }

}
