package br.metodista.tcc.geoequipe;

import android.content.Intent;
import android.os.Bundle;
import org.apache.cordova.*;

public class ActivityGeoequipe extends DroidGap {
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	sendBroadcast(new Intent("ALARM_MANAGER_SINAL"));
    	super.onCreate(savedInstanceState);
        super.loadUrl(Config.getStartUrl());
    }
}