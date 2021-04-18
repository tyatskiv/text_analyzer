package com.yatskiv.textprocessor;

public class OffsetLine {

    private final int offset;
    private final String line;

    public OffsetLine(int offset, String line) {
        this.offset = offset;
        this.line = line;
    }

    public int getOffset() {
        return offset;
    }

    public String getLine() {
        return line;
    }

}
