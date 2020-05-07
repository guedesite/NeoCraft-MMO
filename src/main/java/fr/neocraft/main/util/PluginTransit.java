package fr.neocraft.main.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.SERVER)
public class PluginTransit {

	private static List<Object[]> waitError = new ArrayList<Object[]>();
	private static List<String> waitMsg = new ArrayList<String>();
	private static boolean isErrorRunning = false;
	private static boolean isMsgRunning = false;
	private static Class c;
	public static void sendError(String author, Exception e) {
		
		try {
			if(c == null)
			{
				c = Class.forName("fr.neocraft.packet.main");
			}
			//fr.neocraft.packet.main.instance.printError(author, e);
			send(author,e);
		} catch(ClassNotFoundException e2)
		{
			waitError.add(new Object[] { author, e });
			if(!isErrorRunning)
			{
				isErrorRunning = true;
				sendWaitError();
			}
		}
		
	}
	
	private static void send(String author, Exception e) {
		try {
			if(e != null) {
				Method m = c.getMethod("printError", String.class, Exception.class);
				m.invoke(null, author, e);
			} else {
				Method m = c.getMethod("printMessage", String.class);
				m.invoke(null, author);
			}
		} catch(Exception e2) {
			e2.printStackTrace();
		}
	}
	
	public static void sendMessage(String msg) {
		try {
			//fr.neocraft.packet.main.instance.printMessage(msg);
			send(msg , null);
		} catch(Exception e2)
		{
			waitMsg.add(msg);
			if(!isMsgRunning)
			{
				isMsgRunning = true;
				sendWaitMessage();
			}
		}
		
	}
	
	
	
	private static void sendWaitError() {
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
					for(Object[] obj: waitError)
					{
						try {
							send((String)obj[0],(Exception) obj[1]);
						} catch(Exception e2)
						{
							sendWaitError();
							return;
						}
					}
					isErrorRunning = false;
			}
		}, 10000);;
	}
	
	private static void sendWaitMessage() {
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
					for(String s: waitMsg)
					{
						try {
							//Class.forName("fr.neocraft.packet.main");
							send(s , null);
						} catch(Exception e2)
						{
							sendWaitMessage();
							return;
						}
					}
					isMsgRunning = false;
			}
		}, 10000);;
	}
	
	
	
}
