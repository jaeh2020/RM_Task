package hello.rm_task;

import hello.rm_task.domain.book.Book;
import hello.rm_task.domain.book.BookRepository;
import hello.rm_task.domain.member.Member;
import hello.rm_task.domain.member.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final MemberRepository memberRepository;

    private final BookRepository bookRepository;
    /**
     * 테스트용 데이터 추가
     */

    @PostConstruct
    public void init(){
        Member member = new Member();
        member.setLoginId("test");
        member.setPassword("test1");
        member.setName("테스트용");

        Book book = new Book();
        book.setId(2111L);
        book.setBookName("서울의봄");
        book.setBookCode("seoul-spring");
        book.setAuthor("전두광");
        book.setPublisher("스튜디오드래곤");
        book.setPublicationDate(LocalDate.ofEpochDay(2023-12-12));
        book.setGenre("블랙코메디");
        book.setPrice(BigDecimal.valueOf(30000));
        book.setBookQuantity(20);

        memberRepository.save(member);
        bookRepository.save(book);
    }
}
