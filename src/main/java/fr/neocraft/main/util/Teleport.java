package fr.neocraft.main.util;

import java.util.Timer;
import java.util.TimerTask;

import fr.neocraft.main.main;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.network.ForgeMessage;

public class Teleport {
	private static Timer timer = new Timer();
	public static void player(final int world, final EntityPlayer player, final double x, final double y, final double z)
	{
		if(ValidWorld(world))
		{
			if(player.getEntityWorld().provider.dimensionId == DimensionManager.getWorld(world).provider.dimensionId)
			{
				player(player, x,  y, z);
			}
			else
			{
				checkRiding(player);
				int exp = player.experienceTotal;
				((EntityPlayerMP) player).mcServer.getConfigurationManager().transferPlayerToDimension(((EntityPlayerMP) player),world, DimensionManager.getWorld(world).getDefaultTeleporter());
				player.fallDistance = 0;
				player.setPositionAndUpdate((double) x+0.5, (double) y, (double) z+0.5);
				player.fallDistance = 0;
				player.experienceTotal = exp;
			}
		}
	}
	
	public static void player(final EntityPlayer player, final double x, final double y, final  double z)
	{
		if(main.getPlayer(player.getCommandSenderName()) == null)
		{
			return;
		}
		checkRiding(player);
		player.fallDistance = 0;
		player.setPositionAndUpdate((double) x+0.5, (double) y, (double) z+0.5);
	}
	
	
	private static boolean ValidWorld(int world) {
		return DimensionManager.getWorld(world) != null;
	}
	
	public static void entity(final Entity player, final double posX, final double posY, final double posZ)
	{
		if(player instanceof EntityPlayer && main.getPlayer(player.getCommandSenderName()) == null)
		{
			return;
		}
		player.fallDistance = 0;
		checkRiding(player);
		player.setPosition((double) posX+0.5, (double) posY, (double) posZ+0.5);
	}
	
	public static void entity(final int world, final Entity player, final  double x, final double y, final double z)
	{
		if(ValidWorld(world))
		{
			if(player.worldObj.provider.dimensionId == DimensionManager.getWorld(world).provider.dimensionId)
			{
				entity(player, x,  y, z);
			}
			else
			{
				if(player instanceof EntityPlayer && main.getPlayer(player.getCommandSenderName()) == null)
				{
					return;
				}
				if(player instanceof EntityPlayer)
				{ 
					checkRiding(player);
					int exp = (((EntityPlayer) player).experienceTotal);
					WorldServer ws = ((EntityPlayerMP) player).mcServer.worldServerForDimension(world);
					Teleporter teleporter = new NeoTeleporter(ws);
					((EntityPlayerMP) player).mcServer.getConfigurationManager().transferPlayerToDimension(((EntityPlayerMP) player),world, teleporter);
					player.fallDistance = 0;
					player.setPosition(x + 0.5, y, z + 0.5);
					player.fallDistance = 0;
					((EntityPlayer) player).experienceTotal = exp;
				}
				else
				{
					player.fallDistance = 0;
					player.travelToDimension(world);
					player.setWorld(DimensionManager.getWorld(world));
					player.setPosition(x + 0.5, y, z + 0.5);
				}
			}
		}
	}
	
	
	public static void checkRiding(Entity en)
	{
		if(en.isRiding())
		{
			en.ridingEntity.riddenByEntity = null;
			en.ridingEntity = null;
		}
		
	}
	
	static class NeoTeleporter extends Teleporter
	{

		private final WorldServer worldServerInstance;



		public NeoTeleporter(WorldServer par1WorldServer)
		{
			super(par1WorldServer);
			this.worldServerInstance = par1WorldServer;
		}

		@Override
		public void placeInPortal(Entity entity, double p2, double p3, double p4, float p5)
		{
		       int i = MathHelper.floor_double(entity.posX);
		       int j = MathHelper.floor_double(entity.posY);
		       int k = MathHelper.floor_double(entity.posZ);
		       this.worldServerInstance.getBlock(i, j, k); //dummy load to maybe gen chunk
		       int height = this.worldServerInstance.getHeightValue(i, k);
		       entity.setPosition( i, height, k );
		       return;
		} 

	}


}