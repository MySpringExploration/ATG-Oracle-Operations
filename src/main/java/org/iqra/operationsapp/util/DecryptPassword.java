package org.iqra.operationsapp.util;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

@Component 
public class DecryptPassword{
	
	

	public DecryptPassword() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String decrypt(String encodedPass) throws Exception{
		  byte[] kbytes = "jaas is the way".getBytes();
		     SecretKeySpec key = new SecretKeySpec(kbytes, "Blowfish");
		 
		     BigInteger n = new BigInteger(encodedPass, 16);
		     byte[] encoding = n.toByteArray();
		     
		     if (encoding.length % 8 != 0){
		        int length = encoding.length;
		        int newLength = ((length / 8) + 1) * 8;
		        int pad = newLength - length; //number of leading zeros
		        byte[] old = encoding;
		        encoding = new byte[newLength];
		        for (int i = old.length - 1; i >= 0; i--){
		           encoding[i + pad] = old[i];
		        }
		        if (n.signum() == -1){
		           for (int i = 0; i < newLength - length; i++){
		              encoding[i] = (byte) -1;
		           }
		        }
		     }
		     
		     Cipher cipher = Cipher.getInstance("Blowfish");
		     cipher.init(Cipher.DECRYPT_MODE, key);
		     byte[] decode = cipher.doFinal(encoding);
		     return new String(decode);
	  }
 
 }
