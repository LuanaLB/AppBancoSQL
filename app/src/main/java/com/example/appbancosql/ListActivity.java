package com.example.appbancosql;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import java.util.List;

public class ListActivity extends AppCompatActivity {

    private BdSqlite bd;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Pessoa> myDataset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        bd = new BdSqlite(this);

        recyclerView = (RecyclerView) findViewById(R.id.myRecyclerView);
        recyclerView.setHasFixedSize(true);
        myDataset = bd.consultarDados();

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new Adapter(myDataset, bd, getContextList());
        recyclerView.setAdapter(mAdapter);

        mAdapter = new Adapter(myDataset, bd, getContextList());
        recyclerView.setAdapter(mAdapter);
    }

    private Context getContextList() {
        return this;
    }


    @Override
    protected void onDestroy() {
        bd.close();
        super.onDestroy();
    }
}