package cn.lambdacraft.mob.entity;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

/**
 * The Entity Class for toxic that the bull spit out.
 * @author jiangyue
 *
 */
public class EntityToxic extends Entity {

	private int targetX;
	private int targetY;
	private int targetZ;
	
	private final int ATTACK_RANGE = 5;
	
	/**
	 * The constructor for EntityToxic.
	 * @param par1World The world to spawn.
	 * @param target The target to attack.
	 */
	public EntityToxic(World par1World, Entity target) {
		super(par1World);
		this.targetX = (int) target.posX;
		this.targetY = (int) target.posY;
		this.targetZ = (int) target.posZ;
	    
	}

	public EntityToxic(World worldObj, Entity target, double posX, double posY, double posZ) {
		this(worldObj, target);
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
	}

	@Override
	protected void entityInit() {
		
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound var1) {
		
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound var1) {
		
	}
	
	@Override
	/**
	 * Called every one tick to determine where the target is
	 */
	public void onUpdate() {
		/** Look if near object. */
		if(getDistance() <= 3)
			this.effect();
		else {
			this.setPosition((targetX - posX) / 8 + posX,
					         (targetY - posY) / 8 + posY, 
					         (targetZ - posZ) / 8 + posZ);
		}
        super.onUpdate();
    }

	private void effect() {
		List<EntityLiving> ls = worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(posX - (ATTACK_RANGE), posY - (ATTACK_RANGE / 2), posZ - (ATTACK_RANGE), posX + (ATTACK_RANGE), posY + (ATTACK_RANGE), posZ + (ATTACK_RANGE)).expand(ATTACK_RANGE, ATTACK_RANGE, ATTACK_RANGE));
	    for(int i=0; i<=ls.size(); i++) {
	    	try {
	    	    EntityLivingBase toAttack = ls.get(i);
	    	    if(!toAttack.equals(this)) 
	    	    	toAttack.addPotionEffect(new PotionEffect(Potion.harm.getId(), 20));
	    	} catch(Exception ex) {
	    		/* PlaceHolder. */
	    	}
	    }
	    this.kill();
		
	}

	/**
	 * Get distance from this to target.
	 * @return The distance itself.
	 */
	private int getDistance() {
		return (int) Math.sqrt(pow(targetX - posX, 2) + pow(targetY - posY, 2) + pow(targetZ - posZ, 2));
	}

	private int pow(double d, int ii) {
		double s = 1;
		for(int i=1; i<=ii; i++) {
			s *= d;
		}
		return (int) s;
	}
	

}
