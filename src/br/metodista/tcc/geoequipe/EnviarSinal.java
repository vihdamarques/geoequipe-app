package br.metodista.tcc.geoequipe;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class EnviarSinal extends AsyncTask<String, Void, String>{
	Exception e;
	Context ctx;

	public EnviarSinal(Context _ctx){
		this.ctx = _ctx;
	}

	@Override
	protected String doInBackground(String... _url) {
		String responseCode = "";
		try {
			URL url = new URL(_url[0]);

			Log.i("Servico", "entrou acessa URL: url => " + url.toString());
			//Toast.makeText(this.ctx, "entrou acessa URL: url => " + url.toString(), Toast.LENGTH_LONG).show();

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(60000);
            conn.setReadTimeout(60000);
            responseCode = String.valueOf(conn.getResponseCode()); //Utilizado para solicitar a resposta
            conn.disconnect();
		} catch (MalformedURLException e) {
            System.out.println("Erro ao criar URL. Formato inválido.");
            this.e = e;
        } catch (IOException e) {
            System.out.println("Erro ao Acessar a URL => " + e.getMessage());
            e.printStackTrace(System.out);
            this.e = e;
        }
		return responseCode;
	}

	@Override
	protected void onPreExecute() {
	}

	@Override
    protected void onPostExecute(String response) {
		Toast.makeText(this.ctx, "Resposta da URL: " + response, Toast.LENGTH_LONG).show();
	}
}