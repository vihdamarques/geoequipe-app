package br.metodista.tcc.geoequipe;

import br.metodista.tcc.util.API;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.util.Log;

public class ServicoSinal extends Service implements Runnable {
	private boolean rodar = true;
	private Context ctx = this;
	private int counter = 0;

	public void onCreate() {
		super.onCreate();
		Log.i("Servico", "Iniciou serviço");
		API.atualizarPosicao(ctx);
		Log.i("Servico", "Fazendo requisição de localização");
		new Thread(this).start();
	}
	
	@Override
	public void run(){
		while (this.rodar){
			// Aguarda 30 segundos para o envio
			try { Thread.sleep(30 * 1000); } catch (InterruptedException e) {}
			new Thread(new Runnable() {
			    public void run() {
			        Log.i("Servico", "Enviando sinal...");
			        Location coord = API.getCoord(ctx);
					API.enviarSinal(API.getIMEI(), API.getUser(), coord.getLatitude(), coord.getLongitude());
					API.atualizarPosicao(ctx);
			    }
			}).start();
			if (++counter == 3) { this.rodar = false; }
		}
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
}
