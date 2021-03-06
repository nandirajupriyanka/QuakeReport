package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by priyankanandiraju on 12/18/16.
 */

public class EarthQuakeAdapter extends ArrayAdapter<EarthQuake> {


    private static final String LOCATION_SEPARATOR = " of ";
    String primaryLocationName;
    String locationOffsetName;

    public EarthQuakeAdapter(Context context, ArrayList<EarthQuake> earthquakes) {
        super(context, 0, earthquakes);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null)
           view =  LayoutInflater.from(getContext()).inflate(R.layout.earthquake_row_item, parent, false);

        EarthQuake item = getItem(position);

        TextView mag = (TextView) view.findViewById(R.id.tv_mag);
        TextView locationOffset = (TextView) view.findViewById(R.id.tv_place);
        TextView primaryLocation = (TextView) view.findViewById(R.id.tv_place_2);
        TextView date = (TextView) view.findViewById(R.id.tv_date);
        TextView time = (TextView) view.findViewById(R.id.tv_time);

        if (item != null) {
            // Set the proper background color on the magnitude circle.
            // Fetch the background from the TextView, which is a GradientDrawable.
            GradientDrawable magnitudeCircle = (GradientDrawable) mag.getBackground();

            // Get the appropriate background color based on the current earthquake magnitude
            int magnitudeColor = getMagnitudeColor(item.getMagnitude());


            // Set the color on the magnitude circle
            magnitudeCircle.setColor(magnitudeColor);

            mag.setText(formatMagnitude(item.getMagnitude()));

            // Name
            formatLocationName(item.getName());
            locationOffset.setText(locationOffsetName);
            primaryLocation.setText(primaryLocationName);

            // Date
            Date dateObject = new Date(item.getDate());
            date.setText(formatDate(dateObject));

            time.setText(formatTime(dateObject));
        }

        return view;
    }

    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }

    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    private void formatLocationName(String originalLocation) {
        if (originalLocation.contains(LOCATION_SEPARATOR)) {
            String[] parts = originalLocation.split(LOCATION_SEPARATOR);
            locationOffsetName = parts[0] + LOCATION_SEPARATOR;
            primaryLocationName = parts[1];
        } else {
            locationOffsetName = "Near the";
            primaryLocationName = originalLocation;
        }
    }

    /**
     * Return the formatted magnitude string showing 1 decimal place (i.e. "3.2")
     * from a decimal magnitude value.
     */
    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }

}
