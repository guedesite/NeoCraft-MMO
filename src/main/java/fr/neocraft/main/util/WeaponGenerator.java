package fr.neocraft.main.util;


import java.util.ArrayList;
import java.util.List;

import fr.neocraft.main.Init.ItemMod;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

public class WeaponGenerator {

	
	private static ProbaManager proba = new ProbaManager();
	

	
	public static ItemStack generateBow(int lvl) {
		NBTTagCompound tag = new NBTTagCompound();
		NBTTagCompound display = new NBTTagCompound();
		ItemStack stack = new ItemStack(ItemMod.MMOBow, 1);
		int dmg, base, max;
		tag.setInteger("lvl", lvl);
		if(lvl == 1)
		{
			base = 3;
			max = 18;
		}else if(lvl == 10)
		{
			base = 66355;
			max = 700000;
		}
		else {
			base = Math.round((float)(f(lvl -1) * 16.2));
			max = Math.round((float)(f(lvl) * 18));
		}
		dmg = proba.nextInt(max - base) + base;
		tag.setInteger("dmg", dmg);
		
		proba.start(100);
		if(proba.Do(15))
		{
			tag.setInteger("dmgtype", DmgType.Ice.id);
		}else if(proba.Do(15))
		{
			tag.setInteger("dmgtype", DmgType.Poison.id);
		}else if(proba.Do(10))
		{
			tag.setInteger("dmgtype", DmgType.Feu.id);
		} else {
			tag.setInteger("dmgtype", DmgType.Physique.id);
		}
		
		proba.start(10);
		if(proba.Do(4))
		{
			tag.setInteger("projectiletype", ProjectileType.Rafale.id);
			tag.setInteger("amount", proba.nextInt(3)+1);
		}else
		{
			tag.setInteger("projectiletype", ProjectileType.Horizontal.id);
			tag.setInteger("amount", proba.nextInt(3)+2);
		}
		
		generateIcon(ItemMod.MMOBow, display);
		tag.setTag("display", display);
		stack.stackTagCompound=tag;
		return stack;
	}
	
	public static ItemStack generateStick(int lvl) {
		NBTTagCompound tag = new NBTTagCompound();
		NBTTagCompound display = new NBTTagCompound();
		ItemStack stack = new ItemStack(ItemMod.MMOStick, 1);
		int dmg, base, max;
		tag.setInteger("lvl", lvl);
		if(lvl == 1)
		{
			base = 4;
			max = 22;
		}else if(lvl == 10)
		{
			base = 81100;
			max = 825000;
		}
		else {
			base = Math.round((float)(f(lvl -1) * 19.8));
			max = Math.round((float)(f(lvl) * 22));
		}
		dmg = proba.nextInt(max - base) + base;
		tag.setInteger("dmg", dmg);
		
		proba.start(100);
		if(proba.Do(30))
		{
			tag.setInteger("dmgtype", DmgType.Ice.id);
		}else if(proba.Do(10))
		{
			tag.setInteger("dmgtype", DmgType.Poison.id);
		}else if(proba.Do(30))
		{
			tag.setInteger("dmgtype", DmgType.Feu.id);
		} else {
			tag.setInteger("dmgtype", DmgType.Physique.id);
		}
		
		proba.start(10);
		if(proba.Do(6))
		{
			tag.setInteger("projectiletype", ProjectileType.Rafale.id);
			tag.setInteger("amount", proba.nextInt(3)+1);
		}else
		{
			tag.setInteger("projectiletype", ProjectileType.Horizontal.id);
			tag.setInteger("amount", proba.nextInt(3)+2);
		}
		
		generateIcon(ItemMod.MMOStick, display);
		tag.setTag("display", display);
		stack.stackTagCompound=tag;
		return stack;
	}
	
	public static ItemStack generateSword(int lvl) {
		NBTTagCompound tag = new NBTTagCompound();
		NBTTagCompound display = new NBTTagCompound();
		ItemStack stack = new ItemStack(ItemMod.MMOSword, 1);
		int dmg, base, max;
		tag.setInteger("lvl", lvl);
		if(lvl == 1)
		{
			base = 5;
			max = 30;
		}else if(lvl == 10)
		{
			base = 81100;
			max = 1000000;
		}
		else {
			base = Math.round((float)(f(lvl -1) * 27));
			max = Math.round((float)(f(lvl) * 30));
		}
		dmg = proba.nextInt(max - base) + base;
		tag.setInteger("dmg", dmg);
		
		proba.start(100);
		if(proba.Do(10))
		{
			tag.setInteger("dmgtype", DmgType.Ice.id);
		}else if(proba.Do(50))
		{
			tag.setInteger("dmgtype", DmgType.Poison.id);
		}else if(proba.Do(25))
		{
			tag.setInteger("dmgtype", DmgType.Feu.id);
		} else {
			tag.setInteger("dmgtype", DmgType.Physique.id);
		}
		
	
		generateIcon(ItemMod.MMOSword, display);
		tag.setTag("display", display);
		stack.stackTagCompound=tag;
		return stack;
	}
	
	
	private static void generateIcon(Item i, NBTTagCompound tag)
	{
		tag.setInteger("iconIndex", 0);
		tag.setString("Name", "icon name");
		tag.setString("author", "by author");

	}
	
	
	private static List<String> generateOther(NBTTagCompound tag) {
		List<String> l = new ArrayList<String>();
		proba.start(100);
		if(proba.Do(10))
		{
			tag.setInteger("mf", proba.nextInt(75));
		}
		return l;
	}
	
	private static float f(int nb)
	{
		return nb^nb / nb ^ nb/2;
	}
	
	static enum DmgType {
		Physique(0),
		Ice(1),
		Poison(2),
		Feu(3);
		public int id;
		DmgType(int i)
		{
			id = i;
		}
	}
	static enum ProjectileType {
		Rafale(0),
		Horizontal(1);
		
		public int id;
		ProjectileType(int i)
		{
			id = i;
		}
	}
	

}
