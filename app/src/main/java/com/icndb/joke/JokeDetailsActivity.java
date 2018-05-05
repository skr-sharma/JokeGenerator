package com.icndb.joke;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.icndb.Adapter.CustomAdapter;

import static com.icndb.joke.JokeActivity.JokeArrayList;

/**
 * Created by Skr Sharma on 05-05-2018.
 */

public class JokeDetailsActivity extends AppCompatActivity {
    private LinearLayout layoutDetails;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        initXmlViews();

        CustomAdapter adapter = new CustomAdapter(JokeArrayList);
        mRecyclerView.setAdapter(adapter);
    }

    private void initXmlViews() {
        layoutDetails = (LinearLayout) findViewById(R.id.layoutDetails);
        layoutDetails.setVisibility(View.VISIBLE);
        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
    }
}
