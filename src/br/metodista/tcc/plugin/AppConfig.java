package br.metodista.tcc.plugin;

import org.apache.cordova.api.CallbackContext;
import org.apache.cordova.api.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import android.content.Context;
import android.util.Log;
import br.metodista.tcc.util.Storage;
import br.metodista.tcc.util.Util;

public class AppConfig extends CordovaPlugin  {

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
        	Util.iniciaNotificacao(ctx);
        	return enviaSinal;
        } else if (action.equals("setEnviaSinalOff")) {
        	enviaSinal = Storage.setEnviaSinalOff(ctx);
        	Util.iniciaNotificacao(ctx);
        	return enviaSinal;
        } else if (action.equals("getEnviaSinal")) {
        	Log.i("Servico", "getEnviaSinal: " + Storage.getEnviaSinal(ctx));
        	callbackContext.success(Storage.getEnviaSinal(ctx));
            return true;
        }
        return false;
    }

}
