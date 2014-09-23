package cn.lambdacraft.mob.entity;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
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
	protected final int LENGTH_TO_SPAWN_CHILD = 20000;
	protected final int ATTACK_RANGE = 5;
	
	private int SpawnChildCount = 0;

	@Override
	protected double getMaxHealth2() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected double getFollowRange() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	/**
	 * Seems to drop something -w-
	 */
	protected Item getDropItem() {
		// TODO Drop something?
		return null;
	}
	
	@Override
	protected double getKnockBackResistance() {
		// TODO Auto-generated method stub
		return 0;
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
	public void onUpdate() {
		this.parseSpawnChild();
	}
	
	@Override
	protected void attackEntity(Entity par1Entity, float par2) {
		/* NEVER SAY NO. */
		this.parseSpawnChild();
		// Randomly decide attack method #1 or #2
		java.util.Random rand = new java.util.Random(); 
		boolean AttackModeOne = rand.nextBoolean();
		if(AttackModeOne)
			super.attackEntity(par1Entity, par2);
		else
			this.attackEntityInRange(ATTACK_RANGE);
	}
	
	private void attackEntityInRange(int aTTACK_RANGE2) {
		// TODO Get Everything and attack
		
	}
	
	
	
	
	

	/* Things already done */
	public EntityBigMomoa(World par1World) {
		super(par1World);
	}

	protected void parseSpawnChild() {
		/* Request once while parsing spawn child: Never mind. */
		this.SpawnChildCount ++;
		if(this.SpawnChildCount % LENGTH_TO_SPAWN_CHILD == 0) {
			SpawnChildCount = 0;
			EntityHeadcrab entityHeadcrab = new EntityHeadcrab(this.worldObj);
			entityHeadcrab.setPosition(this.posX, this.posY, this.posZ);
		}
	}
	
	@Override
	public ResourceLocation getTexture() {
		return ClientProps.BIG_MOMOA_MOB_PATH;
	}

}
