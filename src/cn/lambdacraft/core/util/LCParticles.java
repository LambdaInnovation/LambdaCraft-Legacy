/**
 * 
 */
package cn.lambdacraft.core.util;

import cn.lambdacraft.xen.client.EntityXenPortalFX;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

/**
 * @author Administrator
 *
 */
public class LCParticles {

	public static void spawnParticle(World world, String name, double a, double b, double c, double d, double e, double f) {
		Entity ent = null;
		if(name.equals("xenportal")) {
			ent = new EntityXenPortalFX(world, a, b, c, d, e, f);
		} else {
			
		}
		if(ent != null)
			world.spawnEntityInWorld(ent);
	}

}
