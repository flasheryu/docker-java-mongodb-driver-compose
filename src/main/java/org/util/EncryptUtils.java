package org.util;

import java.math.BigInteger;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.axiom.om.util.Base64;

public class EncryptUtils {

  private static String KEY_STR = "something";
  private static Key key;

  public static final String UTF_8 = "UTF-8";
  public static final String DES = "DES";

  static {
    try {
      SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
      DESKeySpec keyspec = new DESKeySpec(KEY_STR.getBytes(UTF_8));
      key = keyFactory.generateSecret(keyspec);

    } catch(Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static String encode(String source) {
    try {
      byte[] sourceBytes = source.getBytes(UTF_8);
      Cipher cipher = Cipher.getInstance(DES);
      cipher.init(Cipher.ENCRYPT_MODE, key);
      byte[] encryptSourceBytes = cipher.doFinal(sourceBytes);

      return Base64.encode(encryptSourceBytes);
    } catch(Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static String decode(String encrypted) {
    try {
      Cipher cipher = Cipher.getInstance(DES);
      cipher.init(Cipher.DECRYPT_MODE, key);
      byte[] decryptStrBytes = cipher.doFinal(Base64.decode(encrypted));
      return new String(decryptStrBytes, UTF_8);
    } catch(Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static String md5Encode(String source)
  {
	try {
		 MessageDigest MD = MessageDigest.getInstance("MD5");
		 MD.reset();
		 MD.update(source.getBytes());
		 String target = new BigInteger(1, MD.digest()).toString(16).toUpperCase();
		 while (target.length() < 32) {
		   target = "0" + target;
		 }
		 return target;
	} catch (NoSuchAlgorithmException e) {
		Logger.get().error(String.format("md5Encoding for string: %s error", source), e);
	}
	return "";
  }
}