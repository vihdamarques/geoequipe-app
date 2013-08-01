package br.metodista.tcc.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import br.metodista.tcc.geoequipe.ActivityGeoequipe;
import br.metodista.tcc.geoequipe.R;

public class Util {
	private static int NOTIFICATION = 30313;
	public static int ICON_OK   = R.drawable.icon;
	public static int ICON_FAIL = R.drawable.icon_fail;

	@SuppressWarnings("deprecation")
	public static void showNotification(Context ctx, int icon, String text) {
		NotificationManager nm = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification(icon, text, System.currentTimeMillis());
		PendingIntent contentIntent = PendingIntent.getActivity(ctx, 0, new Intent(ctx, ActivityGeoequipe.class), 0);
		notification.setLatestEventInfo(ctx, ctx.getText(R.string.app_name),text, contentIntent);
		notification.flags = Notification.FLAG_FOREGROUND_SERVICE;
		nm.notify(NOTIFICATION, notification);
	}

	public static void cancelNotification(Context ctx){
		NotificationManager nm = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
		nm.cancel(NOTIFICATION);
	}

	@SuppressLint("SimpleDateFormat")
	public static String now(){
		return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
	}
}
