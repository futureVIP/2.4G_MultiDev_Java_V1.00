package com.jietong.window.basicOperation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.jietong.MainStart;
import com.jietong.rfid.uhf.entity.Reader;
import com.jietong.rfid.util.ReaderUtil;

public class SocketConnect extends MainStart{
	private static final long serialVersionUID = 1L;
	private List<ServerThread> socketList = new ArrayList<ServerThread>();
	private ServerSocket serverSocket = null;
	private boolean isStartServer = false;
	private Thread sockets;
	
	public void startServer(int port,int backlog,String bindAddrIP) {
		try {
			serverSocket = new ServerSocket(port,10, InetAddress.getByName(bindAddrIP));
			//PropertiesUtils.setValue(filepath, map);
			System.out.println("open " + serverSocket);
			isStartServer = true;
			if (isStartServer) {
				sockets = new Thread(new sockets());
				sockets.start();
			}
		} catch (IOException e1) {
			System.out.println("启动服务器失败");
			// e1.printStackTrace();
			isStartServer = false;
		}
	}

	public void startServer(int port) {
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("open " + serverSocket);
			isStartServer = true;
			if (isStartServer) {
				System.out.println("启动服务器成功");
				sockets = new Thread(new sockets());
				sockets.start();
			}
		} catch (IOException e1) {
			System.out.println("启动服务器失败");
			// e1.printStackTrace();
			isStartServer = false;
		}
	}
	
	public class sockets implements Runnable {
		int ports = 0;
		@Override
		public void run() {
			while (isStartServer) {
				try {
					Socket client = serverSocket.accept();
					ServerThread serverThread = new ServerThread(client);
					new Thread(serverThread).start();
					socketList.add(serverThread);
					ports = client.getPort();
					String device = client.getInetAddress().getHostAddress();
					String state = "连接";
					addToList(listConnect, device,ports, state,0);
					for (int i = 0; i < ReaderUtil.MAX_DEVICE_NUM; i++) {
						if (ReaderUtil.readers[i] == null) {
							ReaderUtil.readers[i] = new Reader();
							ReaderUtil.readers[i].host = device;
							ReaderUtil.readers[i].socket = client;
							ReaderUtil.readers[i].deviceConnected = true;
							break;
						}
					}
				} catch (IOException e) {
					//e.printStackTrace();
				}finally{
					//isStartServer = false;
				}
			}
		}
	}

	public void stopServer() {
		if (isStartServer) {
			for (ServerThread serverThread : socketList) {
				serverThread.stop();
			}
			socketList.clear();
			isStartServer = false;
			try {
				serverSocket.close();
				System.out.println("关闭服务器成功");
				System.out.println("close " + serverSocket);
				for (int i = 0; i < ReaderUtil.MAX_DEVICE_NUM; i++) {
					if (ReaderUtil.readers[i] != null && ReaderUtil.readers[i].socket != null) {
						//System.out.println("socket " + ReaderUtil.readers[i].socket);
						ReaderUtil.readers[i] = null;
					}
				}
			} catch (IOException e) {
				//e.printStackTrace();
				System.out.println("非正常close " + serverSocket);
			}
		}
	}

	public void sendMessage(String str) {
		socketList.get(0).sendMessage(str);
	}

	private class ServerThread implements Runnable {
		private boolean isRunning = false;
		private final Socket socket;
		private final BufferedReader reader;
		private final PrintWriter writer;

		public ServerThread(Socket socket) throws IOException {
			this.socket = socket;
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			isRunning = true;
		}

		public void stop() {
			isRunning = false;
			String port = null;
			try {
				String ports = socket.getInetAddress().getHostAddress();
				for (int j = 0; j < tbl_showConnectInfo.getRowCount(); j++) {
					port = (String) tbl_showConnectInfo.getValueAt(j, 1);
					if (ports.equals(port)) {
						listConnect.remove(j);
						tableConnectModel.removeRow(j);
						break;
					}
				}
				writer.close();
				reader.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void sendMessage(String str) {
			writer.print(str + "\n");
			writer.flush();
		}

		@Override
		public void run() {
			String msg = null;
			String IP2 = null;
			while (isRunning) {
				try {
					//msg = reader.readLine();
					//System.out.println(ServerThread.this.toString() + ": " + msg);
					//System.out.println("断开连接"	+ socket.getPort());
				} catch (Exception e) {
					//System.out.println("非正常断?	+ socket.getPort());
					// e.printStackTrace();
				} finally {
					if (!socket.isConnected()) {
						String IP = socket.getInetAddress().getHostAddress();
						for (int j = 0; j < tbl_showConnectInfo.getRowCount(); j++) {
							IP2 = (String) tbl_showConnectInfo.getValueAt(j, 1);
							if (IP.length() > 5) {
								listConnect.remove(j);
								tableConnectModel.removeRow(j);
								break;
							}
						}
						isRunning = false;
					}
				}
			}
		}
	}
}
