package practice1;

public class Caesar
{
	
	public static void main(String[] args)
	{
		//TODO
		String PlainText = "The quick brown fox jumps over the lazy dog.";
		String CipherText = Caesar(PlainText, 2, "encrypt");
		String rst = Caesar(CipherText, 2, "decrypt");
		
		System.out.println(PlainText);
		System.out.println(CipherText);
		System.out.println(rst);
	}
	
	public static String Caesar(String plain, int Key, String mode)
	{
		String word = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String Plain = plain.toUpperCase();
		String result = "";
		
		if(mode.contentEquals("decrypt"))
		{
			Key = (word.length() - Key) % word.length();
		}
		else if(mode.contentEquals("encrypt"))
		{
			Key = Key % word.length();
		}
		else
		{
			return "err";
		}
		
		for(int i = 0; i < Plain.length(); i++)
		{
			char temp;
			temp = Plain.charAt(i);
			
			
			if(temp != ' ' && temp != '.') //마침표나 공백의 경우 가만히 둔다.
			{
				temp += Key;
				
				if(temp > 'Z') //Key값을 더했을 때 Z를 넘어가는 경우.
				{
					int num = Key / word.length();
					
					if(num == 0)
						temp -= word.length();
					else
						temp -= word.length() * num;
				}
			}
			
			result += temp;
		}
		return result;
	}
}
