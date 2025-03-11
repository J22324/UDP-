import java.io.*;
import java.net.*;

public class client_2 {
	public static void main(String[] args) throws Exception{

		Socket sock =new Socket(args[0], 5000);
 		read t1=new read();
		write t2=new write();
		t1.sock=sock;
		t2.sock=sock;
		t1.start();
		t2.start();
	}
}
//読み込み
class read extends Thread {
	Socket sock;
	public void run(){
		try{
    	BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			String ser;
			while(true){
				ser=in.readLine();     
				//if(ser.equals(".")) break;
				System.out.println(ser);
			}
			//sock.close();
			//in.close();
		}catch(Exception e){
			System.out.println("connection closed");
		}
	}	
}
//書き込み
class write extends  Thread {
	Socket sock;
	public void run(){
		try{		
			BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
    	BufferedWriter out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
			String s;
      while (true) {
				s=buf.readLine();
        if(s.equals(".")) break;
        out.write(s + "\r\n");
        out.flush();
			}
			buf.close();
			out.close();
			sock.close();
		}catch(Exception e){
			System.out.println("ERROR");
			
		}finally{
			System.out.println("connectin close");
		}
	}
}

