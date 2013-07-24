package br.metodista.tcc.geoequipe;

import br.metodista.tcc.util.API;
import br.metodista.tcc.util.Callback;
import br.metodista.tcc.util.Geolocation;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class ServicoSinal extends Service implements Runnable {

	public void onCreate() {
		super.onCreate();
		final Context ctx = this;
		Toast.makeText(ctx, "Iniciou servi�o...", Toast.LENGTH_SHORT).show();
		Log.i("Servico", "Iniciou servi�o");
		Geolocation geo = new Geolocation(this);
    	geo.getLocation(new Callback<Location>() {
			public void run(Location coord) {
				//Toast.makeText(ctx, "Pegou localiza��o", Toast.LENGTH_SHORT).show();
				Log.i("Servico", "Resposta sinal. coord: " + coord);
				if (coord != null) {
					API api = new API(ctx);
					api.enviarSinal (
						api.getIMEI()
					   ,api.getUser()
					   ,coord.getLatitude()
					   ,coord.getLongitude()
					);
				}
			}
		});
	}

	@Override
	public void run() {
		/*API api = new API(this);
		while (true) {
			if (api.getRodar()) {
				api.setRodar(false);
				Geolocation geo = new Geolocation();
		    	geo.getLocation(this, new Callback<Location>() {
					@Override
					public void run(Location coord) {
						if (coord != null) {
							API.enviarSinal (
								API.getIMEI()
							   ,API.getUser()
							   ,coord.getLatitude()
							   ,coord.getLongitude()
							);
							API.setRodar(true);
						}
					}
				});
			}
			try { Thread.sleep(30 * 1000); } catch (InterruptedException e) {}
		}*/
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	/*while (API.getRodar()) {
	// Aguarda 30 segundos para o envio
	try { Thread.sleep(30 * 1000); } catch (InterruptedException e) {}
	new Thread(new Runnable() {
	    public void run() {
	        Log.i("Servico", "Enviando sinal...");
	        Location coord = API.getCoord(ctx);
	        if (coord == null) {
	        	API.atualizarPosicao(ctx, true);
	        } else {
	        	API.enviarSinal(API.getIMEI(), API.getUser(), coord.getLatitude(), coord.getLongitude());
	        	API.atualizarPosicao(ctx, false);
	        }
	    }
	}).start();
	if (++counter == 3) { API.setRodar(false); }
}*/
}
