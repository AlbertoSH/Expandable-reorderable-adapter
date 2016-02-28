package com.github.albertosh.expandableadapter;

import android.view.View;

/**
 * Viewholder super class for child items
 * @param <Item> Child class
 */
public abstract class ChildViewHolder<Item extends IChildItem> extends ReorderableViewHolder<Item> {

    public ChildViewHolder(View itemView) {
        super(itemView);
    }

}
