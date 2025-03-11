import java.io.*;
import java.net.*;
import java.util.*;

public class server_2_thread2 {
  static server_thread[] aaa= new server_thread[2];

  public static void main(String[] args) throws Exception {
    ServerSocket hoge = new ServerSocket(5000);
		aaa[0]=new server_thread(hoge,0);
		aaa[1]=new server_thread(hoge,1);
    aaa[0].start();
    aaa[1].start();
  }
	static class server_thread extends Thread {
    Socket sock;
    ServerSocket hoge;
		int j;
		server_thread(ServerSocket hoge,int j){
			this.hoge=hoge;
		}
    BufferedReader in;
    BufferedWriter out;

    public void run(){
      try{
        while(true){
          sock=hoge.accept(); //接続待ち

          in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
          out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));

          String s,stu,res;
          System.out.println("Someone login from " + sock.getInetAddress() + ":" + sock.getPort());
					
          out.write("Hello. Type your student number and name\n");
          out.flush();

          stu=in.readLine();
          Date date = new Date();
          System.out.println(date + " " + sock.getInetAddress() + ":" +sock.getPort());
          System.out.println("written student number and name:" + stu);

          out.write("OK. Type something\n");
          out.flush();

       		while(true){
						s=in.readLine() ;
            if(s.equals(".")) break;
            System.out.println(date + " " + sock.getInetAddress()  + ":" + sock.getPort());
            System.out.println("written message:" + s);
            System.out.println("---------------------");

            res = stu + "曰く「"+ s + "」\r\n";

						if(aaa[j]==aaa[1]){
							for(int i=0; i<2; i++){
						//for (int i = 0; i < aaa.length; i++) {
              //if (aaa[i] != null && aaa[i].out != null) {
            		aaa[i].out.write(res);//複数のクライアントに送信
            		aaa[i].out.flush();
          		}
						}else{
							out.write(res);
							out.flush();
						
						}
						System.out.println("connection close");
        		System.out.println(new Date());
        		System.out.println("major's friend " +stu+" LOGOUT");
        		in.close();
        		out.close();
        		sock.close();
        	}
				}	
     	}catch(Exception e){
        System.out.println("ERORR");
			}
		}
	}
}
