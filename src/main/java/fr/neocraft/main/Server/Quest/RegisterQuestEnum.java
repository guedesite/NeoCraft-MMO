package fr.neocraft.main.Server.Quest;


import fr.neocraft.main.main;
import fr.neocraft.main.proxy.network.NetWorkClient;
import fr.neocraft.main.proxy.network.util.object.ClienGui;
import fr.neocraft.pnj.Action.EnumPnjAction;
import fr.neocraft.pnj.Action.PnjAction;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.EntityPlayer;

public class RegisterQuestEnum {

	public static void RegisterPnjAction() {
		EnumPnjAction.register();
		/*
		EnumPnjAction.setCondition(EnumPnjAction., new PnjAction() { 
			@Override
			public boolean MakeAction(Object EntityPlayer) {
				return false;
			}
		});
		 * 
		 */
		
		
		
		EnumPnjAction.setCondition(EnumPnjAction.Null, new PnjAction() { });
		
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
				((EntityPlayer)pl).addExperience((Integer)(this.Value));
				return false;
			}
		});
		
	}
	
}
