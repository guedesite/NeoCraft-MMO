package fr.neocraft.main.Server;

import java.io.File;

import fr.neocraft.main.main;
import fr.neocraft.main.util.BlockPlanNBT;
import fr.neocraft.main.util.Teleport;
import fr.neocraft.main.util.Vector3f;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class HouseManager {

	private static final int X = -20000;
	private static final int Z = -20000;
	private static final int Y = 40;
	
	private static int index = -1;
	
	private static final File HouseModel = new File("assets/model3d/house/model.dat");
	
	public static void loadUp() {
		World w = DimensionManager.getWorld(0);
		boolean flag = false;
		int x = -50;
		while(!flag)
		{
			index ++;
			x += 50;
			flag = w.getBlock(X + x, Y, Z) == Blocks.air;
		}
		main.l.info("load "+index+" player's house !");
	}
	
	
	public static int createHouse() {
		BlockPlanNBT.writeBlock(DimensionManager.getWorld(0), X + index * 50, Y, Z, BlockPlanNBT.ReadCompoundTag(HouseModel));
		return index;
	}
	
	public static void TeleportPlayerToHouse(EntityPlayer p) {
		
		Teleport.player(p, X +  main.AllPlayerServer.get(p.getCommandSenderName()).HouseIndex * 50, Y+1, Z);
	}
	
}
