package quiz;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {
	/*
	 Given a byte[] array, produces a hex String,
	 such as "234a6f". with 2 chars for each byte in the array.
	 (provided code)
	 */
	public static String hexToString(byte[] bytes) {
		StringBuffer buff = new StringBuffer();
		for (int i=0; i<bytes.length; i++) {
			int val = bytes[i];
			val = val & 0xff;  // remove higher bits, sign
			if (val<16) buff.append('0'); // leading 0
			buff.append(Integer.toString(val, 16));
		}
		return buff.toString();
	}

	/*
	 * Given a byte[] for a string, creates and uses a MessageDigest 
	 * to compute the Hash Value for that string.
	 */

	public static byte[] computeHash(byte[] byteArr) {
		byte[] result = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA");
			result = md.digest(byteArr);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
