package com.github.albertosh.expandableadapter;

import java.util.List;

/**
 * Interface for parent items
 */
public interface IParentItem extends IShowableItem {

    List<IChildItem> getChildren();
    void addChild(IChildItem child);
    boolean isOpen();
    void toggleOpen();

}
