package com.github.albertosh.expandableadapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for parent Items
 */
public abstract class ParentItem implements IParentItem {

    private final List<IChildItem> children;
    private boolean open;

    public ParentItem() {
        this(new ArrayList<IChildItem>());
    }

    public ParentItem(List<IChildItem> children) {
        this.children = children;
        this.open = false;
    }

    @Override
    public List<IChildItem> getChildren() {
        return children;
    }

    @Override
    public void addChild(IChildItem child) {
        children.add(child);
    }

    @Override
    public boolean isOpen() {
        return open;
    }

    @Override
    public void toggleOpen() {
        open = !open;
    }
}
