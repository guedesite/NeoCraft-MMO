package fr.neocraft.main.entity;


import fr.neocraft.main.Server.Zone.Zone;
import fr.neocraft.main.Server.Zone.ZoneManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityMobZone extends EntityMob {
	
	public Zone base;
	public boolean isMob;
	public boolean canWander;
	public double baseX, baseY,baseZ;

	
	public EntityMobZone(World p_i1745_1_,boolean canWander, boolean isMob, Zone z, double baseX, double baseY, double baseZ)
	{
		super(p_i1745_1_);
		this.base = z;
		this.isMob = isMob;
		this.canWander = canWander;
		this.baseX=baseX;
		this.baseY = baseY;
		this.baseZ=baseZ;
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIWanderInZone(this, 1.0D));
		this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
		this.tasks.addTask(3, new EntityAILookIdle(this));
		if(isMob)
		{
			this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		    this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
		}  
		
	}
	
	
	protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.26000000417232513D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0D);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
    }

    protected void entityInit()
    {
        super.entityInit();
        int lvl = base.getLvl() + this.worldObj.rand.nextInt(6);
        if(base.getDistanceSqToEntity(this) > 64) {
        	lvl =+ 5;
        }
        
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(  this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getBaseValue()*lvl*2);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(  this.getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue()*lvl*2);
        
        this.getDataWatcher().addObject(12, Integer.valueOf(lvl));
        this.getDataWatcher().addObject(13, Boolean.valueOf(this.isMob));

    }
	
	
    protected boolean isAIEnabled()
    {
        return true;
    }
    
    public boolean isChild()
    {
    	return false;
    }
	
    protected int getExperiencePoints(EntityPlayer p_70693_1_)
    {
        return this.getDataWatcher().getWatchableObjectInt(12) * (this.worldObj.rand.nextInt(10)+1);
    }
    
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.UNDEAD;
    }
    
    
    public void writeEntityToNBT(NBTTagCompound c)
    {
        super.writeEntityToNBT(c);

        c.setInteger("lvl", this.getDataWatcher().getWatchableObjectInt(12));
        c.setInteger("ZoneX", this.base.getXPos());
        c.setInteger("ZoneZ", this.base.getZPos());
        c.setBoolean("isMob", this.isMob);
        c.setBoolean("canWander", this.canWander);
        c.setDouble("baseX", this.baseX);
        c.setDouble("baseY", this.baseY);
        c.setDouble("baseZ", this.baseZ);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound c)
    {
        super.readEntityFromNBT(c);
        this.getDataWatcher().updateObject(12, Integer.valueOf(c.getInteger("lvl")));
        if( this.base == null) 
        {
        	this.base = ZoneManager.AllZone.get(c.getInteger("ZoneX"), c.getInteger("ZoneZ"));
        }
        this.isMob = c.getBoolean("isMob");
        this.canWander = c.getBoolean("canWander");
        this.baseX = c.getDouble("baseX");
        this.baseY = c.getDouble("baseY");
        this.baseZ = c.getDouble("baseZ");
        
    }
    
    
	protected boolean canDespawn()
	{
    	return true;
	}
	
	
	/*
	 * 
	 * 	Must be Override
	 * 
	 *  flag must be init
	 * 
	 
	
	public boolean canSpawn() {
		return base.getDistanceSqToEntity(this) > (this.getDataWatcher().getWatchableObjectInt(12) % 10 > 5 ? 75:0);
	} */
    
	protected class EntityAIWanderInZone extends EntityAIBase {
		private EntityMobZone entity;
		private double xPosition;
		private double yPosition;
		private double zPosition;
		private double speed;
		public EntityAIWanderInZone(EntityMobZone p_i1648_1_, double p_i1648_2_)
		{
			this.entity = p_i1648_1_;
			this.speed = p_i1648_2_;
			this.setMutexBits(1);
		}
		public boolean shouldExecute()
		{
			if (this.entity.getRNG().nextInt(30) != 0)
			{
				return false;
			}
			else if(this.entity.canWander)
			{
				Vec3 vec3 = RandomPositionGenerator.findRandomTarget(this.entity, 10, 7);
				if (vec3 == null)
				{
					return false;
				}
				else
				{
					Zone z = ZoneManager.getZoneAtPos(vec3.xCoord, vec3.zCoord);
					if(z != null && this.entity.base.id == z.id && z.getDistanceSqToEntity(this.entity) > (this.entity.getDataWatcher().getWatchableObjectInt(12) % 10 > 5 ? 75:0)) {
						this.xPosition = vec3.xCoord;
						this.yPosition = vec3.yCoord;
						this.zPosition = vec3.zCoord;
						return true;
					}
					else 
					{
						return false;
					}
				}
			} else {
				boolean flag = false;
				if(this.entity.posX-this.entity.baseX > 2 | -2 > this.entity.posX-this.entity.baseX || this.entity.posZ-this.entity.baseZ > 2 | -2 > this.entity.posZ-this.entity.baseZ || this.entity.posY-this.entity.baseY > 2 | -2 > this.entity.posY-this.entity.baseY)
				{
					this.xPosition = this.entity.baseX;
					this.yPosition = this.entity.baseY;
					this.zPosition = this.entity.baseZ;
					return true;
				}
				
			}
			return false;
		}
		public boolean continueExecuting()
		{
			return !this.entity.getNavigator().noPath();
		}
		public void startExecuting()
		{
			this.entity.getNavigator().tryMoveToXYZ(this.xPosition, this.yPosition, this.zPosition, this.speed);
		}
	}
	
	
}
