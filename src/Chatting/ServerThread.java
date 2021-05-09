package Chatting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;


public class ServerThread extends Thread {

	private Socket s;
	BufferedReader br;
	PrintWriter pw;
	HwiServer hserver;

	public ServerThread(Socket s,HwiServer hserver) {

		this.s = s;
		this.hserver = hserver;
		System.out.println(s.getInetAddress());
		System.out.println(s.getLocalAddress());

		try {
			//���Ͽ��� �޽��� ��������
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			//���Ͽ� �޽��� ������
			pw = new PrintWriter(new OutputStreamWriter(s.getOutputStream()),true); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e);
		}
	}

	public void sendMessage(String msg) {

		System.out.println("����"+msg);
		pw.println(msg);


	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		String msg = "";
		try {
			while((msg = br.readLine())!= null) {
				System.out.println("broadCasting");
				hserver.broadCasting(msg);
			}

		} catch (IOException e) {
			System.out.println(s.getInetAddress()+"�� ������ ����Ǿ����ϴ�.");
			hserver.remove(this);

			try {
				s.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}finally {
			pw.close();
		}
	}

}