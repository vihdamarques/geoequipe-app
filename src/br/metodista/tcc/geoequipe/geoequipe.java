package br.metodista.tcc.geoequipe;

import android.content.Intent;
import android.os.Bundle;
import org.apache.cordova.*;

public class Geoequipe extends DroidGap
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // Set by <content src="index.html" /> in config.xml
        super.loadUrl(Config.getStartUrl());
        startService(new Intent("INICIAR_SERVICO"));
        //super.loadUrl("file:///android_asset/www/index.html")
    }
}

