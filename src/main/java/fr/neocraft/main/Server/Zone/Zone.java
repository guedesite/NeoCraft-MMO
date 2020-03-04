package fr.neocraft.main.Server.Zone;

import net.minecraft.entity.Entity;

public class Zone extends ZoneData {

	public final int id;
	
	public Zone(int idUnique, String Name, String secondName, int XPos, int ZPos, int lvl, int pvp) {
		
		this.id = idUnique;
		
		this.setName(Name);
		this.setSecName(secondName);
		this.setXPos(XPos);
		this.setZPos(ZPos);
		this.setLvl(lvl);
		this.setPvp(pvp);
	}
	
	public double getDistanceSqToEntity(Entity p_70068_1_)
    {
        double d0 = getXPos()- p_70068_1_.posX;
        double d2 = getZPos() - p_70068_1_.posZ;
        return d0 * d0 +  d2 * d2;
    }
	
}
