package fr.neocraft.main.Server;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import fr.neocraft.main.main;
import fr.neocraft.main.Server.Zone.Zone;
import fr.neocraft.main.Server.Zone.ZoneManager;
import fr.neocraft.main.util.BlockPlanNBT;
import fr.neocraft.main.util.CRASH;
import fr.neocraft.main.util.Vector6f;
import net.minecraft.block.Block;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.querz.nbt.CompoundTag;
import net.querz.nbt.ListTag;
import net.querz.nbt.custom.SerializableTag;

public class DonjonManager {

	private static ArrayList<File> type0;
	private static ArrayList<File> type1;
	private static ArrayList<File> type2;
	private static ArrayList<File> type3;
	private static ArrayList<stockDonjonBeforeEnd> EndingDonjon = new ArrayList<stockDonjonBeforeEnd>();
	private static final String pathModel = new String("assets/model3d/donjon/");
	
	public static void registerDonjon() {
		main.l.info("start register donjon");
		
		type0 = new ArrayList<File>();
		type1= new ArrayList<File>();
		type2= new ArrayList<File>();
		type3= new ArrayList<File>();
		

		for(int i = 0; i < 256 ; i++)
		{
			File f = new File(pathModel+"donjon-"+i+".dat");
			if(f.exists())
			{
				switch(BlockPlanNBT.ReadCompoundTag(f).getInt("type"))
				{
					case 0:
						type0.add(f);
						break;
					case 1:
						type1.add(f);
						break;
					case 2:
						type2.add(f);
						break;
					case 3:
						type3.add(f);
						break;
				}
			}
		}
		main.l.info("load: "+type0.size()+" type0");
		main.l.info("load: "+type1.size()+" type1");
		main.l.info("load: "+type2.size()+" type2");
		main.l.info("load: "+type3.size()+" type3");
		
		File f = new File(pathModel+"zone.dat");
		if(!f.exists())
		{

			BlockPlanNBT.saveCompoundTag(f, new CompoundTag());
		}
		CompoundTag tag = BlockPlanNBT.ReadCompoundTag(f);
		Iterator<Zone> it = ZoneManager.AllZone.values().iterator();
		String srt = "";
		while(it.hasNext())
		{
			Zone z = it.next();
			if(tag.get(z.id+"") == null)
			{
				tag.put(z.id+"", new ListTag(SerializableTag.class));
			}
			z.DonjonSpot = ((ListTag) tag.get(z.id+"")).size();
			srt += "["+z.id+":"+z.DonjonSpot+"]";
		}
		main.l.info("end register donjon");
	}
	
	private static final Random r = new Random();
	
	public static void timerDonjon() {
		File f  =new File(pathModel+"cache.dat");
		if(!f.exists())
		{
			BlockPlanNBT.saveCompoundTag(f, new CompoundTag());
		}
		CompoundTag tag = BlockPlanNBT.ReadCompoundTag(f);
		Iterator<Zone> it = ZoneManager.AllZone.values().iterator();
		while(it.hasNext())
		{
			Zone z = it.next();
			ListTag l;
			if(tag.getListTag(z.id+"") == null)
			{
				l = new ListTag(SerializableTag.class);
			} else {
				l = tag.getListTag(z.id+"");
			}
			if(l.size() <= z.DonjonSpot/2)
			{	
				File tagf = new File(pathModel+"zone.dat");
				CompoundTag tagZone = BlockPlanNBT.ReadCompoundTag(tagf);
				int d =z.DonjonSpot/2 - l.size();
				for(int i = 0; i <= d; i++)
				{
					int index = r.nextInt(((ListTag) tagZone.get(z.id+"")).size());
					Vector6f v = (Vector6f) ((SerializableTag) ((ListTag) tagZone.get(z.id+"")).get(index)).getValue();
					CompoundTag t = getRandomDonjonWithReg(z.donjonType, v);
					if(t != null)
					{
						BlockPlanNBT.saveCompoundTag(new File(pathModel+"cache-"+v.toSampleString()+".dat"), BlockPlanNBT.LoadBlockAndAire(DimensionManager.getWorld(0), v.x, v.y,v .z, v.u, v.v, v.w));
						BlockPlanNBT.writeBlock(DimensionManager.getWorld(0), v.x, v.y,v .z, t);
						initMobDonjon(v, t);
						l.add(tagZone.get(z.id+"").clone());
						((ListTag) tagZone.get(z.id+"")).remove(index);
					}
				}
				
				BlockPlanNBT.saveCompoundTag(tagf, tagZone);
			}
		}
		BlockPlanNBT.saveCompoundTag(f, tag);
	}
	
	public static void addDonjonZone(int idzone, Vector6f v )
	{
		File tagf = new File(pathModel+"zone.dat");
		CompoundTag tagZone = BlockPlanNBT.ReadCompoundTag(tagf);
		((ListTag) tagZone.get(idzone+"")).add(new SerializableTag(v));
		BlockPlanNBT.saveCompoundTag(tagf, tagZone);
	}
	
	public static void endDonjon(int Zoneid, Vector6f v)
	{
		EndingDonjon.add(new stockDonjonBeforeEnd(v, Zoneid));

	}
	
	public static void endAllDonjon() {
		File f  =new File(pathModel+"cache.dat");
		CompoundTag tag = BlockPlanNBT.ReadCompoundTag(f);
		Iterator<Zone> it = ZoneManager.AllZone.values().iterator();
		while(it.hasNext())
		{
			Zone z = it.next();
			ListTag l = tag.getListTag(z.id+"");
			for(int i = 0; i < l.size(); i++ )
			{
				EndingDonjon.add(new stockDonjonBeforeEnd((Vector6f)((SerializableTag) l.get(i)).getValue(), z.id));
			}
		}
	}
	
	
	
	
	private static void initMobDonjon(Vector6f v, CompoundTag t)
	{
		
	}
	
	private static CompoundTag getRandomDonjonWithReg(int type, Vector6f v)
	{
		ArrayList<File> ar = null;
		switch(type)
		{
			case 0:
				ar = type0;
				break;
			case 1:
				ar = type1;
				break;
			case 2:
				ar = type2;
				break;
			case 3:
				ar = type3;
				break;
			default:
				ar = type0;
				break;
		}
		int o = 0;
		CompoundTag tag = null;
		while(tag == null)
		{
			o++;
			int i  = r.nextInt(ar.size());
			if(((Vector6f)((SerializableTag)BlockPlanNBT.ReadCompoundTag(ar.get(i)).get("xyzuvw")).getValue()).isLessOrEqual(v))
			{
				tag = BlockPlanNBT.ReadCompoundTag(ar.get(i));
			}
			if(o == 30) 
			{
				return null;
			}
		}
		return tag;
	}

	public static void nextFrame(World w) {
		File tagf = new File(pathModel+"zone.dat");
		CompoundTag tagZone = BlockPlanNBT.ReadCompoundTag(tagf);
		
		File f  =new File(pathModel+"cache.dat");
		CompoundTag tag = BlockPlanNBT.ReadCompoundTag(f);
		
		for(stockDonjonBeforeEnd s:EndingDonjon)
		{
			if(s.stat < 1 )
			{
				s.stat++;
				continue;
			}
			int x = s.v.x;
			int y = s.v.y;
			int z = s.v.z;
			for(int i = 0; i < s.stat * s.coef && i < s.l.size(); i++)
			{
				if(r.nextInt(5) == 0)
				{
				
					CompoundTag cp = (CompoundTag)s.l.get(i);
					w.setBlock(x + cp.getInt("x"), y+ cp.getInt("y"), z+ cp.getInt("z"), Block.getBlockById(cp.getInt("id")), cp.getInt("meta"),0);
					if(cp.getBoolean("hastile"))
					{
						TileEntity tile = Block.getBlockById(cp.getInt("id")).createTileEntity(w, cp.getInt("meta"));
						tile.xCoord = x + cp.getInt("x");
						tile.yCoord = y + cp.getInt("y");
						tile.zCoord = z + cp.getInt("z");
						try {
							tile.readFromNBT(CompressedStreamTools.func_152457_a(cp.getByteArray("tile"), new NBTSizeTracker(99999999999L)));
						} catch (IOException e) {
							CRASH.Push(e);
						}
						w.setTileEntity(x + cp.getInt("x"),y + cp.getInt("y"), z + cp.getInt("z"), tile);
					}
				}
			}
			for(int i = 0; i < s.stat * s.coef && i < s.l.size(); i++)
			{
				s.l.remove(i);
			}
			if(s.l.size() == 0)
			{
				ListTag l = tag.getListTag(s.id+"");
				for(int i = 0; i < l.size(); i++ )
				{
					if(((Vector6f)((SerializableTag)l.get(i)).getValue()).isEqual(s.v))
					{
						((ListTag) tagZone.get(s.id+"")).add(l.get(i).clone());
						l.remove(i);
						break;
					}
				}
			}
		}
		BlockPlanNBT.saveCompoundTag(tagf , tagZone);
		BlockPlanNBT.saveCompoundTag(f, tag);
		
	}
	
	static class stockDonjonBeforeEnd{
		public Vector6f v;
		public int id;
		public int stat = -120;
		public int coef;
		public ListTag l;
		public stockDonjonBeforeEnd(Vector6f v2, int id2) {
			v= v2;
			id=id2;
			File f  =new File(pathModel+"cache-"+v.toSampleString()+".dat");
			CompoundTag tag = BlockPlanNBT.ReadCompoundTag(f);
			l = (ListTag) tag.get("blocks");
			coef = l.size() / v2.v;
			
		}
	}
}
