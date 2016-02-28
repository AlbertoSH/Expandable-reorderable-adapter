package com.github.albertosh.expandableadapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Viewholder super class for reorderable viewholders
 * @param <Item>
 */
abstract class ReorderableViewHolder<Item extends IShowableItem> extends RecyclerView.ViewHolder {

    private Item item;

    public ReorderableViewHolder(View itemView) {
        super(itemView);
    }

    protected final Item getItem() {
        return item;
    }

    void bind(Item item) {
        this.item = item;
        bindItem(item);
    }

    protected abstract void bindItem(Item item);


    void mOnItemSelected() {
        this.onItemSelected();
    }

    void mOnItemClear() {
        this.onItemClear();
    }

    /**
     * Override for custom select behaviour.
     */
    protected void onItemSelected() {}

    /**
     * Override for custom clear behaviour
     */
    protected void onItemClear() {}
}
