package fr.neocraft.main.Server.Zone;

public class ZoneData {

	private int Xpos;
	private int ZPos;
	private int lvl;
	
	public int DonjonSpot;
	public int donjonType = 0;
	
	private String Name;
	private String SecName;
	
	private boolean pvp;
	
	public boolean pvpEnable() {
		return pvp;
	}
	
	public void setPvp(int bin) {
		pvp = bin == 1;
	}
	
	public int getXPos() {
		return Xpos;
	}
	public void setXPos(int xpos) {
		Xpos = xpos;
	}
	public int getZPos() {
		return ZPos;
	}
	public void setZPos(int zPos) {
		ZPos = zPos;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getSecName() {
		return SecName;
	}
	public void setSecName(String secName) {
		SecName = secName;
	}
	public int getLvl() {
		return lvl;
	}
	public void setLvl(int lvl) {
		this.lvl = lvl;
	}
}
