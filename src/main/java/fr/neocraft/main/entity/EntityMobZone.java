package fr.neocraft.main.entity;


import fr.neocraft.main.Server.Zone.Zone;
import fr.neocraft.main.Server.Zone.ZoneManager;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
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
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityMobZone extends EntityCreature {
	
	public Zone base;
	public boolean isMob;
	public boolean canWander = false;
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
		this.setHomeArea((int)this.baseX,(int) this.baseY, (int)this.baseZ, 20);
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIWanderInZone(this, 1.0D));
		this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
		this.setHomeArea((int)this.baseX,(int) this.baseY, (int)this.baseZ, 20);
		this.tasks.addTask(3, new EntityAILookIdle(this));
		if(isMob)
		{
			this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		    this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
		}  
		
	}
	
	public EntityMobZone(World p_i1745_1_)
	{
		super(p_i1745_1_);
		this.baseX=0;
		this.baseY = -1;
		this.baseZ=0;
	}
	
	@Override
	protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.26000000417232513D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0D);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
    }
	@Override
    protected void entityInit()
    {
        super.entityInit();
        if(this.base != null)
        {
	        int lvl = base.getLvl() + this.worldObj.rand.nextInt(6);
	        if(base.getDistanceSqToEntity(this) > 64) {
	        	lvl =+ 5;
	        }
	        if(this.isMob)
	        {
	        	this.getDataWatcher().addObject(12, Integer.valueOf(lvl));
	        }
	        this.getDataWatcher().addObject(13, Boolean.valueOf(this.isMob));
	        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(  this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getBaseValue()*lvl*5);
	        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(  this.getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue()*lvl*5);
	       
        }
        
       

    }
	
    @Override
    protected boolean isAIEnabled()
    {
        return true;
    }
    @Override
    public boolean isChild()
    {
    	return false;
    }
    @Override
    protected int getExperiencePoints(EntityPlayer p_70693_1_)
    {
        return this.getDataWatcher().getWatchableObjectInt(12) * (this.worldObj.rand.nextInt(10)+1);
    }
    
    @Override
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.UNDEAD;
    }
    
    @Override
    public void writeEntityToNBT(NBTTagCompound c)
    {
        super.writeEntityToNBT(c);

        if(this.base != null)
        {
	        c.setInteger("lvl", this.getDataWatcher().getWatchableObjectInt(12));
	        c.setInteger("ZoneX", this.base.getXPos());
	        c.setInteger("ZoneZ", this.base.getZPos());
	        c.setBoolean("isMob", this.isMob);
        }
        c.setBoolean("canWander", this.canWander);
        c.setDouble("baseX", this.baseX);
        c.setDouble("baseY", this.baseY);
        c.setDouble("baseZ", this.baseZ);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readEntityFromNBT(NBTTagCompound c)
    {
        super.readEntityFromNBT(c);
        if(c.hasKey("ZoneX"))
        {
	        this.getDataWatcher().updateObject(12, Integer.valueOf(c.getInteger("lvl")));
	        if( this.base == null) 
	        {
	        	this.base = ZoneManager.AllZone.get(c.getInteger("ZoneX"), c.getInteger("ZoneZ"));
	        }
	        this.isMob = c.getBoolean("isMob");
	        this.canWander = c.getBoolean("canWander");
        }
        this.baseX = c.getDouble("baseX");
        this.baseY = c.getDouble("baseY");
        this.baseZ = c.getDouble("baseZ");
        this.setHomeArea((int)this.baseX,(int) this.baseY, (int)this.baseZ, 20);
    }
    
    @Override
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
			if (this.entity.base == null | this.entity.baseY == -1 | this.entity.getRNG().nextInt(30) != 0 )
			{
				return false;
			}
			else if(this.entity.canWander)
			{
				Vec3 vec3 = RandomPositionGenerator.findRandomTarget(this.entity, 30, 7);
				if (vec3 == null)
				{
					return false;
				}
				else
				{
					Zone z = ZoneManager.getZoneAtPos(vec3.xCoord, vec3.zCoord);
					if(z != null && this.entity.base.id == z.id ) {
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
				if(this.entity.posX-this.entity.baseX != 0 | 0 !=  this.entity.posZ-this.entity.baseZ  | 0 !=  this.entity.posY-this.entity.baseY)
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

	@Override
	public int getMaxSpawnedInChunk()
	{
		return 99;
	}
	@Override
	 public int getVerticalFaceSpeed()
	{
	        return 20;
	}

	
	@Override
	public void dropEquipment(boolean p_82160_1_, int p_82160_2_)
    {
		
    }
	
	@Override
	public Item getDropItem()
    {
        return Item.getItemById(0);
    }


	@Override
    public void dropFewItems(boolean p_70628_1_, int p_70628_2_)
    {
        Item item = this.getDropItem();

        if (item != null)
        {
            int j = this.rand.nextInt(3);

            if (p_70628_2_ > 0)
            {
                j += this.rand.nextInt(p_70628_2_ + 1);
            }

            for (int k = 0; k < j; ++k)
            {
                this.dropItem(item, 1);
            }
        }
    }
	
	 @Override
	public boolean attackEntityFrom(DamageSource p_70097_1_, float p_70097_2_)
    {
        if (this.isEntityInvulnerable())
        {
            return false;
        }
        else if (super.attackEntityFrom(p_70097_1_, p_70097_2_))
        {
            Entity entity = p_70097_1_.getEntity();

            if (this.riddenByEntity != entity && this.ridingEntity != entity)
            {
                if (entity != this)
                {
                    this.entityToAttack = entity;
                }

                return true;
            }
            else
            {
                return true;
            }
        }
        else
        {
            return false;
        }
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
	 @Override
    public String getHurtSound()
    {
        return "game.hostile.hurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    @Override
    public String getDeathSound()
    {
        return "game.hostile.die";
    }
    @Override
    public String func_146067_o(int p_146067_1_)
    {
        return p_146067_1_ > 4 ? "game.hostile.hurt.fall.big" : "game.hostile.hurt.fall.small";
    }
    @Override
    public boolean attackEntityAsMob(Entity p_70652_1_)
    {
        float f = (float)this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
        int i = 0;

        if (p_70652_1_ instanceof EntityLivingBase)
        {
            f += EnchantmentHelper.getEnchantmentModifierLiving(this, (EntityLivingBase)p_70652_1_);
            i += EnchantmentHelper.getKnockbackModifier(this, (EntityLivingBase)p_70652_1_);
        }

        boolean flag = p_70652_1_.attackEntityFrom(DamageSource.causeMobDamage(this), f);

        if (flag)
        {
            if (i > 0)
            {
                p_70652_1_.addVelocity((double)(-MathHelper.sin(this.rotationYaw * (float)Math.PI / 180.0F) * (float)i * 0.5F), 0.1D, (double)(MathHelper.cos(this.rotationYaw * (float)Math.PI / 180.0F) * (float)i * 0.5F));
                this.motionX *= 0.6D;
                this.motionZ *= 0.6D;
            }

            int j = EnchantmentHelper.getFireAspectModifier(this);

            if (j > 0)
            {
                p_70652_1_.setFire(j * 4);
            }

            if (p_70652_1_ instanceof EntityLivingBase)
            {
                EnchantmentHelper.func_151384_a((EntityLivingBase)p_70652_1_, this);
            }

            EnchantmentHelper.func_151385_b(this, p_70652_1_);
        }

        return flag;
    }

    /**
     * Basic mob attack. Default to touch of death in EntityCreature. Overridden by each mob to define their attack.
     */
    @Override
    public void attackEntity(Entity p_70785_1_, float p_70785_2_)
    {
        if (this.attackTime <= 0 && p_70785_2_ < 2.0F && p_70785_1_.boundingBox.maxY > this.boundingBox.minY && p_70785_1_.boundingBox.minY < this.boundingBox.maxY)
        {
            this.attackTime = 20;
            this.attackEntityAsMob(p_70785_1_);
        }
    }
    @Override
    public boolean getCanSpawnHere()
    {
    	return true;
    }
    @Override
    public boolean hasHome()
    {
        return true;
    }
	
    @Override
    public boolean canPickUpLoot()
    {
        return false;
    }
    
}
