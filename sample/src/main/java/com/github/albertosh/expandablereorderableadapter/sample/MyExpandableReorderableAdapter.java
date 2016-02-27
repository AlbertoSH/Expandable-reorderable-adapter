package com.github.albertosh.expandablereorderableadapter.sample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.albertosh.expandableadapter.ExpandableReorderableAdapter;

import java.util.List;

public class MyExpandableReorderableAdapter extends ExpandableReorderableAdapter<MyParentViewHolder, MyChildViewHolder> {

    public MyExpandableReorderableAdapter(List<MyParentItem> modelItems) {
        super(modelItems);
    }

    @Override
    protected MyParentViewHolder onCreateParentViewHolder(ViewGroup parent) {
        View root = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_parent, parent, false);
        return new MyParentViewHolder(root);
    }

    @Override
    protected MyChildViewHolder onCreateChildViewHolder(ViewGroup parent) {
        View root = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_child, parent, false);
        return new MyChildViewHolder(root);
    }

}
