package fr.neocraft.main.util;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import fr.neocraft.main.main;
import fr.neocraft.main.proxy.network.NetWorkServer;
import fr.neocraft.main.proxy.network.util.object.ServerError;
import net.minecraft.client.Minecraft;


public class CRASH {
	
	private static final String path = new String("ModCrash/");
	
	public static void init() {
		if(!new File(path).exists())
		{
			new File(path).mkdirs();
		}
	}
	
	public static void Push(Exception e)
	{
		e.printStackTrace();
		int i = 0; 
		File f = new File(path+"Crash-"+LocalDate.now().toString()+"-"+i+".txt");
		while(f.exists())
		{
			i++;
			f = new File(path+"Crash-"+LocalDate.now().toString()+"-"+i+".txt");
		
		}
		try {
			System.err.println("Error saved in: "+f.getAbsolutePath());
		

			
			PrintWriter writer = new PrintWriter(path+"Crash-"+LocalDate.now().toString()+"-"+i+".txt", "UTF-8");
			
			writer.println("########################	error	########################");
			writer.println(" ");
			writer.println("Time: "+LocalDate.now().toString().replace("-", "/")+" "+new SimpleDateFormat("HH:mm:ss").format(java.util.Calendar.getInstance().getTime()));
			writer.println(" ");
			
			e.printStackTrace(writer);
			
			writer.println(" ");
			writer.println("########################	error	########################");
			writer.println(" ");
			writer.close();
			if(FMLCommonHandler.instance().getSide().equals(Side.SERVER))
			{
				PluginTransit.sendError("Server Error", e);
			}
			else {
				main.NetWorkServer.sendToServer(new NetWorkServer(new ServerError(e, Minecraft.getMinecraft().thePlayer.getCommandSenderName() +" Error")));
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		} 
		
	
	}
	
	

}
