package fr.neocraft.main.proxy.network.util.object;

import fr.neocraft.main.proxy.ClientProxy;
import fr.neocraft.main.proxy.network.util.T;

public class ClientSetHeaderText extends T {

	private  static  final  long serialVersionUID =  5464867684657468768L;
	
	private boolean isFirst;
	private String prem;
	private String second;
	
	
	public ClientSetHeaderText(boolean isfirst, String prems,String secon) {
		isFirst = isfirst;
		prem=prems;
		second=secon;
	}
	
	
	@Override
	public void A() {
		ClientProxy.GuiClientManager.addTextToRender(prem, second, isFirst);
	}
	
}
