package practice1;

public class Transposition
{
	public static void main(String[] args)
	{
		//TODO
		String PlainText = "Common sense is not so common.";
		String rst = transposition_encrypt(PlainText, 8);
		
		System.out.println(PlainText);
		System.out.println(rst);
	}
	
	
	public static String transposition_encrypt(String plain, int Key)
	{
		int col = 0;
		String result = "";
		String row;
		int de=0;
		
		col = (int) Math.ceil((double)plain.length()/Key);
		
		for(int i=0; i < Key; i++)
		{
			row = "";
			//row 문자열 초기화
			
			for(int j=0; j < col; j++)
			{
				try //String에서 index범위 벗어난 경우 예외처리
				{
				row += plain.charAt(i + (j*Key));
				
				throw new StringIndexOutOfBoundsException();
				}
				catch (StringIndexOutOfBoundsException e) 
				{ }
				
			}
			
			result += row;
		}
		
		
		
		return result;
	}
}
