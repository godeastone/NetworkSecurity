package practice4;

import java.security.MessageDigest;

public class Hash
{
	public static void main (String[] args)
	{
		String ciphertext = "";
		String plaintext = "SHA-2 is a set of cryptographic hash "
				+ "functions (SHA-224, SHA-256, SHA-384, SHA-512, "
				+ "SHA-512/224, SHA-512/256) designed by the U.S. "
				+ "National Security Agency (NSA) and published in "
				+ "2001 by the NIST as a U.S. Federal Information "
				+ "Processing Standard (FIPS). SHA stands for "
				+ "Secure Hash Algorithm. SHA-2 includes a significant "
				+ "number of changes from its predecessor, "
				+ "SHA-1. SHA-2 currently consists of a set of six hash "
				+ "functions with digests that are 224, 256, 384 or 512 bits.";
		
		
		ciphertext = make_hash(plaintext,"MD5");
		System.out.println("MD5 : " + ciphertext);
		ciphertext = make_hash(plaintext,"SHA-1");
		System.out.println("SHA-1 : " + ciphertext);
		ciphertext = make_hash(plaintext,"SHA-256");
		System.out.println("SHA-256 : " + ciphertext);
		ciphertext = make_hash(plaintext,"SHA-512");
		System.out.println("SHA-512 : " + ciphertext);
	}
	
	public static String make_hash(String plaintext, String mode)
	{
		String result = "";
		try {
			MessageDigest md = MessageDigest.getInstance(mode);
			md.update(plaintext.getBytes());
			byte Data[] = md.digest();
			StringBuffer sb = new StringBuffer();
			for(int i = 0; i < Data.length; i++) {
				sb.append(Integer.toString((Data[i] & 0xff) + 0x100, 16).substring(1));
			}
			result = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
			}
		return result;
	}
}


