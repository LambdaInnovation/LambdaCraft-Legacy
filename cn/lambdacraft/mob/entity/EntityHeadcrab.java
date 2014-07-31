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

import java.util.List;

import cn.lambdacraft.api.entity.IEntityLink;
import cn.lambdacraft.core.proxy.ClientProps;
import cn.lambdacraft.mob.register.CBCMobItems;
import cn.lambdacraft.mob.util.MobHelper;
import cn.liutils.api.entity.LIEntityMob;
import cn.liutils.api.util.GenericUtils;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/**
 * @author WeAthFolD
 * 
 */
public class EntityHeadcrab extends LIEntityMob implements
		IEntityLink<EntityPlayer> {

	public static final float MOVE_SPEED = 0.5F;
	public static final float MAX_HEALTH = 4.0F;
	public static final float ATTACK_DAMAGE = 6F;
	public int lastJumpTick = 0, tickSinceBite = 0;
	public EntityLivingBase attacher;
	protected String throwerName = "";

	/**
	 * Select the EntityPlayer or EntityVillager
	 */
	public static IEntitySelector selector = new IEntitySelector() {
		@Override
		public boolean isEntityApplicable(Entity entity) {
			return GenericUtils.selectorLiving.isEntityApplicable(entity)
					&& (entity instanceof EntityPlayer || entity instanceof EntityVillager);
		}
	};

	public EntityHeadcrab(World par1World) {
		super(par1World);
		this.setSize(0.4F, 0.3F);
		this.experienceValue = 5;
	}

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(20, Integer.valueOf((byte) 0));
	}

	@Override
	public boolean attackEntityAsMob(Entity par1Entity) {
		double i = this.getAttackDamage();
		if (this.onGround || this.hurtResistantTime > 0)
			return false;
		boolean flag = par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), (float)i);
		this.motionX = 0.0;
		this.motionZ = 0.0;
		// Attaching on
		if (par1Entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) par1Entity;
			ItemStack armorStack = player.inventory.armorInventory[3];
			if (armorStack == null) {
				if(player.getHealth() <= 10.0F) {
					attacher = player;
					player.addPotionEffect(new PotionEffect(Potion.blindness.getId(), 200));
					player.addPotionEffect(new PotionEffect(Potion.confusion.getId(), 200));
					this.setPositionAndRotation(attacher.posX, attacher.posY + 0.05, attacher.posZ,
						attacher.rotationYaw, attacher.rotationPitch);
				}
			} else {
				armorStack.damageItem(5, this);
			}
		} else if (par1Entity instanceof EntityVillager) {
			attacher = (EntityLivingBase) par1Entity;
			this.setPositionAndRotation(attacher.posX, attacher.posY
				+ attacher.height + 0.05, attacher.posZ,
				attacher.rotationYaw, attacher.rotationPitch);
		}
		return flag;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		if (!this.dead && attacher != null) {
			if (attacher instanceof EntityPlayer)
				this.setPositionAndRotation(attacher.posX,
						attacher.posY + 0.05, attacher.posZ,
						attacher.rotationYaw, attacher.rotationPitch);
			else
				this.setPositionAndRotation(attacher.posX, attacher.posY
						+ attacher.height + 0.05, attacher.posZ,
						attacher.rotationYaw, attacher.rotationPitch);
			if (++tickSinceBite >= 15) { //Drain health and indicate player stat
				dataWatcher.updateObject(20, Integer.valueOf(attacher.entityId));
				tickSinceBite = 0;
				float health = attacher.getHealth() - 1;
				if (!(attacher instanceof EntityPlayer && ((EntityPlayer) attacher).capabilities.isCreativeMode)) {
					attacher.setHealth(health);

					if (health <= 0 && !worldObj.isRemote) {
						NBTTagCompound nbt = attacher.getEntityData();
						if(!nbt.getBoolean("spawnedZombie")) {
							nbt.setBoolean("spawnedZombie", true);
							attacher = null;
							MobHelper.spawnCreature(worldObj, EntityHLZombie.class,
								this, false);
							dataWatcher.updateObject(20, Integer.valueOf(0));
							this.setDead();
						}
					}
				}
			}
			if (attacher != null && attacher.isDead) {
				attacher = null;
			}
		} else {
			if (worldObj.isRemote) {
				int id = dataWatcher.getWatchableObjectInt(20);
				Entity e = worldObj.getEntityByID(id);
				if (e == null || e instanceof EntityLivingBase) //一个逻辑判断解决一大个bug（笑
					attacher = (EntityLivingBase) e;
			} else {
				dataWatcher.updateObject(20, Integer.valueOf(0));
			}
		}
	}

	/**
	 * Disables a mob's ability to move on its own while true.
	 */
	@Override
	protected boolean isMovementCeased() {
		return attacher != null;
	}

	/**
	 * 头蟹掉落不受伤害=w=
	 */
	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
		if (par1DamageSource == DamageSource.fall
				|| par1DamageSource == DamageSource.fallingBlock) {
			return false;
		} else if (super.attackEntityFrom(par1DamageSource, par2)) {
			Entity entity = par1DamageSource.getEntity();
			if (entity != this) {
				this.entityToAttack = entity;
			}
			this.motionX += -MathHelper.sin(this.rotationYaw / 180.0F
					* (float) Math.PI)
					* MathHelper.cos(this.rotationPitch / 180.0F
							* (float) Math.PI) * 0.2F;
			this.motionZ += MathHelper.cos(this.rotationYaw / 180.0F
					* (float) Math.PI)
					* MathHelper.cos(this.rotationPitch / 180.0F
							* (float) Math.PI) * 0.2F;

			return true;
		}
		return false;
	}

	/**
	 * 自定义的寻路函数，
	 */
	@Override
	protected Entity findPlayerToAttack() {
		return GenericUtils.getNearestEntityTo(this, GenericUtils.getEntitiesAround(this, 8.0F, selector));
	}

	/**
	 * Returns the sound this mob makes while it's alive.
	 */
	@Override
	protected String getLivingSound() {
		return GenericUtils.getRandomSound("lambdacraft:mobs.hc_idle", 5);
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected String getHurtSound() {
		return GenericUtils.getRandomSound("lambdacraft:mobs.hc_pain", 3);
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	@Override
	protected String getDeathSound() {
		return GenericUtils.getRandomSound("lambdacraft:mobs.hc_die", 2);
	}

	/**
	 * Basic mob attack. Default to touch of death in EntityCreature. Overridden
	 * by each mob to define their attack.
	 */
	@Override
	protected void attackEntity(Entity par1Entity, float par2) {
		if (par2 < 6.0F && this.rand.nextInt(6) == 0) {
			if (this.onGround && ticksExisted - lastJumpTick > 30) {
				double d0 = par1Entity.posX - this.posX;
				double d1 = par1Entity.posZ - this.posZ;
				float f2 = MathHelper.sqrt_double(d0 * d0 + d1 * d1);
				this.motionX = d0 / f2 * 1.0 + this.motionX * 0.20;
				this.motionZ = d1 / f2 * 1.0 + this.motionZ * 0.20;
				this.motionY = 0.50;
				lastJumpTick = ticksExisted;
				this.playSound(
						GenericUtils.getRandomSound("lambdacraft:mobs.hc_attack", 3),
						0.5F, 1.0F);
			}
		} else {
			super.attackEntity(par1Entity, par2);
		}
	}

	@Override
	public EntityItem dropItemWithOffset(int par1, int par2, float par3) {
		return this.entityDropItem(new ItemStack(par1, par2, 0), par3);
	}

	@Override
	public int getDropItemId() {
		return CBCMobItems.dna.itemID;
	}

	/**
	 * Get this Entity's EnumCreatureAttribute
	 */
	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.ARTHROPOD;
	}

	@Override
	public boolean isPotionApplicable(PotionEffect par1PotionEffect) {
		return par1PotionEffect.getPotionID() == Potion.poison.id ? false
				: super.isPotionApplicable(par1PotionEffect);
	}

	/**
	 * Initialize this creature.
	 * 
	 * TODO: initCreature;
	 */
	// @Override
	// public void initCreature() {
	// this.playSound(GenericUtils.getRandomSound("lambdacraft:mobs.hc_idle", 5),
	// 0.5F, 1.0F);
	// }

	@Override
	public EntityPlayer getLinkedEntity() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setLinkedEntity(EntityPlayer entity) {
		throwerName = entity.username;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setString("thrower", throwerName);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		throwerName = nbt.getString("thrower");
	}

	@Override
	protected double getMaxHealth2() {
		return MAX_HEALTH;
	}

	@Override
	protected double getFollowRange() {
		return 0;
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
		return ATTACK_DAMAGE;
	}

	@Override
	public ResourceLocation getTexture() {
		return ClientProps.HEADCRAB_MOB_PATH;
	}
}
