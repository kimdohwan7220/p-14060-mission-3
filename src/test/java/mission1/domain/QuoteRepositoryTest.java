package mission1.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.*;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

class QuoteRepositoryTest {

    private static final Path BASE_DIR = Paths.get("db", "wiseSaying");

    // 테스트 유틸: 폴더/파일 전체 삭제
    private static void deleteDirIfExists(Path dir) throws IOException {
        if (!Files.exists(dir)) return;
        Files.walk(dir)
                .sorted((a, b) -> b.compareTo(a))
                .forEach(p -> { try { Files.deleteIfExists(p); } catch (IOException ignored) {} });
    }

    @BeforeEach
    void cleanBefore() throws IOException {
        deleteDirIfExists(BASE_DIR);
        Files.createDirectories(BASE_DIR);
    }

    @AfterEach
    void cleanAfter() throws IOException {
        deleteDirIfExists(BASE_DIR);
    }

    @Test
    @DisplayName("명언 등록 테스트")
    void 명언_등록_테스트() {
        QuoteRepository repository = new QuoteRepository();

        Quote quote = repository.save("현재를 사랑하라.", "작자미상");

        assertThat(quote.getId()).isEqualTo(1);
        assertThat(quote.getContent()).isEqualTo("현재를 사랑하라.");
        assertThat(quote.getAuthor()).isEqualTo("작자미상");
    }

    @Test
    @DisplayName("명언 목록 조회 테스트(내림차순)")
    void 명언_목록_조회_테스트() {
        QuoteRepository repository = new QuoteRepository();
        Quote q1 = repository.save("현재를 사랑하라.", "작자미상"); // id=1
        Quote q2 = repository.save("과거에 집착하지 마라.", "작자미상"); // id=2

        List<Quote> quotes = repository.findAll();

        assertThat(quotes).hasSize(2);
        // 내림차순(최근 등록 먼저): 2, 1
        assertThat(quotes.get(0).getId()).isEqualTo(q2.getId());
        assertThat(quotes.get(1).getId()).isEqualTo(q1.getId());
    }

    @Test
    @DisplayName("명언 삭제 테스트(메모리+파일)")
    void 명언_삭제_테스트() {
        QuoteRepository repository = new QuoteRepository();
        Quote q = repository.save("현재를 사랑하라.", "작자미상");

        boolean deleted = repository.deleteById(q.getId());
        assertThat(deleted).isTrue();

        boolean notDeleted = repository.deleteById(999);
        assertThat(notDeleted).isFalse();
    }

    @Test
    @DisplayName("ID로 명언 조회 테스트")
    void 명언_조회_테스트() {
        QuoteRepository repository = new QuoteRepository();
        Quote saved = repository.save("현재를 사랑하라.", "작자미상");

        Quote found = repository.findById(saved.getId());
        assertThat(found).isNotNull();
        assertThat(found.getContent()).isEqualTo("현재를 사랑하라.");

        Quote missing = repository.findById(999);
        assertThat(missing).isNull();
    }

    @Test
    @DisplayName("명언 수정 테스트(파일 갱신 포함)")
    void 명언_수정_테스트() {
        QuoteRepository repository = new QuoteRepository();
        Quote saved = repository.save("현재를 사랑하라.", "작자미상");

        repository.update(saved.getId(), "현재와 자신을 사랑하라.", "홍길동");

        Quote updated = repository.findById(saved.getId());
        assertThat(updated).isNotNull();
        assertThat(updated.getContent()).isEqualTo("현재와 자신을 사랑하라.");
        assertThat(updated.getAuthor()).isEqualTo("홍길동");

        repository.update(999, "없는 명언", "익명"); // 무시됨
        Quote missing = repository.findById(999);
        assertThat(missing).isNull();
    }
}
