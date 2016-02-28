package com.github.albertosh.expandablereorderableadapter.sample;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.github.albertosh.expandableadapter.ChildViewHolder;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyChildViewHolder extends ChildViewHolder<MyChildItem> {

    @Bind(R.id.title) TextView title;

    public MyChildViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    protected void bindItem(MyChildItem item) {
        title.setText(item.getTitle());
    }

    @Override
    public void onItemSelected() {
        itemView.setBackgroundColor(Color.RED);
    }

    @Override
    public void onItemClear() {
        itemView.setBackgroundColor(Color.TRANSPARENT);
    }
}
