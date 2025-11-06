package mission1.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import mission1.domain.Quote;
import mission1.service.QuoteService;
import mission1.controller.QuoteHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class QuoteHandlerTest {

    @DisplayName("정상 시나리오: 명언 전체 흐름 테스트")
    @Test
    void 정상_시나리오_테스트() {
        QuoteService service = new QuoteService();
        QuoteHandler handler = new QuoteHandler(service);

        Quote q1 = service.registerQuote("현재를 사랑하라.", "작자미상");
        Quote q2 = service.registerQuote("과거에 집착하지 마라.", "홍길동");

        List<Quote> allQuotes = service.findAllQuotes();
        assertThat(allQuotes).hasSize(2);
        assertThat(allQuotes.get(0).getContent()).isEqualTo("현재를 사랑하라.");
        assertThat(allQuotes.get(1).getAuthor()).isEqualTo("홍길동");

        service.updateQuote(1, "현재와 자신을 사랑하라.", "홍길동");
        Quote updated = service.findQuoteById(1);
        assertThat(updated.getContent()).isEqualTo("현재와 자신을 사랑하라.");
        assertThat(updated.getAuthor()).isEqualTo("홍길동");

        service.deleteQuote(2);
        allQuotes = service.findAllQuotes();
        assertThat(allQuotes).hasSize(1);
        assertThat(allQuotes.get(0).getId()).isEqualTo(1);
    }

    @DisplayName("예외 시나리오: 존재하지 않는 ID 처리 테스트")
    @Test
    void 예외_시나리오_테스트() {
        QuoteService service = new QuoteService();
        QuoteHandler handler = new QuoteHandler(service);

        assertThatThrownBy(() -> {
            Quote q = service.findQuoteById(999); // 존재하지 않는 ID
            if (q == null) throw new IllegalArgumentException("999번 명언은 존재하지 않습니다.");
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("999번 명언은 존재하지 않습니다.");

        assertThatThrownBy(() -> service.deleteQuote(999))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("999번 명언은 존재하지 않습니다.");

        assertThatThrownBy(() -> service.updateQuote(999, "내용", "작자"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("999번 명언은 존재하지 않습니다.");
    }
}
