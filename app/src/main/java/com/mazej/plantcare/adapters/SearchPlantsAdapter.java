package com.mazej.plantcare.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.mazej.plantcare.activities.MainActivity;
import com.mazej.plantcare.R;
import com.mazej.plantcare.objects.MyPlant;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class SearchPlantsAdapter extends ArrayAdapter<MyPlant> implements Filterable {

    private Context mContext;
    private int mResource;
    private LayoutInflater inflater;

    private String id;
    private String image;
    private String name;

    private ImageView tvImage;
    private TextView tvName;
    public CheckBox simpleCheckBox;

    public ArrayList<MyPlant> plantsArrayList;
    public ArrayList<MyPlant> orig;

    public SearchPlantsAdapter(Context context, int resource, ArrayList<MyPlant> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        this.plantsArrayList = objects;
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
        simpleCheckBox = (CheckBox) convertView.findViewById(R.id.simpleCheckBox);

        tvImage.setImageResource(Integer.parseInt(image));
        tvName.setText(name);

        // Add on list to save plants
        simpleCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                if (arg1) {
                    MainActivity.addPlantsList.add(Integer.parseInt(getItem(position).getId()));
                    System.out.println(position);
                } else {
                    for (int i = 0; i < MainActivity.addPlantsList.size(); i++) {
                        if (MainActivity.addPlantsList.get(i) == Integer.parseInt(getItem(position).getId())) {
                            MainActivity.addPlantsList.remove(i);
                        }
                    }
                }
            }
        });
        return convertView;
    }

    public Filter getFilter() { // Make filter for custom adapter
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<MyPlant> results = new ArrayList<MyPlant>();
                if (orig == null)
                    orig = plantsArrayList;
                if (constraint != null) {
                    if (orig != null && orig.size() > 0) {
                        for (final MyPlant g : orig) {
                            if (g.getName().toLowerCase().contains(constraint.toString()))
                                results.add(g);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                plantsArrayList = (ArrayList<MyPlant>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return plantsArrayList.size();
    }

    @Override
    public MyPlant getItem(int position) {
        return plantsArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
