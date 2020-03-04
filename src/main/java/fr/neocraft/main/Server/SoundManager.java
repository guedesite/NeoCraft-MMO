package fr.neocraft.main.Server;

import java.util.Collections;
import java.util.List;

import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.neocraft.main.main;
import fr.neocraft.main.proxy.network.NetWorkClient;
import fr.neocraft.main.proxy.network.util.object.ClientSound;
import net.minecraft.client.Minecraft;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class SoundManager {
	private static SimpleNetworkWrapper nw = main.NetWorkClient;
	static EntityAINearestAttackableTarget.Sorter theNearestAttackableTargetSorter;
	
	private static IEntitySelector targetEntitySelector = new IEntitySelector()
    {
        public boolean isEntityApplicable(Entity p_82704_1_)
        {
            return (p_82704_1_ instanceof EntityPlayer);
        }
    };
	
	public static void PlaySound(String e, EntityPlayer p)
	{
		nw.sendTo(new NetWorkClient(new ClientSound(e, 1,1)), (EntityPlayerMP) p);
	}
	
	public static void PlaySoundAll(String e)
	{
		nw.sendToAll(new NetWorkClient(new ClientSound(e,1 ,1)));
	}
	

	@SideOnly(Side.CLIENT)
	public static void PlaySound(String e) {
		if(Minecraft.getMinecraft().thePlayer != null && e != null)
		{
			Minecraft.getMinecraft().thePlayer.playSound(e, 1, 1);
		}
	}
	
	@SideOnly(Side.CLIENT)
	public static void PlaySound(String e, float vol, float pi) {
		if(Minecraft.getMinecraft().thePlayer != null && e != null)
		{
			Minecraft.getMinecraft().thePlayer.playSound(e, vol, pi);
		}
	}
	

	public static void PlaySoundWithDistance(String e, EntityPlayer p) {
		theNearestAttackableTargetSorter = new EntityAINearestAttackableTarget.Sorter(p);
		List list = p.worldObj.selectEntitiesWithinAABB(EntityPlayer.class, p.boundingBox.expand(10, 10, 10), targetEntitySelector);
        Collections.sort(list, theNearestAttackableTargetSorter);
        nw.sendTo(new NetWorkClient(new ClientSound(e, 1 ,1)), (EntityPlayerMP) p);
        if (list.isEmpty())
        {
            return;
        }
        for(int i = 0; i < list.size(); i++)
        {
        	nw.sendTo(new NetWorkClient(new ClientSound(e, getDistanceToEntity(p, (EntityPlayer) list.get(i)) ,1)), (EntityPlayerMP) list.get(i));
        }
		
		
	}
	
	public static float getDistanceToEntity(Entity entity, EntityPlayer pos) {
		float deltaX = (float) Math.abs(entity.posX - pos.posX);
		float deltaY = (float) Math.abs(entity.posY - pos.posY);
		float deltaZ = (float) Math.abs(entity.posZ - pos.posZ);
		float nb = 0;
		if(deltaX < deltaY && deltaX < deltaZ)
		{
			nb =  deltaX;
		}else if(deltaY < deltaX && deltaY < deltaZ)
		{
			nb = deltaY;
		} else if(deltaZ < deltaY && deltaZ < deltaX)
		{
			nb =deltaZ;
		}
		if(nb > 9) { nb =  10; }
		else {	
		nb = Math.round(nb /10);
		}
		return nb;
	}
}
