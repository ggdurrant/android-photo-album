package com.example.ggdurrant.androidphotos79;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.content.DialogInterface;
import android.view.View;
import java.util.ArrayList;
import java.io.File;
import android.app.AlertDialog;
import android.widget.*;
import android.content.Context;


public class AlbumActivity extends AppCompatActivity{

    private GridView grid;
    private Album thisAlbum;
    private MyAdapter adapter;
    Context c = this;
    int i;

    protected void onCreate(final Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_main);

        grid = (GridView) findViewById(R.id.grid);

        adapter = new MyAdapter(this,thisAlbum.getPhotos());
    }

}
