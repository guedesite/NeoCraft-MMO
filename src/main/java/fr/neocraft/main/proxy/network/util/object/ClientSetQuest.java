package fr.neocraft.main.proxy.network.util.object;


import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.neocraft.main.main;
import fr.neocraft.main.Server.Quest.QuestClientGuiInfo;
import fr.neocraft.main.proxy.network.util.T;
import net.minecraft.client.Minecraft;

public class ClientSetQuest extends T {

	private  static  final  long serialVersionUID =  5464867684657468768L;
	
	private QuestClientGuiInfo[] Id;
	public ClientSetQuest(QuestClientGuiInfo[] id) {
		Id=id;

	}
	
	
	@Override
	@SideOnly(Side.CLIENT)
	public void A() {
		main.AllPlayer.get(Minecraft.getMinecraft().thePlayer.getCommandSenderName()).clientQuest = Id;
	}
	
}

