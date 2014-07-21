/** 
 * Copyright (c) LambdaCraft Modding Team, 2013
 * 版权许可：LambdaCraft 制作小组， 2013.
 * http://lambdacraft.half-life.cn/
 * 
 * LambdaCraft is open-source. It is distributed under the terms of the
 * LambdaCraft Open Source License. It grants rights to read, modify, compile
 * or run the code. It does *NOT* grant the right to redistribute this software
 * or its modifications in any form, binary or source, except if expressively
 * granted by the copyright holder.
 *
 * LambdaCraft是完全开源的。它的发布遵从《LambdaCraft开源协议》。你允许阅读，修改以及调试运行
 * 源代码， 然而你不允许将源代码以另外任何的方式发布，除非你得到了版权所有者的许可。
 */
package cn.lambdacraft.mob.entity;

import cn.lambdacraft.core.proxy.ClientProps;
import cn.liutils.api.entity.LIEntityMob;
import cn.liutils.api.util.GenericUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIBreakDoor;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/**
 * 喜闻乐见的僵尸，第一次写mob 肯定很渣233
 * @author mkpoli
 */
public class EntityHLZombie extends LIEntityMob {
	public final static float MOVE_SPEED = 0.23F;
	public final static float MAX_HEALTH = 30F;
	
	public int damagetype;
	public int damage;
	protected int playtick = 0;
	public Entity entityToAttack;
	public int tickCountAttack;
	private EntityAIBase ai1, ai2;

	public EntityHLZombie(World par1World) {
		super(par1World);
		this.experienceValue = 10;
		/*
		this.getNavigator().setBreakDoors(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIBreakDoor(this));
        this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, this.getMoveSpeed(), false));
        this.tasks.addTask(3, new EntityAIAttackOnCollide(this, EntityVillager.class, this.getMoveSpeed(), true));
        this.tasks.addTask(4, new EntityAIMoveTowardsRestriction(this, this.getMoveSpeed()));
        this.tasks.addTask(5, new EntityAIMoveThroughVillage(this, this.getMoveSpeed(), false));
        this.tasks.addTask(6, new EntityAIWander(this, this.getMoveSpeed()));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        ai1 = new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true);
        this.targetTasks.addTask(2, ai1);
        ai2 = new EntityAINearestAttackableTarget(this, EntityVillager.class, 0, false);
        this.targetTasks.addTask(2, ai2);
        */
		
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIBreakDoor(this));
        this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
        this.tasks.addTask(3, new EntityAIAttackOnCollide(this, EntityVillager.class, 1.0D, true));
        this.tasks.addTask(4, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(5, new EntityAIMoveThroughVillage(this, 1.0D, false));
        this.tasks.addTask(6, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityVillager.class, 0, false));
	}
	
    @Override
	protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setAttribute(40.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(0.23000000417232513D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setAttribute(3.0D);
    }
	
	@Override
	public void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(20, Byte.valueOf((byte)0));
	}

	@Override
	public double getMaxHealth2() {
		return MAX_HEALTH;
	}
	
	@Override
	public void onUpdate() {
		boolean preIsBurning = this.isBurning();
		super.onUpdate();
		updateStats();
		if(isAttacking()) {
			if(--tickCountAttack <= 0)
				doRealAttack();
		}
		if (!this.worldObj.isRemote) {
			if (this.isBurning()) {
				this.targetTasks.removeTask(ai1);
				this.targetTasks.removeTask(ai2);
				onBuring();
			} else if(preIsBurning)
				this.attackEntityFrom(DamageSource.causeMobDamage(this), 200);
		}
	}
	
	public boolean isAttacking() {
		return (worldObj.isRemote && tickCountAttack > 0) || this.entityToAttack != null;
	}

	@Override
	public boolean attackEntityAsMob(Entity par1Entity) {
		if(this.isBurning())
			return false;
		damagetype = this.worldObj.rand.nextInt(3);
		if (damagetype == 0) {
			this.worldObj.playSoundAtEntity(par1Entity, "lambdacraft:mobs.zo_attacka", 0.5f, 1.0f);
			damage = 6;
		} else {
			this.worldObj.playSoundAtEntity(par1Entity, "lambdacraft:mobs.zo_attackb", 0.5f, 1.0f);
			damage = 3;
		}
		this.entityToAttack = par1Entity;
		this.tickCountAttack = damagetype == 0 ? 10 : 5;
		return false;
	}
	
	protected void doRealAttack() {
		if(!worldObj.isRemote) {
			double distance = this.getDistanceSqToEntity(entityToAttack);
			String sndPath;
			if(distance <= 3.0) {
				entityToAttack.attackEntityFrom(DamageSource.causeMobDamage(this), damage);
				sndPath = GenericUtils.getRandomSound("lambdacraft:mobs.zo_claw_strike", 3);
			} else 
				sndPath = GenericUtils.getRandomSound("lambdacraft:mobs.zo_claw_miss", 2);
			this.playSound(sndPath, 0.5F, 1.0F);
		}
		this.tickCountAttack = 0;
		this.entityToAttack = null;
	}

	@Override
	public void onDeath(DamageSource par1DamageSource) {
		super.onDeath(par1DamageSource);
		if (!this.worldObj.isRemote && !par1DamageSource.isFireDamage()) {
			EntityHeadcrab entityHeadcrab = new EntityHeadcrab(this.worldObj);
			entityHeadcrab.setLocationAndAngles(this.posX, this.posY + this.height, this.posZ, MathHelper.wrapAngleTo180_float(this.worldObj.rand.nextFloat() * 360.0F), 0.0F);
			this.worldObj.spawnEntityInWorld(entityHeadcrab);
			entityHeadcrab.setHealth(10);
		}
	}
	
	public void updateStats() {
		if(worldObj.isRemote) {
			Byte a = dataWatcher.getWatchableObjectByte(20);
			if(!this.isAttacking() && a != 0) {
				this.damagetype = a - 1;
				tickCountAttack = damagetype == 0 ? 10 : 5;
			} 
		}
		else {
			dataWatcher.updateObject(20, Byte.valueOf((byte)((isAttacking() ? 1 : 0) * (this.damagetype + 1))));
		}
	}
	
	public void onBuring() {
		if(playtick >= 40) {
			//this.worldObj.playSoundAtEntity(this, GenericUtils.getRandomSound("lambdacraft:mobs.zo_moan_loop", 3), 0.5F, 1.0F);
			playtick = 0;
		}
		++playtick;
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(1.0f);
	}

	@Override
    protected int getDropItemId()
    {
        return Item.rottenFlesh.itemID;
    }
	
    /**
     * Returns true if the newer Entity AI code should be run
     */
    @Override
	protected boolean isAIEnabled()
    {
        return true;
    }
    
	@Override
	protected String getLivingSound() {
		return GenericUtils.getRandomSound("lambdacraft:mobs.zo_idle", 3);
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected String getHurtSound() {
		return GenericUtils.getRandomSound("lambdacraft:mobs.zo_pain", 2);
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	@Override
	protected String getDeathSound() {
		return GenericUtils.getRandomSound("lambdacraft:mobs.hc_die", 2);
	}
    
	/* (non-Javadoc)
	 * @see net.minecraft.entity.monster.EntityMob#attackEntity(net.minecraft.entity.Entity, float)
	 */
	@Override
	protected void attackEntity(Entity par1Entity, float par2) {
		super.attackEntity(par1Entity, par2);
	}

	@Override
	protected double getFollowRange() {
		return 10;
	}

	@Override
	protected double getMoveSpeed() {
		return MOVE_SPEED;
	}

	@Override
	protected double getKnockBackResistance() {
		return 0;
	}

	@Override
	protected double getAttackDamage() {
		return 0;
	}

	@Override
	public ResourceLocation getTexture() {
		return ClientProps.ZOMBIE_MOB_PATH;
	}	
    
}
