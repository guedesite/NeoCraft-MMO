package fr.neocraft.main.Server.cmd;

import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import fr.neocraft.main.main;
import fr.neocraft.main.Server.ServerPlayerData;
import fr.neocraft.main.proxy.network.NetWorkClient;
import fr.neocraft.main.proxy.network.util.object.ClientCine;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;

public class CommandCinematic extends CommandBase{

	@Override
    public String getCommandName() {
        return "cinematic";
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
			final ServerPlayerData data = main.AllPlayerServer.get(((EntityPlayer)ic).getCommandSenderName());
			EntityPlayer owner = (EntityPlayer) ic;
			ClientCine cine = new ClientCine();
			boolean flag = false;
			if(arg[0].equalsIgnoreCase("help"))
			{
				sendHelp(ic);
			}else if(arg[0].equalsIgnoreCase("start")) {
				cine.Id = 0;
				flag= true;
			}else if(arg[0].equalsIgnoreCase("add") && arg.length== 2 ) {
				cine.Id = 1;
				cine.also = Integer.parseInt(arg[1]);
				flag= true;
			}else if(arg[0].equalsIgnoreCase("remove")) {
				cine.Id = 2;
				flag= true;
			}else if(arg[0].equalsIgnoreCase("test")) {
				cine.Id = 3;
				flag= true;
			}else if(arg[0].equalsIgnoreCase("getfreeid")) {
				cine.Id = 4;
				flag= true;
			}else if(arg[0].equalsIgnoreCase("register")&& arg.length == 2) {
				cine.Id = 5;
				cine.also = Integer.parseInt(arg[1]);
				flag= true;
			}else if(arg[0].equalsIgnoreCase("play")&& arg.length == 2) {
				cine.also = Integer.parseInt(arg[1]);
				cine.Id = 6;
				flag= true;
			}else {
				sendHelp(ic);
			}
			if(flag) {
				main.NetWorkClient.sendTo(new NetWorkClient(cine),(EntityPlayerMP)ic);
			}
			
		} else {
			
			sendHelp(ic);
		}
	}
	private void M(ICommandSender player, String msg) {
		player.addChatMessage(new ChatComponentText(msg));
		
	}
	
	
	private void sendHelp(ICommandSender ic) {
		M(ic, "/cinematic start (start at your pos)");
		M(ic, "/cinematic add speed(how many frame ex: 30) (add new step at your pos)");
		M(ic, "/cinematic remove (remove last add)");
		M(ic, "/cinematic test (play just a test)");
		M(ic, "/cinematic getfreedid (return free id to register)");
		M(ic, "/cinematic register id(0,1,2,3 ...)");
		M(ic, "/cinematic play id(0,1,2,3 ... )");
	

	}
}
