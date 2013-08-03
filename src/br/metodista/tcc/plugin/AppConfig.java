package br.metodista.tcc.plugin;

import org.apache.cordova.api.CallbackContext;
import org.apache.cordova.api.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.util.Log;
import br.metodista.tcc.util.Util;

public class AppConfig extends CordovaPlugin  {

	private Context ctx;

	@Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		this.ctx = this.cordova.getActivity().getBaseContext();

		if (action.equals("getUserId")) {
            String userId = Util.getUserId(ctx);
            Log.i("Servico", "getUserId: " + userId);
            callbackContext.success(userId);
            return true;
        } else if (action.equals("setUserId")) {
            return Util.setUserId(ctx, args.getString(0));
        }
        return false;
    }

}
