package br.metodista.tcc.geoequipe;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.apache.cordova.*;

public class ActivityGeoequipe extends DroidGap {
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	Log.i("Servico", "Iniciou Activity");
    	sendBroadcast(new Intent("ALARM_MANAGER_SINAL"));
    	super.onCreate(savedInstanceState);
        // Set by <content src="index.html" /> in config.xml
        super.loadUrl(Config.getStartUrl());
        //super.loadUrl("file:///android_asset/www/index.html")
    }
}