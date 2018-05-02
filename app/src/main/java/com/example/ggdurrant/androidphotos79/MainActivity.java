package com.example.ggdurrant.androidphotos79;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.view.*;
import android.widget.*;
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView albums;
    ArrayAdapter<Album> adapter;
    public static AlbumInfo info;
    final Context c = this;
    String response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        info = AlbumInfo.load(this);
        if(info==null){
            info = new AlbumInfo();
        }
        if(info.albums==null){
            info.albums = new ArrayList<Album>();
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        albums = (ListView) findViewById(R.id.albumList);
        albums.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        albums.setSelection(0);
        adapter = new ArrayAdapter<Album>(c, R.layout.album_main, info.albums);
        albums.setAdapter(adapter);

        Button add = (Button) findViewById(R.id.addBtn);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Toast.makeText(getApplicationContext(), "adding album...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
