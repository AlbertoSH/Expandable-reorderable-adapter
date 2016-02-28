package com.github.albertosh.expandablereorderableadapter.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.github.albertosh.expandableadapter.ExpandableReorderableAdapter;
import com.github.albertosh.expandableadapter.Reorderable;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<MyParentItem> modelItems = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            MyParentItem item = new MyParentItem("Parent " + i);
            modelItems.add(item);
            if (i % 2 == 0) {
                for (int j = 0; j < 4; j++) {
                    MyChildItem child = new MyChildItem("Child " + i + "." + j);
                    item.addChild(child);
                }
            }
            if (i % 4 == 0) {
                item.toggleOpen();
            }
        }

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        final ExpandableReorderableAdapter adapter = new MyExpandableReorderableAdapter(modelItems);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Switch switchView = (Switch) findViewById(R.id.enableReorderableSwitch);
        switchView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    Reorderable.addReorderableBehaviour(recyclerView, adapter);
                else
                    Reorderable.removeReorderableBehaviour(recyclerView, adapter);
            }
        });
    }

}
