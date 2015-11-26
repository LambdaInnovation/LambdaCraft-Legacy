package cn.lambdacraft.mob.entity;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

/* 表示加了CASE后代码行数瞬间上去了 -w- */

/**
 * @author jiangyue
 * The entity for mob big momoa.
 * - Attaching damage: 5
 * - Moving speed: 0.9
 */
public class EntityBigMomoa extends EntityMob implements IBossDisplayData {
	/* IN THIS CLASS EVERYTHING INCLUDING SPAWNING CHILD IS ABLE TO CHANGE THE PARAMETRE.
	 * SO IN DIFFERENT GAME TYPE MIGHT BE DIFFER.
	 */
	/* About 2.5 Hearts */
	protected float ATTACK_DAMAGE = 5f;
	protected float MOVING_SPEED = 0.9f;
	/* About 50 Hearts */
	protected float HEALTH_POINT;
	protected int LENGTH_TO_SPAWN_CHILD = 400;
	protected int ATTACK_RANGE = 5;
	protected double FOLLOW_RANGE = 10;
	protected int KNOCKBACK_RESISTANCE = 2;
	protected stats currStat = stats.IDLE;
	
	protected int diff;	
	public enum stats {
		SPAWING_CHILD, ATTACKING, ATTACKING_RANGE, ATTACKED, IDLE
	}
	private int spawnChildCount = 1;

	
	public EntityBigMomoa(World par1World) {
		super(par1World);
		
		// SO >w< WE NEED TO SET DIFFERENT VALUES IN DIFFERENT DIFFICULTY
		this.diff = worldObj.difficultySetting.getDifficultyId();
		
		switch(diff) {
			case 0:
				// this.setDead(); -- It will exec itself.
				break;
			case 1:
				this.ATTACK_DAMAGE = 3f;
				this.ATTACK_RANGE = 4;
				this.HEALTH_POINT = 70;
				this.MOVING_SPEED = 0.5f;
				this.LENGTH_TO_SPAWN_CHILD = 1000;
				break;
			case 2:
				this.ATTACK_DAMAGE = 4f;
				this.ATTACK_RANGE = 5;
				this.HEALTH_POINT = 85;
				this.MOVING_SPEED = 0.65f;
				this.LENGTH_TO_SPAWN_CHILD = 700;
				break;
			case 3:
				this.ATTACK_DAMAGE = 6f;
				this.ATTACK_RANGE = 7;
				this.HEALTH_POINT = 120;
				this.MOVING_SPEED = 0.7f;
				this.LENGTH_TO_SPAWN_CHILD = 400;
				break;
		}
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(HEALTH_POINT); // Max Health
		this.setHealth(HEALTH_POINT);
		if (this.FOLLOW_RANGE != 0)
			this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(FOLLOW_RANGE); // Follow Range
		if (this.KNOCKBACK_RESISTANCE != 0)
			this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(KNOCKBACK_RESISTANCE); // knockbackResistance
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(MOVING_SPEED); // Move Speed
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(ATTACK_DAMAGE); // Attack Damage
	}
	
	@Override
	public void onUpdate() {
		BossStatus.setBossStatus(this, true);
		super.onUpdate();
		this.reqSpawnChild();
	}
	
	protected void reqSpawnChild() {
		/* Request once while parsing spawn child: Never mind. */
		// Minecraft.getMinecraft().getSystemTime();
		this.spawnChildCount ++;
		if(this.spawnChildCount % LENGTH_TO_SPAWN_CHILD == 0) {
			spawnChildCount = 0;
			this.currStat = stats.SPAWING_CHILD;
			EntityHeadcrab entityBabyHeadcrab = new EntityBabyHeadcrab(worldObj);
			entityBabyHeadcrab.setPosition(posX + 2, posY + 2, posZ);
			worldObj.spawnEntityInWorld(entityBabyHeadcrab);
		}
	}
	
	@Override
	/**
	 * Seems to drop something -w-
	 */
	protected Item getDropItem() {
		return null;
	}
	
	@Override
	protected void attackEntity(Entity par1Entity, float par2) {
		boolean TEST = false;
		
		if (!TEST) {
			/* NEVER SAY NO. */
			// Randomly decide attack method #1 or #2
			// MODIFY: Attack mode choosing is less centered.
			int AttackMode = rand.nextInt() % 30;
			if (AttackMode != 5) {
			    super.attackEntity(par1Entity, par2);
			    this.currStat = stats.ATTACKING;
			} else
				this.attackEntityInRange(this.ATTACK_RANGE);
		}
		
	}
	
	private void attackEntityInRange(int ATTACK_RANGE) {
		this.currStat = stats.ATTACKING_RANGE;
		//System.err.println("[TESTING] {INVOKED}");
	    List<EntityLiving> ls = worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(posX - (ATTACK_RANGE), posY - (ATTACK_RANGE / 2), posZ - (ATTACK_RANGE), posX + (ATTACK_RANGE), posY + (ATTACK_RANGE), posZ + (ATTACK_RANGE)).expand(ATTACK_RANGE, ATTACK_RANGE, ATTACK_RANGE));
	    for(int i=0; i<=ls.size(); i++) {
	    	// YOU WILL BE PUNISHED!
	    	try {
	    	    EntityLivingBase toAttack = ls.get(i);
	    	    if(!toAttack.equals(this)) 
	    	    	toAttack.attackEntityFrom(DamageSource.causeMobDamage(this), toAttack.getHealth() - 1.5f);
	    	} catch(Exception ex) {
	    		//System.err.println("For heaven's sake... -w-");
	    	}
	    }
		
		
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
		this.currStat = stats.ATTACKED;
		return super.attackEntityFrom(par1DamageSource, par2);
	}

}