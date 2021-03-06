package br.metodista.tcc.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;

public class Geolocation {
    private LocationManager    lm;
    private boolean            network_enabled
    		                  ,gps_enabled;
    private List<Location>     sinais;
    private Callback<Location> callback;
    private Timer              timer;
    private Context            context;
    private int                tempo;
    private int                motivo = 0;
    private WifiManager        wifiManager;

    public Geolocation(Context _context) {
    	this.context         = _context;
    	this.sinais          = new ArrayList<Location>();
    	this.network_enabled = false;
 		this.gps_enabled     = false;
 		this.tempo           = 1000 * 60;
 		this.wifiManager     = (WifiManager) this.context.getSystemService(Context.WIFI_SERVICE);
    }

    public int getMotivo(){
    	return this.motivo;
    }

    public boolean getLocation(Callback<Location> c) {
    	Log.i("Servico", "iniciou Geolocation");
    	this.callback = c;
        this.timer = new Timer();
        this.timer.schedule(new TimerTask(){
        	public void run(){
        		Log.i("Servico", "Timeout: Cancelando listeners e chamando callback");
        		lm.removeUpdates(ll);
        		timer.cancel();
        		callback.run(escolheMelhorSinal(true));
        	}
        }, tempo);

        if(this.lm == null)
            this.lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        try {
        	this.gps_enabled = this.lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        	Log.i("Servico", "GPS: " + this.gps_enabled);
    	} catch (Exception ex) {Log.i("Servico", "Erro GPS");}

        try {
        	this.network_enabled = this.lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        	Log.i("Servico", "NETWORK: " + this.network_enabled);
    	} catch (Exception ex) {Log.i("Servico", "Erro NETWORK");}

        if (!this.gps_enabled) this.motivo |= Notify.GPS_DISABLED;
        if (wifiManager.getWifiState() != WifiManager.WIFI_STATE_ENABLED) this.motivo |= Notify.WIFI_DISABLED;

        if (!this.gps_enabled && !this.network_enabled) return false;

        if (this.gps_enabled) this.lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, ll);
        if (this.network_enabled) this.lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0, ll);

        return true;
    }

    LocationListener ll = new LocationListener() {
        public void onLocationChanged(Location location) {
        	Log.i("Servico", "Recebeu Sinal: " + location);
        	//Toast.makeText(context, "Recebeu Sinal: " + location + " \n Total de sinais: " + (sinais.size() + 1), Toast.LENGTH_LONG).show();
        	if (sinais.size() < 7) {
        		if (location.getAccuracy() <= 40) {
        			Log.i("Servico", "adicionou sinal no array");
        			sinais.add(location);
        		}
        	} else {
        		timer.cancel();
        		lm.removeUpdates(this);
        		callback.run(escolheMelhorSinal(true));
        	}
        }
        public void onProviderDisabled(String provider) {}
        public void onProviderEnabled(String provider) {}
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    };

	private Location escolheMelhorSinal(boolean limparSinais) {
		Log.i("Servico", "Escolhendo o melhor sinal de " + this.sinais.size() + " sinais.");
		float bestAccuracy = Float.MAX_VALUE;
		Location candidate = null;
		if (this.sinais.size() > 0) {
			for (int i = 0; i < this.sinais.size(); i++) {
				Location sinal = this.sinais.get(i);
				float accuracy = sinal.getAccuracy();
				String provider = sinal.getProvider(); 
				Log.i("Servico", "Precis�o: " + accuracy);
				if (((provider.equals("network") && accuracy <= 40) || (provider.equals("gps") && accuracy < 30))
						&& accuracy <= bestAccuracy){
					candidate = sinal;
					bestAccuracy = accuracy;
				}
			}
			if (limparSinais)
				this.sinais.clear();
		}
		return candidate;
	}
}