package br.metodista.tcc.geoequipe;

//import br.metodista.tcc.util.API;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ServicoSinal extends Service /*implements Runnable */{
	/*@Override
	public void run() {
		// TODO Auto-generated method stub
	}*/

	@Override
	 public void onStart(Intent intent, int startId) {
			new EnviarSinal().execute();
			stopSelf();
	 }

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
}
