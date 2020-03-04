package fr.neocraft.main.event;


import java.util.Random;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;
import fr.neocraft.main.Server.DonjonManager;
import net.minecraftforge.common.DimensionManager;


public class TickServerEvent {

	private long TimerRandom = System.currentTimeMillis();
	private long Timer1sur4 = System.currentTimeMillis();
	private Random r = new Random();
	@SubscribeEvent
    public void onTick(ServerTickEvent evt) {
		if(evt.phase == TickEvent.Phase.START)
		{
			if(TimerRandom < System.currentTimeMillis())
			{
				TimerRandom = System.currentTimeMillis() + (r.nextInt(25)+5) * 60000;
				DonjonManager.timerDonjon();
			}
			if(Timer1sur4 < System.currentTimeMillis())
			{
				Timer1sur4 = System.currentTimeMillis() + 250;
				DonjonManager.nextFrame(DimensionManager.getWorld(0));
			}
		}
	}
	
}
