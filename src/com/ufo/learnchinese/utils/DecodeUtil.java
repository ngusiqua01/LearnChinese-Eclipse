package com.ufo.learnchinese.utils;


import android.util.Base64;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;


public final class DecodeUtil
{
	public static final char[] KEY_GRAMMAR = { 49, 99, 99, 108, 117, 102, 72, 110, 121, 98, 74, 65, 119, 57, 77, 51, 84, 80, 122, 55, 73, 81, 61, 61 };
	public static final char[] KEY_PHRASE = { 50, 111, 47, 122, 73, 55, 106, 68, 67, 76, 84, 113, 43, 54, 65, 69, 78, 69, 112, 56, 111, 103, 61, 61 };
	private Cipher mCipher = null;
	private SecretKey mSecretKey = null;
	public static String keynative = "abcdef123456ghijkl654321";
	public static String keydecode_grammar = "fuckingyou";
	public static String keycode_phrase = "ffucking4u";
  
  
  public DecodeUtil(String key)
  {
    try
    {
//    	System.out.println("DecodeUtil.DecodeUtil() key = "+new Native().getName());
//      DESKeySpec localDESKeySpec = new DESKeySpec(keydecode.getBytes("UTF8"));
      DESKeySpec localDESKeySpec = new DESKeySpec(key.getBytes("UTF8"));
      
//      DESKeySpec localDESKeySpec = new DESKeySpec("fuck_you".getBytes("UTF8"));
      this.mSecretKey = SecretKeyFactory.getInstance("DES").generateSecret(localDESKeySpec);
      this.mCipher = Cipher.getInstance("DES");
      //return;
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public final String decode(String str)
  {
    byte [] array = Base64.decode(str, 0);
    try
    {
      this.mCipher.init(2, this.mSecretKey);
      String s = new String(this.mCipher.doFinal(array), "UTF8");
      return s;
    }
    catch (InvalidKeyException ie)
    {
      ie.printStackTrace();
      return null;
    }
    catch (IllegalBlockSizeException ibe)
    {
      ibe.printStackTrace();
      return null;
    }
    catch (BadPaddingException be)
    {
      be.printStackTrace();
      return null;
    }
    catch (UnsupportedEncodingException uee)
    {
      uee.printStackTrace();
    }
    return null;
  }
}

