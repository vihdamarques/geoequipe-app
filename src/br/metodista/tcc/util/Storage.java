package br.metodista.tcc.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import android.content.Context;

public class Storage {
	private static String FILENAME_USUARIO = "usuario";
	private static String FILENAME_SINAL   = "sinal";

	public static String getUserId(Context ctx) {
		return readFile(ctx, FILENAME_USUARIO);
	}

	public static boolean setUserId(Context ctx, String userId) {
		return writeToFile(ctx, FILENAME_USUARIO, userId);
	}

	public static boolean setEnviaSinalOn(Context ctx){
		return writeToFile(ctx, FILENAME_SINAL, "on");
	}

	public static boolean setEnviaSinalOff(Context ctx){
		return writeToFile(ctx, FILENAME_SINAL, "off");
	}

	public static String getEnviaSinal(Context ctx){
		String enviarSinal = readFile(ctx, FILENAME_SINAL);
		return enviarSinal != null && enviarSinal.length() > 0 ? enviarSinal : "on";
	}

	private static boolean writeToFile(Context ctx, String fileName, String content) {
		FileOutputStream fos;
		try {
			fos = ctx.openFileOutput(fileName, Context.MODE_PRIVATE);
			fos.write((content != null ? content : "").getBytes());
			fos.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private static String readFile(Context ctx, String fileName) {
		String content = "";
		try {
			FileInputStream file = ctx.openFileInput(fileName);
			int chunk;
			while ((chunk = file.read()) != -1)
				content = content + (char) chunk;
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content;
	}
}
