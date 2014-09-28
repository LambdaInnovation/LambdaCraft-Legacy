package cn.lambdacraft.mob.entity;

import java.util.List;

import cn.lambdacraft.core.prop.GeneralProps;
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
 * 
 * @author jiangyue
 *
 */
public class EntityBullToxic extends Entity {
	protected double targetX, targetY, targetZ;
	protected double ATTACK_RANGE = 4.0f;

	public EntityBullToxic(World par1World) {
		super(par1World);
		// TODO Auto-generated constructor stub
	}

	public Entity setPosAndTargXYZ(double x, double y, double z, double tX, double tY, double tZ) {
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		this.targetX = tX;
		this.targetY = tY;
		this.targetZ = tZ;
		return this;
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		// FIXME: Insert code instead of setting position in order to 'approach' player
		this.setPosition(targetX, targetY, targetZ);
		double distance = Math.sqrt(Math.abs(targetX - this.posX)*Math.abs(targetX - this.posX) + Math.abs(targetX - this.posX)*Math.abs(targetY - this.posY) + Math.abs(targetZ - this.posZ)*Math.abs(targetZ - this.posZ));
		if(distance <= 1.5)
			this.affectStart();
		// The mission is OVER, good luck, bro :P
		this.setDead();
	}
	
	
	
	private void affectStart() {
		List<EntityLiving> ls = worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(posX - (ATTACK_RANGE), posY - (ATTACK_RANGE / 2), posZ - (ATTACK_RANGE), posX + (ATTACK_RANGE), posY + (ATTACK_RANGE), posZ + (ATTACK_RANGE)).expand(ATTACK_RANGE, ATTACK_RANGE, ATTACK_RANGE));
	    for(int i=0; i<=ls.size(); i++) {
	    	// YOU WILL BE PUNISHED!
	    	try {
	    	    EntityLivingBase toAttack = ls.get(i);
	    	    if(!toAttack.equals(this) && toAttack.getEntityId() != GeneralProps.ENT_ID_MOB_BULL) {
	    	    	toAttack.addPotionEffect(new PotionEffect(Potion.harm.getId(), 2));
	    	    	toAttack.addPotionEffect(new PotionEffect(Potion.poison.getId(), 2));
	    	    }
	    	} catch(Exception ex) {
	    		System.err.println("For heaven's sake... -w-");
	    	}
	    }
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

}
