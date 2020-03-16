package fr.neocraft.main.Server.cmd;

import java.io.File;
import java.time.LocalDate;

import fr.neocraft.main.main;
import fr.neocraft.main.Server.DonjonManager;
import fr.neocraft.main.Server.ServerPlayerData;
import fr.neocraft.main.util.BlockPlanNBT;
import fr.neocraft.main.util.PosVec3DHelper;
import fr.neocraft.main.util.Vector3f;
import fr.neocraft.main.util.Vector6f;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.querz.nbt.CompoundTag;
import net.querz.nbt.ListTag;
import net.querz.nbt.custom.SerializableTag;

public class CommandDonjon extends CommandBase{

	private static final String pathModel = new String("assets/model3d/donjon/");
	@Override
    public String getCommandName() {
        return "donjon";
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
		if(arg.length != 0 && ic instanceof EntityPlayer)
		{
			EntityPlayer p = (EntityPlayer)ic;
			ServerPlayerData data = main.AllPlayerServer.get(p.getCommandSenderName());
			if(arg[0].equals("gettype"))
			{
				M(ic, "type 0: all");
				M(ic, "type 1: forest");
				M(ic, "type 2: sand");
				M(ic, "type 3: water ( water donjon will must be type 3 not 0)");
			}if(arg[0].equals("addmob"))
			{
				if(data.seeds != null)
				{
					Vector3f v = new Vector3f(MathHelper.floor_double(p.posX),MathHelper.floor_double(p.posY),MathHelper.floor_double(p.posZ));
					if(data.seeds.getListTag("mobs") != null)
					{
						ListTag l =data.seeds.getListTag("mobs");
						l.add(new SerializableTag(v));
						
					} else {
						ListTag all = new ListTag(SerializableTag.class);
						
						all.add(new SerializableTag(v));
						
						data.seeds.put("mobs", all);
					}
					M(ic, "add mob at:"+v.toString());
				} else {
					M(ic, "copy region first with /seeds");
				}
			}else if(arg[0].equals("addboss"))
			{
				if(data.seeds != null)
				{
					Vector3f v = new Vector3f(MathHelper.floor_double(p.posX),MathHelper.floor_double(p.posY),MathHelper.floor_double(p.posZ));
					if(data.seeds.getListTag("boss") != null)
					{
						ListTag l =data.seeds.getListTag("boss");
					
						l.add(new SerializableTag(v));

					} else {
						ListTag all = new ListTag(SerializableTag.class);
						
						all.add(new SerializableTag(v));
						
						data.seeds.put("boss", all);
					}
					M(ic, "add boss at:"+v.toString());
				} else {
					M(ic, "copy region first with /seeds");
				}
			}else if(arg[0].equals("register"))
			{
				if(arg.length != 1)
				{
				 	M(ic, "register donjon:");
				 	M(ic, "dim: "+((SerializableTag)data.seeds.get("xyzuvw")).getValue().toString());
				 	M(ic, (data.seeds.getListTag("mobs") != null ? data.seeds.getListTag("mobs").size() : 0) +" mobs");
				 	M(ic, (data.seeds.getListTag("boss") != null ? data.seeds.getListTag("boss").size() : 0) +" boss");
				 	try {
				 		int type = Integer.parseInt(arg[1]);
				 		M(ic, "type: "+type);
				 		data.seeds.putInt("type", type);
				 		int i = 0; 
						File f = new File(pathModel+"donjon-"+i+".dat");
						while(f.exists())
						{
							i++;
							f = new File(pathModel+"donjon-"+i+".dat");
						
						}
						M(ic, "Save at: "+f.getAbsolutePath());
						BlockPlanNBT.saveCompoundTag(f, data.seeds);
						M(ic, "finish");
				 		
				 	} catch(NumberFormatException e) {
				 		M(ic, "type invalide");
				 	}
				 	
				} else {
					sendHelp(ic);
				}
			}else if(arg[0].equals("startSys"))
			{
				DonjonManager.timerDonjon();
				M(ic, "run DonjonManager.timerDonjon();");
			}else if(arg[0].equals("stopSys"))
			{
				DonjonManager.endAllDonjon();
				M(ic, "run DonjonManager.enAllDonjon();");
			}else if(arg[0].equals("addZone"))
			{
				if(data.pos1 != null && data.pos2 != null)
				{
					Vector6f v6 = PosVec3DHelper.getMinPosAndVec(data.pos1, data.pos2);
				} else {
					M(ic, "Make region first with /seeds");
				}
			}else if(arg[0].equals("reload"))
			{
				DonjonManager.registerDonjon();
				M(ic, "reload");
			}else {
				sendHelp(ic);
			}
		} else {
			sendHelp(ic);
		}
	}
	

	private void M(ICommandSender player, String msg) {
		player.addChatMessage(new ChatComponentText(msg));
		
	}
	
	private void sendHelp(ICommandSender ic) {
		M(ic, "/donjon addmob");
		M(ic, "/donjon addboss");
		M(ic, "/donjon register (type)");
		M(ic, "/donjon gettype");
		M(ic, "/donjon startSys");
		M(ic, "/donjon stopSys");
		M(ic, "/donjon addZone");
		M(ic, "/donjon reload");
	}
}
