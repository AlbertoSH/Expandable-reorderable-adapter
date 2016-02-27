package com.github.albertosh.expandableadapter;

import android.view.View;

/**
 * Viewholder super class for parent items
 * @param <Item> Parent class
 */
public abstract class ParentViewHolder<Item extends IParentItem> extends ReorderableViewHolder<Item> {

    private boolean wasOpened;
    private IExpandOrCollapseListener listener;

    public ParentViewHolder(View itemView) {
        super(itemView);
    }

    void setListener(IExpandOrCollapseListener listener) {
        this.listener = listener;
    }

    protected abstract void bindItem(Item item);

    protected void expandOrCollapse() {
        if (getItem().isOpen())
            listener.collapse(getItem());
        else
            listener.expand(getItem());
    }

    @Override
    void mOnItemSelected() {
        wasOpened = getItem().isOpen();
        if (wasOpened)
            listener.collapse(getItem());

        this.onItemSelected();
    }

    @Override
    void mOnItemClear() {
        this.onItemClear();

        if (wasOpened)
            listener.expand(getItem());
    }
}
