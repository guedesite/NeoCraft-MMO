package fr.neocraft.main.proxy.network.util.object;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.neocraft.main.proxy.ClientProxy;
import fr.neocraft.main.proxy.network.util.T;

public class ClientExecuteCommand extends T {

	private  static  final  long serialVersionUID =  5464867684657468768L;
	
	private String Cmd;

	
	
	public ClientExecuteCommand(String cmd) {
		this.Cmd=cmd;

	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void A() {
		ClientProxy.ExecuteCommand(this.Cmd);
	}
}
