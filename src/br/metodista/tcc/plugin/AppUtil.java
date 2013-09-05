package br.metodista.tcc.plugin;

import org.apache.cordova.api.CallbackContext;
import org.apache.cordova.api.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import br.metodista.tcc.util.Blowfish;
import br.metodista.tcc.util.Notify;
import br.metodista.tcc.util.Storage;

public class AppUtil extends CordovaPlugin  {

	private Context ctx;

	@Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		this.ctx = this.cordova.getActivity().getBaseContext();
		boolean enviaSinal;
		if (action.equals("getUserId")) {
            callbackContext.success(Storage.getUserId(ctx));
            return true;
        } else if (action.equals("setUserId")) {
            return Storage.setUserId(ctx, args.getString(0));
        } else if (action.equals("setEnviaSinalOn")) {
        	enviaSinal = Storage.setEnviaSinalOn(ctx);
        	Notify.iniciaNotificacao(ctx);
        	return enviaSinal;
        } else if (action.equals("setEnviaSinalOff")) {
        	enviaSinal = Storage.setEnviaSinalOff(ctx);
        	Notify.iniciaNotificacao(ctx);
        	return enviaSinal;
        } else if (action.equals("getEnviaSinal")) {
        	callbackContext.success(Storage.getEnviaSinal(ctx));
            return true;
        } else if (action.equals("openMaps")) {
        	String lat = args.getString(0)
        		  ,lng = args.getString(1);

        	ctx.startActivity(
    			new Intent(
    			    android.content.Intent.ACTION_VIEW,
    			    Uri.parse("geo:"+lat+","+lng+"?q="+lat+","+lng)
			    ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
			);

        	return true;
        } else if (action.equals("updateNotification")){
        	Notify.iniciaNotificacao(ctx);
        	return true;
        } else if (action.equals("encrypt")) {
        	Blowfish bf = new Blowfish();
        	callbackContext.success(bf.encrypt(args.getString(0)));
        	return true;
        }
        return false;
    }
}