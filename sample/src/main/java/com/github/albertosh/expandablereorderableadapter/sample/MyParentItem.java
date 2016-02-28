package com.github.albertosh.expandablereorderableadapter.sample;

import com.github.albertosh.expandableadapter.ParentItem;

public class MyParentItem extends ParentItem {

    private final String title;

    public MyParentItem(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
