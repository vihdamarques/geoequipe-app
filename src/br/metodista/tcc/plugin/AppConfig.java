package br.metodista.tcc.plugin;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.cordova.api.CallbackContext;
import org.apache.cordova.api.CordovaPlugin;

import android.content.Context;

public class AppConfig extends CordovaPlugin  {

	private String FILENAME_USUARIO = "usuario";
	//private String FILENAME_SINAL   = "sinal";
	private Context ctx = this.cordova.getActivity().getApplicationContext();

	@Override
    public boolean execute(String action, String param, CallbackContext callbackContext) {
        if (action.equals("getUserId")) {
            this.getUserId(callbackContext);
            return true;
        } else if (action.equals("setUserId")) {
        	this.setUserId(param, callbackContext);
            return true;
        }
        return false;
    }

	private void getUserId(CallbackContext callbackContext) {
        String userId = "";

        try {
			FileInputStream arquivo = ctx.openFileInput(FILENAME_USUARIO);
			int content;
			while ((content = arquivo.read()) != -1) {
				userId = userId + (char) content;
			}
			arquivo.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

        callbackContext.success(userId);
    }

	private void setUserId(String userId, CallbackContext callbackContext) {
		if (userId!= null && userId.length() > 0) {
			FileOutputStream fos;
			try {
				fos = ctx.openFileOutput(FILENAME_USUARIO, Context.MODE_PRIVATE);
				fos.write(userId.getBytes());
				fos.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
