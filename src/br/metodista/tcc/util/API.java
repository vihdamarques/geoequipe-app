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
import android.util.Log;

@SuppressLint("SimpleDateFormat")
public class API {
	private static boolean rodar = true;

	public static void acessaURL(String host, int port, String param) {
		Log.i("Servico", "entrou acessa URL: url => " + host + ":" + port + param);
		try {
            URL url = new URL(host + ":" + port + param);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(60000);
            conn.setReadTimeout(60000);
            conn.getResponseCode(); //Utilizado para solicitar a resposta
            conn.disconnect();
        } catch (MalformedURLException e) {
            System.out.println("Erro ao criar URL. Formato inválido.");
        } catch (IOException e) {
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

    public static void enviarSinal(int imei, int user, double lat, double lng) {
    	Log.i("Servico", "Enviando sinal...");
    	acessaURL(getUrl(), getPort(), "/sinal/" + geraParametros(imei, user, lat, lng));
    }

	public static boolean getRodar() {
		return rodar;
	}

	public static void setRodar(boolean bool) {
		rodar = bool;
	}
}
