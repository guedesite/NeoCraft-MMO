package fr.neocraft.main.Server.cmd;

import java.io.File;

import fr.neocraft.main.main;
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
import net.querz.nbt.custom.SerializableTag;

public class CommandSeeds extends CommandBase{

	private static final String pathModel = new String("assets/model3d/seeds/");
	@Override
    public String getCommandName() {
        return "seeds";
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
			if(arg[0].equals("pos1"))
			{
				data.pos1 = new Vector3f(MathHelper.floor_double(p.posX),MathHelper.floor_double(p.posY),MathHelper.floor_double(p.posZ));
				M(ic, data.pos1.toString());
			}else if(arg[0].equals("pos2"))
			{
				data.pos2 = new Vector3f(MathHelper.floor_double(p.posX),MathHelper.floor_double(p.posY),MathHelper.floor_double(p.posZ));
				M(ic, data.pos2.toString());
			} else if(arg[0].equals("savefile") && arg.length != 1)
			{
				if(data.seeds != null)
				{
					BlockPlanNBT.saveCompoundTag(new File(pathModel+arg[1] + ".dat"), data.seeds);
					M(ic, "save at "+pathModel+arg[1] + ".dat");
				} else {
					M(ic, "paste first");
				}
			}else if(arg[0].equals("loadfile")&& arg.length != 1)
			{
				File f = new File(pathModel+arg[1] + ".dat");
				if(f.exists())
				{
					data.seeds = BlockPlanNBT.ReadCompoundTag(f);
					M(ic, "load "+pathModel+arg[1] + ".dat");
				} else {
					M(ic, "file doesn't exist: "+pathModel+arg[1] + ".dat");
				}
			}else if(arg[0].equals("copy"))
			{
				if(data.pos1 != null && data.pos2 != null)
				{
					Vector6f v6 = PosVec3DHelper.getMinPosAndVec(data.pos1, data.pos2);
					CompoundTag tag = BlockPlanNBT.LoadBlock(p.worldObj, v6.x, v6.y, v6.z, v6.u, v6.v, v6.w);
					tag.put("playerpos",new SerializableTag(new Vector3f(v6.x - MathHelper.floor_double(p.posX), v6.y-MathHelper.floor_double(p.posY), v6.z-MathHelper.floor_double(p.posZ))));
					data.seeds = tag;
					M(ic, "copy from: "+v6.toString());
				} else {
					M(ic, "make first the both pos");
				}
			}else if(arg[0].equals("paste"))
			{
				if(data.pos1 != null && data.pos2 != null)
				{
					BlockPlanNBT.writeBlock(p.worldObj, MathHelper.floor_double(p.posX),MathHelper.floor_double(p.posY),MathHelper.floor_double(p.posZ), data.seeds);
					Vector6f v6 = PosVec3DHelper.getMinPosAndVec(data.pos1, data.pos2);
					M(ic, "paste to: "+v6.toString());
				} else {
					M(ic, "make first the both pos");
				}
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
		M(ic, "/seeds pos1");
		M(ic, "/seeds pos2");
		M(ic, "/seeds savefile (file-name)");
		M(ic, "/seeds loadfile (file-name)");
		M(ic, "/seeds copy");
		M(ic, "/seeds paste");
	}

}
