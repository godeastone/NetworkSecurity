package chatting_program;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.PublicKey;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Client implements Runnable
{

	BufferedReader reader1, reader2;
	PrintWriter writer;
	Socket socket;
	Thread t1, t2;
	String in = "";
	String out = "";
	String host = "";
	static int PORT = 5678;
	ObjectInputStream inO = null;
	InputStream is = null;
	boolean start1 = true;
	boolean flag1 = false;
	PublicKey pub_RSA = null;
	SecretKey sKey = null;
	ObjectOutputStream outO = null;
	OutputStream os = null;
	String encrypted_AESkey = null;
	
	
	
	public static void main(String[] args)
	{
		new Client();
		
	}
	
	
	public Client()
	{
		try {
			
			//Thread for read
			t1 = new Thread(this);
			
			//Thread for write
			t2 = new Thread(this);
			
			
			socket = new Socket(host, PORT);
			
			t1.start();
			t2.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public void run(){
		try{
			if (Thread.currentThread() == t2){
				/*
				 * Writing Thread
				 */
				while(true) {
					if(flag1 == true) {
						
						
						os = socket.getOutputStream();
						outO = new ObjectOutputStream(os);
						outO.writeObject(sKey);
						outO.flush();
						
						
						break;
					}
					
				}
				
				while (!in.equals("exit")){
										
					reader1 = new BufferedReader(new InputStreamReader(System.in));
					writer = new PrintWriter(socket.getOutputStream(), true);
					
					in = reader1.readLine();
					writer.println(in);

				}
				
			} else {
				/*
				 * Reading Thread
				 */
				
				while(true) {
					if(pub_RSA == null) {
						
						is = socket.getInputStream();
						inO = new ObjectInputStream(is);
						pub_RSA = (PublicKey)inO.readObject();
						
						
					} else {
						sKey = generate_AES_key();
						encrypted_AESkey = Encrypt_RSA(sKey.toString(), pub_RSA);
						flag1 = true;
						
						break;
					}
					
				}
				
				while (!out.equals("exit")) {
					
					reader2 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					out = reader2.readLine();
					
					System.out.println("[From Server] \"" + out + "\"");
				}
			}
			
			writer.close();
			reader1.close();
			reader2.close();
			socket.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static String Encrypt_RSA(String plaintext, PublicKey publicKey) 
	{
		String encrypted = null;
		
		try {
			
			Cipher cipher = Cipher.getInstance("RSA");
		
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] bytePlain = cipher.doFinal(plaintext.getBytes());
		
			encrypted = Base64.getEncoder().encodeToString(bytePlain);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return encrypted;
	}
	
	
	public static SecretKey generate_AES_key() {
		
		SecretKey key = null;
		
		try {
			
		    KeyGenerator gen = KeyGenerator.getInstance("AES");
		    gen.init(256);
		    
		    key = gen.generateKey();
		    
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return key; 
	}
		
	
	public static String Encrypt_AES(String plaintext, String key)
	{
		return null;
	}

	
}