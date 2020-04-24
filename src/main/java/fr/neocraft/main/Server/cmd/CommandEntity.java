package fr.neocraft.main.Server.cmd;

import java.util.Collections;
import java.util.List;

import fr.neocraft.main.main;
import fr.neocraft.main.Server.ServerPlayerData;
import fr.neocraft.main.entity.EntityPnjAction;
import fr.neocraft.main.util.Teleport;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

public class CommandEntity extends CommandBase{

	@Override
    public String getCommandName() {
        return "entity";
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
			EntityPlayer owner = (EntityPlayer) ic;
			if(arg[0].equals("help"))
			{
				sendHelp(ic);
			}else if(arg[0].equals("getEntity"))
			{
				if(arg.length == 2)
				{
					try {
						int distance = Integer.parseInt(arg[1]);
							
						list = owner.worldObj.selectEntitiesWithinAABB(Entity.class, owner.boundingBox.expand(distance, 4.0D, distance),new IEntitySelector()
				        {
				            public boolean isEntityApplicable(Entity p_82704_1_)
				            {
				                return true;
				            }
				        });
						
						Collections.sort(list, new net.minecraft.entity.ai.EntityAINearestAttackableTarget.Sorter(owner));

						if (list.isEmpty())
						{
							M(ic, "No Entity found.");
						}
						else
						{
				            for(int i = 0; i < list.size(); i++)
				            {
				            	M(ic, "["+i+"] "+list.get(i).getCommandSenderName()+" X:"+list.get(i).posX+" Y:"+list.get(i).posY+" Z:"+list.get(i).posZ);
				            }
						}
					} catch(NumberFormatException e) {
						M(ic, "error number invalid");
					}
				}else {
					sendHelp(ic);
				}
			}else if(arg[0].equals("selectEntity"))
			{
				if(arg.length == 2)
				{
					try {
						int id = Integer.parseInt(arg[1]);
						if(list.size() < id)
						{
							data.setSelectedEntity(list.get(id));
						}else {
							M(ic, "error number invalid");
						}
					} catch(NumberFormatException e) {
						M(ic, "error number invalid");
					}
				}else {
					sendHelp(ic);
				}
			}else if(arg[0].equals("kill"))
			{
				if(data.hasSelectedEntity())
				{
					data.getSelectedEntity().setDead();
				}else {
					M(ic, "No entity selected");
				}
			}else if(arg[0].equals("tphere"))
			{
				if(data.hasSelectedEntity())
				{
					Teleport.entity(data.getSelectedEntity(), owner.posX, owner.posY, owner.posZ);
				}else {
					M(ic, "No entity selected");
				}
			}else if(arg[0].equals("tp"))
			{
				if(data.hasSelectedEntity())
				{
					Teleport.entity(owner, data.getSelectedEntity().posX, data.getSelectedEntity().posY, data.getSelectedEntity().posZ);
				}else {
					M(ic, "No entity selected");
				}
			}else if(arg[0].equals("setInFire"))
			{
				if(data.hasSelectedEntity())
				{
					data.getSelectedEntity().setFire(10);
				}else {
					M(ic, "No entity selected");
				}
			}else if(arg[0].equals("setPnjActionId"))
			{
				if(arg.length == 2)
				{
					if(data.hasSelectedEntity())
					{
						try  {
							int id = Integer.parseInt(arg[1]);
							if(data.getSelectedEntity() instanceof EntityPnjAction)  {
								((EntityPnjAction)data.getSelectedEntity()).indexPnj = id;
								M(ic, "change successfull");
							}
							else {
								M(ic, "Entity must be EntityPnjAction");
							}
						} catch(NumberFormatException e) {
							M(ic, "error number invalid");
						}
					}else {
						M(ic, "No entity selected");
					}
				}else {
					sendHelp(ic);
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
	
	private List<EntityLivingBase> list;
	
	private void sendHelp(ICommandSender ic) {
		M(ic, "/entity getEntity distance(0,1,2,3 ...)");
		M(ic, "/donjon selectEntity id(get id with /entity getEntity)");
		M(ic, "Commande applicable seulement a l'entité sélectionné:");
		M(ic, "/donjon kill");
		M(ic, "/donjon tpHere");
		M(ic, "/donjon tp");
		M(ic, "/donjon setInFire");
		M(ic, "/donjon setPnjActionId id(0,1,2 ...)");

	}
}
