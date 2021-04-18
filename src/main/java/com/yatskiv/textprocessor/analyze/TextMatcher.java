package com.yatskiv.textprocessor.analyze;

import com.yatskiv.textprocessor.OffsetLine;

import java.util.List;
import java.util.Map;

public interface TextMatcher {

    void process(int from, boolean globalOffset, OffsetLine[] lines, List<String> matches, Map<String, List<String>> consumer);

}
