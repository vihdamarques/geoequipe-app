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
import br.metodista.tcc.util.Notify;
import br.metodista.tcc.util.Storage;

public class ServicoSinal extends Service {

	Service that = this;

	@Override
	public void onCreate() {
		super.onCreate();
		final Context ctx = this;
		if (podeMandarSinal(ctx)) {
			Log.i("Servico", "Iniciou serviço Sinal");
			Geolocation geo = new Geolocation(this);
			Notify.obtendoLocalizacao(ctx);
			API.setRunning();
	    	geo.getLocation(new Callback<Location>() {
				public void run(Location coord) {
					Log.i("Servico", "Resposta sinal. coord: " + coord);
					if (podeMandarSinal(ctx))
						if (coord != null) {
							API api = new API(ctx);
							api.enviarSinal (
								api.getIMEI()
							   ,api.getUser()
							   ,coord.getLatitude()
							   ,coord.getLongitude()
							);
						} else
							Notify.erroLocalizacao(ctx);
					API.setNotRunning();
					that.stopSelf();
				}
			});
		}
	}

	private boolean podeMandarSinal(Context ctx) {
		if (Storage.getEnviaSinal(ctx).equals("on") && !Storage.getUserId(ctx).equals("")) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public IBinder onBind(Intent arg0) { return null; }
}