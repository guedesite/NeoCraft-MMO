package fr.neocraft.main.proxy.network.util.object;

import java.io.Serializable;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.neocraft.main.proxy.network.util.T;
import fr.neocraft.main.util.PluginTransit;

public class ServerError extends T implements Serializable{

	private  static  final  long serialVersionUID =  5464867684657468768L;
	
	private Exception e;
	private String author;
	public ServerError(Exception e, String author)
	{
		this.e=e;
		this.author = author;
	}
	
	@SideOnly(Side.SERVER)
	@Override
	public void A() {
		PluginTransit.sendError(author, e);
		
	}

}
