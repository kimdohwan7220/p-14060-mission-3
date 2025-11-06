package mission1.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import mission1.domain.Quote;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class QuoteServiceTest {

    @DisplayName("명언 등록 테스트")
    @Test
    void 명언_등록_테스트() {
        QuoteService service = new QuoteService();

        Quote quote = service.registerQuote("현재를 사랑하라.", "작자미상");

        assertThat(quote.getId()).isEqualTo(1);
        assertThat(quote.getContent()).isEqualTo("현재를 사랑하라.");
        assertThat(quote.getAuthor()).isEqualTo("작자미상");
    }

    @DisplayName("전체 명언 조회 테스트")
    @Test
    void 명언_목록_조회_테스트() {
        QuoteService service = new QuoteService();

        service.registerQuote("현재를 사랑하라.", "작자미상");
        service.registerQuote("과거에 집착하지 마라.", "작자미상");

        List<Quote> quotes = service.findAllQuotes();

        assertThat(quotes).hasSize(2);
        assertThat(quotes.get(0).getContent()).isEqualTo("현재를 사랑하라.");
        assertThat(quotes.get(1).getContent()).isEqualTo("과거에 집착하지 마라.");
    }

    @DisplayName("명언 삭제 테스트 - 존재하는 ID")
    @Test
    void 명언_삭제_테스트_존재_ID() {
        QuoteService service = new QuoteService();

        service.registerQuote("현재를 사랑하라.", "작자미상");

        service.deleteQuote(1);

        assertThat(service.findAllQuotes()).isEmpty();
    }

    @DisplayName("명언 삭제 테스트 - 존재하지 않는 ID")
    @Test
    void 명언_삭제_테스트_미존재_ID() {
        QuoteService service = new QuoteService();

        assertThatThrownBy(() -> service.deleteQuote(999))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("999번 명언은 존재하지 않습니다.");
    }

    @DisplayName("명언 수정 테스트 - 존재하는 ID")
    @Test
    void 명언_수정_테스트_존재_ID() {
        QuoteService service = new QuoteService();

        service.registerQuote("현재를 사랑하라.", "작자미상");
        service.updateQuote(1, "현재와 자신을 사랑하라.", "홍길동");

        Quote updated = service.findQuoteById(1);
        assertThat(updated.getContent()).isEqualTo("현재와 자신을 사랑하라.");
        assertThat(updated.getAuthor()).isEqualTo("홍길동");
    }

    @DisplayName("명언 수정 테스트 - 존재하지 않는 ID")
    @Test
    void 명언_수정_테스트_미존재_ID() {
        QuoteService service = new QuoteService();

        assertThatThrownBy(() -> service.updateQuote(999, "내용", "작자"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("999번 명언은 존재하지 않습니다.");
    }

    @DisplayName("ID로 명언 조회 테스트")
    @Test
    void 명언_조회_테스트() {
        QuoteService service = new QuoteService();
        service.registerQuote("현재를 사랑하라.", "작자미상");

        Quote quote = service.findQuoteById(1);
        assertThat(quote).isNotNull();
        assertThat(quote.getContent()).isEqualTo("현재를 사랑하라.");

        Quote missingQuote = service.findQuoteById(999);
        assertThat(missingQuote).isNull();
    }
}
