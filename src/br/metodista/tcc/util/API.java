package br.metodista.tcc.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

@SuppressLint("SimpleDateFormat")
public class API {
	public static void acessaURL(String host, int port, String param) {
		Log.i("Servico", "entrou acessa URL: url => " + host + ":" + port + param);
		try {
            URL url = new URL(host + ":" + port + param);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(60000);
            conn.setReadTimeout(60000);
            conn.getResponseCode(); // Utilizado para solicitar a resposta
            conn.disconnect();
        } catch (MalformedURLException e){
            System.out.println("Erro ao criar URL. Formato inválido.");
        } catch (IOException e){
            System.out.println("Erro ao Acessar a URL => " + e.getMessage());
            e.printStackTrace(System.out);
        }
    }

    public static String geraParametros(int imei, int user, double lat, double lng) {

        JSONObject json = new JSONObject();
        try {
	        json.put("imei", String.valueOf(imei));
	        json.put("user", String.valueOf(user));
	        json.put("data", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
	
	        JSONObject coord = new JSONObject();
	        coord.put("lat",String.valueOf(lat));
	        coord.put("lng",String.valueOf(lng));
	
	        json.put("coord", coord);
        } catch (JSONException e){
        	e.printStackTrace(System.out);
        }

        //System.out.println(json.toString());
        return Base64.encodeBytes(json.toString().getBytes());
    }

    public static String getUrl(){
    	return "http://10.0.2.2";
    }

    public static int getPort(){
    	return 3014;
    }

    public static int getIMEI(){
    	//return android.telephony.TelephonyManager.getDeviceId();
    	return 111111111;
    }

    public static int getUser(){
    	return 1;
    }

    public static Location getCoord(Context ctx){
    	final String locProvider = LocationManager.GPS_PROVIDER; //NETWORK_PROVIDER
    	LocationManager lm = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
    	Location coord = lm.getLastKnownLocation(locProvider);
    	return coord;
    }

    public static void enviarSinal(int imei, int user, double lat, double lng) {
    	acessaURL(getUrl(), getPort(), "/sinal/" + geraParametros(imei, user, lat, lng));
    }

    public static void atualizarPosicao(final Context ctx){
    	new Thread(new Runnable() {
		    public void run() {
		    	Looper.prepare();
		    	final String locProvider = LocationManager.GPS_PROVIDER; //NETWORK_PROVIDER
		    	LocationManager lm = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
		    	LocationListener ll = new LocationListener() {
		    		@Override
		            public void onLocationChanged(final Location coord) {
		            	Log.i("Servico", "Atualizou posição");
		            	LocationManager lm = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
		            	lm.removeUpdates(this);
		            }
		            @Override
		            public void onStatusChanged(final String provider, final int status, final Bundle extras) {}
		            @Override
		            public void onProviderEnabled(final String provider) {}
		            @Override
		            public void onProviderDisabled(final String provider) {}
		        };
		        lm.requestLocationUpdates(locProvider, 0, 0, ll);
		        Looper.loop();
		        Looper.myLooper().quit();
		    }
		}).start();
    }
}
