package br.metodista.tcc.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import br.metodista.tcc.util.Base64;

public class Blowfish {

	public final String PROJECT_KEY = "G3@#qU1p";
	private String key;

	public Blowfish(String _key){
		this.key = _key;
	}

	public Blowfish(){
		this.key = this.PROJECT_KEY;
	}

    private String pad(String str) {
        String newStr = str;
        int pad_bytes = 8 - (str.length() % 8);
        for (int x = 1; x <= pad_bytes; x++) {
            newStr += (char) 0;
        }
        return newStr;
    }

    public String encrypt(String str) {
        try {
        	SecretKeySpec key = new SecretKeySpec(this.key.getBytes(), "Blowfish");
            Cipher cipher = Cipher.getInstance("Blowfish/ECB/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return Base64.encodeBytes(cipher.doFinal(pad(str).getBytes()));
        } catch (Exception e) { return null; }
    }

    public String decrypt(String str) {
        try {
        	SecretKeySpec key = new SecretKeySpec(this.key.getBytes(), "Blowfish");
            Cipher cipher = Cipher.getInstance("Blowfish/ECB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decrypted = cipher.doFinal(Base64.decode(str));
            return new String(decrypted).replaceAll("\\x00+$","");
        } catch (Exception e) { return null; }
    }
}
