package br.metodista.tcc.geoequipe;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.util.Log;
import br.metodista.tcc.util.API;
import br.metodista.tcc.util.Callback;
import br.metodista.tcc.util.Geolocation;
import br.metodista.tcc.util.Storage;
import br.metodista.tcc.util.Util;

public class ServicoSinal extends Service {

	Service that = this;

	@Override
	public void onCreate() {
		super.onCreate();
		final Context ctx = this;
		if (Storage.getEnviaSinal(ctx).equals("on")) {
			Log.i("Servico", "Iniciou serviço Sinal");
			Geolocation geo = new Geolocation(this);
			Util.showNotification(ctx, Util.ICON_OK, "Obtendo localização...");
	    	geo.getLocation(new Callback<Location>() {
				public void run(Location coord) {
					Log.i("Servico", "Resposta sinal. coord: " + coord);
					if (coord != null) {
						API api = new API(ctx);
						api.enviarSinal (
							api.getIMEI()
						   ,api.getUser()
						   ,coord.getLatitude()
						   ,coord.getLongitude()
						);
					} else {
						Util.showNotification(ctx, Util.ICON_FAIL, "Não foi possível obter localização");
					}
	
					that.stopSelf();
				}
			});
		}
	}

	@Override
	public IBinder onBind(Intent arg0) { return null; }
}