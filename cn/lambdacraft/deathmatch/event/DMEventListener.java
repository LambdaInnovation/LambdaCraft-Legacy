/**
 * 
 */
package cn.lambdacraft.deathmatch.event;

import cn.lambdacraft.terrain.ModuleTerrain;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

/**
 * @author WeathFolD
 *
 */
public class DMEventListener {

	@SubscribeEvent
	/**
	 * 于是Xen掉落伤害的问题终于被我们愉快的解决了
	 */
	public void onFall(LivingAttackEvent event) {
		if(event.entityLiving instanceof EntityPlayer && event.source == DamageSource.fall) {
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			
			if(player.worldObj.provider.dimensionId == ModuleTerrain.xenIslandDimensionID 
					|| player.worldObj.provider.dimensionId == ModuleTerrain.xenContinentDimensionID) {
				player.attackTime = -1;
				player.attackEntityFrom(DamageSource.generic, event.ammount * 0.4F);
				event.setCanceled(true);
			}
		}
	}
}
