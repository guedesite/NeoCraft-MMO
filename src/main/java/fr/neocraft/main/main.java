package fr.neocraft.main;

import java.util.HashMap;
import java.util.ListIterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.neocraft.main.Init.BlockMod;
import fr.neocraft.main.Init.ItemMod;
import fr.neocraft.main.Server.ClientPlayerData;
import fr.neocraft.main.Server.HouseManager;
import fr.neocraft.main.Server.ServerPlayerData;
import fr.neocraft.main.Server.Zone.ZoneManager;
import fr.neocraft.main.Server.cmd.CommandDonjon;
import fr.neocraft.main.Server.cmd.CommandManager;
import fr.neocraft.main.Server.cmd.CommandSeeds;
import fr.neocraft.main.event.ZoneEventFML;
import fr.neocraft.main.event.ZoneEventFORGE;
import fr.neocraft.main.proxy.CommonProxy;
import fr.neocraft.main.util.CRASH;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, useMetadata = true)
public class main {

	@SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY)
    public static CommonProxy proxy;
    @Instance(Reference.MOD_ID)
    public static main instance;
	
    public static final Logger l = LogManager.getLogger();
    
    public static final bdd bdd = new bdd();
    public static HashMap<String, ClientPlayerData> AllPlayer = new HashMap<String, ClientPlayerData>();
    
    public static HashMap<String, ServerPlayerData> AllPlayerServer = new HashMap<String, ServerPlayerData>();
    
    public static SimpleNetworkWrapper NetWorkClient, NetWorkServer;
    public static CreativeTabs neocraft;
    private void registerTab()
    {
    	neocraft= new CreativeTabs("neocraft")
        {
            @SideOnly(Side.CLIENT)
            public Item getTabIconItem()
            {
                return ItemMod.Copyright_NeoCraft;
            }
        };
    }
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	CRASH.init();
    	
    	registerTab();
    	ItemMod.Init();
    	ItemMod.register();
    	BlockMod.init();
    	BlockMod.register();
    	 NetWorkClient = NetworkRegistry.INSTANCE.newSimpleChannel("NeoNetWorkClient");
         NetWorkClient.registerMessage(fr.neocraft.main.proxy.network.NetWorkClient.Handler.class, fr.neocraft.main.proxy.network.NetWorkClient.class, 0, Side.CLIENT);
        
         NetWorkServer = NetworkRegistry.INSTANCE.newSimpleChannel("NeoNetWorkServer");
	       NetWorkServer.registerMessage(fr.neocraft.main.proxy.network.NetWorkServer.Handler.class, fr.neocraft.main.proxy.network.NetWorkServer.class, 0, Side.SERVER);
    	
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	proxy.onInit();
    	if(bdd.IsClass)
    	{
    		FMLCommonHandler.instance().bus().register(new ZoneEventFML());
    		MinecraftForge.EVENT_BUS.register(new ZoneEventFORGE());
    	} else {
    		
    	}
    	
    	
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    	
    }
    
    @EventHandler
    public void serverStarting(FMLServerStartedEvent event)
    {	
    	if(bdd.IsClass)
    	{
    		bdd.Openbdd();
    		if(bdd.IsOpen)
    		{
    			HouseManager.loadUp();
    			ZoneManager.Register();
    			ServerCommandManager cmdman = (ServerCommandManager) MinecraftServer.getServer().getCommandManager(); 
		    	cmdman.registerCommand(new CommandManager());
		    	cmdman.registerCommand(new CommandSeeds());
		    	cmdman.registerCommand(new CommandDonjon());
    		}
    	}
    }
    
    @EventHandler
    public void onServerStopped(FMLServerStoppingEvent event)
    {	

    }
    
    public static EntityPlayer getPlayer(String name){
	    ListIterator li = (ListIterator) MinecraftServer.getServer().getConfigurationManager().playerEntityList.listIterator();
	    while (li.hasNext()){
	        EntityPlayer p = (EntityPlayer) li.next();
	        if(p.getGameProfile().getName().equals(name)){
	            return p;
	        }
	    }
	    return null;
	}
    
    public static ListIterator getPlayer(){
	    return (ListIterator) MinecraftServer.getServer().getConfigurationManager().playerEntityList.listIterator();

	}
}
