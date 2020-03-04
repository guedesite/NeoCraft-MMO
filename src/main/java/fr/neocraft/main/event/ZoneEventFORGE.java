package fr.neocraft.main.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import fr.neocraft.main.main;
import fr.neocraft.main.Init.ItemMod;
import fr.neocraft.main.Server.ServerPlayerData;
import fr.neocraft.main.Server.Zone.Zone;
import fr.neocraft.main.Server.Zone.ZoneManager;
import fr.neocraft.main.proxy.network.NetWorkClient;
import fr.neocraft.main.proxy.network.util.object.ClientSetHeaderText;
import fr.neocraft.main.util.Vector3f;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraftforge.event.entity.EntityEvent.EnteringChunk;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

public class ZoneEventFORGE {

	@SubscribeEvent 
	public void EnterChunk(EnteringChunk event)
	{
		if(event.entity instanceof net.minecraft.entity.player.EntityPlayerMP) {
			
			ServerPlayerData data = main.AllPlayerServer.get(event.entity.getCommandSenderName());
			
			Zone znew = ZoneManager.getZoneAtEntity(event.entity);
			if(data != null)
			{
				if(data.debug)
				{
					main.NetWorkClient.sendTo(new NetWorkClient(new  ClientSetHeaderText(true, znew.getName(), "[DEBUG] ZoneId:"+znew.id+", Center:"+znew.getDistanceSqToEntity(event.entity)+"m, XPos:"+znew.getXPos()+", ZPos:"+znew.getZPos()+", lvl:"+znew.getLvl()+", pvp:"+znew.pvpEnable())), (EntityPlayerMP)event.entity);
				} else if(znew.id != data.Zone.id){
					main.NetWorkClient.sendTo(new NetWorkClient(new  ClientSetHeaderText(true, znew.getName(), znew.getSecName())), (EntityPlayerMP)event.entity);
				}
			}
			
		}
	}
	
	@SubscribeEvent
	public void PlayerInteract(PlayerInteractEvent event) {
		if(event.entityPlayer instanceof EntityPlayer) {
			ServerPlayerData data = main.AllPlayerServer.get(event.entity.getCommandSenderName());
			
			Zone znew = ZoneManager.getZoneAtEntity(event.entity);
			if(data != null)
			{
				if(isHolding(event.entityPlayer,ItemMod.Seeds_Stick))
				{
					if(event.action.equals(Action.LEFT_CLICK_BLOCK))
					{
						data.pos1 = new Vector3f(MathHelper.floor_double(event.x),MathHelper.floor_double(event.y),MathHelper.floor_double(event.z));
						event.entityPlayer.addChatMessage(new ChatComponentText("pos1: "+data.pos1.toString()));
					}else if(event.action.equals(Action.RIGHT_CLICK_BLOCK))
					{
						if(event.entityPlayer.getHeldItem() != null && event.entityPlayer.getHeldItem().getItem() == ItemMod.Seeds_Stick)
						{
							data.pos2 = new Vector3f(MathHelper.floor_double(event.x),MathHelper.floor_double(event.y),MathHelper.floor_double(event.z));
							event.entityPlayer.addChatMessage(new ChatComponentText("pos2: "+data.pos2.toString()));
						}
					}
				}
			}
		}
	}
	
	
	private boolean isHolding(EntityPlayer p, Item i )
	{
		return p.getHeldItem() != null && p.getHeldItem().getItem() == i;
	}
}
