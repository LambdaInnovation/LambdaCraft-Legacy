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

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import cn.lambdacraft.api.entity.IEntityLink;
import cn.lambdacraft.core.proxy.ClientProps;
import cn.lambdacraft.mob.register.CBCMobItems;
import cn.liutils.api.entity.LIEntityMob;
import cn.liutils.api.util.GenericUtils;

/**
 * TODO:坑完了
 * @author WeAt3hFolD
 *
 */
public class EntityHoundeye extends LIEntityMob implements IEntityLink<EntityLivingBase> {
	public final static float MOVE_SPEED = 3.0f;
	
	
	protected String throwerName = "";
	public boolean isCharging = false;
	public int chargeTick = 0;
	protected int lastShockTick = 0;
	
	private static IEntitySelector selector = new IEntitySelector() {

		@Override
		public boolean isEntityApplicable(Entity entity) {
			return entity instanceof EntityPlayer || entity instanceof EntityVillager;
		}
		
	};
	
	/**
	 * @param par1World
	 */
	public EntityHoundeye(World par1World) {
		super(par1World);
	}
	
	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(20, Byte.valueOf((byte) 0));
	}
	
	@Override
    protected Entity findPlayerToAttack()
    {
		Entity e = GenericUtils.getNearestEntityTo(this, GenericUtils.getEntitiesAround_CheckSight(this, 15.0, selector));
		if(e != null && e.getEntityName().equals(throwerName))
			return null;
		return e;
    }
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		
		if(!worldObj.isRemote && isCharging && !dead) {
			if(++chargeTick > 50) {
				worldObj.spawnEntityInWorld(new EntityShockwave(worldObj, this, posX, posY, posZ));
				this.playSound(GenericUtils.getRandomSound("lambdacraft:mobs.he_blast", 3), 0.5F, 1.0F);
				lastShockTick = ticksExisted;
				isCharging = false;
			}
		}
		
		if(worldObj.isRemote) {
			isCharging = dataWatcher.getWatchableObjectByte(20) > 0;
			chargeTick = dataWatcher.getWatchableObjectByte(20) - 1;
		} else {
			dataWatcher.updateObject(20, Byte.valueOf((byte)(isCharging? chargeTick + 1 : 0)));
		}
	}

	/* (non-Javadoc)
	 * @see net.minecraft.entity.EntityLiving#getMaxHealth()
	 */
	@Override
	public double getMaxHealth2() {
		return 14.0f;
	}
	
	/**
	 * Basic mob attack. Default to touch of death in EntityCreature. Overridden
	 * by each mob to define their attack.
	 */
	@Override
	protected void attackEntity(Entity par1Entity, float par2) {
		if (par2 < 4.0F && this.rand.nextInt(6) == 0 && !isCharging) {
			if (this.onGround && ticksExisted - lastShockTick > 40) {
				double d0 = par1Entity.posX - this.posX;
				double d1 = par1Entity.posZ - this.posZ;
				float f2 = MathHelper.sqrt_double(d0 * d0 + d1 * d1);
				this.motionX = -d0 / f2 * 0.2 + this.motionX;
				this.motionZ = -d1 / f2 * 0.2 + this.motionZ;
				this.motionY = 0.30;
				isCharging = true;
				chargeTick = 0;
				this.playSound(GenericUtils.getRandomSound("lambdacraft:mobs.he_attack", 3), 0.5F, 1.0F);
			}
		}
	}
	
	@Override
    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
    {
		if(par2 >= 10 || rand.nextInt(4) == 0)
			isCharging = false;
    	return super.attackEntityFrom(par1DamageSource, par2);
    }
	
	@Override
	protected boolean isMovementBlocked()
	{
		return isCharging;
	}
	
	
    @Override
	public EntityItem dropItemWithOffset(int par1, int par2, float par3)
    {
        return this.entityDropItem(new ItemStack(par1, par2, 2), par3);
    }
    
    @Override
    public int getDropItemId() {
    	return CBCMobItems.dna.itemID;
    }
	
	/**
	 * Returns the sound this mob makes while it's alive.
	 */
	@Override
	protected String getLivingSound() {
		return GenericUtils.getRandomSound("lambdacraft:mobs.he_idle", 4);
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected String getHurtSound() {
		return GenericUtils.getRandomSound("lambdacraft:mobs.he_pain", 5);
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	@Override
	protected String getDeathSound() {
		return GenericUtils.getRandomSound("lambdacraft:mobs.he_die", 3);
	}

	@Override
	public EntityLiving getLinkedEntity() {
		return null;
	}

	@Override
	public void setLinkedEntity(EntityLivingBase entity) {
		throwerName = entity.getEntityName();
	}
	
	@Override
    public void writeEntityToNBT(NBTTagCompound nbt)
    {
    	super.writeEntityToNBT(nbt);
    	if(throwerName != null && throwerName.equals(""))
    		nbt.setString("thrower", throwerName);
    }
	
	@Override
    public void readEntityFromNBT(NBTTagCompound nbt)
    {
    	super.readEntityFromNBT(nbt);
    	throwerName = nbt.getString("thrower");
    }

	@Override
	protected double getFollowRange() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected double getMoveSpeed() {
		return MOVE_SPEED;
	}

	@Override
	protected double getKnockBackResistance() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected double getAttackDamage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ResourceLocation getTexture() {
		return ClientProps.HOUNDEYE_PATH;
	}

}
