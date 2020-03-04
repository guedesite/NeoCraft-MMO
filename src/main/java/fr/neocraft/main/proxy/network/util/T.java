package fr.neocraft.main.proxy.network.util;

import java.io.Serializable;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;

public class T implements Serializable{
	private  static  final  long serialVersionUID =  5464867684657468768L;
	
	@SideOnly(Side.SERVER)
	public void A(EntityPlayer p) {
		
	}
	
	@SideOnly(Side.CLIENT)
	public void A() {
			
	}

}
