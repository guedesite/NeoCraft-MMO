package fr.neocraft.main.proxy.network.util.object;

import fr.neocraft.main.main;
import fr.neocraft.main.Server.ClientPlayerData;
import fr.neocraft.main.proxy.network.util.T;

public class ClientUpdateListPlayer extends T {
	private  static  final  long serialVersionUID =  5464867684657468768L;
	
	private String ind;
	private ClientPlayerData player;
	private int id;
	
	public ClientUpdateListPlayer(int i, String index, ClientPlayerData pl) {
		ind = index;
		player = pl;
		id = i;
	}
	
	@Override
	public void A() {
		switch(id) {
			case 0:
				main.AllPlayer.put(ind, player);
				break;
			case 1:
				main.AllPlayer.replace(ind, player);
				break;
			case 2:
				main.AllPlayer.remove(ind);
				break;
				
		}
		
	}
	
}
