package com.vidring.util;

import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {
	public static void copyProperties(Object source, Object target) {
		BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
	}

	private static String[] getNullPropertyNames(Object source) {
		final BeanWrapper src = new BeanWrapperImpl(source);
		java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
		Set<String> emptyNames = new HashSet<String>();
		for (java.beans.PropertyDescriptor pd : pds) {
			Object srcValue = src.getPropertyValue(pd.getName());
			if (srcValue == null)
				emptyNames.add(pd.getName());
		}
		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
	}

	public static String classToJsonConvert(Object objects) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(objects);
			return json;
		} catch (Exception e) {
			return null;
		}
	}

	public static String TimweEncryption(String Data, String preSharedKey) throws Exception {
		String phrasetoEncrypt = Data + "#" + System.currentTimeMillis();
		String encryptionAlgorithm = "AES/ECB/PKCS5Padding";
		Cipher cipher = Cipher.getInstance(encryptionAlgorithm);
		SecretKeySpec key = new SecretKeySpec(preSharedKey.getBytes(), "AES");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		final byte[] crypted = cipher.doFinal(phrasetoEncrypt.getBytes());
		String encrypted = Base64.getEncoder().encodeToString(crypted);
		return encrypted;
	}

}
