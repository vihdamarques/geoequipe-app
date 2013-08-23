package br.metodista.tcc.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import br.metodista.tcc.geoequipe.ActivityGeoequipe;
import br.metodista.tcc.geoequipe.R;

public class Notify {

	private static int NOTIFICATION = 716979;

	public static int ICON_OK       = R.drawable.icon;
	public static int ICON_FAIL     = R.drawable.icon_fail;

	@SuppressWarnings("deprecation")
	private static void showNotification(Context ctx, int icon, String text) {
		NotificationManager nm = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification(icon, text, System.currentTimeMillis());
		PendingIntent contentIntent = PendingIntent.getActivity(ctx, 0, new Intent(ctx, ActivityGeoequipe.class), 0);
		notification.setLatestEventInfo(ctx, ctx.getText(R.string.app_name),text, contentIntent);
		notification.flags = Notification.FLAG_FOREGROUND_SERVICE;
		nm.notify(NOTIFICATION, notification);
	}

	@SuppressWarnings("unused")
	private static void cancelNotification(Context ctx){
		NotificationManager nm = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
		nm.cancel(NOTIFICATION);
	}

	public static void iniciaNotificacao(Context ctx){
		if (Storage.getUserId(ctx).equals("")) {
			fazerLogin(ctx);
		} else {
			if (Storage.getEnviaSinal(ctx).equals("on")){
				envioAtivo(ctx);
			} else {
				envioInativo(ctx);
			}
		}
	}

	public static void fazerLogin(Context ctx) {
		showNotification(ctx, ICON_FAIL, "Clique para fazer login");
	}

	public static void envioAtivo(Context ctx) {
		showNotification(ctx, ICON_OK, "Envio de sinal ativo");
	}

	public static void envioInativo(Context ctx) {
		showNotification(ctx, ICON_FAIL, "Envio de sinal inativo");
	}

	public static void obtendoLocalizacao(Context ctx) {
		showNotification(ctx, ICON_OK, "Obtendo localização...");
	}

	public static void erroLocalizacao(Context ctx) {
		showNotification(ctx, ICON_FAIL, "Não foi possível obter localização");
	}

	public static void sinalEnviado(Context ctx) {
		showNotification(ctx, ICON_OK, "Sinal enviado em " + Util.now());
	}

	public static void erroSinal(Context ctx) {
		showNotification(ctx, ICON_FAIL, "Erro ao enviar último sinal");
	}

}
