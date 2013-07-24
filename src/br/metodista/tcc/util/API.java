package br.metodista.tcc.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;
import br.metodista.tcc.geoequipe.EnviarSinal;

@SuppressLint("SimpleDateFormat")
public class API {
	private boolean rodar;
	private Context ctx;

	public API(Context _context){
		this.ctx = _context;
		this.rodar = true;
		//Toast.makeText(ctx, "Criou API", Toast.LENGTH_SHORT).show();
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

        return Base64.encodeBytes(json.toString().getBytes());
    }

    public String getHost(){
    	//return "http://10.0.2.2";
    	return "http://geoequipe.aws.af.cm";
    }

    public int getPort(){
    	//return 3014;
    	return 80;
    }

    public String getUrl(String param){
    	return getHost() + ":" + getPort() + param;
    }

    public String getIMEI(){
    	TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
    	Log.i("Servico", "IMEI: " + tm.getDeviceId());
    	return tm.getDeviceId();
    	//return 111111111;
    }

    public int getUser(){
    	return 1;
    }

    public void enviarSinal(String imei, int user, double lat, double lng) {
    	Log.i("Servico", "Enviando sinal...");
    	new EnviarSinal(this.ctx).execute(getUrl("/sinal/" + geraParametros(imei, user, lat, lng)));
    }

	public boolean getRodar() {
		return rodar;
	}

	public void setRodar(boolean bool) {
		rodar = bool;
	}
}
