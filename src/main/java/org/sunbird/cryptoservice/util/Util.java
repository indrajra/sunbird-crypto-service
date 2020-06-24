package org.sunbird.cryptoservice.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class Util {
	private static Pattern pattern;
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	static {
		pattern = Pattern.compile(EMAIL_PATTERN);
	}

	public static boolean isEmailvalid(final String email) {
		if (StringUtils.isBlank(email)) {
			return false;
		}
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
}
