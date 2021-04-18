package com.yatskiv.textprocessor.print;

import java.util.List;
import java.util.Map;

public class ConsolePrinter implements TextPrinter {

    @Override
    public void print(Map<String, List<String>> text) {
        text.forEach((k, v) -> System.out.println(k + " --> " + v));
    }

}
