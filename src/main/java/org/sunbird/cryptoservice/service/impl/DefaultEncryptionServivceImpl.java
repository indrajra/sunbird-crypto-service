package org.sunbird.cryptoservice.service.impl;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;
import org.sunbird.cryptoservice.service.EncryptionService;
import org.sunbird.cryptoservice.util.Constants;
import org.sunbird.cryptoservice.util.PropertiesCache;

public class DefaultEncryptionServivceImpl implements EncryptionService {

	 private static String encryption_key = "";

	  private String sunbirdEncryption = "";

	  private static Cipher c;

	  static {
	    try {
	      encryption_key = getSalt();
	      Key key = generateKey();
	      c = Cipher.getInstance(ALGORITHM);
	      c.init(Cipher.ENCRYPT_MODE, key);
	    } catch (Exception e) {

	    }
	  }

	  public DefaultEncryptionServivceImpl() {
	    sunbirdEncryption = System.getenv(Constants.SUNBIRD_ENCRYPTION);
	    if (StringUtils.isBlank(sunbirdEncryption)) {
	      sunbirdEncryption = PropertiesCache.getInstance().getProperty(Constants.SUNBIRD_ENCRYPTION);
	    }
	  }

	  @Override
	  public Map<String, Object> encryptData(Map<String, Object> data) throws Exception {
	    if (Constants.ON.equalsIgnoreCase(sunbirdEncryption)) {
	      if (data == null) {
	        return data;
	      }
	      Iterator<Entry<String, Object>> itr = data.entrySet().iterator();
	      while (itr.hasNext()) {
	        Entry<String, Object> entry = itr.next();
	        if (!(entry.getValue() instanceof Map || entry.getValue() instanceof List)
	            && null != entry.getValue()) {
	          data.put(entry.getKey(), encrypt(entry.getValue() + ""));
	        }
	      }
	    }
	    return data;
	  }

	  @Override
	  public List<Map<String, Object>> encryptData(List<Map<String, Object>> data) throws Exception {
	    if (Constants.ON.equalsIgnoreCase(sunbirdEncryption)) {
	      if (data == null || data.isEmpty()) {
	        return data;
	      }
	      for (Map<String, Object> map : data) {
	        encryptData(map);
	      }
	    }
	    return data;
	  }

	  @Override
	  public String encryptData(String data) throws Exception {
	    if (Constants.ON.equalsIgnoreCase(sunbirdEncryption)) {
	      if (StringUtils.isBlank(data)) {
	        return data;
	      }
	      if (null != data) {
	        return encrypt(data);
	      } else {
	        return data;
	      }
	    } else {
	      return data;
	    }
	  }

	  /**
	   * this method is used to encrypt the password.
	   *
	   * @param value String password
	   * @param encryption_key
	   * @return encrypted password.
	   * @throws NoSuchPaddingException
	   * @throws NoSuchAlgorithmException
	   * @throws InvalidKeyException
	   * @throws BadPaddingException
	   * @throws IllegalBlockSizeException
	   * @throws UnsupportedEncodingException
	   */
	  @SuppressWarnings("restriction")
	  public static String encrypt(String value)
	      throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
	          IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
	    String valueToEnc = null;
	    String eValue = value;
	    for (int i = 0; i < ITERATIONS; i++) {
	      valueToEnc = encryption_key + eValue;
	      byte[] encValue = c.doFinal(valueToEnc.getBytes(StandardCharsets.UTF_8));
	      eValue = new sun.misc.BASE64Encoder().encode(encValue);
	    }
	    return eValue;
	  }

	  private static Key generateKey() {
	    return new SecretKeySpec(keyValue, ALGORITHM);
	  }

	  /** @return */
	  public static String getSalt() {
	    if (!StringUtils.isBlank(encryption_key)) {
	      return encryption_key;
	    } else {
	      encryption_key = System.getenv(Constants.ENCRYPTION_KEY);
	      if (StringUtils.isBlank(encryption_key)) {
	        encryption_key = PropertiesCache.getInstance().getProperty(Constants.ENCRYPTION_KEY);
	      }
	    }
	    if (StringUtils.isBlank(encryption_key)) {
	     
	    	
	    }
	    return encryption_key;
	  }
}
