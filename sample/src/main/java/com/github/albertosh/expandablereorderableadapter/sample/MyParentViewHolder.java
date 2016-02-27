package com.github.albertosh.expandablereorderableadapter.sample;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.albertosh.expandableadapter.ParentViewHolder;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyParentViewHolder extends ParentViewHolder<MyParentItem> {

    @Bind(R.id.title) TextView title;
    @Bind(R.id.expand) ImageButton expand;

    public MyParentViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandOrCollapse();
                setExpandIcon();
            }
        });
    }

    @Override
    protected void bindItem(MyParentItem item) {
        title.setText(item.getTitle());
        if (item.getChildren().isEmpty()) {
            expand.setVisibility(View.GONE);
        } else {
            expand.setVisibility(View.VISIBLE);
            setExpandIcon();
        }
    }

    private void setExpandIcon() {
        if (getItem().isOpen())
            expand.setImageResource(R.drawable.ic_expand_less_black_24dp);
        else
            expand.setImageResource(R.drawable.ic_expand_more_black_24dp);
    }

    @Override
    public void onItemSelected() {
        itemView.setBackgroundColor(Color.GREEN);
    }

    @Override
    public void onItemClear() {
        itemView.setBackgroundColor(Color.TRANSPARENT);
    }
}
