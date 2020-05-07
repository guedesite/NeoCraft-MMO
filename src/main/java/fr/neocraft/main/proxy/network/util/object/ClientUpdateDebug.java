package fr.neocraft.main.proxy.network.util.object;

import java.io.Serializable;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.neocraft.main.Server.SoundManager;
import fr.neocraft.main.proxy.ClientProxy;
import fr.neocraft.main.proxy.network.util.T;

public class ClientUpdateDebug extends T implements Serializable{

	private  static  final  long serialVersionUID =  5464867684657468768L;
	
	boolean deb;
	
	public ClientUpdateDebug(boolean debu)
	{
		deb = debu;
	}
	
	
	@SideOnly(Side.CLIENT)
	@Override
	public void A() {
		ClientProxy.player.debug = deb;
		
	}

}
