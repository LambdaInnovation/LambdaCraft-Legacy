package cn.lambdacraft.mob.entity;

import cn.lambdacraft.core.prop.ClientProps;
import cn.lambdacraft.core.prop.GeneralProps;
import cn.liutils.api.entity.LIEntityMob;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
/**
 * @author jiangyue
 * The entity class of Mob Bull.
 * No right reserved.
 */

public class EntityMobBull extends LIEntityMob {

	/* Fields Declaration. Do not change unless nessessary. */
	protected final double MAX_HEALTH = 40.0;
	protected final double FOLLOW_RANGE = 6.5;
	protected final double MOVE_SPEED = 0.6;
	protected final double KNOCKBACK_RESISTANCE = 0.3;
	protected final double ATTACK_DAMAGE = 4.0;
	protected stats currStat = stats.IDLE;
	public enum stats {
		ATTACKING, SPITTING_TOXIC, ATTACKING_TAIL, ATTACKED, IDLE
	}

	public EntityMobBull(World worldcfg) {
		super(worldcfg);
	}
	
	public EntityMobBull(World worldcfg, int x, int y, int z) {
	    this(worldcfg);
	    this.posX = x;
	    this.posY = y;
	    this.posZ = z;
	}

	@Override
	public void onUpdate() {
		/* Implements onUpdate by the System */
		super.onUpdate();
	}

	@Override
	/**
	 * Obviously, there are two ways of attacking an entity:
	 * One is directly attack with interacting,
	 * Another is attack long with toxic.
	 * Reference: Half-life Wiki
	 */
	protected void attackEntity(Entity par1Entity, float par2) {
		if(par1Entity.getEntityId() == GeneralProps.ENT_ID_HOUNDEYE) {
			return;
		} else {
			double distance = Math.sqrt(Math.abs(par1Entity.posX - this.posX)*Math.abs(par1Entity.posX - this.posX) + Math.abs(par1Entity.posX - this.posX)*Math.abs(par1Entity.posY - this.posY) + Math.abs(par1Entity.posZ - this.posZ)*Math.abs(par1Entity.posZ - this.posZ));
			if(distance <= 6) {
				this.attackShortRange(par1Entity, par2);
				currStat = stats.ATTACKING;
			} else if(distance <= 15){
				this.attackLongRange(par1Entity);
				currStat = stats.SPITTING_TOXIC;
			} else {
				doNothing();
				currStat = stats.IDLE;
			}
		}
	}

	private void doNothing() {
		// Just for readable code, NOTHING ELSE!

	}

	private void attackShortRange(Entity par1Entity, float par2) {
		int isAttackingWithTail = rand.nextInt();
		if(isAttackingWithTail % 8 == 0) this.currStat = stats.ATTACKING_TAIL;
		super.attackEntity(par1Entity, par2);
	}

	/**
	 * Issue a spit of toxic to object from long range.
	 * @param entity the entity to attack.
	 */
	private void attackLongRange(Entity entity) {
		int r2 = rand.nextInt();
		if(r2 % 60 == 0) worldObj.spawnEntityInWorld(new EntityToxic(worldObj, entity, posX, posY, posZ));
	}

	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
		this.currStat = stats.ATTACKED;
		return super.attackEntityFrom(par1DamageSource, par2);
	}

	// Properties implementations: Refer to beginning of the code
	@Override
	protected double getMaxHealth2() {
		return this.MAX_HEALTH;
    }

	@Override
	protected double getFollowRange() {
		return this.FOLLOW_RANGE;
	}

	@Override
	protected double getMoveSpeed() {
		return this.MOVE_SPEED;
	}

	@Override
	protected double getKnockBackResistance() {
		return this.KNOCKBACK_RESISTANCE;
	}

	@Override
	protected double getAttackDamage() {
		return this.ATTACK_DAMAGE;
	}

	@Override
	public ResourceLocation getTexture() {
		return ClientProps.BULL_MOB_PATH;
	}


}
