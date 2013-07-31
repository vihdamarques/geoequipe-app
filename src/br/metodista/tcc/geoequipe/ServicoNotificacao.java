package br.metodista.tcc.geoequipe;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ServicoNotificacao extends Service {
	private NotificationManager nm;
	private int NOTIFICATION = 30313;

	@Override
	public void onCreate() {
		super.onCreate();
		//final Context ctx = this;
		showNotification();
	}

	@Override
	public void onTaskRemoved(Intent rootIntent) {
		cancelNotification();
	}

	@SuppressWarnings("deprecation")
	private void showNotification() {
		nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		CharSequence text = getText(R.string.running);
		Notification notification = new Notification(R.drawable.icon, text, System.currentTimeMillis());
		/*Notification notification = new Notification.Builder(this)
        .setContentTitle("New mail from " + "test@gmail.com")
        .setContentText("Subject")
        .setSmallIcon(R.drawable.icon).build();*/

		 PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
	                new Intent(this, ActivityGeoequipe.class), 0);
		notification.setLatestEventInfo(this, getText(R.string.app_name),text, contentIntent);
		notification.flags = Notification.FLAG_FOREGROUND_SERVICE;
		nm.notify(NOTIFICATION, notification);
	}

	private void cancelNotification(){
		nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		nm.cancel(NOTIFICATION);
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
}