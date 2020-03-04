package fr.neocraft.main.proxy.network.util.object;

import java.util.ArrayList;
import java.util.HashMap;

import fr.neocraft.main.main;
import fr.neocraft.main.Server.ClientPlayerData;
import fr.neocraft.main.proxy.network.util.T;

public class ClientSetListPlayer extends T {

	private  static  final  long serialVersionUID =  5464867684657468768L;
	
	private HashMap<String, ClientPlayerData> list;
	
	public ClientSetListPlayer(HashMap<String, ClientPlayerData> l) {
		list = l ;
	}
	
	
	@Override
	public void A() {
		main.AllPlayer = list;
	}
	
}
