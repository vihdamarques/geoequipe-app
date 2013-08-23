package br.metodista.tcc.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;

public class Util {

	@SuppressLint("SimpleDateFormat")
	public static String now(){
		return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
	}

}