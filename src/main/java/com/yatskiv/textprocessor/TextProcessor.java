package com.yatskiv.textprocessor;

import com.yatskiv.textprocessor.analyze.TextMatcher;
import com.yatskiv.textprocessor.print.TextPrinter;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface TextProcessor {

    Map<String, List<String >> consumer = new ConcurrentHashMap<>();

    void init() throws Exception;

    void init(int options) throws Exception;

    void analyze(TextMatcher matcher);

    void printResults(TextPrinter printer);

}
