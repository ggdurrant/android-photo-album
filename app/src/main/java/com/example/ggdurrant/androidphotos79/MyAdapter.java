package com.example.ggdurrant.androidphotos79;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * specialized adapter which returns a view from a photo
 * @author George Durrant
 * @author Omar Morsy
 */
public class MyAdapter extends ArrayAdapter{

    private Context context;
    private ArrayList list;

    public MyAdapter(Context c, ArrayList l){
        super(c, R.layout.thumbnail_main, l);
        this.context = c;
        this.list = l;
    }

    @Override
    public View getView(int index, View v, ViewGroup g){

        if(v==null){
            LayoutInflater l = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = (View) l.inflate(R.layout.thumbnail_main, g, false);
        }

        ImageView i = (ImageView) v.findViewById(R.id.image);
        TextView t = (TextView) v.findViewById(R.id.photoName);

        Photo p = (Photo) list.get(index);
        i.setImageBitmap(p.getImage());
        t.setText(p.getPhotoName());

        return v;
    }
}
