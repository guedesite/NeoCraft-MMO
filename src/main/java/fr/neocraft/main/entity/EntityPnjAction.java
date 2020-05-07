package fr.neocraft.main.entity;

import java.util.HashMap;

import fr.neocraft.main.Init.ItemMod;
import fr.neocraft.main.Server.Quest.DataManager;
import fr.neocraft.pnj.PnjData;
import fr.neocraft.pnj.Action.PnjAction;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityPnjAction extends EntityMobZone {


	public int indexPnj = -1;

	public EntityPnjAction(World p_i1745_1_, double baseX, double baseY, double baseZ, int index) {
		super(p_i1745_1_);
		this.isMob = false;
		this.indexPnj = index;
		PnjData data = DataManager.getPnjById(index);
		this.canWander = data.MoveRadius != 0;
		this.baseX=baseX;
		this.baseY = baseY;
		this.baseZ=baseZ;
		this.setHomeArea((int)this.baseX,(int) this.baseY, (int)this.baseZ, 20);
	//	this.tasks.addTask(0, new EntityAISwimming(this));
	//	this.tasks.addTask(2, new EntityAIWanderInZone(this, 1.0D));
	//	this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
	//	this.tasks.addTask(3, new EntityAILookIdle(this));
		if(data.AttackAnimals | data.AttackMob | data.AttackPlayer)
		{
			/*this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
			if(data.AttackPlayer)
			{
				this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
			} if(data.AttackAnimals)
			{
				this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityAnimal.class, 0, true));
			} if(data.AttackMob)
			{
				this.targetTasks.addTask(4, new EntityAINearestAttackableTarget(this, EntityMob.class, 0, true));
			}
			 	*/
		}  
		if(data.AttackOnCollid)
		{
			//this.targetTasks.addTask(3, new EntityAIAttackOnCollide(this, 1.0D, true));
		}
	}
	
	public EntityPnjAction(World p_i1745_1_) {
		super(p_i1745_1_);
	}
	
	@Override
    public void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(14, Integer.valueOf(0));
        this.dataWatcher.addObject(15, Integer.valueOf(0));
        
    }
	public int getModelIndex() {
		return this.dataWatcher.getWatchableObjectInt(14);
	}
	
	public int getSkinIndex() {
		return this.dataWatcher.getWatchableObjectInt(15);
	}
	
	public void setModelIndex(int i) {
		this.dataWatcher.updateObject(14, i);
	}
	
	public void setSkinIndex(int i){
		this.dataWatcher.updateObject(15, i);
	}
	
		@Override
	    public void writeEntityToNBT(NBTTagCompound c)
	    {
	        super.writeEntityToNBT(c);
	        c.setInteger("indexPnj", indexPnj);
	   
	    }

	    /**
	     * (abstract) Protected helper method to read subclass entity data from NBT.
	     */
	    @Override
	    public void readEntityFromNBT(NBTTagCompound c)
	    {
	    	super.readEntityFromNBT(c);
	    	indexPnj = c.getInteger("indexPnj");
	    }


	
	@Override
	public boolean interact(EntityPlayer p)
	{
		if(p.getHeldItem() != null && !p.worldObj.isRemote)
		{
			if(p.getHeldItem().getItem() == ItemMod.ItemChangeModelPnjAction)
			{
				int i = this.getModelIndex();
				
				if(!p.isSneaking())
				{
					i++;
					if(i >=  allClass.size())
					{
						i = 0;
					}
				} else {
					i--;
					if(0>i)
					{
						i = allClass.size();
					}
				}
				this.setModelIndex(i);
				return true;
			} else if(p.getHeldItem().getItem() == ItemMod.ItemChangeSkinPnjAction)
			{
				int i = this.getSkinIndex();
				int model = this.getModelIndex();
				if(!p.isSneaking())
				{
					i++;
					if(i >= allClass.get(model).res.length)
					{
						i = 0;
					}
				} else {
					i--;
					if(0>i)
					{
						i = allClass.get(model).res.length;
					}
				}
				this.setSkinIndex(i);
				return true;
			}
			
		}if(!p.worldObj.isRemote && indexPnj != -1){
			PnjData data = DataManager.getPnjById(indexPnj);
			for(PnjAction ac:data.Actions)
			{
				if(ac.canAction(p))
				{
					ac.MakeAction(p);
					return true;
				}
			}
		}
		return super.interact(p);
	}
	
	public static HashMap<Integer, ClassSkin> allClass = new HashMap<Integer,ClassSkin>();
	

	
	public static void registerModel()
	{
		
	}


	public static class ClassSkin {
		public String model;
		public ResourceLocation[] res;
		public ClassSkin(String model, ResourceLocation[] res)
		{
			this.model = model;
			this.res = res;
		}
		
		
	}
	

	
}
