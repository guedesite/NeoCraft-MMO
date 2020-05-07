package fr.neocraft.main.entity;


import fr.neocraft.main.Server.Zone.Zone;
import fr.neocraft.main.Server.Zone.ZoneManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityMobZone extends EntityNeoCreature {
	
	public Zone base;
	public boolean isMob = false;
	public boolean canWander = true;
	public double baseX, baseY,baseZ;
	public int boss = 1;
	private EntityLivingBase attackTarget;
	private int AttackTimer, SpecialAttackTimer;
	
	public EntityMobZone(World p_i1745_1_,boolean canWander, boolean isMob, int boss, Zone z, double baseX, double baseY, double baseZ)
	{
		super(p_i1745_1_);
		this.base = z;
		this.isMob = isMob;
		this.boss = boss;
		this.canWander = canWander;
		this.baseX=baseX;
		this.baseY = baseY;
		this.baseZ=baseZ;
		this.setHomeArea(this.baseX, this.baseY,this.baseZ, 20);
	
		
	}
	
	
	public EntityMobZone(World p_i1745_1_)
	{
		super(p_i1745_1_);
		this.baseX=0;
		this.baseY = -1;
		this.baseZ=0;
	
	}
	
	
	

	
	
	@Override
	public void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0D);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
    }
	
	@Override
    public void entityInit()
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
	      
	        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(  this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getBaseValue()*lvl*2*this.boss);
	        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(  this.getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue()*lvl*2*this.boss);
        }
        
       

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
	        c.setInteger("boss", this.boss);
        }
        c.setBoolean("canWander", this.canWander);
        c.setDouble("baseX", this.baseX);
        c.setDouble("baseY", this.baseY);
        c.setDouble("baseZ", this.baseZ);
    }

    /**
     * (abstract) public helper method to read subclass entity data from NBT.
     */
    @Override
    public void readEntityFromNBT(NBTTagCompound c)
    {
        super.readEntityFromNBT(c);
        if(c.hasKey("ZoneX"))
        {
	        if( this.base == null) 
	        {
	        	this.base = ZoneManager.AllZone.get(c.getInteger("ZoneX"), c.getInteger("ZoneZ"));
	        }
	        this.isMob = c.getBoolean("isMob");
	        this.boss = c.getInteger("boss");
        }
        this.baseX = c.getDouble("baseX");
        this.baseY = c.getDouble("baseY");
        this.baseZ = c.getDouble("baseZ");
        this.canWander = c.getBoolean("canWander");
        if(this.baseY != -1)
        {
        	this.setHomeArea(this.baseX,this.baseY, this.baseZ, 20);
        }
    }
    
    @Override
	public boolean canDespawn()
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
    
	
	@Override
	public int getMaxSpawnedInChunk()
	{
		return 99;
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
    public boolean getCanSpawnHere()
    {
    	return true;
    }
   
	
    @Override
    public boolean canPickUpLoot()
    {
        return false;
    }


}
