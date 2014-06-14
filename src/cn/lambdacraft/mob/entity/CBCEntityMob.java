package cn.lambdacraft.mob.entity;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/**
 * 为便于mob管理的通用类
 * 
 * @author mkpoli
 *
 */
public abstract class CBCEntityMob extends EntityMob {
	
	public CBCEntityMob(World par1World) {
		super(par1World);
	}

	/**
	 * Change attributes
	 */
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth)
				.setAttribute(getMaxHealth2()); // Max Health
		if (getFollowRange() != 0)
			this.getEntityAttribute(SharedMonsterAttributes.followRange)
					.setAttribute(getFollowRange()); // Follow Range
		if (getKnockBackResistance() != 0)
			this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance)
					.setAttribute(getKnockBackResistance()); // knockbackResistance
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
				.setAttribute(getMoveSpeed()); // Move Speed
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage)
				.setAttribute(getAttackDamage()); // Attack Damage
	}

	abstract protected double getMaxHealth2();

	abstract protected double getFollowRange();

	abstract protected double getMoveSpeed();

	abstract protected double getKnockBackResistance();

	abstract protected double getAttackDamage();

	public abstract ResourceLocation getTexture();
}
