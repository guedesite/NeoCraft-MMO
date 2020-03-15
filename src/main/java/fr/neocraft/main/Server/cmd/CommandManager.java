package fr.neocraft.main.Server.cmd;

import static fr.neocraft.main.main.bdd;

import fr.neocraft.main.main;
import fr.neocraft.main.Server.HouseManager;
import fr.neocraft.main.Server.ServerPlayerData;
import fr.neocraft.main.Server.Zone.Zone;
import fr.neocraft.main.Server.Zone.ZoneManager;
import fr.neocraft.main.proxy.network.NetWorkClient;
import fr.neocraft.main.proxy.network.util.object.ClientSetHeaderText;
import fr.neocraft.main.util.Vector3f;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;

public class CommandManager extends CommandBase{

	
	@Override
    public String getCommandName() {
        return "manager";
    }
    
    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 1;
    }
 
    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
    	return true;
    }


  
	@Override
	public void processCommand(ICommandSender ic, String[] arg) {
		if(arg.length > 0)
		{
			ServerPlayerData data = main.AllPlayerServer.get(((EntityPlayer)ic).getCommandSenderName());
			if(arg[0].equals("help"))
			{
				sendHelp(ic);
			}
			else if(arg[0].equals("createzone"))
			{
				if(ZoneManager.getZoneAtEntity((EntityPlayer)ic).id ==-1)
				{
					Vector3f v = ZoneManager.getPosOfZoneAtEntity((EntityPlayer)ic);
					bdd.execute("INSERT INTO "+bdd.getStringZone()+" (`XPos`, `ZPos`) VALUES ('"+(int)v.x+"', '"+(int)v.z+"')");
					M(ic, "zone créat at "+v.toString());
				}
				else {
					M(ic, "zone déjà éxistante");
				}
			}else if(arg[0].equals("reload"))
			{
			
				ZoneManager.Register();
				HouseManager.loadUp();
				M(ic, "Reload");
				
			}else if(arg[0].equals("setzonename"))
			{
				Zone zone = ZoneManager.getZoneAtEntity((EntityPlayer)ic);
				if(zone .id !=-1)
				{
					String f = "";
					for(int i = 1; i < arg.length; i++)
					{
						f += arg[i];
					}
					zone.setName(f);
					bdd.updateProtocole(bdd.getStringZone(),new Object[] {"Name",f }, "WHERE id="+zone.id);
					
					
					M(ic, "New name: "+f);
				}
				else {
					M(ic, "vous devez être dans une zone");
				}
			}else if(arg[0].equals("setzonename"))
			{
				Zone zone = ZoneManager.getZoneAtEntity((EntityPlayer)ic);
				if(zone.id !=-1)
				{
					String f = "";
					for(int i = 1; i < arg.length; i++)
					{
						f += arg[i];
					}
					zone.setName(f);
					bdd.updateProtocole(bdd.getStringZone(),new Object[] {"Name",f }, "WHERE id="+zone.id);
					
					
					M(ic, "New name: "+f);
				}
				else {
					M(ic, "vous devez être dans une zone");
				}
			}else if(arg[0].equals("setzonesecname"))
			{
				Zone zone = ZoneManager.getZoneAtEntity((EntityPlayer)ic);
				if(zone.id !=-1)
				{
					String f = "";
					for(int i = 1; i < arg.length; i++)
					{
						f += arg[i];
					}
					zone.setSecName(f);
					bdd.updateProtocole(bdd.getStringZone(),new Object[] {"SecName",f }, "WHERE id="+zone.id);
					
					
					M(ic, "New name: "+f);
				}
				else {
					M(ic, "vous devez être dans une zone");
				}
			}
			else if(arg[0].equals("setzonelvl"))
			{
				Zone zone = ZoneManager.getZoneAtEntity((EntityPlayer)ic);
				if(zone.id !=-1)
				{
					try {
						int lvl = Integer.parseInt(arg[1]);
						
						zone.setLvl(lvl);
						bdd.updateProtocole(bdd.getStringZone(),new Object[] {"lvl",lvl }, "WHERE id="+zone.id);
						
						
						M(ic, "New lvl: "+lvl);
					} catch(Exception e)
					{
						M(ic, "Nombre invalide");
					}

				}
			}
			else if(arg[0].equals("setzonepvp"))
			{
				Zone zone = ZoneManager.getZoneAtEntity((EntityPlayer)ic);
				if(zone.id !=-1)
				{
					try {
						int pvp = Integer.parseInt(arg[1]);
						
						zone.setPvp(pvp);
						bdd.updateProtocole(bdd.getStringZone(),new Object[] {"pvp",pvp }, "WHERE id="+zone.id);			
							
						M(ic, "New pvp stat: "+pvp);
					} catch(Exception e)
					{
						M(ic, "Nombre invalide");
					}
				}
				else {
					M(ic, "vous devez être dans une zone");
				}
			}else if(arg[0].equals("debug"))
			{
				Zone znew = ZoneManager.getZoneAtEntity((EntityPlayer)ic);
				if(data.debug)
				{
					data.debug = false;
					main.NetWorkClient.sendTo(new NetWorkClient(new  ClientSetHeaderText(true, znew.getName(), znew.getSecName())), (EntityPlayerMP)ic);
				}
				else {
					data.debug = true;
					main.NetWorkClient.sendTo(new NetWorkClient(new  ClientSetHeaderText(true, znew.getName(), "[DEBUG] ZoneId:"+znew.id+", Center:"+znew.getDistanceSqToEntity((EntityPlayer)ic)+"m, XPos:"+znew.getXPos()+", ZPos:"+znew.getZPos()+", lvl:"+znew.getLvl()+", pvp:"+znew.pvpEnable())), (EntityPlayerMP)ic);
				}
				M(ic, "debug:"+data.debug);
			}
			else if(arg[0].equals("createmyhouse"))
			{
				if(data.HouseIndex == -1)
				{
					data.HouseIndex = HouseManager.createHouse();
					HouseManager.TeleportPlayerToHouse(data);
				}else {
					M(ic, "Tu en as déjà une !");
				}
			}else if(arg[0].equals("tpmyhouse"))
			{
				HouseManager.TeleportPlayerToHouse(data);
			}
		} else {
			sendHelp(ic);
		}
	}
	
	private void M(ICommandSender player, String msg) {
		player.addChatMessage(new ChatComponentText(msg));
		
	}
	
	private void sendHelp(ICommandSender ic) {
		M(ic, "/manager createzone");
		M(ic, "/manager setzonename nom");
		M(ic, "/manager setzonesecname secondnom");
		M(ic, "/manager setzonelvl 0/1/2...");
		M(ic, "/manager setzonepvp 0(false)/1(true)");
		M(ic, "/manager debug");
		M(ic, "/manager reload");
		M(ic, "/manager createmyhouse");
		M(ic, "/manager tpmyhouse");
	}

}
