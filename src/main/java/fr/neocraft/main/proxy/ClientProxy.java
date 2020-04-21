package fr.neocraft.main.proxy;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import fr.neocraft.main.Init.ItemMod;
import fr.neocraft.main.Server.ClientPlayerData;
import fr.neocraft.main.entity.EntityPnjAction;
import fr.neocraft.main.event.RenderEventClient;
import fr.neocraft.main.event.TickClientEvent;
import fr.neocraft.main.render.Block.RenderBlockBarrierRed;
import fr.neocraft.main.render.Block.RenderBlockDoorHouse;
import fr.neocraft.main.render.Entity.NeoEntityRenderer;
import fr.neocraft.main.render.Item.BowRenderer;
import fr.neocraft.main.render.gui.internal.GuiNeoInGame;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {


	public static int RenderIdBarrierRed = 0,
					  RenderIdBlockDoorHouse = 0;
	
	public static GuiNeoInGame NeoInGame;
	
	public static NeoEntityRenderer EntityRenderer;
	public static RenderEventClient GuiClientManager;
	
	public static ClientPlayerData player;
	
	@Override
	public void onInit() {
		Minecraft mc = Minecraft.getMinecraft();
		mc.entityRenderer = EntityRenderer= new NeoEntityRenderer(mc, mc.getResourceManager());
		mc.mcProfiler.profilingEnabled = false;
	
		EntityPnjAction.registerClientModel();
		
		FMLCommonHandler.instance().bus().register(new TickClientEvent(Minecraft.getMinecraft()));
		MinecraftForge.EVENT_BUS.register(GuiClientManager = new RenderEventClient());
		RenderIdBarrierRed = RenderingRegistry.getNextAvailableRenderId();
	    RenderingRegistry.registerBlockHandler(RenderIdBarrierRed, new RenderBlockBarrierRed());
	    
	    RenderIdBlockDoorHouse = RenderingRegistry.getNextAvailableRenderId();
	    RenderingRegistry.registerBlockHandler(RenderIdBlockDoorHouse, new RenderBlockDoorHouse());
	    
	    MinecraftForgeClient.registerItemRenderer(ItemMod.MMOBow, new BowRenderer());

	    
	}
	
}
