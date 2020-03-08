package fr.neocraft.main.Server.function;

import java.util.Timer;
import java.util.TimerTask;

import fr.neocraft.main.main;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class repairWorld {

	private int x = -10000, y = 0, z = -10000;
	
	
	public repairWorld() {
		loop(DimensionManager.getWorld(0));
	}
	
	private void loop(final World w)
	{
		for(int i = 0; i <= 5000; i++) {
			x++;
			if(x >= 10000 )
			{
				x = -10000;
				y++;
			}
			if(y > 254)
			{
				z++;
				y = 0;
			}
			if(z >= 10000 )
			{
				return;
			}
			if(Block.getIdFromBlock(w.getBlock(x, y, z)) == 191)
			{
				w.setBlock(x, y, z, Blocks.fence, 0, 1);
			}
		}
		main.l.info("[repairWorld] x:"+x+" y:"+y+" z:"+z);
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				loop(w);
			}
		}, 5);
	}
}
