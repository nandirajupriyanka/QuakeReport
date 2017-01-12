package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.io.IOException;
import java.util.List;

/**
 * Created by priyankanandiraju on 1/11/17.
 */

public class EarthQuakeLoader extends AsyncTaskLoader<List<EarthQuake>> {

    private String url;

    public EarthQuakeLoader(Context context, String eqUrl) {
        super(context);
        url = eqUrl;
    }

    @Override
    public List<EarthQuake> loadInBackground() {
        List<EarthQuake> earthQuake = null;
        String jsonResponse = "";
        try {
            jsonResponse = QueryUtils.makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        earthQuake = QueryUtils.extractEarthquakes(jsonResponse);
        return earthQuake;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}

