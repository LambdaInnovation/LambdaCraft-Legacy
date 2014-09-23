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
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import cn.lambdacraft.api.entity.IEntityLink;
import cn.lambdacraft.core.prop.ClientProps;
import cn.lambdacraft.mob.register.CBCMobItems;
import cn.liutils.api.command.LICommandBase;
import cn.liutils.api.entity.EntityBullet;
import cn.liutils.api.entity.LIEntityMob;
import cn.liutils.api.util.EntityUtils;
import cn.liutils.api.util.GenericUtils;

/**
 * 你问我为什么要继承EntityLiving？这货本来可是大光圈基地的产物啊，233
 * @author WeAthFolD
 * 
 * 卧槽原来是光圈的？
 *
 */
public class EntitySentry extends LIEntityMob implements IEntityLink {

	public Entity currentTarget;
	public String placerName = "";
	public boolean isActivated, rotationSet;
	public int activationCounter = 0;
	public int tickSinceLastAttack = 0;
	public float rotationYawSearch;
	public boolean attackPlayer = false;
	public static final float TURNING_SPEED = 5.0F;
	
	protected IEntitySelector selector = new IEntitySelector() {

		@Override
		public boolean isEntityApplicable(Entity entity) {
			if(entity instanceof EntityPlayer) {
				EntityPlayer ep = (EntityPlayer) entity;
				if(!attackPlayer || ep.getCommandSenderName().equals(placerName))
					return false;
			} else if(entity instanceof EntitySentry)
				return false;
			else if(GenericUtils.selectorLiving.isEntityApplicable(entity)) {
				if(entity instanceof IMob)
					return true;
			}
			return false;
		}
		
	};
	
	/**
	 * @param par1World
	 */
	public EntitySentry(World par1World) {
		super(par1World);
	}
	
	public EntitySentry(World par1World, double x, double y, double z, float yaw) {
		super(par1World);
		this.setPosition(x, y, z);
		this.rotationYaw = yaw;
		this.rotationYawSearch = yaw;
	}
	
	public EntitySentry(World par1World, EntityPlayer placer, double x, double y, double z, float yaw) {
		super(par1World);
		this.setPosition(x, y, z);
		placerName = placer.getCommandSenderName();
		this.rotationYaw = yaw;
		this.rotationYawSearch = yaw;
	}

	/* (non-Javadoc)
	 * @see net.minecraft.entity.Entity#entityInit()
	 */
	@Override
	protected void entityInit() {
		super.entityInit();
		this.setSize(0.4F, 1.575F);
		this.dataWatcher.addObject(15, Integer.valueOf(0));
		this.dataWatcher.addObject(16, Byte.valueOf((byte) 0));
	}
	
	@Override
	public void onUpdate() {
		
		//float lastRotationYaw = this.rotationYaw;
		super.onUpdate();
		//this.rotationYaw = lastRotationYaw;
		if(this.isActivated) {
			++activationCounter;
			if(this.isSearching() && !worldObj.isRemote) {
				this.rotationYawHead = MathHelper.sin(ticksExisted * 0.06F) * 50 + this.rotationYawSearch;
				this.rotationYawHead = GenericUtils.wrapYawAngle(rotationYawHead);
			}
			if(currentTarget == null){
				if(!worldObj.isRemote && ticksExisted % 10 == 0) {
					if(ticksExisted % 30 == 0)
						this.playSound("lambdacraft:mobs.tu_ping", 0.5F, 1.0F);
					searchForTarget();
				}
			} else attemptAttack();
		}
		this.sync();
	}
	
    @Override
	protected void onDeathUpdate()
    {
    	super.onDeathUpdate();
    	if(this.deathTime == 2) {
    		this.playSound(GenericUtils.getRandomSound("lambdacraft:mobs.tu_die", 3), 0.5F, 1.0F);
    	}
    }
	
	public void sync() {
		if(worldObj.isRemote) {
			int entityid = dataWatcher.getWatchableObjectInt(15);
			Entity e = worldObj.getEntityByID(entityid);
			currentTarget = e;
			
			this.isActivated = dataWatcher.getWatchableObjectByte(16) > 0;
		} else {
			if(currentTarget != null)
				dataWatcher.updateObject(15, currentTarget.getEntityId());
			dataWatcher.updateObject(16, Byte.valueOf((byte) (isActivated? 0x1 : 0x0)));
		}
	}
	
	protected void searchForTarget() {
		if(worldObj.isRemote)
			return;
		Entity targetEntity = EntityUtils.getNearestEntityTo(this, EntityUtils.getEntitiesAround_CheckSight(this, 15.0F, selector));
		
		if(targetEntity != null) {
			if(this.rand.nextFloat() < 0.2)
				this.playSound("lambdacraft:mobs.tu_spinup", 0.5F, 1.0F);
			currentTarget = targetEntity;
		}
	}
	
	protected boolean isSearching() {
		boolean b = this.activationCounter > 20 && currentTarget == null;
		b = b || (currentTarget != null && !this.canEntityBeSeen(this.currentTarget));
		return b;
	}
	
    @Override
	public boolean attackEntityFrom(DamageSource src, float par2)
    {
    	if(src == DamageSource.drown || src == DamageSource.fall || src == DamageSource.onFire)
    		return false;
    	boolean b = super.attackEntityFrom(src, par2);
    	if(b) {
    		isActivated = true;
    		Entity e = src.getEntity();
    		if(e != null && e.getCommandSenderName() == this.placerName) {
    			func_145778_a(CBCMobItems.turret, 1, 0.5F);
    			setDead();
    		} else
    		if(src.getEntity() != null && selector.isEntityApplicable(src.getEntity()))
    			currentTarget = src.getEntity();
    	}
    	return b;
    }
	
	/**
	 * Similar to setArrowHeading, it's point the throwable entity to a x, y, z
	 * direction.
	 */
	public void setSentryHeading(double par1, double par3, double par5,
			float par7) {
		float f2 = MathHelper.sqrt_double(par1 * par1 + par3 * par3 + par5
				* par5);
		par1 /= f2;
		par3 /= f2;
		par5 /= f2;
		par1 *= par7;
		par3 *= par7;
		par5 *= par7;
		
		double dx = par1, dy = par3, dz = par5;
		float f3 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);

		float lastRotationYaw = rotationYawHead;
		this.prevRotationYawHead = this.rotationYawHead = (float) (Math.atan2(par1,
				par5) * 180.0D / Math.PI);
		float dyaw = GenericUtils.wrapYawAngle(rotationYawHead - lastRotationYaw);
		
		if(Math.abs(dyaw) > 20.0F) {
			dyaw = dyaw > 0 ? 20.0F : -20.0F;
			this.rotationYawHead = GenericUtils.wrapYawAngle(lastRotationYaw + dyaw);
		}
		this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(par3,
				f3) * 180.0D / Math.PI);
	}
	
	protected void attemptAttack() {
		if(currentTarget == null)
			return;
		double dx = currentTarget.posX - this.posX, dy = currentTarget.posY - this.posY, dz = currentTarget.posZ
				- this.posZ;
		if(!isSearching())
			this.setSentryHeading(dx, dy, dz, 1.0F);
		
		if(++tickSinceLastAttack > 5) {
			tickSinceLastAttack = 0;
			if(!canEntityBeSeen(currentTarget)) {
				if(!rotationSet)
					rotationYawSearch = rotationYawHead;
				rotationSet = true;
			} else { 
				rotationSet = false;
				this.playSound("lambdacraft:mobs.tu_fire", 0.5F, 1.0F);
				worldObj.spawnEntityInWorld(new EntityBullet(worldObj, this, currentTarget, 5).setEntitySelector(selector));
			}
			if(currentTarget.getDistanceSqToEntity(this) > 400) {
				this.currentTarget = null;
				return;
			}
		}
		
		if(currentTarget.isDead || currentTarget.isEntityInvulnerable() || rand.nextFloat() <= 0.01)
			this.currentTarget = null;
	}
	
	@Override
	protected boolean isMovementBlocked()
	{
		return true;
	}

	/* (non-Javadoc)
	 * @see net.minecraft.entity.Entity#readEntityFromNBT(net.minecraft.nbt.NBTTagCompound)
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		int entityid = nbt.getInteger("targetId");
		Entity e = worldObj.getEntityByID(entityid);
		if(e != null)
			currentTarget = e;
		isActivated = nbt.getBoolean("isActivated");
		activationCounter = nbt.getInteger("activationTime");
		rotationYawSearch = nbt.getFloat("searchYaw");
		placerName = nbt.getString("placer");
		attackPlayer = nbt.getBoolean("attackPlayer");
	}
	
	public void activate() {
		if(!isActivated) {
			this.playSound("lambdacraft:weapons.mine_activate", 0.5F, 1.0F);
			isActivated = true;
		}
	}
	
	@Override
	public boolean interact(EntityPlayer player)
    {
		if(!worldObj.isRemote) {
			if(!player.getCommandSenderName().equals(placerName)) {
				LICommandBase.sendChat(player, "sentry.deny.name");
				return false;
			}
			if(player.isSneaking()) {
				attackPlayer = !attackPlayer;
				LICommandBase.sendChat(player, "sentry.attackstat" + (attackPlayer ? 1 : 0)  + ".name");
			} else {
				this.isActivated = !isActivated;
				LICommandBase.sendChat(player, ("sentry.status" + (isActivated ? 1 : 0) + ".name"));
				
				String s = isActivated ? "lambdacraft:mobs.tu_deploy" : "lambdacraft:mobs.tu_spindown";
				this.playSound(s, 0.5F, 1.0F);
			}
		}
		return true;
    }

	/* (non-Javadoc)
	 * @see net.minecraft.entity.Entity#writeEntityToNBT(net.minecraft.nbt.NBTTagCompound)
	 */
	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		if(currentTarget != null)
			nbt.setInteger("targetId", currentTarget.getEntityId());
		nbt.setBoolean("isActivated", isActivated);
		nbt.setInteger("activationTime", activationCounter);
		nbt.setFloat("searchYaw", rotationYawSearch);
		nbt.setString("placer", placerName);
		nbt.setBoolean("attackPlayer", attackPlayer);
	}

	@Override
	public double getMaxHealth2() {
		return 20;
	}

	@Override
	public Entity getLinkedEntity() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setLinkedEntity(Entity entity) {
		if(!(entity instanceof EntityPlayer))return;
		this.placerName = ((EntityPlayer)entity).getCommandSenderName();
	}

	@Override
	protected double getFollowRange() {
		return 0;
	}

	@Override
	protected double getMoveSpeed() {
		return 0;
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
		return ClientProps.TURRET_PATH;
	}

}
