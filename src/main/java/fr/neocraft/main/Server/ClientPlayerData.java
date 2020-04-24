package fr.neocraft.main.Server;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.opengl.GL11;

import fr.neocraft.main.Server.Quest.QuestClientGuiInfo;

import fr.neocraft.main.util.Vector7f;
import fr.neocraft.main.util.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;

import net.querz.nbt.NBTUtil;
import net.querz.nbt.custom.SerializableTag;

public class ClientPlayerData implements Serializable{

	private static final long serialVersionUID = 32463413463453445L;

	public String grade;
	
	public String classe;
	public String race;

	public int power = 0;
	
	public int Money = 0;
	
	public int level;
	public int exp;
	
	public ArrayList<Vector3f> posMap = new  ArrayList<Vector3f>();
	public QuestClientGuiInfo[] clientQuest ;
	

	
	public double[] reputation = new double[2];

	
	public ClientPlayerData CloneForOther() {
		ClientPlayerData data = new ClientPlayerData();
		data.grade = grade;
		data.classe = classe;
		data.race = race;
		data.level = level;
		data.power = power;
		return data;
	}
	
	public int getExpNextLevel() {
		return level * level * level * 10 +1000;
	}
	
	public double calculPercWithExp() {
		
		return (320 * exp) / getExpNextLevel();
	}
	
	public static class Cam  {
		
		private static HashMap<Integer, Vector7f[]> data;
		 
		public static Vector7f[] active;
		private static int index = 0;
		private static float indexFrame=0;
		private static float lastX = 0,lastY = 0,lastZ = 0,lastRotX = 0,lastRotY = 0,lastRotZ = 0;
	
		
		public static void register() throws IOException
		{
			File f = new File("assets/cine.dat");
			if(f.exists())
			{
				data = (HashMap<Integer, Vector7f[]>) ((SerializableTag) NBTUtil.readTag(f).clone()).getValue();
			}
		}
		
		public static boolean playCinematic(int id) {
			if(data.containsKey(id))
			{
				active = data.get(id);
				return true;
			}
			return false;
		}
		

		public static boolean Cinematic(float delta) {
			if(active == null)
			{
				return false;
			}
			else if(index == active.length-1) {
				index = 0; 
				active = null;
				lastX = 0;lastY = 0;lastZ = 0;lastRotX = 0;lastRotY = 0;lastRotZ = 0;
				return false;
				
			}
			nextFrame(delta);
			return true;
		}
		
		
		public static void nextFrame(float delta) {
			Vector7f v = active[index];
			indexFrame *= delta;
			indexFrame--;
			if(indexFrame<1)
			{
				index++;
				lastX = 0;lastY = 0;lastZ = 0;lastRotX = 0;lastRotY = 0;lastRotZ = 0;
				if(index == active.length-1)
				{
					index = 0; 
					indexFrame=0;
					active = null;
					
					return;
				}
				indexFrame = active[index].custom;
				nextFrame(delta);
			}else{
				lastX += v.x/v.custom * delta;
				lastY += v.y/v.custom * delta;
				lastZ += v.z/v.custom * delta;
				lastRotX += v.u/v.custom * delta;
				lastRotY += v.v/v.custom * delta;
				lastRotZ += v.z/v.custom * delta;
				GL11.glTranslatef(lastX, lastY, lastZ);
				GL11.glRotatef(lastRotX, 0.0F, 0.0F, 1.0F);
				GL11.glRotatef(lastRotY, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(lastRotZ, 1.0F, 0.0F, 0.0F);
			}
		}	
		
	}
	 
	 public static int floor_double(double p_76128_0_)
	    {
	        int i = (int)p_76128_0_;
	        return p_76128_0_ < (double)i ? i - 1 : i;
	    }
	 
	 public static int floor_float(float p_76128_0_)
	    {
	        int i = (int)p_76128_0_;
	        return p_76128_0_ < (float)i ? i - 1 : i;
	    }
}
