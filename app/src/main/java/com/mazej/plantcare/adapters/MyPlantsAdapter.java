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

import com.mazej.plantcare.MainActivity;
import com.mazej.plantcare.R;
import com.mazej.plantcare.objects.MyPlant;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class MyPlantsAdapter extends ArrayAdapter<MyPlant> {

    private Context mContext;
    private int mResource;

    private String id;
    private String image;
    private String name;
    private int water;

    private LayoutInflater inflater;

    private ImageView tvImage;
    private TextView tvName;
    private TextView tvWater;

    public static CheckBox simpleCheckBox;

    public MyPlantsAdapter(Context context, int resource, ArrayList<MyPlant> objects) {
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
        water = getItem(position).getWater();

        inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        tvImage = (ImageView) convertView.findViewById(R.id.plantImage);
        tvName = (TextView) convertView.findViewById(R.id.plantName);
        tvWater = (TextView) convertView.findViewById(R.id.plantText);
        simpleCheckBox = (CheckBox) convertView.findViewById(R.id.simpleCheckBox);

        tvImage.setImageResource(R.mipmap.cactus);
        tvName.setText(name);
        tvWater.setText("Needs water in " + water + " days");

        // Adds item to remove list
        simpleCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                if (arg1) {
                    MainActivity.deleteList.add(position);
                    System.out.println(position);
                }
                else{
                    for(int i = 0; i < MainActivity.deleteList.size(); i++){
                        if(MainActivity.deleteList.get(i) == position){
                            MainActivity.deleteList.remove(i);
                        }
                    }
                }
            }
        });
        return convertView;
    }
}
