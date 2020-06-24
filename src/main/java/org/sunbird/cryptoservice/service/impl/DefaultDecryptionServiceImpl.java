package org.sunbird.cryptoservice.service.impl;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;
import org.sunbird.cryptoservice.service.DecryptionService;
import org.sunbird.cryptoservice.util.Constants;
import org.sunbird.cryptoservice.util.PropertiesCache;

public class DefaultDecryptionServiceImpl implements DecryptionService {

	  private static String sunbird_encryption = "";

	  private String sunbirdEncryption = "";

	  private static Cipher c;

	  static {
	    try {
	      sunbird_encryption = DefaultEncryptionServivceImpl.getSalt();
	      Key key = generateKey();
	      c = Cipher.getInstance(ALGORITHM);
	      c.init(Cipher.DECRYPT_MODE, key);
	    } catch (Exception e) {
	     
	    }
	  }

	  public DefaultDecryptionServiceImpl() {
	    sunbirdEncryption = System.getenv(Constants.SUNBIRD_ENCRYPTION);
	    if (StringUtils.isBlank(sunbirdEncryption)) {
	      sunbirdEncryption = PropertiesCache.getInstance().getProperty(Constants.SUNBIRD_ENCRYPTION);
	    }
	  }

	  @Override
	  public Map<String, Object> decryptData(Map<String, Object> data) {
	    if (Constants.ON.equalsIgnoreCase(sunbirdEncryption)) {
	      if (data == null) {
	        return data;
	      }
	      Iterator<Entry<String, Object>> itr = data.entrySet().iterator();
	      while (itr.hasNext()) {
	        Entry<String, Object> entry = itr.next();
	        if (!(entry.getValue() instanceof Map || entry.getValue() instanceof List)
	            && null != entry.getValue()) {
	          data.put(entry.getKey(), decrypt(entry.getValue() + "", false));
	        }
	      }
	    }
	    return data;
	  }

	  @Override
	  public List<Map<String, Object>> decryptData(List<Map<String, Object>> data) {
	    if (Constants.ON.equalsIgnoreCase(sunbirdEncryption)) {
	      if (data == null || data.isEmpty()) {
	        return data;
	      }

	      for (Map<String, Object> map : data) {
	        decryptData(map);
	      }
	    }
	    return data;
	  }

	  @Override
	  public String decryptData(String data) {
	    return decryptData(data, false);
	  }

	  @Override
	  public String decryptData(String data, boolean throwExceptionOnFailure) {
	    if (Constants.ON.equalsIgnoreCase(sunbirdEncryption)) {
	      if (StringUtils.isBlank(data)) {
	        return data;
	      } else {
	        return decrypt(data, throwExceptionOnFailure);
	      }
	    } else {
	      return data;
	    }
	  }

	  public static String decrypt(String value, boolean throwExceptionOnFailure) {
	    try {
	      String dValue = null;
	      String valueToDecrypt = value.trim();
	      for (int i = 0; i < ITERATIONS; i++) {
	        byte[] decordedValue = new sun.misc.BASE64Decoder().decodeBuffer(valueToDecrypt);
	        byte[] decValue = c.doFinal(decordedValue);
	        dValue =
	            new String(decValue, StandardCharsets.UTF_8).substring(sunbird_encryption.length());
	        valueToDecrypt = dValue;
	      }
	      return dValue;
	    } catch (Exception ex) {
	     
	      if (throwExceptionOnFailure) {
	      }
	    }
	    return value;
	  }

	  private static Key generateKey() {
	    return new SecretKeySpec(keyValue, ALGORITHM);
	  }

}
