package com.github.albertosh.expandablereorderableadapter.sample;

import com.github.albertosh.expandableadapter.IChildItem;

public class MyChildItem implements IChildItem {

    private final String title;

    public MyChildItem(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
