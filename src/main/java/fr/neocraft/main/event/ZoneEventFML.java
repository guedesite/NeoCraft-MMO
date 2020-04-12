package fr.neocraft.main.event;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ListIterator;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import fr.neocraft.main.bdd;
import fr.neocraft.main.main;
import fr.neocraft.main.Server.ClientPlayerData;
import fr.neocraft.main.Server.ServerPlayerData;
import fr.neocraft.main.Server.Zone.ZoneManager;
import fr.neocraft.main.proxy.network.NetWorkClient;
import fr.neocraft.main.proxy.network.util.Serializer;
import fr.neocraft.main.proxy.network.util.object.ClientSetHeaderText;
import fr.neocraft.main.proxy.network.util.object.ClientSetListPlayer;
import fr.neocraft.main.proxy.network.util.object.ClientUpdateListPlayer;
import fr.neocraft.main.util.CRASH;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import static fr.neocraft.main.main.bdd;

public class ZoneEventFML {

	//@SubscribeEvent
	
	
	@SubscribeEvent
	public void PlayerJoin(PlayerLoggedInEvent event) {
		
		ClientPlayerData dataClient = new ClientPlayerData();
		ServerPlayerData dataServer = new ServerPlayerData(event.player);
		
		dataServer.Zone = ZoneManager.getZoneAtEntity(event.player);
		
		int id = bdd.GetFreeId();
		if(!bdd.Exist("SELECT * FROM "+bdd.getStringPlayer()+" WHERE Pseudo='"+event.player.getCommandSenderName()+"'"))
		{
			bdd.execute("INSERT INTO "+bdd.getStringPlayer()+"( `Pseudo`) VALUES ('"+event.player.getCommandSenderName()+"')");
		}
		ResultSet result = bdd.query("SELECT * FROM "+bdd.getStringPlayer()+" WHERE Pseudo='"+event.player.getCommandSenderName()+"'", id);
		try {
			while(result.next())
			{
				dataClient.classe = result.getString("Classe");
				dataClient.race = result.getString("Race");
				dataClient.reputation[0] = result.getDouble("Reputation1");
				dataClient.reputation[1] = result.getDouble("Reputation2");
				dataClient.clientQuest = dataServer.quest.SendToPlayerAllQuest(event.player);
				
				
				dataServer.classe = result.getString("Classe");
				dataServer.race = result.getString("Race");
				dataServer.reputation[0] = result.getDouble("Reputation1");
				dataServer.reputation[1] = result.getDouble("Reputation2");
				dataServer.damage = result.getDouble("Damage");
				dataServer.life = result.getInt("Life");
				dataServer.MF = result.getInt("MF");
				dataServer.speed = result.getDouble("Speed");
				dataServer.HouseIndex = result.getInt("HouseIndex");
				dataServer.regen = result.getInt("Regen");
				dataServer.Money = result.getDouble("Money");
				dataServer.Zone = ZoneManager.getZoneAtEntity(event.player);
				dataServer.HouseBy = result.getInt("HouseBy");

				if(result.getString("posMap") != null) {
					ArrayList ar = (ArrayList) Serializer.fromString(result.getString("posMap"));
					dataServer.posMap = ar;
					dataClient.posMap = ar;
				}
				
			}
		} catch(Exception e) {
			CRASH.Push(e);
		}
		 ListIterator li = main.getPlayer();
	
		 EntityPlayerMP p ;
		 while (li.hasNext()){
		    	p = ((EntityPlayerMP) li.next());
		        if(!p.getCommandSenderName().equals(event.player.getCommandSenderName()))
		        {
		        	main.NetWorkClient.sendTo(new NetWorkClient(new  ClientUpdateListPlayer(0, event.player.getCommandSenderName(), dataClient.CloneForOther())), p);
		        }
		    }
		 main.NetWorkClient.sendToAll(new NetWorkClient(new  ClientUpdateListPlayer(0, event.player.getCommandSenderName(), dataClient)));
		// main.AllPlayer.put(event.player.getCommandSenderName(), dataClient);
		 main.AllPlayerServer.put(event.player.getCommandSenderName(), dataServer);
		 main.AllPlayer.put(event.player.getCommandSenderName(), dataClient);
		 main.NetWorkClient.sendTo(new NetWorkClient(new  ClientSetListPlayer(main.AllPlayer)), (EntityPlayerMP) event.player);
		 main.NetWorkClient.sendTo(new NetWorkClient(new  ClientSetHeaderText(true, dataServer.Zone.getName(), dataServer.Zone.getSecName())), (EntityPlayerMP)event.player);
		 bdd.CloseFreeId(id);
	}
	
	@SubscribeEvent
	public void PlayerLeave(PlayerLoggedOutEvent event) {
		ListIterator li = (ListIterator) MinecraftServer.getServer().getConfigurationManager().playerEntityList.listIterator();
		
		 EntityPlayerMP p ;
		 while (li.hasNext()){
		    	p = ((EntityPlayerMP) li.next());
		        if(!p.getCommandSenderName().equals(event.player.getCommandSenderName()))
		        {
		        	main.NetWorkClient.sendTo(new NetWorkClient(new  ClientUpdateListPlayer(2, event.player.getCommandSenderName(), null)), p);
		        }
		    }
		 ServerPlayerData data = main.AllPlayerServer.get(event.player.getCommandSenderName());
		 data.quest.Save(event.player.getUniqueID().toString());
		 if(data.lastBeforeHouse != null)
		 {
			 event.player.setPosition(data.lastBeforeHouse.x+0.5, data.lastBeforeHouse.y+0.5, data.lastBeforeHouse.z+0.5);
		 }
		 main.AllPlayer.remove(event.player.getCommandSenderName());
		 main.AllPlayerServer.remove(event.player.getCommandSenderName());
	}
	
	
	
}
