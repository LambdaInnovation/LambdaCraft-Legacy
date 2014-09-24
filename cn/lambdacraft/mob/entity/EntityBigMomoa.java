package cn.lambdacraft.mob.entity;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import cn.liutils.api.entity.LIEntityMob;
import cn.lambdacraft.core.prop.ClientProps;

/**
 * @author jiangyue
 * The entity for mob big momoa.
 * - Attaching damage: 7
 * - Moving speed: 0.45
 */
public class EntityBigMomoa extends LIEntityMob {
	protected final float ATTACK_DAMAGE = 7f;
	protected final float MOVING_SPEED = 0.45f;
	protected final float HEALTH_POINT = 100f;
	protected final int LENGTH_TO_SPAWN_CHILD = 3000;
	protected final int ATTACK_RANGE = 5;
	protected final double FOLLOW_RANGE = 20;
	
	private int spawnChildCount = 1;

	@Override
	public void onUpdate() {
		super.onUpdate();
		this.parseSpawnChild();
	}
	
	@Override
	protected double getMaxHealth2() {
		// TODO Placeholder
		return HEALTH_POINT;
	}

	@Override
	protected double getFollowRange() {
		// TODO Auto-generated method stub
		return FOLLOW_RANGE;
	}

	@Override
	/**
	 * Seems to drop something -w-
	 */
	protected Item getDropItem() {
		// TODO Drop something?
		return null;
	}
	
	
	
	
	
	
	
	

	/* Things already done */
	
	@Override
	protected double getKnockBackResistance() {
		// Gotcha.
		return 20;
	}
	
	public EntityBigMomoa(World par1World) {
		super(par1World);
		// Initializing.
		this.setHealth(HEALTH_POINT);
	}

	protected void parseSpawnChild() {
		/* Request once while parsing spawn child: Never mind. */
		// Minecraft.getMinecraft().getSystemTime();
		System.out.println("SpawnChildCount is now " + this.spawnChildCount + ".");
		this.spawnChildCount ++;
		if(this.spawnChildCount % LENGTH_TO_SPAWN_CHILD == 0) {
			spawnChildCount = 0;
			EntityHeadcrab entityHeadcrab = new EntityHeadcrab(worldObj);
			entityHeadcrab.setPosition(posX + 2, posY + 2, posZ);
			worldObj.spawnEntityInWorld(entityHeadcrab);
		}
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
	protected void attackEntity(Entity par1Entity, float par2) {
		boolean TEST = false;
		
		if (!TEST) {
			/* NEVER SAY NO. */
			this.parseSpawnChild();
			// Randomly decide attack method #1 or #2
			java.util.Random rand = new java.util.Random();
			boolean AttackModeOne = rand.nextBoolean();
			System.err.println(AttackModeOne);
			// if (AttackModeOne)
			//	 super.attackEntity(par1Entity, par2);
			// else
				this.attackEntityInRange(this.ATTACK_RANGE);
		}
		
	    // par1Entity.setFire(5);
	}
	
	private void attackEntityInRange(int aTTACK_RANGE2) {
		// TODO Get Everything and attack
	    List<EntityLiving> ls = worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(posX - 16, posY - 16, posZ - 16, posX + 16, posY + 16, posZ + 16).expand(16, 16, 16));
	    for(int i=0; i<=ls.size(); i++) {
	    	// YOU WILL BE PUNISHED!
	    	try {
	    	    EntityLiving toAttack = ls.get(i);
	    	    if(!toAttack.equals(this)) toAttack.setHealth(toAttack.getHealth() - 1.5f);
	    	} catch(Exception ex) {
	    		System.err.println("For heaven's sake... -w-");
	    	}
	    }
		
		
	}
	
	@Override
	public ResourceLocation getTexture() {
		return ClientProps.BIG_MOMOA_MOB_PATH;
	}

}
