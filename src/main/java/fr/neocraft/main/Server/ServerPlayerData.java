package fr.neocraft.main.Server;

import fr.neocraft.main.util.Vector3f;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import static fr.neocraft.main.main.bdd;
import net.minecraft.util.ChatComponentTranslation;
import net.querz.nbt.CompoundTag;
import fr.neocraft.main.Server.Zone.Zone;

public class ServerPlayerData {
	public int life;
	public int regen;
	public double damage;
	public double speed;
	public int MF;
	
	public String grade;
	
	public String classe;
	public String race;
	
	public double Money;
	
	public int power;
	
	public double[] reputation = new double[2];
	
	public boolean debug = false;
	
	public int HouseIndex;
	public int HouseBy;
	
	public Zone Zone;
	
	// custom:
	public Vector3f pos1, pos2, lastBeforeHouse = null;
	public CompoundTag seeds;
	
	public EntityPlayer p;
	
	public ServerPlayerData(EntityPlayer pl) {
		p = pl;
	}
	
	public boolean hasMoneyAndNotif( double amount)
	{
		if(Money > amount || debug)
		{	SoundManager.PlaySound(EnumSound.NeoMClick.getSound(), p);
			return true;
		}else {
			SoundManager.PlaySound(EnumSound.NeoMNope.getSound(), p);
			M(p, "neo.money.deny.amount", "&5"+ (amount - Money));
			return false;
		}
	}
	
	public boolean hasMoney( double amount)
	{
		return Money > amount || debug;
	}
	
	public void RemoveMoney(double amount) {
		if(!debug)
		{
			Money -= amount;
		}
		M(p, "neo.money.remove", "&4"+amount);
		UpdateVal("Money", Money);
	}
	
	private void M(ICommandSender player, String msg, Object ... obj) {
		player.addChatMessage(new ChatComponentTranslation(msg,obj));
		
	}
	
	public void UpdateVal(String key, Object value)
	{
		if(value instanceof String)
		{
			value = "'"+value+"'";
		}
		bdd.updateProtocole(bdd.getStringPlayer(), new Object[] { key, value}, "WHERE Pseudo='"+p.getCommandSenderName()+"'");
	}
	
}
