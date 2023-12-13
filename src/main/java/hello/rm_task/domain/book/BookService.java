package hello.rm_task.domain.book;

import org.springframework.stereotype.Service;

@Service
public class BookService {
    private final LoanHistoryRepository loanHistoryRepository;

    public BookService(LoanHistoryRepository loanHistoryRepository) {
        this.loanHistoryRepository = loanHistoryRepository;
    }
}
