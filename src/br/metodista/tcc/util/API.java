package br.metodista.tcc.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.util.Log;
import br.metodista.tcc.geoequipe.EnviarSinal;

@SuppressLint("SimpleDateFormat")
public class API {
	private boolean rodar;
	private Context ctx;
	private String  host = "http://geoequipe.aws.af.cm"; // http://10.0.2.2
	private int     port = 80; // 3014
	private String  key  = "G3@#qU1p";
	private Handler handler = new Handler(Looper.getMainLooper());

	public API(Context _ctx){
		this.ctx   = _ctx;
		this.rodar = true;
	}

    public String geraParametros(String imei, int user, double lat, double lng) {
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
        Blowfish bf = new Blowfish(this.getKey());
        return bf.encrypt(json.toString());
        //return Base64.encodeBytes(json.toString().getBytes());
    }

    public void enviarSinal(final String imei, final int user, final double lat, final double lng) {
    	Log.i("Servico", "Enviando sinal...");
    	handler.post(new Runnable() {
            public void run() {
		    	new EnviarSinal(ctx)
		    	   .execute(getUrl("/sinal/" + geraParametros(imei, user, lat, lng)
		    			                      .replaceAll("\\/", "_")
		    			                      .replaceAll("\\+", "-")
		    			                      ));
            }
         });
    }

    public String getIMEI(){
    	TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
    	Log.i("Servico", "IMEI: " + tm.getDeviceId());
    	return tm.getDeviceId();
    }

    public String getUrl(String param){
    	return getHost() + ":" + getPort() + param;
    }

    public String getHost(){
    	return this.host;
    }

    public int getPort(){
    	return this.port;
    }

    private String getKey(){
    	return this.key;
    }

    public int getUser(){
    	return 1;
    }

	public boolean getRodar() {
		return rodar;
	}

	public void setRodar(boolean bool) {
		rodar = bool;
	}
}
