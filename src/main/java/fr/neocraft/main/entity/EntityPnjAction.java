package fr.neocraft.main.entity;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import fr.neocraft.main.Server.Quest.DataManager;
import fr.neocraft.main.Server.Zone.Zone;
import fr.neocraft.main.Server.Zone.ZoneManager;
import fr.neocraft.main.util.Vector6f;
import fr.neocraft.pnj.PnjData;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
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
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIWanderInZone(this, 1.0D));
		this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
		this.tasks.addTask(3, new EntityAILookIdle(this));
		if(data.AttackAnimals | data.AttackMob | data.AttackPlayer)
		{
			this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
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

		}  
		if(data.AttackOnCollid)
		{
			this.targetTasks.addTask(3, new EntityAIAttackOnCollide(this, 1.0D, true));
		}
	}
	
	public EntityPnjAction(World p_i1745_1_) {
		super(p_i1745_1_);
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

	
	protected class EntityAIWanderInZone extends EntityAIBase {
		private EntityPnjAction entity;
		private double xPosition;
		private double yPosition;
		private double zPosition;
		private double speed;
		public EntityAIWanderInZone(EntityPnjAction p_i1648_1_, double p_i1648_2_)
		{
			this.entity = p_i1648_1_;
			this.speed = p_i1648_2_;
			this.setMutexBits(1);
		}
		public boolean shouldExecute()
		{
			if (this.entity.getRNG().nextInt(30) != 0 )
			{
				return false;
			}
			else if(this.entity.canWander)
			{ 
				Vec3 vec3 = RandomPositionGenerator.findRandomTarget(this.entity, 5, 7);
				if (vec3 == null)
				{
					return false;
				}
				else
				{
					Zone z = ZoneManager.getZoneAtPos(vec3.xCoord, vec3.zCoord);
					if(z != null && this.entity.base.id == z.id) {
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
	
	protected class EntityAINearestAttackableTarget extends EntityAITarget
	{
	    private final Class targetClass;
	    private final int targetChance;
	    /** Instance of EntityAINearestAttackableTargetSorter. */
	    private final EntityAINearestAttackableTarget.Sorter theNearestAttackableTargetSorter;
	    private EntityLivingBase ow;
	    /**
	     * This filter is applied to the Entity search.  Only matching entities will be targetted.  (null -> no
	     * restrictions)
	     */
	    private final IEntitySelector targetEntitySelector;
	    private EntityLivingBase targetEntity;

	    public EntityAINearestAttackableTarget(EntityCreature p_i1663_1_, Class p_i1663_2_, int p_i1663_3_, boolean p_i1663_4_)
	    {
	        this(p_i1663_1_, p_i1663_2_, p_i1663_3_, p_i1663_4_, false);
	    }

	    public EntityAINearestAttackableTarget(EntityCreature p_i1664_1_, Class p_i1664_2_, int p_i1664_3_, boolean p_i1664_4_, boolean p_i1664_5_)
	    {
	        this(p_i1664_1_, p_i1664_2_, p_i1664_3_, p_i1664_4_, p_i1664_5_, (IEntitySelector)null);
	    }

	    public EntityAINearestAttackableTarget(EntityCreature p_i1665_1_, Class p_i1665_2_, int p_i1665_3_, boolean p_i1665_4_, boolean p_i1665_5_, final IEntitySelector p_i1665_6_)
	    {
	        super(p_i1665_1_, p_i1665_4_, p_i1665_5_);
	        this.targetClass = p_i1665_2_;
	        this.targetChance = p_i1665_3_;
	        this.ow = p_i1665_1_;
	        this.theNearestAttackableTargetSorter = new EntityAINearestAttackableTarget.Sorter(p_i1665_1_);
	        this.setMutexBits(1);
	        this.targetEntitySelector = new IEntitySelector()
	        {
	            private static final String __OBFID = "CL_00001621";
	            /**
	             * Return whether the specified entity is applicable to this filter.
	             */
	            public boolean isEntityApplicable(Entity p_82704_1_)
	            {
	                return !(p_82704_1_ instanceof EntityLivingBase) ? false : (p_i1665_6_ != null && !p_i1665_6_.isEntityApplicable(p_82704_1_) ? false : EntityAINearestAttackableTarget.this.isSuitableTarget((EntityLivingBase)p_82704_1_, false));
	            }
	        };
	    }

	    /**
	     * Returns whether the EntityAIBase should begin execution.
	     */
	    public boolean shouldExecute()
	    {
	        if (this.targetChance > 0 && this.taskOwner.getRNG().nextInt(this.targetChance) != 0)
	        {
	            return false;
	        }
	        else
	        {
	            double d0 = 24;
	            List<EntityLivingBase> list = this.taskOwner.worldObj.selectEntitiesWithinAABB(this.targetClass, this.taskOwner.boundingBox.expand(d0, 4.0D, d0), this.targetEntitySelector);
	            Collections.sort(list, this.theNearestAttackableTargetSorter);

	            if (list.isEmpty())
	            {
	                return false;
	            }
	            else
	            {
	            	
	            	for(EntityLivingBase lv:list)
	            	{
	            		double entyaw = this.ow.rotationYaw + 180;
	            		Vector6f v = new Vector6f((int)this.ow.posX, 0, (int)this.ow.posZ, 24, 0 ,24);
	            				
	            		
	            	}
	                this.targetEntity = (EntityLivingBase)list.get(0);
	                return true;
	            }
	        }
	    }

	    /**
	     * Execute a one shot task or start executing a continuous task
	     */
	    public void startExecuting()
	    {
	        this.taskOwner.setAttackTarget(this.targetEntity);
	        super.startExecuting();
	    }

	    protected class Sorter implements Comparator
	        {
	            private final Entity theEntity;
	            private static final String __OBFID = "CL_00001622";

	            public Sorter(Entity p_i1662_1_)
	            {
	                this.theEntity = p_i1662_1_;
	            }

	            public int compare(Entity p_compare_1_, Entity p_compare_2_)
	            {
	                double d0 = this.theEntity.getDistanceSqToEntity(p_compare_1_);
	                double d1 = this.theEntity.getDistanceSqToEntity(p_compare_2_);
	                return d0 < d1 ? -1 : (d0 > d1 ? 1 : 0);
	            }

	            public int compare(Object p_compare_1_, Object p_compare_2_)
	            {
	                return this.compare((Entity)p_compare_1_, (Entity)p_compare_2_);
	            }
	        }
	}
	
	@Override
	public boolean interact(EntityPlayer p_70085_1_)
	{
		return false;
	}
	
}
