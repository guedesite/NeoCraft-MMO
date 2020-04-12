package fr.neocraft.main.Server.Zone;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import fr.neocraft.main.util.CRASH;
import fr.neocraft.main.util.Vector3f;

import static fr.neocraft.main.main.bdd;
import static fr.neocraft.main.main.l;

import java.sql.ResultSet;


import net.minecraft.entity.Entity;

public class ZoneManager {

	public static Table<Integer, Integer, Zone> AllZone  = HashBasedTable.create();;
	
	private static Zone ZoneDefault = new Zone(-1,"l'Abysse Perdu",null,-999,-999,0,0);
	
	private static int ZMinX = 0, ZMinZ = 0;
	
	public static void Register() {
		AllZone = HashBasedTable.create();
		int id = bdd.GetFreeId();
		ResultSet result = bdd.query("SELECT * FROM "+bdd.getStringZone(), id);
		l.info("START REGISTER ZONE");
		if(result != null) {
			try {
				l.info("FOUND "+result.getFetchSize()+" ZONE");
				while(result.next())
				{
					AllZone.put(result.getInt("XPos"), result.getInt("ZPos"), new Zone(
							result.getInt("id"),
							result.getString("Name"),
							result.getString("SecName"),
							result.getInt("XPos"), 
							result.getInt("ZPos"),
							result.getInt("lvl"),
							result.getInt("pvp")
							));
				}
			} catch(Exception e)
			{
				CRASH.Push(e);
			}
		} else {
			l.info("FOUND 0 ZONE");
		}
		l.info("END REGISTER");
		bdd.CloseFreeId(id);
	}
	
	
	
	public static Zone getZoneAtPos(double posX, double posZ) {
		int ModX = (int) (posX % 2000);
		int ModZ= (int) (posZ % 2000);
		int x = ZMinX +(int) ( posX - ModX);
		int z = ZMinZ +(int) (posZ - ModZ);
		if(z % 4000 != 0)
		{
			x += 1000;
		}
		z+= 1000;
		if(ModX >= 1000)
		{
			ModX-=1000;
			x += 2000;
		}
		return AllZone.get(x, z) != null ? AllZone.get(x, z) : ZoneDefault;
	}
	
	public static Vector3f getPosOfZone(double posX, double posZ) {
		int ModX = (int) (posX % 2000);
		int ModZ= (int) (posZ % 2000);
		int x = ZMinX +(int) ( posX - ModX);
		int z = ZMinZ +(int) (posZ - ModZ);
		if(z % 4000 != 0)
		{
			x += 1000;
		}
		z+= 1000;
		if(ModX >= 1000)
		{
			ModX-=1000;
			x += 2000;
		}
		return new Vector3f(x,0,z);
	}
	
	public static Zone getZoneAtEntity(Entity en)
	{
		return getZoneAtPos(en.posX, en.posZ);
	}
	public static Vector3f getPosOfZoneAtEntity(Entity en)
	{
		return getPosOfZone(en.posX, en.posZ);
	}

}
