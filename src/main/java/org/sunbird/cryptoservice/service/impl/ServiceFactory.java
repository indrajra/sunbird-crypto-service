package org.sunbird.cryptoservice.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.sunbird.cryptoservice.service.DataMaskingService;
import org.sunbird.cryptoservice.service.DecryptionService;
import org.sunbird.cryptoservice.service.EncryptionService;

public class ServiceFactory {

	  private static EncryptionService encryptionService;
	  private static DecryptionService decryptionService;
	  private static DataMaskingService maskingService;

	  static {
	    encryptionService = new DefaultEncryptionServivceImpl();
	    decryptionService = new DefaultDecryptionServiceImpl();
	    maskingService = new DefaultDataMaskServiceImpl();
	  }

	  /**
	   * this method will provide encryptionServiceImple instance. by default it will provide
	   * DefaultEncryptionServiceImpl instance to get a particular service impl instance , need to
	   * change the object creation and provided logic.
	   *
	   * @param val String ( pass null or empty in case of defaultImple object.)
	   * @return EncryptionService
	   */
	  public static EncryptionService getEncryptionServiceInstance(String val) {
	    if (StringUtils.isBlank(val)) {
	      return encryptionService;
	    }
	    switch (val) {
	      case "defaultEncryption":
	        return encryptionService;
	      default:
	        return encryptionService;
	    }
	  }

	  /**
	   * this method will provide decryptionServiceImple instance. by default it will provide
	   * DefaultDecryptionServiceImpl instance to get a particular service impl instance , need to
	   * change the object creation and provided logic.
	   *
	   * @param val String ( pass null or empty in case of defaultImple object.)
	   * @return DecryptionService
	   */
	  public static DecryptionService getDecryptionServiceInstance(String val) {
	    if (StringUtils.isBlank(val)) {
	      return decryptionService;
	    }
	    switch (val) {
	      case "defaultDecryption":
	        return decryptionService;
	      default:
	        return decryptionService;
	    }
	  }

	  public static DataMaskingService getMaskingServiceInstance(String val) {
	    if (StringUtils.isBlank(val)) {
	      return maskingService;
	    }
	    switch (val) {
	      case "defaultMasking":
	        return maskingService;
	      default:
	        return maskingService;
	    }
	  }
}
