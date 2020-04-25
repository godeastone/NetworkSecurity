package practice3;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class DES_ECB
{
	static int num = 0;
	static String temp = "";
	static boolean multiple_of_8 = false;

	public static SecretKey key;

	public static void main(String[] args)
	{

		byte[] k = { (byte) 0x01, (byte) 0x23, (byte) 0x45, (byte) 0x67, (byte) 0x89, (byte) 0xAB, (byte) 0xCD,
				(byte) 0xEF };
		key = new SecretKeySpec(k, 0, k.length, "DES");

		String plainText = "Now is the time ";

		if (plainText.length() % 8 == 0)
			multiple_of_8 = true;

		num = (int) Math.ceil((double) plainText.length() / 8);

		System.out.println("plain Text : " + plainText);

		System.out.print("Zero Padding : ");
		Zero_Padding(plainText);

		System.out.print("ANSI X9.23 : ");
		ANSI(plainText);

		System.out.print("PKCS 5 : ");
		PKCS_5(plainText);

	}

	public static void PKCS_5(String data)
	{
		for (int i = 0; i < num; i++)
		{
			try
			{
				temp = data.substring(8 * i, 8 * (i + 1));
			} catch (Exception e1)
			{
				// padding 과정
				temp = data.substring(8 * i);
				String n = temp;
				for (int j = 0; j < 8 - n.length(); j++)
				{
					temp += (char) (8 - n.length());
				}
			}
			try
			{
				DES_encrypt(temp);
			} catch (Exception e2)
			{
				e2.printStackTrace();
			}
		}
		if (multiple_of_8 == true)
		{
			temp = "";
			for (int k = 0; k < 8; k++)
			{
				temp += (char) (8);
			}
			try
			{
				DES_encrypt(temp);
			} catch (Exception e2)
			{
				e2.printStackTrace();
			}
		}
		System.out.println();
	}

	public static void ANSI(String data)
	{
		for (int i = 0; i < num; i++)
		{
			try
			{
				temp = data.substring(8 * i, 8 * (i + 1));
			} catch (Exception e3)
			{
				// padding 과정
				temp = data.substring(8 * i);
				String n = temp;
				for (int j = 0; j < 8 - n.length() - 1; j++)
				{
					temp += '\0';
				}
				// padding의 크기 추가
				temp += (char) (8 - n.length());
			}
			try
			{
				DES_encrypt(temp);
			} catch (Exception e4)
			{
				e4.printStackTrace();
			}
		}
		if (multiple_of_8 == true)
		{
			temp = "";
			for (int k = 0; k < 7; k++)
			{
				temp += '\0';
			}
			temp += (char) (8);
			try
			{
				DES_encrypt(temp);
			} catch (Exception e4)
			{
				e4.printStackTrace();
			}
		}
		System.out.println();
	}

	public static void Zero_Padding(String data)
	{
		for (int i = 0; i < num; i++)
		{
			try
			{
				temp = data.substring(8 * i, 8 * (i + 1));
			} catch (Exception e5)
			{
				// padding 과정
				temp = data.substring(8 * i);
				String n = temp;
				for (int j = 0; j < 8 - n.length(); j++)
				{
					temp += '\0';
				}
			}
			try
			{
				DES_encrypt(temp);
			} catch (Exception e6)
			{
				e6.printStackTrace();
			}
		}
		System.out.println();
	}

	public static void DES_encrypt(String data) throws Exception
	{
		if (data == null || data.length() == 0)
			return;

		Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
		cipher.init(Cipher.ENCRYPT_MODE, key);

		byte[] plainToByte = data.getBytes();
		byte[] encryptedByte = cipher.doFinal(plainToByte);

		System.out.print("[");
		for (int i = 0; i < encryptedByte.length; i++)
			System.out.format("%02X ", encryptedByte[i]);
		System.out.print("] ");

		return;
	}

}
