package chatting_program;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

public class Server implements Runnable {
	
	ServerSocket serversocket;
	Socket socket;
	BufferedReader reader1, reader2;
	PrintWriter writer;
	Thread t1, t2;
	String in = "";
	String out = "";
	static int PORT = 5678;
	KeyPair keypair_RSA = null;
	PrivateKey priv_RSA = null;
	PublicKey pub_RSA = null;
	boolean start1 = true;
	PublicKey pub = null;
	ObjectOutputStream outO = null;
	OutputStream os = null;
	ObjectInputStream inO = null;
	InputStream is = null;
	SecretKey sKey = null;
	
	
	public static void main(String[] args) {
		new Server();
	}
	
	
	public Server() 
	{
		/**
		 * Create RSA Key Pair
		 */
		keypair_RSA = generate_RSA_key();
		pub_RSA = keypair_RSA.getPublic();
		priv_RSA = keypair_RSA.getPrivate();
		
		
		try {
			
			//Thread for write
			t1 = new Thread(this);
			
			//Thread for read
			t2 = new Thread(this);
			
			serversocket = new ServerSocket(PORT);
			
			System.out.println("Connecting...");			
			socket = serversocket.accept();
			
			System.out.println("##Connection success##\n");
			
			t1.start();
			t2.start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void run()
	{
		try {			
			if(Thread.currentThread() == t1) {
				/*
				 * Writing Thread
				 */
				
				//send RSA public key to client
				if(start1 = true) {
					os = socket.getOutputStream();
					outO = new ObjectOutputStream(os);
					outO.writeObject(pub_RSA);
					outO.flush();
					
					System.out.println(pub_RSA);
					
					start1 = false;
				}
				
				while(!in.equals("exit")) {
					
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
					
					if(sKey == null) {
						is = socket.getInputStream();
						inO = new ObjectInputStream(is);
						sKey = (SecretKey)inO.readObject();
						
					} else {
						System.out.println(sKey);
						break;
					}
					
				}

				while(!out.equals("exit")) {
					
					reader2 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					out = reader2.readLine();
					
					System.out.println("[From Client] : \"" + out + "\"");
					
				}
			}
			
			writer.close();
			reader1.close();
			reader2.close();
			outO.close();
			
			socket.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static KeyPair generate_RSA_key() {
		
		KeyPair keyPair = null;
		
		try {
		    SecureRandom sr = new SecureRandom();
		    KeyPairGenerator generator;
		
		    generator = KeyPairGenerator.getInstance("RSA");
		    generator.initialize(2048, sr);
		
		    keyPair = generator.generateKeyPair();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return keyPair;
	}
	
	
	public static String Decrypt_RSA(String encrypted, PrivateKey privateKey) {
		
		String decrypted = null;
		
		try {
			
			Cipher cipher = Cipher.getInstance("RSA");
			
			byte[] byteEncrypted = Base64.getDecoder().decode(encrypted.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] bytePlain = cipher.doFinal(byteEncrypted);
			
			decrypted = new String(bytePlain, "utf-8");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return decrypted;
	}
		
	
	
}