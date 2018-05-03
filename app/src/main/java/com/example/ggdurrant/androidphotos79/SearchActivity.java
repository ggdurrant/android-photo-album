package com.example.ggdurrant.androidphotos79;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

/**
 * controls search results screen
 * @author George Durrant
 * @author Omar Morsy
 */
public class SearchActivity extends AppCompatActivity{

    final Context c = this;
    private GridView grid;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_main);

        /**
         * shows results from search in new screen
         */
        grid = (GridView) findViewById(R.id.resultGrid);
        adapter = new MyAdapter(c,MainActivity.result);
        grid.setAdapter(adapter);

        Button back = (Button) findViewById(R.id.backBtn);

        /**
         * brings user back to main screen
         */
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });



    }
}
