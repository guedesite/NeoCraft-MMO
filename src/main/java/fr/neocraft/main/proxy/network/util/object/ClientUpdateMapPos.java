package fr.neocraft.main.proxy.network.util.object;

import static fr.neocraft.main.main.bdd;

import java.util.ArrayList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.neocraft.main.main;
import fr.neocraft.main.Server.ClientPlayerData;
import fr.neocraft.main.Server.ServerPlayerData;
import fr.neocraft.main.proxy.network.util.Serializer;
import fr.neocraft.main.proxy.network.util.T;
import fr.neocraft.main.util.Vector3f;
import net.minecraft.client.Minecraft;

public class ClientUpdateMapPos extends T {

	private  static  final  long serialVersionUID =  5464867684657468768L;
	
	private int stat;
	private Vector3f v;

	
	
	public ClientUpdateMapPos(String pseudo, int s, Vector3f v2) {
		stat=s;
		v=v2;
		ServerPlayerData data = main.AllPlayerServer.get(pseudo);
		switch(stat) {
			case -1:
				
				for(int i = 0; i < data.posMap.size(); i++) {
					if(data.posMap.get(i).isEqual(v)) {
						data.posMap.remove(i);
					}
				}
				break;
			case 0:
				if(data.posMap !=  null || data.posMap.size() == 0) {
					data.posMap.add(v);
				}else {
					data.posMap = new ArrayList();
					data.posMap.add(v);
				}
				break;
		}
		main.bdd.updateProtocole(bdd.getStringPlayer(), new Object[] { "posMap", Serializer.toString(v)}, "WHERE Pseudo='"+pseudo+"'");
		
		
	}
	
	
	@Override
	@SideOnly(Side.CLIENT)
	public void A() {
		ClientPlayerData data = main.AllPlayer.get(Minecraft.getMinecraft().thePlayer.getCommandSenderName());
		switch(stat) {
			case -1:
				for(int i = 0; i < data.posMap.size(); i++) {
					if(data.posMap.get(i).isEqual(v)) {
						data.posMap.remove(i);
					}
				}
				break;
			case 0:
				if(data.posMap !=  null || data.posMap.size() == 0) {
					data.posMap.add(v);
				}else {
					data.posMap = new ArrayList();
					data.posMap.add(v);
				}
				break;
		
		}
	}

	
}

