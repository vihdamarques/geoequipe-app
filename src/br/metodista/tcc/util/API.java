package br.metodista.tcc.util;

import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.util.Log;
import br.metodista.tcc.geoequipe.EnviarSinal;

public class API {
	private Context ctx;
	private String  host = "http://geoequipe.aws.af.cm"; // http://10.0.2.2
	private int     port = 80; // 3014
	private Handler handler = new Handler(Looper.getMainLooper());
	private static boolean rodando;

	public API(Context _ctx) {
		this.ctx    = _ctx;
	}

    public String geraParametros(String imei, String user, double lat, double lng) {
        JSONObject json = new JSONObject();

        try {
	        json.put("imei", String.valueOf(imei));
	        json.put("user", String.valueOf(user));
	        json.put("data", Util.now());

	        JSONObject coord = new JSONObject();
	        coord.put("lat",String.valueOf(lat));
	        coord.put("lng",String.valueOf(lng));

	        json.put("coord", coord);
        } catch (JSONException e){
        	e.printStackTrace(System.out);
        }

        Blowfish bf = new Blowfish();
        return bf.encrypt(json.toString()).replaceAll("\\/", "_").replaceAll("\\+", "-");
        //return Base64.encodeBytes(json.toString().getBytes()).replaceAll("\\/", "_").replaceAll("\\+", "-");
    }

    public void enviarSinal(final String imei, final String user, final double lat, final double lng) {
    	Log.i("Servico", "Enviando sinal...");
    	handler.post(new Runnable() {
            public void run() {
		    	new EnviarSinal(ctx)
		    	   .execute(getUrl("/sinal/" + geraParametros(imei, user, lat, lng)));
            }
         });
    }

    public String getIMEI() {
    	TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
    	Log.i("Servico", "IMEI: " + tm.getDeviceId());
    	return tm.getDeviceId();
    }

    public String getUrl(String param) {
    	return getHost() + ":" + getPort() + param;
    }

    public String getHost() {
    	return this.host;
    }

    public int getPort() {
    	return this.port;
    }

    public String getUser() {
    	return Storage.getUserId(this.ctx);
    }

    public static void setRunning() {
		API.rodando = true;
	}

	public static void setNotRunning() {
		API.rodando = false;
	}

	public static boolean isRunning() {
		return API.rodando;
	}
}