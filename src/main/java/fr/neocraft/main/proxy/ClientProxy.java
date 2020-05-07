package fr.neocraft.main.proxy;

import java.util.HashMap;
import java.util.Iterator;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import fr.neocraft.main.Init.EntityMod;
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
import fr.neocraft.main.util.CRASH;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {


	public static int RenderIdBarrierRed = 0,
					  RenderIdBlockDoorHouse = 0;
	
	public static GuiNeoInGame NeoInGame;
	
	public static NeoEntityRenderer EntityRenderer;
	public static RenderEventClient GuiClientManager;
	
	public static ClientPlayerData player = null;
	
	@Override
	public void onInit() {
		Minecraft mc = Minecraft.getMinecraft();
		mc.entityRenderer = EntityRenderer= new NeoEntityRenderer(mc, mc.getResourceManager());
		mc.mcProfiler.profilingEnabled = false;
	
		registerClientModel();
		
		FMLCommonHandler.instance().bus().register(new TickClientEvent(Minecraft.getMinecraft()));
		MinecraftForge.EVENT_BUS.register(GuiClientManager = new RenderEventClient());
		RenderIdBarrierRed = RenderingRegistry.getNextAvailableRenderId();
	    RenderingRegistry.registerBlockHandler(RenderIdBarrierRed, new RenderBlockBarrierRed());
	    
	    RenderIdBlockDoorHouse = RenderingRegistry.getNextAvailableRenderId();
	    RenderingRegistry.registerBlockHandler(RenderIdBlockDoorHouse, new RenderBlockDoorHouse());
	    
	    MinecraftForgeClient.registerItemRenderer(ItemMod.MMOBow, new BowRenderer());

	    
	}

	public static boolean ExecuteCommand(String cmd)
	{
		return net.minecraftforge.client.ClientCommandHandler.instance.executeCommand(Minecraft.getMinecraft().thePlayer, cmd) != 0;
	}
	
	@Override
	public void onPostInit() {
		RenderEventClient.PostInit();
	}
	
	
	public static HashMap<Integer, ModelSkin> allModel = new HashMap<Integer, ModelSkin>();
	

	static class ModelSkin {
		public ResourceLocation[] res;
		public ModelBase model;
		public ModelSkin(ModelBase model, ResourceLocation[] res)
		{
			this.res = res;
			this.model = model;
		}
	}
	
	public static void registerClientModel() {
		EntityMod.initRender();
		Iterator it = EntityPnjAction.allClass.keySet().iterator();
		while(it.hasNext())
		{
			int i = (Integer) it.next();
			EntityPnjAction.ClassSkin ck = EntityPnjAction.allClass.get(i);
			
			try {
				allModel.put(i, new ModelSkin((ModelBase) Class.forName(ck.model).newInstance(), ck.res));
			} catch (Exception e) {
				CRASH.Push(e);
			}
		}
		EntityPnjAction.allClass = null;
	}
}
