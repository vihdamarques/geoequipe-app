package br.metodista.tcc.geoequipe;

import android.content.Intent;
import android.os.Bundle;
import org.apache.cordova.*;

public class ActivityGeoequipe extends DroidGap {
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	sendBroadcast(new Intent("BROADCAST_SINAL"));
    	super.onCreate(savedInstanceState);
    	super.setIntegerProperty("splashscreen", R.drawable.splashscreen);
        super.loadUrl(Config.getStartUrl(), 10000);
    }
}