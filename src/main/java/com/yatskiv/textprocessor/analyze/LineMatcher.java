package com.yatskiv.textprocessor.analyze;

import com.yatskiv.textprocessor.OffsetLine;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LineMatcher implements TextMatcher {

    @Override
    public void process(int from, boolean globalOffset, OffsetLine[] lines, List<String> matches, Map<String, List<String>> consumer) {
        int lineNumber = from;

        List<String> regexWords = new ArrayList<>();
        for (String match : matches) {
            regexWords.add("\\b" + match + "\\b");
        }

        String regex = String.join("|", regexWords);
        for (OffsetLine line : lines) {
            lineNumber++;
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(line.getLine());
            while (matcher.find()) {
                String key = matcher.group();
                String find = "{lineOffset=" + lineNumber + "," +
                        "charOffset=" + ((matcher.start() + 1) + (globalOffset ? line.getOffset() : 0)) + "}";
                List<String> list = consumer.get(key) != null ? consumer.get(key) : Collections.synchronizedList(new ArrayList<>());
                synchronized (list) {
                    consumer.computeIfAbsent(key, k -> list).add(find);
                }
            }
        }
    }

}
