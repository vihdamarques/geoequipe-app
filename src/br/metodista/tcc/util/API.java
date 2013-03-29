package br.metodista.tcc.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.StrictMode;
import android.util.Log;

public class API {
	public static void acessaURL(String host, int port, String param) {

		try {
            URL url = new URL(host + ":" + port + param);
            Log.i("Servico", "url => " + host + ":" + port + param);
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
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        JSONObject json = new JSONObject();
        try {
	        json.put("imei", String.valueOf(imei));
	        json.put("user", String.valueOf(user));
	        json.put("data", fmt.format(new Date()));
	
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
}
