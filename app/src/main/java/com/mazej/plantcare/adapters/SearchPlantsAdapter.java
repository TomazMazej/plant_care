package com.mazej.plantcare.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mazej.plantcare.activities.MainActivity;
import com.mazej.plantcare.R;
import com.mazej.plantcare.objects.MyPlant;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class SearchPlantsAdapter extends ArrayAdapter<MyPlant> {

    private Context mContext;
    private int mResource;

    private String id;
    private String image;
    private String name;

    private LayoutInflater inflater;

    private ImageView tvImage;
    private TextView tvName;

    public CheckBox simpleCheckBox;

    public SearchPlantsAdapter(Context context, int resource, ArrayList<MyPlant> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        id = getItem(position).getId();
        image = getItem(position).getImage();
        name = getItem(position).getName();

        inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        tvImage = (ImageView) convertView.findViewById(R.id.plantImage);
        tvName = (TextView) convertView.findViewById(R.id.plantName);

        tvImage.setImageResource(R.mipmap.cactus);
        tvName.setText(name);

        return convertView;
    }
}
