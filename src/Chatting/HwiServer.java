package Chatting;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HwiServer {
	private List<ServerThread> room =
			Collections.synchronizedList(new ArrayList<ServerThread>());

	
	public HwiServer() {
	
		room.clear();
	}
	
	public synchronized void add(ServerThread st) {
		
		room.add(st);
	}

	public void remove(ServerThread st) {
		
		room.remove(st);
		
	}

	public synchronized void broadCasting(String msg) {
		System.out.println("�氳��"+room.size());
		for (int i = 0; i<room.size(); i++) {
			ServerThread st = room.get(i);
			st.sendMessage(msg);
		}
		
	}
	
	public static void main(String[] args) {
		HwiServer hserver = new HwiServer();
		hserver.go();
	}
	
	ServerSocket ss;
	int port =9907;
	
	public void go() {
		
		try {
			ss = new ServerSocket(port);
			ss.setReuseAddress(true);
			System.out.println("�������� �غ�Ϸ�!");
			//blocking
			while(true) {
				Socket s = ss.accept();
				ServerThread st = new ServerThread(s,this);
				add(st);
				st.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e);
			
		} finally {
			
			try {
				ss.close();
			} catch (IOException e) {
				
			}
		}
	} 
		
		
	}

	

