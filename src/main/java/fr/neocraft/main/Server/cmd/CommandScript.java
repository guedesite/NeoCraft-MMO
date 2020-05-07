package fr.neocraft.main.Server.cmd;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;

import fr.neocraft.main.main;
import fr.neocraft.main.util.CRASH;
import fr.neocraft.main.util.Teleport;
import fr.neocraft.main.util.Vector6d;
import fr.neocraft.main.util.Vector6f;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.DimensionManager;
import net.querz.nbt.NBTUtil;
import net.querz.nbt.custom.SerializableTag;

public class CommandScript extends CommandBase{

	public HashMap<String, Serializable> data;
	
	public CommandScript() {
		File f = new File("assets/dataScript.dat");
		if(f.exists())
		{
			try {
				data = (HashMap<String, Serializable>) ((SerializableTag) NBTUtil.readTag(f).clone()).getValue();
			} catch (Exception e) {
				CRASH.Push(e);
			}
		}
		else {
			data = new HashMap<String, Serializable>();
			Save();
		}
	}
	public void Save() {
		try {
			NBTUtil.writeTag(new SerializableTag((Serializable) data), new File("assets/dataScript.dat"));
		} catch(Exception e) {
			CRASH.Push(e);
		}
	}
	
	@Override
    public String getCommandName() {
        return "script";
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
		EntityPlayer p = main.getPlayer(arg[0]);
		if(arg.length > 1)
		{
			 if(arg[1].equalsIgnoreCase("setco")) {
				if(arg.length > 2) {
					data.put("co-"+arg[2], new Vector6d(p.posX, p.posY, p.posZ, p.rotationYaw, p.rotationPitch, p.worldObj.provider.dimensionId));
				}
			}else if(arg[1].equalsIgnoreCase("tpco")) {
				if(arg.length > 2) {
					if(data.containsKey("co-"+arg[2]))
					{
						Vector6d v =(Vector6d) data.get("co-"+arg[2]);
						Teleport.player((int)v.w, p, (int)v.x,  (int)v.y, (int) v.z);
					}
				}
			}
		}
	}
	
	
}
