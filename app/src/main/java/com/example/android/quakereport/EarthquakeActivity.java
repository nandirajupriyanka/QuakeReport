/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.android.quakereport.QueryUtils.EQ_URL;

public class EarthquakeActivity extends AppCompatActivity {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private ListView earthquakeListView;
    private EarthQuakeAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        // Create a fake list of earthquake locations.

//        ArrayList<EarthQuake> earthquakes = QueryUtils.extractEarthquakes();

        // Find a reference to the {@link ListView} in the layout
        earthquakeListView = (ListView) findViewById(R.id.list);
        mAdapter = new EarthQuakeAdapter(EarthquakeActivity.this, new ArrayList<EarthQuake>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        if (earthquakeListView != null) {
            earthquakeListView.setAdapter(mAdapter);
        } else {
            Log.e(LOG_TAG, "ARRAY ADAPTER IS NULL");
        }

        // to open a website with more information about the selected earthquake.
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current earthquake that was clicked on
                EarthQuake currentEarthquake = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri earthquakeUri = null;
                if (currentEarthquake != null) {
                    earthquakeUri = Uri.parse(currentEarthquake.getUrl());

                    // Create a new intent to view the earthquake URI
                    Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                    // Send the intent to launch a new activity
                    startActivity(websiteIntent);
                }
            }
        });
        new FetchEarthQuakeData().execute(EQ_URL);
    }

    private class FetchEarthQuakeData extends AsyncTask<String, Void, List<EarthQuake>> {


        @Override
        protected List<EarthQuake> doInBackground(String... params) {
            List<EarthQuake> earthQuake = null;
            for (int i = 0; i < params.length; i++) {
                String jsonResponse = "";
                try {
                    jsonResponse = QueryUtils.makeHttpRequest(params[i]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                earthQuake = QueryUtils.extractEarthquakes(jsonResponse);
            }
            return earthQuake;
        }

        @Override
        protected void onPostExecute(List<EarthQuake> data) {
            // Clear the adapter of previous earthquake data
            mAdapter.clear();

            // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
            // data set. This will trigger the ListView to update.
            if (data != null && !data.isEmpty()) {
                mAdapter.addAll(data);
            }
        }
    }

}
