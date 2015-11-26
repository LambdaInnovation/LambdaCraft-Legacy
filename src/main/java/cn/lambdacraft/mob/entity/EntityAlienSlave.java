/** 
 * Copyright (c) LambdaCraft Modding Team, 2013
 * 鐗堟潈璁稿彲锛歀ambdaCraft 鍒朵綔灏忕粍锛�2013.
 * http://lambdacraft.half-life.cn/
 * 
 * LambdaCraft is open-source. It is distributed under the terms of the
 * LambdaCraft Open Source License. It grants rights to read, modify, compile
 * or run the code. It does *NOT* grant the right to redistribute this software
 * or its modifications in any form, binary or source, except if expressively
 * granted by the copyright holder.
 *
 * LambdaCraft鏄畬鍏ㄥ紑婧愮殑銆傚畠鐨勫彂甯冮伒浠庛�LambdaCraft寮�簮鍗忚銆嬨�浣犲厑璁搁槄璇伙紝淇敼浠ュ強璋冭瘯杩愯
 * 婧愪唬鐮侊紝 鐒惰�浣犱笉鍏佽灏嗘簮浠ｇ爜浠ュ彟澶栦换浣曠殑鏂瑰紡鍙戝竷锛岄櫎闈炰綘寰楀埌浜嗙増鏉冩墍鏈夎�鐨勮鍙�
 */
package cn.lambdacraft.mob.entity;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import cn.lambdacraft.core.prop.ClientProps;
import cn.lambdacraft.mob.register.CBCMobItems;
import cn.liutils.api.entity.LIEntityMob;
import cn.liutils.api.util.BlockPos;
import cn.liutils.api.util.GenericUtils;

/**
 * @author WeAthFolD
 *
 */
public class EntityAlienSlave extends LIEntityMob {
	public final static float MAX_HEALTH = 10.0f;
	public final static float MOVE_SPEED = 0.65f;
	public final static float ATTACK_DAMAGE = 5f;
	
	public boolean isCharging;
	public int chargeTick;
	private int lastAttackTick;
	
	boolean lastTickFleeing = false;
	
	public HashSet<Vec3> electrolyze_left = new HashSet(), electrolyze_right = new HashSet();
	
	private static IEntitySelector selector = new IEntitySelector() {

		@Override
		public boolean isEntityApplicable(Entity entity) {
			return entity instanceof EntityPlayer || entity instanceof EntityVillager;
		}
		
	};
	
	/**
	 * @param par1World
	 */
	public EntityAlienSlave(World par1World) {
		super(par1World);
		this.setAbsorptionAmount(MAX_HEALTH);
	}
	
	@Override
	public void entityInit() {
		super.entityInit();
		this.setSize(1.5F, 1.8F);
		dataWatcher.addObject(20, Byte.valueOf((byte) 0));
		dataWatcher.addObject(21, Short.valueOf((short) 0));
		dataWatcher.addObject(22, Integer.valueOf(0));
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		
		//Sync&Calculate Charging
		if(worldObj.isRemote) {
			boolean preisCharging = isCharging;
			if(!preisCharging) {
				chargeTick = dataWatcher.getWatchableObjectShort(21);
				isCharging = dataWatcher.getWatchableObjectByte(20) == 1;
				if(isCharging) {
					this.updateElectrolyzes();
				}
			} else isCharging = dataWatcher.getWatchableObjectByte(20) == 1;
			entityToAttack = worldObj.getEntityByID(dataWatcher.getWatchableObjectInt(22));
		} else {
			dataWatcher.updateObject(20, isCharging? (byte)1 : (byte)0);
			dataWatcher.updateObject(21, (short)chargeTick);
			dataWatcher.updateObject(22, entityToAttack == null ? 0 : entityToAttack.getEntityId());
			
			//Path movement
			if(this.entityToAttack != null ) {
				double distanceSq = this.getDistanceSqToEntity(entityToAttack);
				if(distanceSq > 25.0) {
					//original pathNavigate
					lastTickFleeing = false;
					if(lastTickFleeing || rand.nextInt(20) == 0)
						this.setPathToEntity(worldObj.getPathEntityToEntity(this, entityToAttack, 16.0F, true, false, false, true));
				} else if(!lastTickFleeing || rand.nextInt(20) == 0){
					//else try to get away from the player
					lastTickFleeing = true;
					int x = (int) (3 * posX - 2 * entityToAttack.posX), y = (int) (3 * posY - 2 * entityToAttack.posY), z = (int) (3 * posZ - 2 * entityToAttack.posZ);
					x += rand.nextInt(5) - 2;
					y += rand.nextInt(3) - 1;
					z += rand.nextInt(5) - 2;
					this.setPathToEntity(worldObj.getEntityPathToXYZ(this, x , y, z, 16.0F, true, false, false, true));
				}
			} else {
				if(this.rand.nextInt(30) == 0 || this.fleeingTick > 0) {
					this.updateWanderPath();
				}
			}
		}
		
		if(isCharging && this.entityToAttack != null) {
			if(++chargeTick >= 30) {
				isCharging = false;
				if(worldObj.isRemote)
					worldObj.spawnEntityInWorld(new EntityVortigauntRay(worldObj, this, entityToAttack));
				boolean b = (GenericUtils.traceBetweenEntities(this, entityToAttack) == null);
				// System.out.println(b);
				if(b) entityToAttack.attackEntityFrom(DamageSource.causeMobDamage(this), 6);
				// else System.out.println();
				this.playSound("lambdacraft:mobs.zapa", 0.5F, 1.0F);
				this.playSound(GenericUtils.getRandomSound("lambdacraft:weapons.electro", 3), 0.5F, 1.0F);
				lastAttackTick = ticksExisted;
				electrolyze_left.clear();
				electrolyze_right.clear();
			}
		}
		
	}
	
	private void updateElectrolyzes() {
		Set<BlockPos> set_left = GenericUtils.getBlocksWithinAABB(worldObj, AxisAlignedBB.getBoundingBox(posX - 2, posY - 1, posZ - 2, posX + 2, posY + 2, posZ + 2));
		Iterator<BlockPos> iterator = set_left.iterator();
		int cnt = 0;
		while(iterator.hasNext() && ++cnt < 10) {
			BlockPos bp = iterator.next();
			HashSet set = cnt < 5 ? electrolyze_left : electrolyze_right;
			set.add(Vec3.createVectorHelper
					(bp.x + rand.nextDouble() - 0.5 - posX, bp.y + rand.nextDouble() - 0.5 - posY, bp.z + rand.nextDouble() - 0.5 - posZ));
		}
	}

	@Override
	protected void attackEntity(Entity par1Entity, float par2) {
		/**
		 * Check if the distance is between 3 and 16 and more than 40 ticks passed from ticksExisted and randomed and not running
		 */
		if(par2 >= 3.0F && par2 <= 16.0F && ticksExisted - lastAttackTick > 40 && rand.nextInt(5) == 0 && !isCharging) {
			this.playSound("lambdacraft:mobs.zapd", 0.5F, 1.0F);
			isCharging = true;
//			super.attackEntity(par1Entity, par2);
			chargeTick = 0;
		} else {
			super.attackEntity(par1Entity, par2);
		}
	}
	
	@Override
    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
    {
		boolean b = super.attackEntityFrom(par1DamageSource, par2);
		if(entityToAttack != null && entityToAttack instanceof EntityAlienSlave) {
			this.entityToAttack = this.findPlayerToAttack();
		}
		
    	if(b && (par2 > 6 || rand.nextInt(4) == 0)) {
    		this.electrolyze_left.clear();
    		this.electrolyze_right.clear();
    		this.isCharging = false;
    		lastAttackTick = ticksExisted;
    		return true;
    	} else return false;
    }
	
    /**
     * Disables a mob's ability to move on its own while true.
     */
	@Override
    protected boolean isMovementCeased()
    {
        return true;
    }
	
	@Override
	protected boolean isMovementBlocked()
	{
		return isCharging;
	}
	
    @Override
	public EntityItem func_145778_a(Item par1, int par2, float par3)
    {
        return this.entityDropItem(new ItemStack(par1, par2, 4), par3);
    }
    
    @Override
    public Item getDropItem() {
    	return CBCMobItems.dna;
    }
	
	@Override
    protected Entity findPlayerToAttack()
    {
    	Entity e = super.findPlayerToAttack();
    	if(e != null && rand.nextInt(3) == 0) {
    		this.playSound(GenericUtils.getRandomSound("lambdacraft:mobs.slv_alert", 3), 0.5F, 1.0F);
    	}
    	return e;
    }
	
	@Override
    protected String getLivingSound()
    {
    	return GenericUtils.getRandomSound("lambdacraft:mobs.slv_word", 8);
    }
    
    /**
     * Returns the sound this mob makes when it is hurt.
     */
	@Override
    protected String getHurtSound()
    {
        return GenericUtils.getRandomSound("lambdacraft:mobs.slv_pain", 2);
    }
    
	@Override
    protected String getDeathSound()
    {
        return GenericUtils.getRandomSound("lambdacraft:mobs.slv_die", 2);
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
		return 4;
	}

	@Override
	protected double getAttackDamage() {
		return ATTACK_DAMAGE;
	}

	@Override
	public ResourceLocation getTexture() {
		return ClientProps.VORTIGAUNT_PATH;
	}


}
