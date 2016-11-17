package com.driw.controller.product.viewmodels;

public class OptionViewModel {
    private long value;
    private String text;

    public OptionViewModel(long value, String text) {
        this.value = value;
        this.text = text;
    }

    public long getValue() {
        return value;
    }

    public String getText() {
        return text;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public void setText(String text) {
        this.text = text;
    }
}
