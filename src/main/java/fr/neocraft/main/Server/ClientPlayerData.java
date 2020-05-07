package fr.neocraft.main.Server;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.opengl.GL11;

import fr.neocraft.main.Server.Quest.QuestClientGuiInfo;
import fr.neocraft.main.util.CRASH;
import fr.neocraft.main.util.Vector3d;
import fr.neocraft.main.util.Vector3f;
import fr.neocraft.main.util.Vector6d;
import fr.neocraft.main.util.Vector7d;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
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
	public boolean debug = false;
	
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
		exp++;
		if(exp > getExpNextLevel())
		{
			exp = 0;
			level++;
		}
		return (320 * exp) / getExpNextLevel();
	}
	
	public static class Cam  {
		
		private static HashMap<Integer, Vector7d[]> data;
		 
		public static Vector7d[] active;
		private static int index = 0;
		private static double indexFrame=0;
		private static double  lastX = 0,lastY = 0,lastZ = 0,lastRotX = 0,lastRotY = 0;//lastRotZ = 0;
	
		public static List<Vector7d> toReg = new ArrayList<Vector7d>();
		public static Vector6d old;
		public static Vector3d oldPos;
		
		public static void register() throws IOException
		{
			File f = new File("assets/cine.dat");
			if(f.exists())
			{
				data = (HashMap<Integer, Vector7d[]>) ((SerializableTag) NBTUtil.readTag(f).clone()).getValue();
			}else {
				data = new  HashMap<Integer, Vector7d[]>();
				save();
			}
		}
		
		public static boolean save() {
			File f = new File("assets/cine.dat");
			try {
				NBTUtil.writeTag(new SerializableTag(data), f);
				return true;
			} catch (IOException e) {
				CRASH.Push(e);
				return false;
			}
		}
		
		public static boolean registerNew(int index) {
			data.put(index, (Vector7d[]) toReg.toArray(new Vector7d[] { }));
			return save();
		}
		public static int getFreeIndex() {
			Iterator it = data.keySet().iterator();
			int max = 0;
			while(it.hasNext()) {
			   int i = (Integer) it.next();
			   if(i > max) {
				   max = i;
			   }
			}
			return max;
		}
		
		public static int oldView = 0;
		public static boolean playCinematic(int id) {
			if(data.containsKey(id))
			{
				oldView = Minecraft.getMinecraft().gameSettings.thirdPersonView;
				Minecraft.getMinecraft().gameSettings.thirdPersonView = 1;
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
			else if(index == active.length) {
				index = 0; 
				active = null;
				lastX = 0;lastY = 0;lastZ = 0;lastRotX = 0;lastRotY = 0;//lastRotZ = 0;
				Minecraft.getMinecraft().gameSettings.thirdPersonView = oldView;
				return false;
				
			}
			else if(index == 0) {
				Vector7d v = active[index];
				lastX = v.x;lastY = v.y;lastZ = v.z;lastRotX = v.u;lastRotY = v.v;
				System.out.println(v.toString());
				EntityPlayer en = Minecraft.getMinecraft().thePlayer;
				oldPos = new Vector3d(en.posX, en.posY, en.posZ);
				//GL11.glRotated(90F, 0, 1, 0);
				GL11.glRotated(lastRotX, 1.0F, 0.0F, 0.0F);
				GL11.glRotated(lastRotY + 180F, 0.0F, 1.0F, 0.0F);
				GL11.glTranslated(lastX, lastY, lastZ);
				
				//GL11.glRotated(lastRotX, 1.0F, 0.0F, 0.0F);
				//GL11.glRotated(lastRotY + 180F, 0.0F, 1.0F, 0.0F);
				index++;
				indexFrame = active[index].custom;
				
				return true;
			}else {
				nextFrame(delta);
				return true;
			}
		}
		
		
		public static void nextFrame(float delta) {
			System.out.println( delta);
			Vector7d v = active[index];
			System.out.println("-:"+indexFrame);
			if(indexFrame == v.custom && indexFrame == 0)
			{
				index++;

				if(index == active.length)
				{
					index = 0; 
					indexFrame=0;
					active = null;
					Minecraft.getMinecraft().gameSettings.thirdPersonView = oldView;
					return;
				}
				lastX -= v.x;
				lastY -= v.y;
				lastZ -= v.z;
				lastRotX -= v.u;
				lastRotY -= v.v;
				indexFrame = active[index].custom;
				nextFrame(delta);
			}
			else if(indexFrame<= 0.5)
			{
				index++;
				//lastX = 0;lastY = 0;lastZ = 0;lastRotX = 0;lastRotY = 0;//lastRotZ = 0;
				if(index == active.length)
				{
					index = 0; 
					indexFrame=0;
					active = null;
					Minecraft.getMinecraft().gameSettings.thirdPersonView = oldView;
					return;
				}
				indexFrame = active[index].custom;
				nextFrame(delta);
			}else{
				System.out.println(v.toString());
				lastX -= (v.x/v.custom)*(double) delta;
				lastY -= (v.y/v.custom)*(double) delta;
				lastZ -= (v.z/v.custom)*(double) delta;
				lastRotX -= (v.u/v.custom)*(double) delta;
				lastRotY -= (v.v/v.custom)*(double) delta;
				
				//lastRotZ += v.z/v.custom * delta;
				EntityPlayer en = Minecraft.getMinecraft().thePlayer;
				GL11.glRotated(lastRotX, 1.0F, 0.0F, 0.0F);
				GL11.glRotated(lastRotY + 180F, 0.0F, 1.0F, 0.0F);
				GL11.glTranslated(lastX - ((en.prevPosX + (en.posX-en.prevPosX) *(double) delta) - oldPos.x), lastY - ((en.prevPosY + (en.posY-en.prevPosY) *(double) delta) - oldPos.y), lastZ - ((en.prevPosZ + (en.posZ-en.prevPosZ) *(double) delta) - oldPos.z));
				
				//GL11.glRotated(lastRotX, 1.0F, 0.0F, 0.0F);
				//GL11.glRotated(lastRotY + 180F, 0.0F, 1.0F, 0.0F);
				//GL11.glRotatef(lastRotZ, 1.0F, 0.0F, 0.0F);
			}
			indexFrame -= 1.0D*(double)delta;
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
