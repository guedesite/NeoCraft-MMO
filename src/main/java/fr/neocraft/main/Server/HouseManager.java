package fr.neocraft.main.Server;

import java.io.File;

import fr.neocraft.main.main;
import fr.neocraft.main.util.BlockPlanNBT;
import fr.neocraft.main.util.Teleport;
import fr.neocraft.main.util.Vector3f;
import fr.neocraft.main.util.Vector6f;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.querz.nbt.CompoundTag;
import net.querz.nbt.custom.SerializableTag;

public class HouseManager {

	private static final int X = -20000;
	private static final int Z = -20000;
	private static final int Y = 80;
	
	public static int[] price = new int[] {
			50,100,250,500,750,1000,1500,2000,3000,4000,5000,7500,10000
	};
	
	private static int index = -1;
	
	private static final File HouseModel = new File("assets/model3d/house/model.dat");
	
	private static final Vector6f size = new Vector6f(0,0,0,0,0,0);
	
	public static void loadUp() {
		if(HouseModel.exists())
		{
			CompoundTag tag = BlockPlanNBT.ReadCompoundTag(HouseModel);
			if(tag != null && tag.get("xyzuvw") != null)
			{
				Vector6f v = (Vector6f)((SerializableTag)tag.get("xyzuvw")).getValue();
				size.u = v.u;
				size.v = v.v;
				size.w = v.w;
				size.y = Y;
				size.z = Z;
			}
			
			World w = DimensionManager.getWorld(0);
			boolean flag = false;
			int x = 0;
			index = -1;
			while(!flag)
			{
				index ++;
				x += 50;
				flag = w.getBlock(X + x, Y, Z) == Blocks.air;
			}
			main.l.info("load "+index+" player's house !");
		}
	}
	
	
	
	public static int createHouse() {
		BlockPlanNBT.writeBlock(DimensionManager.getWorld(0), X + index * 55, Y, Z, BlockPlanNBT.ReadCompoundTag(HouseModel));
		return index;
	}
	
	public static void TeleportPlayerToHouse(ServerPlayerData p) {
		p.lastBeforeHouse = new Vector3f((int)p.p.posX,(int) p.p.posY,(int)p.p.posZ);
		Teleport.player(p.p, X +  p.HouseIndex * 55, Y+1, Z);
	}
	
	public static boolean isUnderHouse(ServerPlayerData p)
	{
		Vector6f v = size.copy();
		v.x = X + p.HouseIndex * 55;
		return v.isUnderExclu(new Vector3f((int)p.p.posX,(int)p.p.posY,(int)p.p.posZ));
	}
	
}
