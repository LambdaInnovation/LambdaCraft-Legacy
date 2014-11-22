package cn.lambdacraft.deathmatch.entity;

import cn.weaponmod.api.WeaponHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityDisplacerCannon extends EntityThrowable {

	public EntityDisplacerCannon(World par1World, EntityPlayer par2EntityPlayer) {
		super(par1World, par2EntityPlayer);
	}

	@Override
	protected void onImpact(MovingObjectPosition var1) {
		/* If the entity itself attacked the player... */
		WeaponHelper.Explode(worldObj, this, 1.0F, 3.0F, posX, posY, posZ, 30);
		this.setDead();
	}

}
