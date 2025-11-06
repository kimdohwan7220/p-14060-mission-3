package mission1.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.nio.file.*;

public class QuoteRepository {
    private static final Path BASE_DIR = Paths.get("db", "wiseSaying");
    private static final Path DATA_JSON = BASE_DIR.resolve("data.json");
    private static final Path LAST_ID_FILE = BASE_DIR.resolve("lastId.txt");

    private final Map<Integer, Quote> store = new LinkedHashMap<>();
    private int nextId = 1;

    private final ObjectMapper mapper
            = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    public QuoteRepository() {
        init();
    }

    private void init() {
        try {
            if (!Files.exists(BASE_DIR)) Files.createDirectories(BASE_DIR);

            if (Files.exists(LAST_ID_FILE)) {
                String lastIdText = Files.readString(LAST_ID_FILE, StandardCharsets.UTF_8).trim();
                if (!lastIdText.isEmpty()) nextId = Integer.parseInt(lastIdText) + 1;
            }

            try (var files = Files.list(BASE_DIR)) {
                for (Path jsonFile : files
                        .filter(p -> p.toString().matches("\\d+\\.json"))
                        .toList()) {
                    Quote quote = mapper.readValue(jsonFile.toFile(), Quote.class);
                    store.put(quote.getId(), quote);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("저장소 초기화 실패", e);
        }
    }

    public Quote save(String content, String author) {
        int id = getNextId();
        Quote quote = new Quote(id, content, author);
        store.put(id, quote);
        writeQuote(quote);
        writeLastId(id);
        return quote;
    }

    private Path jsonPath(int id) {
        return BASE_DIR.resolve(id + ".json");
    }

    private void writeQuote(Quote quote) {
        try {
            mapper.writeValue(jsonPath(quote.getId()).toFile(), quote);
        } catch (IOException e) {
            throw new RuntimeException("명언 저장 실패", e);
        }
    }

    private void writeLastId(int lastId) {
        try {
            Files.writeString(
                    LAST_ID_FILE,
                    String.valueOf(lastId),
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );
        } catch (IOException ignored) {}
    }

    private int getNextId() {
        return nextId++;
    }

    public List<Quote> findAll() {
        List<Quote> list = new ArrayList<>(store.values());
        list.sort(Comparator.comparingInt(Quote::getId).reversed());
        return list;
    }

    public Quote findById(int id) {
        return store.get(id);
    }

    public boolean deleteById(int id) {
        Quote removed = store.remove(id);
        try {
            Files.deleteIfExists(jsonPath(id));
        } catch (IOException ignored) {}
        return removed != null;
    }

    public void update(int id, String content, String author) {
        if (!store.containsKey(id)) return;
        Quote updated = new Quote(id, content, author);
        store.put(id, updated);
        writeQuote(updated);
    }

    public void buildDataJson() {
        try {
            List<Quote> list = new ArrayList<>(store.values());
            list.sort(Comparator.comparingInt(Quote::getId)); // 1 → N 오름차순

            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(DATA_JSON.toFile(), list);

        } catch (IOException e) {
            throw new RuntimeException("data.json 생성/갱신 실패", e);
        }
    }
}
