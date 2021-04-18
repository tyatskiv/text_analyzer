package com.yatskiv.textprocessor;

import com.yatskiv.textprocessor.analyze.TextMatcher;
import com.yatskiv.textprocessor.print.TextPrinter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class FileTextProcessor implements TextProcessor {

    public static final int SKIP_EMPTY_LINES = 0x00000002;
    public static final int GLOBAL_CHAR_OFFSET = 0x00000004;

    private static final int CHUNK_SIZE = 1000;
    private static final int MAX_WORKERS = 8;

    private final String fileName;
    private final List<String> matches;
    private final List<OffsetLine> data = new LinkedList<>();

    private final List<Thread> workers = new ArrayList<>();

    private int options = 0;

    public FileTextProcessor(String fileName, List<String> matches) {
        this.fileName = fileName;
        this.matches = matches;
    }

    @Override
    public void init() throws IOException {
        init(0);
    }

    @Override
    public void init(int options) throws IOException {
        data.clear();
        workers.clear();
        consumer.clear();

        this.options = options;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            AtomicInteger offset = new AtomicInteger(0);
            if ((this.options & SKIP_EMPTY_LINES) > 0) {
                reader.lines().filter(line -> !line.isEmpty()).forEach(
                        line -> data.add(new OffsetLine(offset.getAndAdd(line.length()), line)));
            } else {
                reader.lines().forEach(line -> data.add(new OffsetLine(offset.getAndAdd(line.length()), line)));
            }
        }
    }

    @Override
    public void analyze(TextMatcher matcher) {
        for (int i = 0; i < data.size(); i += CHUNK_SIZE) {
            while (workers.stream().filter(Thread::isAlive).count() >= MAX_WORKERS) {
                Thread.onSpinWait();
            }

            int capacity = Math.min(i + CHUNK_SIZE, data.size());
            int from = i;
            Thread worker = new Thread(() -> matcher.process(from, (options & GLOBAL_CHAR_OFFSET) > 0,
                    Arrays.copyOfRange(data.toArray(new OffsetLine[0]), from, capacity), matches, consumer));
            workers.add(worker);
            worker.start();
        }
    }

    @Override
    public void printResults(TextPrinter printer) {
        while (workers.stream().anyMatch(Thread::isAlive)) {
            Thread.onSpinWait();
        }
        printer.print(consumer);
    }

}
