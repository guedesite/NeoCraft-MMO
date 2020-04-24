package fr.neocraft.main.Server.Quest;


import fr.neocraft.main.main;
import fr.neocraft.main.proxy.network.NetWorkClient;
import fr.neocraft.main.proxy.network.util.object.ClienGui;
import fr.neocraft.main.proxy.network.util.object.ClientUpdateMapPos;
import fr.neocraft.main.util.Teleport;
import fr.neocraft.main.util.Vector3f;
import fr.neocraft.pnj.Action.EnumPnjAction;
import fr.neocraft.pnj.Action.PnjAction;
import fr.neocraft.quest.Recompense.EnumRecompense;
import fr.neocraft.quest.Recompense.QuestRecompense;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;

public class RegisterQuestEnum {

	public static void RegisterPnjAction() {
		EnumPnjAction.register();
		/*


	ExecutePlayerCommand,
	ExecuteConsolCommand,
			//net.minecraftforge.client.ClientCommandHandler.instance.executeCommand(mc.thePlayer, p_146403_1_)
		 */
		
		
		EnumPnjAction.setCondition(EnumPnjAction.Null, new PnjAction() {
			@Override
			public boolean MakeAction(Object arg0) {
				net.minecraftforge.client.ClientCommandHandler.instance.executeCommand(MinecraftServer.getServer(), "");
				return true;
			} 
		});
		EnumPnjAction.setCondition(EnumPnjAction.GiveMoney, new PnjAction() {
			@Override
			public boolean MakeAction(Object entity) {
				main.AllPlayerServer.get((EntityPlayer)entity).giveMoney(Integer.parseInt((String) this.Value));
				return true;
			} 
		});
		EnumPnjAction.setCondition(EnumPnjAction.RemoveMoney, new PnjAction() {
			@Override
			public boolean MakeAction(Object entity) {
				main.AllPlayerServer.get((EntityPlayer)entity).removeMoney(Integer.parseInt((String) this.Value));
				return true;
			} 
		});
		EnumPnjAction.setCondition(EnumPnjAction.GiveQuest, new PnjAction() {
			@Override
			public boolean MakeAction(Object entity)
			{
				 main.AllPlayerServer.get((EntityPlayer)entity).quest.addQuest((EntityPlayer)entity, DataManager.getQuestById(Integer.parseInt(this.Value+"")));
				return true;
			}
		});
		
		EnumPnjAction.setCondition(EnumPnjAction.Teleport, new PnjAction() {
			@Override
			public boolean MakeAction(Object entity)
			{
				String[] xyz = ((String) this.Value).split(":");
				Teleport.player((EntityPlayer)entity, Integer.parseInt(xyz[0]), Integer.parseInt(xyz[1]), Integer.parseInt(xyz[2]));
				return true;
			}
		});
		
		EnumPnjAction.setCondition(EnumPnjAction.CloseText, new PnjAction() { 
			@Override
			public boolean MakeAction(Object EntityPlayer) {
				main.NetWorkClient.sendTo(new NetWorkClient(new ClienGui(-1)), (EntityPlayerMP)EntityPlayer);
				return true;
			}
		});
		
		EnumPnjAction.setCondition(EnumPnjAction.GiveExp, new PnjAction() { 
			@Override
			public boolean MakeAction(Object pl) {
				((EntityPlayer)pl).addExperience(Integer.parseInt((String)this.Value));
				return true;
			}
		});
		EnumPnjAction.setCondition(EnumPnjAction.GiveItem, new PnjAction() { 
			@Override
			public boolean MakeAction(Object pl) {
				int dmg = 0;
				int id = 0;
				int amount = this.Value2 != null && (String)this.Value2 != "" && (String)this.Value2 != " " ? Integer.parseInt((String)this.Value2) :0;
				if(((String)this.Value).contains(":"))
				{
					int i = ((String)this.Value).indexOf(":");
					id = Integer.parseInt(((String)this.Value).substring(0, i));
					dmg = Integer.parseInt(((String)this.Value).substring(i));
				} else {
					id = Integer.parseInt(((String)this.Value));
				}
				ItemStack it = new ItemStack(Item.getItemById(id), amount, dmg);
				EntityPlayer player = ((EntityPlayer)pl);
				if(!player.inventory.addItemStackToInventory(it)) {
					EntityItem ent = new EntityItem(player.worldObj, player.posX+0.5, player.posY+0.5, player.posZ+0.5, it);
					player.worldObj.spawnEntityInWorld(ent);
					player.addChatMessage(new ChatComponentTranslation("neo.dropItem"));
				}
				return true;
			}
		});
		EnumPnjAction.setCondition(EnumPnjAction.GiveLvl, new PnjAction() { 
			@Override
			public boolean MakeAction(Object pl) {
				((EntityPlayer)pl).addExperienceLevel(Integer.parseInt((String)this.Value));
				return true;
			}
		});
		EnumPnjAction.setCondition(EnumPnjAction.ShowDialogue, new PnjAction() { 
			@Override
			public boolean MakeAction(Object pl) {
				main.NetWorkClient.sendTo(new NetWorkClient(new ClienGui(Integer.parseInt((String)this.Value))), (EntityPlayerMP)pl);
				return true;
			}
		});
		EnumPnjAction.setCondition(EnumPnjAction.TextInChat, new PnjAction() { 
			@Override
			public boolean MakeAction(Object pl) {
				((EntityPlayer)pl).addChatMessage(new ChatComponentText((String)this.Value));
				return true;
			}
		});
		
		EnumPnjAction.setCondition(EnumPnjAction.AddMapPoint, new PnjAction() { 
			@Override
			public boolean MakeAction(Object pl) {
				main.NetWorkClient.sendTo(new NetWorkClient(new ClientUpdateMapPos(((EntityPlayer)pl).getCommandSenderName(), 0, new Vector3f(Integer.parseInt((String)this.Value), 0, Integer.parseInt((String)this.Value2)))), (EntityPlayerMP)pl);
				return true;
			}
		});
		
		EnumPnjAction.setCondition(EnumPnjAction.RemoveMapPoint, new PnjAction() { 
			@Override
			public boolean MakeAction(Object pl) {
				main.NetWorkClient.sendTo(new NetWorkClient(new ClientUpdateMapPos(((EntityPlayer)pl).getCommandSenderName(),-1, new Vector3f(Integer.parseInt((String)this.Value), 125, Integer.parseInt((String)this.Value2)))), (EntityPlayerMP)pl);
				return true;
			}
		});
		
		
		
		//cmd
		
		
		EnumRecompense.register();
		EnumRecompense.setCondition(EnumRecompense.Null, new QuestRecompense() {

			@Override
			public void GiveRecompense(Object arg0) {
		
			} });
		
		
		
		EnumRecompense.setCondition(EnumRecompense.GiveXp, new QuestRecompense() { 
			@Override
			public void GiveRecompense(Object pl) {
				((EntityPlayer)pl).addExperience(Integer.parseInt((String)this.Value));
		
			}
		});
		EnumRecompense.setCondition(EnumRecompense.GiveItem, new QuestRecompense() { 
			@Override
			public void GiveRecompense(Object pl) {
				int dmg = 0;
				int id = 0;
				int amount = this.Value2 != null && (String)this.Value2 != "" && (String)this.Value2 != " " ? Integer.parseInt((String)this.Value2) :0;
				if(((String)this.Value).contains(":"))
				{
					int i = ((String)this.Value).indexOf(":");
					id = Integer.parseInt(((String)this.Value).substring(0, i));
					dmg = Integer.parseInt(((String)this.Value).substring(i));
				} else {
					id = Integer.parseInt(((String)this.Value));
				}
				ItemStack it = new ItemStack(Item.getItemById(id), amount, dmg);
				EntityPlayer player = ((EntityPlayer)pl);
				if(!player.inventory.addItemStackToInventory(it)) {
					EntityItem ent = new EntityItem(player.worldObj, player.posX+0.5, player.posY+0.5, player.posZ+0.5, it);
					player.worldObj.spawnEntityInWorld(ent);
					player.addChatMessage(new ChatComponentTranslation("neo.dropItem"));
				}
			}
		});
		EnumRecompense.setCondition(EnumRecompense.GiveLevel, new QuestRecompense() { 
			@Override
			public void GiveRecompense(Object pl) {
				((EntityPlayer)pl).addExperienceLevel(Integer.parseInt((String)this.Value));
			}
		});
		EnumRecompense.setCondition(EnumRecompense.ShowDialogue, new QuestRecompense() { 
			@Override
			public void GiveRecompense(Object pl) {
				main.NetWorkClient.sendTo(new NetWorkClient(new ClienGui(Integer.parseInt((String)this.Value))), (EntityPlayerMP)pl);
			}
		});
		EnumRecompense.setCondition(EnumRecompense.TextInChat, new QuestRecompense() { 
			@Override
			public void GiveRecompense(Object pl) {
				((EntityPlayer)pl).addChatMessage(new ChatComponentText((String)this.Value));
			}
		});
		
		EnumRecompense.setCondition(EnumRecompense.AddMapPoint, new QuestRecompense() { 
			@Override
			public void GiveRecompense(Object pl) {
				main.NetWorkClient.sendTo(new NetWorkClient(new ClientUpdateMapPos(((EntityPlayer)pl).getCommandSenderName(), 0, new Vector3f(Integer.parseInt((String)this.Value), 0, Integer.parseInt((String)this.Value2)))), (EntityPlayerMP)pl);
			}
		});
		
		EnumRecompense.setCondition(EnumRecompense.RemoveMapPoint, new QuestRecompense() { 
			@Override
			public void GiveRecompense(Object pl) {
				main.NetWorkClient.sendTo(new NetWorkClient(new ClientUpdateMapPos(((EntityPlayer)pl).getCommandSenderName(),-1, new Vector3f(Integer.parseInt((String)this.Value), 125, Integer.parseInt((String)this.Value2)))), (EntityPlayerMP)pl);
			}
		});
		
		EnumRecompense.setCondition(EnumRecompense.Teleport, new QuestRecompense() { 
			
			@Override
			public void GiveRecompense(Object entity)
			{
				String[] xyz = ((String) this.Value).split(":");
				Teleport.player((EntityPlayer)entity, Integer.parseInt(xyz[0]), Integer.parseInt(xyz[1]), Integer.parseInt(xyz[2]));
			}
			
		});
		
		EnumRecompense.setCondition(EnumRecompense.GiveQuest, new QuestRecompense() { 
			
			@Override
			public void GiveRecompense(Object entity)
			{
				main.AllPlayerServer.get((EntityPlayer)entity).quest.addQuest((EntityPlayer)entity, DataManager.getQuestById(Integer.parseInt(this.Value+"")));
			}
			
		});
		
		EnumRecompense.setCondition(EnumRecompense.GiveMoney, new QuestRecompense() { 
			@Override
			public void GiveRecompense(Object entity)
			{
				main.AllPlayerServer.get((EntityPlayer)entity).giveMoney(Integer.parseInt((String) this.Value));
			} 
		});
		EnumRecompense.setCondition(EnumRecompense.RemoveMoney, new QuestRecompense() { 
			
			@Override
			public void GiveRecompense(Object entity)
			{
				main.AllPlayerServer.get((EntityPlayer)entity).removeMoney(Integer.parseInt((String) this.Value));
			} 
		});
		
	}
	
}
