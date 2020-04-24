package fr.neocraft.main.proxy;

import net.minecraft.server.MinecraftServer;

public class CommonProxy {

	public void onInit() {
		MinecraftServer.getServer().theProfiler.profilingEnabled = false;
	//	EntityRegistry.registerModEntity(EntityArrowBow.class, "ArrowBow", EntityRegistry.findGlobalUniqueEntityId(), main.instance, 10, 20, false); 
	}
	
	public void onPostInit() {
		
	}
	
}
