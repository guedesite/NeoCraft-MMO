package fr.neocraft.main.proxy.network.util.object;

import java.util.HashMap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.neocraft.main.main;
import fr.neocraft.main.Server.ClientPlayerData;
import fr.neocraft.main.proxy.ClientProxy;
import fr.neocraft.main.proxy.network.util.T;
import fr.neocraft.main.render.gui.internal.GuiNeoInGame;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;

public class ClientSetListPlayer extends T {

	private  static  final  long serialVersionUID =  5464867684657468768L;
	
	private HashMap<String, ClientPlayerData> list;
	
	public ClientSetListPlayer(HashMap<String, ClientPlayerData> l) {
		list = l ;
	}
	
	
	@Override
	@SideOnly(Side.CLIENT)
	public void A() {
		main.AllPlayer = list;
		if(list.containsKey(Minecraft.getMinecraft().thePlayer.getCommandSenderName()))
		{
			if(ClientProxy.player == null)
			{
				Minecraft.getMinecraft().ingameGUI = ClientProxy.NeoInGame = new GuiNeoInGame(Minecraft.getMinecraft());
			}
			ClientProxy.player = list.get(Minecraft.getMinecraft().thePlayer.getCommandSenderName());
		}

	}
	
}
