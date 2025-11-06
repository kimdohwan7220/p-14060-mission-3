package mission1.service;

import java.util.List;
import mission1.domain.Quote;
import mission1.domain.QuoteRepository;
import mission1.utils.QuoteValidator;

public class QuoteService {
    private final QuoteRepository repository = new QuoteRepository();
    private final QuoteValidator validator = new QuoteValidator(repository);

    public Quote registerQuote(String content, String author) {
        return repository.save(content, author);
    }

    public List<Quote> findAllQuotes() {
        return repository.findAll();
    }

    public void deleteQuote(int id) {
        validator.validateQuoteExists(id);
        repository.deleteById(id);
    }

    public QuoteRepository getRepository() {
        return repository;
    }

    public Quote findQuoteById(int id) {
        return repository.findById(id);
    }

    public void updateQuote(int id, String content, String author) {
        validator.validateQuoteExists(id);
        repository.update(id, content, author);
    }

    public void buildDataJson() {
        repository.buildDataJson();
    }
}
