package br.metodista.tcc.geoequipe;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmManagerSinal extends BroadcastReceiver {
	private static final long REPEAT_TIME = 1000 * 60 * 2;

	@Override
	public void onReceive(Context context, Intent it) {
		Log.i("Servico", "Recebeu broadcast");
		Intent serviceIntent = new Intent(context, ServicoSinal.class);
		PendingIntent pi = PendingIntent.getService(context, 0, serviceIntent,PendingIntent.FLAG_UPDATE_CURRENT);
	    AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
	    alarm.cancel(pi);
	    alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), REPEAT_TIME, pi);
	}
}