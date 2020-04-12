package fr.neocraft.main.Server;

import fr.neocraft.main.util.CRASH;
import fr.neocraft.main.util.Vector3f;
import fr.neocraft.pnj.PnjData;
import fr.neocraft.quest.ElementData;
import fr.neocraft.quest.QuestData;
import fr.neocraft.quest.Condition.EnumCondition;
import fr.neocraft.quest.Condition.QuestCondition;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import static fr.neocraft.main.main.bdd;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.util.ChatComponentTranslation;
import net.querz.nbt.CompoundTag;
import net.querz.nbt.NBTUtil;
import net.querz.nbt.Tag;
import net.querz.nbt.custom.PnjDataTag;
import net.querz.nbt.custom.SerializableTag;
import fr.neocraft.main.main;
import fr.neocraft.main.Server.Quest.DataManager;
import fr.neocraft.main.Server.Quest.QuestClientGuiInfo;
import fr.neocraft.main.Server.Zone.Zone;
import fr.neocraft.main.proxy.network.NetWorkClient;
import fr.neocraft.main.proxy.network.util.object.ClientSetQuest;

public class ServerPlayerData {
	public int life;
	public int regen;
	public double damage;
	public double speed;
	public int MF;
	
	public String grade;
	
	public String classe;
	public String race;
	
	public double Money =0;
	
	public int power =0;
	
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
	public Quest quest;
	
	public ServerPlayerData(EntityPlayer pl) {
		p = pl;
		quest=new Quest(pl.getUniqueID().toString());
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
	
	public void giveMoney(double amount) {
		Money += amount;
		M(p, "neo.money.add", "&a"+amount);
		UpdateVal("Money", Money);
	}
	
	public void removeMoney(double amount) {
		if(!debug)
		{
			Money -= amount;
		}
		if(0 > Money)
		{
			Money = 0;
		}
		M(p, "neo.money.remove", "&4"+amount);
		UpdateVal("Money", Money);
	}
	
	public boolean hasMoney( double amount)
	{
		return Money > amount || debug;
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
	

	public class Quest {
		
		public Map<Integer, QuestPlayerData> data;
		
		
		public void addQuest(EntityPlayer pl, QuestData q) {
			
			data.put(q.id, new QuestPlayerData(q.Element.get(0).condition));
			Save(pl.getUniqueID().toString());
			SendToPlayerAllQuest(pl);
		}
		
		public void removeQuest(EntityPlayer pl, int id) {
			
			data.remove(id);
			Save(pl.getUniqueID().toString());
		}
		
		
		public QuestClientGuiInfo[] SendToPlayerAllQuest(EntityPlayer pl) {
			QuestClientGuiInfo[] finale = new QuestClientGuiInfo[data.size()];
			
			int o =0;
			Iterator it = data.keySet().iterator();
			while(it.hasNext())
			{
				int id = (Integer) it.next();
				QuestClientGuiInfo info = new QuestClientGuiInfo();
				QuestData q = DataManager.getQuestById(id);
				
				info.titre = q.Title;
				info.texte = q.Description;
				
				ElementData el = q.Element.get(data.get(id).PlayerIndex);
				
				info.conditions = new Object[el.condition.size()][4];
				for(int i = 0; i < el.condition.size() ; i++)
				{
				
					info.conditions[i] = new Object[] { el.condition.get(i).getName(), el.condition.get(i).Value,el.condition.get(i).Value2, false }; 
				}
	
				info.recompense = new Object[el.recompense.size()][3];
				for(int i = 0; i < q.QuestCondition.size() ; i++)
				{
					info.recompense[i] = new Object[] { el.recompense.get(i).getName(), el.recompense.get(i).Value,el.recompense.get(i).Value2 }; 
				}
				finale[o] = info;
				o++;
			}
			
			return finale;
		}
		
		public void Event(EntityPlayer EntityPlayer, Object ad1, Object ad2, EnumCondition en) {
			Iterator it = data.keySet().iterator();
			ArrayList<Integer> toRemove = new ArrayList<Integer>();
			while(it.hasNext())
			{
				int id = (Integer) it.next();
				QuestPlayerData pl = (QuestPlayerData) data.get(id);
				if(pl.Event(EntityPlayer, ad1, ad2, en)) {
					DataManager.getQuestById(id).endElement(EntityPlayer, pl.PlayerIndex);
					if(pl.PlayerIndex == -1)
					{
						toRemove.add(id);
					}
					else {
						
					}
				}
			}
			for(int i:toRemove) {
				data.remove(i);
			}
		}
		
		public Quest(String uuid) {
			File f = new File("assets/PlayerQuest/"+uuid+".dat");
			if(f.exists())
			{
				try {
					data = (HashMap<Integer, QuestPlayerData>) ((SerializableTag) NBTUtil.readTag(f).clone()).getValue();
				} catch (Exception e) {
					CRASH.Push(e);
				}
			}
			else {
				data = new HashMap<Integer, QuestPlayerData>();
				Save(uuid);
			}
		}
		
		public void Save(String uuid) {
			try {
				NBTUtil.writeTag(new SerializableTag((Serializable) data), "assets/PlayerQuest/"+uuid+".dat");
			} catch(Exception e) {
				CRASH.Push(e);
			}
		}
		
		
		class QuestPlayerData implements Serializable{
			private static final long serialVersionUID = 879879986L;
			public int PlayerIndex;
			
			public ArrayList<valCondition> allcond;
			
			public QuestPlayerData(ArrayList<QuestCondition> cond)
			{ 
				PlayerIndex=0;
				allcond = new ArrayList<valCondition>();
				for(QuestCondition q:cond)
				{
					allcond.add(new valCondition((q.Value != null ? ((String)q.Value) : "")+(q.Value2 != null ? ((String) q.Value2) : "")+q.getName()));
				}
			}
			
			
			
			public boolean Event(Object EntityPlayer, Object data, Object data2, EnumCondition en) {
				String ev = (data != null ? ((String)data) : "")+(data2 != null ? ((String) data2) : "")+en.name();
				for(int i = 0; i < allcond.size(); i++)
				{
					if(allcond.get(i).index.equals(ev))
					{
						int d = EnumCondition.getCondition(en).CheckCondition(EntityPlayer, data, data2,allcond.get(i).amount);
						if(d == 0)
						{
							allcond.remove(i);
						}
						else if(d > 0)
						{
							allcond.get(i).amount += d;
						}
					}
				}
				return  allcond.size() == 0;
			}
			
			class valCondition implements Serializable{

				private static final long serialVersionUID = 879879987L;
				String index;
				int amount;
				
				public valCondition(String in)
				{
					index = in;
					amount = 0;
				}
			}
		}
		
	}
	
	
	
}
