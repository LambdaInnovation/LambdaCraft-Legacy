package cn.lambdacraft.mob.entity;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import cn.liutils.api.entity.LIEntityMob;
import cn.lambdacraft.core.prop.ClientProps;

/* 表示加了CASE后代码行数瞬间上去了 -w- */

/**
 * @author jiangyue
 * The entity for mob big momoa.
 * - Attaching damage: 5
 * - Moving speed: 0.9
 */
public class EntityBigMomoa extends LIEntityMob implements IBossDisplayData {
	/* IN THIS CLASS EVERYTHING INCLUDING SPAWNING CHILD IS ABLE TO CHANGE THE PARAMETRE.
	 * SO IN DIFFERENT GAME TYPE MIGHT BE DIFFER.
	 */
	/* About 2.5 Hearts */
	protected final float ATTACK_DAMAGE = 5f;
	protected final float MOVING_SPEED = 0.9f;
	/* About 50 Hearts */
	protected final float HEALTH_POINT = 100f;
	protected int LENGTH_TO_SPAWN_CHILD = 400;
	protected int ATTACK_RANGE = 5;
	protected final double FOLLOW_RANGE = 10;

	protected int diff;	
	// For logging, and I REALLY CAN'T STAND THE BAS. TYPE OF JAVA
	public String[] eventStat = new String[300];
	public String[] eventTime = new String[300];
	public int topL = 0;
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
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3f);
				this.ATTACK_RANGE = 4;
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(70);
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5f);
				this.LENGTH_TO_SPAWN_CHILD = 1000;
				break;
			case 2:
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4f);
				this.ATTACK_RANGE = 5;
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(85);
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.65f);
				this.LENGTH_TO_SPAWN_CHILD = 700;
				break;
			case 3:
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(6f);
				this.ATTACK_RANGE = 7;
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(120);
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.7f);
				this.LENGTH_TO_SPAWN_CHILD = 400;
				break;
		}
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
		try {
		    this.eventStat[topL] = "SPAWNING_CHILD";
		    this.eventTime[topL] = String.valueOf(Minecraft.getSystemTime());
		    topL ++;
		} catch(Exception ex) {
			topL = 0;
		}
		System.out.println("SpawnChildCount is now " + this.spawnChildCount + ".");
		this.spawnChildCount ++;
		if(this.spawnChildCount % LENGTH_TO_SPAWN_CHILD == 0) {
			spawnChildCount = 0;
			EntityHeadcrab entityBabyHeadcrab = new EntityBabyHeadcrab(worldObj);
			entityBabyHeadcrab.setPosition(posX + 2, posY + 2, posZ);
			worldObj.spawnEntityInWorld(entityBabyHeadcrab);
		}
	}
	
	@Override
	protected double getMaxHealth2() {
		return HEALTH_POINT;
	}

	@Override
	protected double getFollowRange() {
		return FOLLOW_RANGE;
	}

	@Override
	/**
	 * Seems to drop something -w-
	 */
	protected Item getDropItem() {
		return null;
	}
	
	@Override
	protected double getKnockBackResistance() {
		// Gotcha.
		return 2;
	}
	
	@Override
	protected double getMoveSpeed() {
		return MOVING_SPEED;
	}
	
	@Override
	protected double getAttackDamage() {
		return ATTACK_DAMAGE;
	}
	
	@Override
	public ResourceLocation getTexture() {
		return ClientProps.BIG_MOMOA_MOB_PATH;
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
			    try {
				    this.eventStat[topL] = "ATTACKING_ENTITY";
				    this.eventTime[topL] = String.valueOf(Minecraft.getSystemTime());
				    topL ++;
				} catch(Exception ex) {
					topL = 0;
				}
			} else
				this.attackEntityInRange(this.ATTACK_RANGE);
		}
		
	}
	
	private void attackEntityInRange(int ATTACK_RANGE) {
		try {
		    this.eventStat[topL] = "ATTACKING_ENTITY_IN_RANGE";
		    this.eventTime[topL] = String.valueOf(Minecraft.getSystemTime());
		    topL ++;
		} catch(Exception ex) {
			topL = 0;
		}
		System.err.println("[TESTING] {INVOKED}");
	    List<EntityLiving> ls = worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(posX - (ATTACK_RANGE), posY - (ATTACK_RANGE / 2), posZ - (ATTACK_RANGE), posX + (ATTACK_RANGE), posY + (ATTACK_RANGE), posZ + (ATTACK_RANGE)).expand(ATTACK_RANGE, ATTACK_RANGE, ATTACK_RANGE));
	    for(int i=0; i<=ls.size(); i++) {
	    	// YOU WILL BE PUNISHED!
	    	try {
	    	    EntityLivingBase toAttack = ls.get(i);
	    	    if(!toAttack.equals(this)) 
	    	    	toAttack.attackEntityFrom(DamageSource.causeMobDamage(this), toAttack.getHealth() - 1.5f);
	    	} catch(Exception ex) {
	    		System.err.println("For heaven's sake... -w-");
	    	}
	    }
		
		
	}

}