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
import java.util.Date;

import androidx.annotation.NonNull;

public class MyPlantsAdapter extends ArrayAdapter<MyPlant> {

    private Context mContext;
    private int mResource;

    private String id;
    private String image;
    private String name;
    private int water;

    private LayoutInflater inflater;

    private int apiPlantId;
    private Date last_water_date;
    private int remaining_water_days;

    private ImageView tvImage;
    private TextView tvName;
    private TextView tvWater;

    public CheckBox simpleCheckBox;

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
        //apiPlantId = getItem(position)

        inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        tvImage = (ImageView) convertView.findViewById(R.id.plantImage);
        tvName = (TextView) convertView.findViewById(R.id.nameText);
        tvWater = (TextView) convertView.findViewById(R.id.waterText);
        simpleCheckBox = (CheckBox) convertView.findViewById(R.id.simpleCheckBox);

        tvImage.setImageResource(R.mipmap.cactus);
        tvName.setText(name);
        tvWater.setText("Needs water in " + water + " days");

        // Adds item to remove list
        simpleCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                if (arg1) {
                    MainActivity.deleteList.add(getItem(position).getApiPlantId());
                    System.out.println(position);
                    System.out.println(getItem(position).getApiPlantId());
                }
                else{
                    for(int i = 0; i < MainActivity.deleteList.size(); i++){
                        if(MainActivity.deleteList.get(i) == getItem(position).getApiPlantId()){
                            MainActivity.deleteList.remove(i);
                        }
                    }
                }
            }
        });
        return convertView;
    }
}
