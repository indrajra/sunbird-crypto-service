package com.sunbird.cryptoservice;

import org.sunbird.cryptoservice.service.DataMaskingService;
import org.sunbird.cryptoservice.service.DecryptionService;
import org.sunbird.cryptoservice.service.EncryptionService;
import org.sunbird.cryptoservice.service.impl.ServiceFactory;

public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		String str="nirmalkar.satish@gmail.com";
		EncryptionService encryptionService = ServiceFactory.getEncryptionServiceInstance(null);
		DecryptionService decryptionService = ServiceFactory.getDecryptionServiceInstance(null);
		DataMaskingService maskingService = ServiceFactory.getMaskingServiceInstance(null);
		String encryptedData = encryptionService.encryptData(str);
		System.out.println(encryptedData);
	
		System.out.println(decryptionService.decryptData(encryptedData));
		
		String maskedData = maskingService.maskData(str);
		System.out.println(maskedData);
		
		System.out.println(maskingService.maskEmail(str));

	}

}
