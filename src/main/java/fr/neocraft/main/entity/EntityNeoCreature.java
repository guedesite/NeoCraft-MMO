package fr.neocraft.main.entity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

import fr.neocraft.main.Init.EntityMod;
import fr.neocraft.main.entity.ai.NeoAITask;
import fr.neocraft.main.util.CRASH;
import fr.neocraft.main.util.PosVec3DHelper;
import fr.neocraft.main.util.Vector3d;
import fr.neocraft.main.util.Vector4d;
import fr.neocraft.main.util.Vector6d;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public abstract class EntityNeoCreature extends EntityLiving{

	public ArrayList<NeoAITask> task = new ArrayList<NeoAITask>();
	private int TimerAttack = 0;
	private int TimerMemory = 0;
	private EntityLivingBase AttackMemory;
	private EntityLivingBase attackTarget;
	public PathEntity pathToEntity;
	private Vector3d toMove;
	private Vector4d Home;
	
	
	public Vector6d SeeVector =new Vector6d(0,this.posY - getSeeRange()/2,0,getSeeRange(),getSeeRange()/2,getSeeRange());
	
	public EntityLivingBase getAttackTarget()
    {
        return this.attackTarget;
    }

	public boolean hasAttackTarget() {
		 return this.attackTarget != null;
	}
    public void setAttackTarget(EntityLivingBase p_70624_1_)
    {
        this.attackTarget = p_70624_1_;
        ForgeHooks.onLivingSetAttackTarget(this, p_70624_1_);
    }
	
    public int getMemory() {
    	return this.TimerMemory;
    }
    
    public boolean wasAttacked() {
    	return AttackMemory != null;
    }
    
    public EntityLivingBase getEntityMemory() {
    	return this.AttackMemory;
    }
    
    public double getMaxDistanceToFollow() {
    	return 0.2;
    }
    
    public void startMemory() {
    	this.TimerMemory = getMaxMemory();
    }
    
    public int getMaxMemory() {
    	return 15 * 20;
    }
	
    
	public EntityNeoCreature(World p_i1595_1_) {
		super(p_i1595_1_);
	}


	public int getAttackTimer() {
		return 20;
	}
	
	public int getSpecialAttackTimer() {
		return 20;
	}
	
	public void SpecialAttack() {
		
	}
	
	public void Attack() {
		
	}
	
	public int getKnockback() {
		return 0;
	}
	
	public int getFireAttack() {
		return 0;
	}
	
	public float getAttackRange() {
		return 0.65F;
	}
	
	public double getSeeRange() {
		return 20;
	}
	
	public void updateAITasks()
    {
		this.despawnEntity();
		
		for(NeoAITask t: this.task) {
			t.Try(this);
		}
		for(NeoAITask t: this.task) {
			t.Execute(this);
		}

		this.getEntitySenses().clearSensingCache();
		this.getNavigator().onUpdateNavigation();
		this.getMoveHelper().onUpdateMoveHelper();
		this.getLookHelper().onUpdateLook();
		this.getJumpHelper().doJump();
    }
	
	@Override
	public boolean canEntityBeSeen(Entity en)
    {
		float f2 = this.interpolateRotation(this.prevRenderYawOffset, this.renderYawOffset);
		if(!this.worldObj.isRemote) {
			 f2-=360;
		}
		 Vector3d pos = PosVec3DHelper.RotateArroundBase(new Vector3d(en.posX- this.posX, en.posY, en.posZ-this.posZ), -Math.toRadians(337.5D-f2));
		 Vector3d pos2 = PosVec3DHelper.RotateArroundBase(new Vector3d(en.posX- this.posX, en.posY, en.posZ-this.posZ), -Math.toRadians(292.5D-f2));
		 return this.SeeVector.isUnder(pos) || this.SeeVector.isUnder(pos2);
    }
	@Override
    public boolean isAIEnabled()
    {
        return false;
    }
	
	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();
		if(!this.worldObj.isRemote)
		{
			updateAITasks();
		}
		

		 
		if(this.wasAttacked()) {
			TimerMemory--;
			if(TimerMemory <= 0)
			{
				this.AttackMemory = null;
			}
		}
	
		 
		if(this.hasAttackTarget())
		{
			TimerAttack++;
			if(TimerAttack % getSpecialAttackTimer() == 0) {
				SpecialAttack();
			}
			else if(TimerAttack % getAttackTimer() == 0 && this.getAttackTarget().boundingBox.maxY > this.boundingBox.minY && this.getAttackTarget().boundingBox.minY < this.boundingBox.maxY) {
				Attack();
			} 
		}
		
	}
	
	public void setMovingTo(double x, double y, double z) {
		toMove = new Vector3d(x,y,z);
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound c)
	{
		super.writeEntityToNBT(c);
		c.setInteger("TimerAttack", TimerAttack);
		if(toMove != null) {
			c.setDouble("toMoveX", toMove.x);
			c.setDouble("toMoveY", toMove.y);
			c.setDouble("toMoveZ", toMove.z);
		}
		if(Home != null) {
			c.setDouble("HomeX", Home.x);
			c.setDouble("HomeY", Home.y);
			c.setDouble("HomeZ", Home.z);
			c.setDouble("HomeRange", Home.custom);
		}
		
		if(!this.worldObj.isRemote)
		{
			 ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos;
             try {
                 oos = new ObjectOutputStream( baos );
                 oos.writeObject( task );
                 oos.close();
                 c.setString(Base64.getEncoder().encodeToString(baos.toByteArray()), "NeoAITask");;
             } catch (Exception e) {
             	CRASH.Push(e);
             }
		}
		
		
	}
	
    @Override
    public void readEntityFromNBT(NBTTagCompound c)
    {
        super.readEntityFromNBT(c);
        TimerAttack = c.getInteger("TimerAttack");
        if(c.hasKey("toMoveX")) {
        	toMove = new Vector3d(
        	c.getDouble("toMoveX")
        	,c.getDouble("toMoveY")
        	,c.getDouble("toMoveZ"));
        }
        
        if(c.hasKey("HomeX")) {
        	Home = new Vector4d(
        	c.getDouble("HomeX")
        	,c.getDouble("HomeY")
        	,c.getDouble("HomeZ")
        	,c.getDouble("HomeRange"));
        }
        if(!this.worldObj.isRemote)
		{
        	if(c.hasKey("NeoAITask"))
        	{
        		 byte [] data = Base64.getDecoder().decode( c.getString("NeoAITask"));
                 ObjectInputStream ois;
                 try{
                     ois = new ObjectInputStream( new ByteArrayInputStream( data ) );
                     Object o;
                     o = ois.readObject();
                     ois.close();
                     this.task = (ArrayList<NeoAITask>) o;
                 } catch (Exception e) {
                 	CRASH.Push(e);
                 }
        	}
		}
    }
    
    
	@Override
	public void updateEntityActionState()
    {
	
	   int i = MathHelper.floor_double(this.boundingBox.minY + 0.5D);
        boolean flag = this.isInWater();
        boolean flag1 = this.handleLavaMovement();
        this.rotationPitch = 0.0F;
	 	if (this.pathToEntity != null && this.rand.nextInt(100) != 0)
        {
            Vec3 vec3 = this.pathToEntity.getPosition(this);
            double d0 = (double)(this.width * 2.0F);

            while (vec3 != null && vec3.squareDistanceTo(this.posX, vec3.yCoord, this.posZ) < d0 * d0)
            {
                this.pathToEntity.incrementPathIndex();

                if (this.pathToEntity.isFinished())
                {
                    vec3 = null;
                    this.pathToEntity = null;
                }
                else
                {
                    vec3 = this.pathToEntity.getPosition(this);
                }
            }

            this.isJumping = false;

            if (vec3 != null)
            {
                double d1 = vec3.xCoord - this.posX;
                double d2 = vec3.zCoord - this.posZ;
                double d3 = vec3.yCoord - (double)i;
                float f1 = (float)(Math.atan2(d2, d1) * 180.0D / Math.PI) - 90.0F;
                float f2 = MathHelper.wrapAngleTo180_float(f1 - this.rotationYaw);
                this.moveForward = (float)this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();

                if (f2 > 30.0F)
                {
                    f2 = 30.0F;
                }

                if (f2 < -30.0F)
                {
                    f2 = -30.0F;
                }

                this.rotationYaw += f2;

                if (this.hasAttackTarget())
                {
                	this.faceEntity(this.getAttackTarget(), 30.0F, 30.0F);
                    double d4 = this.getAttackTarget().posX - this.posX;
                    double d5 = this.getAttackTarget().posZ - this.posZ;
                    float f3 = this.rotationYaw;
                    this.rotationYaw = (float)(Math.atan2(d5, d4) * 180.0D / Math.PI) - 90.0F;
                    f2 = (f3 - this.rotationYaw + 90.0F) * (float)Math.PI / 180.0F;
                    this.moveStrafing = -MathHelper.sin(f2) * this.moveForward * 1.0F;
                    this.moveForward = MathHelper.cos(f2) * this.moveForward * 1.0F;
                }

                if (d3 > 0.0D)
                {
                    this.isJumping = true;
                }
            }

          

            if (this.isCollidedHorizontally && !this.hasPath())
            {
                this.isJumping = true;
            }

            if (this.rand.nextFloat() < 0.8F && (flag || flag1))
            {
                this.isJumping = true;
            }

        } 
		super.updateEntityActionState();
    }
	
	@Override
	public void onDeathUpdate()
    {
        ++this.deathTime;

        if (this.deathTime == 20)
        {
            int i;

            if (!this.worldObj.isRemote && (this.recentlyHit > 0 || this.isPlayer()) && this.func_146066_aG() && this.worldObj.getGameRules().getGameRuleBooleanValue("doMobLoot"))
            {
                i = this.getExperiencePoints(this.attackingPlayer);

                while (i > 0)
                {
                    int j = EntityXPOrb.getXPSplit(i);
                    i -= j;
                    this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, j));
                }
            }

            this.setDead();

            for (i = 0; i < 20; ++i)
            {
                double d2 = this.rand.nextGaussian() * 0.02D;
                double d0 = this.rand.nextGaussian() * 0.02D;
                double d1 = this.rand.nextGaussian() * 0.02D;
                this.worldObj.spawnParticle("explode", this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, d2, d0, d1);
            }
        }
    }
    public boolean hasPath()
    {
        return this.pathToEntity != null;
    }
    
    public boolean canAttackEntity(Entity en) {
    	double d0 = this.getAttackRange();
        List<Entity> list = this.worldObj.getEntitiesWithinAABB(en.getClass(), this.boundingBox.expand(d0, 4.0D, d0));
        Collections.sort(list, EntityMod.prepareSorter(this));

        if (list.isEmpty())
        {
            return false;
        }
        else
        {
            return list.contains(en);
        }
    }
    
    public boolean attackEntity(Entity p_70652_1_)
    {
    	
    	int i = this.getKnockback();
        boolean flag = p_70652_1_.attackEntityFrom(DamageSource.causeMobDamage(this), (float)this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue());

        if (flag)
        {
            if (i > 0)
            {
                p_70652_1_.addVelocity((double)(-MathHelper.sin(this.rotationYaw * (float)Math.PI / 180.0F) * (float)i * 0.5F), 0.1D, (double)(MathHelper.cos(this.rotationYaw * (float)Math.PI / 180.0F) * (float)i * 0.5F));
                this.motionX *= 0.6D;
                this.motionZ *= 0.6D;
            }

            int j = getFireAttack();

            if (j > 0)
            {
                p_70652_1_.setFire(j * 2);
            }
        }
        return flag;
    }
    
    public void setHomeArea(double x, double y, double z, double range) {
    	this.Home = new Vector4d(x,y,z, range);
    }
    
    public boolean hasHome() {
    	return this.Home != null;
    }
    
    public Vector4d getHomePosition()
    {
    	return this.Home;
    }
    private float interpolateRotation(float p_77034_1_, float p_77034_2_)
    {
        float f3;

        for (f3 = p_77034_2_ - p_77034_1_; f3 < -180.0F; f3 += 360.0F)
        {
            ;
        }

        while (f3 >= 180.0F)
        {
            f3 -= 360.0F;
        }

        return p_77034_1_ + f3;
    }
    
    public boolean isWithinHomeDistanceCurrentPosition()
    {
        return this.isWithinHomeDistance(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ));
    }

    public boolean isWithinHomeDistance(int p_110176_1_, int p_110176_2_, int p_110176_3_)
    {
        return this.Home.custom == -1.0F ? true : PosVec3DHelper.getDistanceSquared(this.Home.x,this.Home.y,this.Home.z, p_110176_1_, p_110176_2_, p_110176_3_) < this.Home.custom * this.Home.custom;
    }
    public boolean attackEntityFrom(DamageSource p_70097_1_, float p_70097_2_)
    {
        if (ForgeHooks.onLivingAttack(this, p_70097_1_, p_70097_2_)) return false;
        if (this.isEntityInvulnerable())
        {
            return false;
        }
        else if (this.worldObj.isRemote)
        {
            return false;
        }
        else
        {

            if (this.getHealth() <= 0.0F)
            {
                return false;
            }
            else if (p_70097_1_.isFireDamage() && this.isPotionActive(Potion.fireResistance))
            {
                return false;
            }
            else
            {
                if ((p_70097_1_ == DamageSource.anvil || p_70097_1_ == DamageSource.fallingBlock) && this.getEquipmentInSlot(4) != null)
                {
                    this.getEquipmentInSlot(4).damageItem((int)(p_70097_2_ * 4.0F + this.rand.nextFloat() * p_70097_2_ * 2.0F), this);
                    p_70097_2_ *= 0.75F;
                }

                this.limbSwingAmount = 1.5F;
                boolean flag = true;

                if ((float)this.hurtResistantTime > (float)this.maxHurtResistantTime / 2.0F)
                {
                    if (p_70097_2_ <= this.lastDamage)
                    {
                        return false;
                    }

                    this.damageEntity(p_70097_1_, p_70097_2_ - this.lastDamage);
                    this.lastDamage = p_70097_2_;
                    flag = false;
                }
                else
                {
                    this.lastDamage = p_70097_2_;
                    this.prevHealth = this.getHealth();
                    this.hurtResistantTime = this.maxHurtResistantTime;
                    this.damageEntity(p_70097_1_, p_70097_2_);
                    this.hurtTime = this.maxHurtTime = 10;
                }

                this.attackedAtYaw = 0.0F;
                Entity entity = p_70097_1_.getEntity();

                if (entity != null)
                {
                    if (entity instanceof EntityLivingBase)
                    {
                        this.AttackMemory = (EntityLivingBase) entity;
                        this.TimerMemory = this.getMaxMemory();
                    }

                   
                }

                if (flag)
                {
                    this.worldObj.setEntityState(this, (byte)2);

                    if (p_70097_1_ != DamageSource.drown)
                    {
                        this.setBeenAttacked();
                    }

                    if (entity != null)
                    {
                        double d1 = entity.posX - this.posX;
                        double d0;

                        for (d0 = entity.posZ - this.posZ; d1 * d1 + d0 * d0 < 1.0E-4D; d0 = (Math.random() - Math.random()) * 0.01D)
                        {
                            d1 = (Math.random() - Math.random()) * 0.01D;
                        }

                        this.attackedAtYaw = (float)(Math.atan2(d0, d1) * 180.0D / Math.PI) - this.rotationYaw;
                        this.knockBack(entity, p_70097_2_, d1, d0);
                    }
                    else
                    {
                        this.attackedAtYaw = (float)((int)(Math.random() * 2.0D) * 180);
                    }
                }

                String s;

                if (this.getHealth() <= 0.0F)
                {
                    s = this.getDeathSound();

                    if (flag && s != null)
                    {
                        this.playSound(s, this.getSoundVolume(), this.getSoundPitch());
                    }

                    this.onDeath(p_70097_1_);
                }
                else
                {
                    s = this.getHurtSound();

                    if (flag && s != null)
                    {
                        this.playSound(s, this.getSoundVolume(), this.getSoundPitch());
                    }
                }

                return true;
            }
        }
    }
}
