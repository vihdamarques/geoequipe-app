package br.metodista.tcc.geoequipe;

import br.metodista.tcc.util.API;
import android.os.AsyncTask;

public class EnviarSinal extends AsyncTask<String, Void, String> {
	private Exception exception;
	
	@Override
	protected void onPostExecute(String retorno) {
		 // TODO: check this.exception 
         // TODO: do something with the feed
     }

	@Override
	protected String doInBackground(String... url) {
		API.acessaURL("http://10.0.2.2", 3014, "/sinal/" + API.geraParametros(111111111, 1, -23.8241324, -46.765872));
		return null;
	}
}
