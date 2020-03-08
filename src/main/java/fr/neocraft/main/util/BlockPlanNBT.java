package fr.neocraft.main.util;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.querz.nbt.CompoundTag;
import net.querz.nbt.ListTag;
import net.querz.nbt.NBTUtil;
import net.querz.nbt.custom.SerializableTag;

public class BlockPlanNBT {

	public static CompoundTag LoadBlock(World w, int x, int y, int z, int widthx, int height, int widthz)
	{
		CompoundTag tag = new CompoundTag();
		tag.put("xyzuvw",new SerializableTag(new Vector6f(0,0,0,widthx,height, widthz)));
		ListTag all = new ListTag(CompoundTag.class);
		for(int y2 = 0; y2 <= height; y2++)
		{
			for(int x2 = 0; x2 <= widthx; x2++)
			{
				for(int z2 = 0; z2 <= widthz; z2++)
				{
					Block b = w.getBlock(x + x2, y+y2, z+z2);
					if(b != Blocks.air)
					{
						CompoundTag blockhere = new CompoundTag();
						
						int meta = w.getBlockMetadata(x + x2, y+y2, z+z2);
						blockhere.putInt("x", x2);
						blockhere.putInt("y", y2);
						blockhere.putInt("z", z2);
						blockhere.putInt("id", Block.getIdFromBlock(b));
						blockhere.putInt("meta", meta);
						if(b.hasTileEntity(meta))
						{
							blockhere.putBoolean("hastile", true);
							try {
								NBTTagCompound nbt = new NBTTagCompound();
								w.getTileEntity(x + x2, y+y2, z+z2).writeToNBT(nbt);
								blockhere.putByteArray("tile",  CompressedStreamTools.compress(nbt));
							} catch (IOException e) {
								CRASH.Push(e);
							}
							
						}else {
							blockhere.putBoolean("hastile", false);
						}
						all.add(blockhere);
					}
				}
			}
		}
		tag.put("blocks", all);
		return tag;
	}
	
	public static CompoundTag LoadBlockAndAire(World w, int x, int y, int z, int widthx, int height, int widthz)
	{
		CompoundTag tag = new CompoundTag();
		tag.put("xyzuvw",new SerializableTag(new Vector6f(0,0,0,widthx,height, widthz)));
		ListTag all = new ListTag(CompoundTag.class);
		for(int y2 = 0; y2 <= height; y2++)
		{
			for(int x2 = 0; x2 <= widthx; x2++)
			{
				for(int z2 = 0; z2 <= widthz; z2++)
				{
					Block b = w.getBlock(x + x2, y+y2, z+z2);

						CompoundTag blockhere = new CompoundTag();
						
						int meta = w.getBlockMetadata(x + x2, y+y2, z+z2);
						blockhere.putInt("x", x2);
						blockhere.putInt("y", y2);
						blockhere.putInt("z", z2);
						blockhere.putInt("id", Block.getIdFromBlock(b));
						blockhere.putInt("meta", meta);
						if(b.hasTileEntity(meta))
						{
							blockhere.putBoolean("hastile", true);
							try {
								NBTTagCompound nbt = new NBTTagCompound();
								w.getTileEntity(x + x2, y+y2, z+z2).writeToNBT(nbt);
								blockhere.putByteArray("tile",  CompressedStreamTools.compress(nbt));
							} catch (IOException e) {
								CRASH.Push(e);
							}
							
						}else {
							blockhere.putBoolean("hastile", false);
						}
						all.add(blockhere);
					}
			}
		}
		tag.put("blocks", all);
		return tag;
	}
	
	public static CompoundTag ReadCompoundTag(File filepath)
	{
		try {
			return (CompoundTag) NBTUtil.readTag(filepath).clone();
		} catch(Exception e) {
			CRASH.Push(e);
			return null;
		}
		
	}
	
	public static void saveCompoundTag(File filepath, CompoundTag c)
	{
		try
		{
			NBTUtil.writeTag(c, filepath);
		}
		catch(Exception e)
		{
			CRASH.Push(e);
		}
	}
	
	public static void writeBlock(World w, int x, int y, int z, CompoundTag c)
	{
		if(c.get("playerpos") != null)
		{
			Vector3f v = ((Vector3f)((SerializableTag)c.get("playerpos")).getValue());
			x +=v.x;
			y +=v.y;
			z +=v.z;
		}
		ListTag all = c.getListTag("blocks");
		Iterator it = all.iterator();
		while(it.hasNext())
		{
			CompoundTag cp = (CompoundTag)it.next();
			w.setBlock(x + cp.getInt("x"), y+ cp.getInt("y"), z+ cp.getInt("z"), Block.getBlockById(cp.getInt("id")), cp.getInt("meta"),2);
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
	
}
