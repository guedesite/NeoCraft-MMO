package fr.neocraft.main.proxy.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import fr.neocraft.main.util.CRASH;
import fr.neocraft.main.util.PluginTransit;

public class NetWorkSocketLauncher extends Thread {

private ServerSocket serverSocket;
	
	public boolean isConnected() {
		return this.serverSocket != null;
	}
	
	public void Close() throws IOException {
		this.serverSocket.close();
	}
	
	public void Open(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		serverSocket.setPerformancePreferences(-5, -5, -5);
	}
	
	@Override
	public void run() {
		while(this.serverSocket != null && !this.serverSocket.isClosed())
		{
			Socket socket = null;
			OutputStream out = null;
			try {
				socket = serverSocket.accept();
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream() );
	            Object o = ois.readObject();
	            if(o instanceof Exception) {
	            	PluginTransit.sendError("Launcher Client:"+ socket.getRemoteSocketAddress().toString(), (Exception)o) ;
	            } else {
	            	PluginTransit.sendMessage(socket.getRemoteSocketAddress().toString()+" is sending out range data, DOS ?");
	            }
	            ois.close();
			} catch(Exception e)
			{
				CRASH.Push(e);
			}
		}
	}
	
}
