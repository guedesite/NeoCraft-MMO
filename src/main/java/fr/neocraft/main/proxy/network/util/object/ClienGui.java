package fr.neocraft.main.proxy.network.util.object;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.neocraft.main.proxy.ClientProxy;
import fr.neocraft.main.proxy.network.util.T;
import net.minecraft.client.Minecraft;

public class ClienGui extends T {

	private  static  final  long serialVersionUID =  5464867684657468768L;
	
	private int Id;;

	
	
	public ClienGui(int id) {
		Id=id;

	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void A() {
		switch(Id) {
			case -1:
				Minecraft.getMinecraft().thePlayer.closeScreen();
		
		
		}
	}
	
}

