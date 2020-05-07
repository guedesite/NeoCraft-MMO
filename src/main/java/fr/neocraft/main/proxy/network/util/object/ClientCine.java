package fr.neocraft.main.proxy.network.util.object;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.neocraft.main.Server.ClientPlayerData;
import fr.neocraft.main.proxy.ClientProxy;
import fr.neocraft.main.proxy.network.util.T;
import fr.neocraft.main.util.CRASH;
import fr.neocraft.main.util.Vector6d;
import fr.neocraft.main.util.Vector7d;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

public class ClientCine extends T {

	private  static  final  long serialVersionUID =  5464867684657468768L;
	
	public int Id;

	public double also;

	public ClientCine() {

	}
	
	public ClientCine(int id, double also) {
		this.Id=id;
		this.also = also;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void A() {
		if(ClientProxy.player == null) { return; }
		EntityLivingBase p = Minecraft.getMinecraft().renderViewEntity;
		EntityPlayer pl = Minecraft.getMinecraft().thePlayer;
		Vector7d temp;
		
		try {
			switch(Id) {
				case 0:
					ClientPlayerData.Cam.toReg.clear();
					//ClientPlayerData.Cam.toReg.add( temp = new Vector7d(0, 0, 0, p.rotationPitch,p.rotationYawHead, 0D,0));
					ClientPlayerData.Cam.toReg.add( temp = new Vector7d(0, 0,0, 0,0, 0D, also));
					ClientPlayerData.Cam.old = new Vector6d(p.posX, p.posY, p.posZ,0, 0, 0D);
					pl.addChatMessage(new ChatComponentText("start with: "+ ClientPlayerData.Cam.old.toString()));
					break;
				case 1:
					if(ClientPlayerData.Cam.old == null) { pl.addChatMessage(new ChatComponentText("You must start before")); return; }
					
					Vector6d old = ClientPlayerData.Cam.old;
					ClientPlayerData.Cam.toReg.add( temp = new Vector7d( p.posX -old.x, p.posY - old.y, p.posZ-old.z, old.u - p.rotationPitch,old.v - p.rotationYaw, 0D, also));
					pl.addChatMessage(new ChatComponentText("add: "+ temp.toString()));
					ClientPlayerData.Cam.old = new Vector6d(p.posX, p.posY, p.posZ, p.rotationPitch,  p.rotationYaw, 0D);
					break;
				case 2:
					if(ClientPlayerData.Cam.toReg.size() > 0) {
						ClientPlayerData.Cam.toReg.remove(ClientPlayerData.Cam.toReg.size() - 1);
						pl.addChatMessage(new ChatComponentText("remove index: "+ ClientPlayerData.Cam.toReg.size()));
					}
					else {
						pl.addChatMessage(new ChatComponentText("nothing to remove"));
					}
					break;
				case 3:
					if(ClientPlayerData.Cam.toReg.size() >1) {
						ClientPlayerData.Cam.active = (Vector7d[]) ClientPlayerData.Cam.toReg.toArray(new Vector7d[] { });
						ClientPlayerData.Cam.oldView = Minecraft.getMinecraft().gameSettings.thirdPersonView;
						Minecraft.getMinecraft().gameSettings.thirdPersonView = 1;
						pl.addChatMessage(new ChatComponentText("play"));
					} else {
						pl.addChatMessage(new ChatComponentText("must be more than 2 element"));
					}
					break;
				case 4:
					pl.addChatMessage(new ChatComponentText("free index: "+ClientPlayerData.Cam.getFreeIndex()));
					break;
				case 5:
					if(ClientPlayerData.Cam.toReg.size() >1 ) {
						if(ClientPlayerData.Cam.registerNew((int) also)) 
						{
							pl.addChatMessage(new ChatComponentText("successfull reg"));
						} else {
							pl.addChatMessage(new ChatComponentText("error"));
						}
					} else {
						pl.addChatMessage(new ChatComponentText("must be more than 1 element"));
					}
					break;
				case 6:
					if(ClientPlayerData.Cam.playCinematic((int)also) ) {
						pl.addChatMessage(new ChatComponentText("play"));
					} else {
						pl.addChatMessage(new ChatComponentText("id not found"));
					}
					break;
				
			}
		} catch(Exception e) {
			CRASH.Push(e);
		}

	}
}