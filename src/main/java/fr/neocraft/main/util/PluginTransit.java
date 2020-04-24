package fr.neocraft.main.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PluginTransit {

	private static List<Object[]> waitError = new ArrayList<Object[]>();
	private static List<String> waitMsg = new ArrayList<String>();
	private static boolean isErrorRunning = false;
	private static boolean isMsgRunning = false;
	
	public static void sendError(String author, Exception e) {
		
		try {
			Class c =Class.forName("fr.neocraft.packet.main");
			fr.neocraft.packet.main.instance.printError(author, e);
		} catch(Exception e2)
		{
			waitError.add(new Object[] { author, e });
			if(!isErrorRunning)
			{
				isErrorRunning = true;
				sendWaitError();
			}
		}
		
	}
	
	public static void sendMessage(String msg) {
		try {
			Class c =Class.forName("fr.neocraft.packet.main");
			fr.neocraft.packet.main.instance.printMessage(msg);
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
							Class c = Class.forName("fr.neocraft.packet.main");
							fr.neocraft.packet.main.instance.printError((String)obj[0],(Exception) obj[1]);
						} catch(ClassNotFoundException e2)
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
							Class c = Class.forName("fr.neocraft.packet.main");
							fr.neocraft.packet.main.instance.printMessage(s);
						} catch(ClassNotFoundException e2)
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
