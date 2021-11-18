package com.share4happy;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.share4happy.model.Location;

import java.util.List;

public class LocationAdapter extends ArrayAdapter<Location> {

    Activity context;
    int resource;
    @NonNull List<Location> objects;
    public LocationAdapter(@NonNull Activity context, int resource, @NonNull List<Location> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View row, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        row = inflater.inflate(this.resource,null);

        //Hiện thị một số trường chọn lọc
        TextView name = row.findViewById(R.id.name);
        TextView death = row.findViewById(R.id.death);
        TextView cases = row.findViewById(R.id.cases);
        TextView casesToday = row.findViewById(R.id.casesToday);

        //Muốn hiện thị trường dữ liệu thì tùy bạn nhé

        name.setText(""+this.objects.get(position).getName());
        death.setText(""+this.objects.get(position).getDeath());
        cases.setText(""+this.objects.get(position).getCases());
        casesToday.setText(""+this.objects.get(position).getCasestoday());
        return row;
    }
}
