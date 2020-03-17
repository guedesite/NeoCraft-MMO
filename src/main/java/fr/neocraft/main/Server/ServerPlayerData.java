package fr.neocraft.main.Server;

import fr.neocraft.main.util.CRASH;
import fr.neocraft.main.util.Vector3f;
import fr.neocraft.pnj.PnjData;
import fr.neocraft.quest.QuestData;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import static fr.neocraft.main.main.bdd;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.util.ChatComponentTranslation;
import net.querz.nbt.CompoundTag;
import net.querz.nbt.NBTUtil;
import net.querz.nbt.Tag;
import net.querz.nbt.custom.PnjDataTag;
import net.querz.nbt.custom.SerializableTag;
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
	
	public ArrayList<Vector3f> posMap;
	
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
	

	class Quest {
		
		public List<QuestData> data;
		
		public Quest(String uuid) {
			data = new ArrayList<QuestData>();
			File f = new File("assets/PlayerQuest/"+uuid+".dat");
			if(f.exists())
			{
				try {
					data = (List<QuestData>) ((SerializableTag) NBTUtil.readTag(f).clone()).getValue();
				} catch (Exception e) {
					CRASH.Push(e);
				}
			}
		}
		
		public void Save(String uuid) {
			try {
				File f = new File("assets/PlayerQuest/"+uuid+".dat");
				NBTUtil.writeTag(new SerializableTag((Serializable) data), f);
			} catch(Exception e) {
				CRASH.Push(e);
			}
		}
		
	}
	
	
	
}
