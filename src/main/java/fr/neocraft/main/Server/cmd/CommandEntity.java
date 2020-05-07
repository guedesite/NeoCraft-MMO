package fr.neocraft.main.Server.cmd;

import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import fr.neocraft.main.main;
import fr.neocraft.main.Server.ServerPlayerData;
import fr.neocraft.main.entity.EntityPnjAction;
import fr.neocraft.main.util.Teleport;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
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
			final ServerPlayerData data = main.AllPlayerServer.get(((EntityPlayer)ic).getCommandSenderName());
			EntityPlayer owner = (EntityPlayer) ic;
			if(arg[0].equalsIgnoreCase("help"))
			{
				sendHelp(ic);
			}else if(arg[0].equalsIgnoreCase("getEntity"))
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
			}else if(arg[0].equalsIgnoreCase("selectEntity"))
			{
				if(arg.length == 2)
				{
					try {
						int id = Integer.parseInt(arg[1]);
						if(id < list.size())
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
			}else if(arg[0].equalsIgnoreCase("kill"))
			{
				if(data.hasSelectedEntity())
				{
					data.getSelectedEntity().setDead();
				}else {
					M(ic, "No entity selected");
				}
			}else if(arg[0].equalsIgnoreCase("tphere"))
			{
				if(data.hasSelectedEntity())
				{
					Teleport.entity(data.getSelectedEntity(), owner.posX, owner.posY, owner.posZ);
				}else {
					M(ic, "No entity selected");
				}
			}else if(arg[0].equalsIgnoreCase("tp"))
			{
				if(data.hasSelectedEntity())
				{
					Teleport.entity(owner, data.getSelectedEntity().posX, data.getSelectedEntity().posY, data.getSelectedEntity().posZ);
				}else {
					M(ic, "No entity selected");
				}
			}else if(arg[0].equalsIgnoreCase("setInFire"))
			{
				if(data.hasSelectedEntity())
				{
					data.getSelectedEntity().setFire(10);
				}else {
					M(ic, "No entity selected");
				}
			}else if(arg[0].equalsIgnoreCase("setPnjActionId"))
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
			}else if(arg[0].equalsIgnoreCase("freeze"))
			{
				if(arg.length == 2)
				{
					if(data.hasSelectedEntity())
					{
						try  {
							int seconde = Integer.parseInt(arg[1]);
							if(data.getSelectedEntity() instanceof EntityLiving) {
								EntityLiving en = (EntityLiving) data.getSelectedEntity();
								final UUID Speed = UUID.fromString("7dc53df5-703e-49b3-8670-b1c468f47f2f");
								final IAttributeInstance attri = en.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
								AttributeModifier modif = attri.getModifier(Speed);
								if(modif != null ) {
									final double old = modif.getAmount();
									final int oldOp = modif.getOperation();
									attri.removeModifier(modif);
									final AttributeModifier New = new AttributeModifier(Speed, "NeoCraft Freeze", 0, oldOp).setSaved(false);
									attri.applyModifier(New);
									new Timer().schedule(new TimerTask() {
										@Override
										public void run() {
											if(data.getSelectedEntity().isDead) { return;}
											attri.removeModifier(New);
											attri.applyModifier(new AttributeModifier(Speed, "generic.movementSpeed", old, oldOp ).setSaved(true));
										}
									}, seconde * 1000);;
									
								}else {
									M(ic, "Entity invalid for freeze data 2");
								}

							} else {
								M(ic, "Entity invalid for freeze");
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
		M(ic, "/entity selectEntity id(get id with /entity getEntity)");
		M(ic, "Commande applicable seulement a l'entité sélectionné:");
		M(ic, "/entity kill");
		M(ic, "/entity tpHere");
		M(ic, "/entity tp");
		M(ic, "/entity setInFire");
		M(ic, "/entity setPnjActionId id(0,1,2 ...)");
		M(ic, "/entity freeze seconde(1,2 ...)");

	}
}
