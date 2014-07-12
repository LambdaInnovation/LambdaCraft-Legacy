/** 
 * Copyright (c) LambdaCraft Modding Team, 2013
 * 版权许可：LambdaCraft 制作小组， 2013.
 * http://lambdacraft.half-life.cn/
 * 
 * LambdaCraft is open-source. It is distributed under the terms of the
 * LambdaCraft Open Source License. It grants rights to read, modify, compile
 * or run the code. It does *NOT* grant the right to redistribute this software
 * or its modifications in any form, binary or source, except if expressively
 * granted by the copyright holder.
 *
 * LambdaCraft是完全开源的。它的发布遵从《LambdaCraft开源协议》。你允许阅读，修改以及调试运行
 * 源代码， 然而你不允许将源代码以另外任何的方式发布，除非你得到了版权所有者的许可。
 */
package cn.lambdacraft.mob.proxy;

import net.minecraftforge.client.MinecraftForgeClient;
import cn.lambdacraft.mob.block.tile.TileSentryRay;
import cn.lambdacraft.mob.client.model.ModelHLZombie;
import cn.lambdacraft.mob.client.model.ModelHeadcrab;
import cn.lambdacraft.mob.client.model.ModelSnark;
import cn.lambdacraft.mob.client.renderer.RenderAlienSlave;
import cn.lambdacraft.mob.client.renderer.RenderBarnacle;
import cn.lambdacraft.mob.client.renderer.RenderHoundeye;
import cn.lambdacraft.mob.client.renderer.RenderSentryRay;
import cn.lambdacraft.mob.client.renderer.RenderShockwave;
import cn.lambdacraft.mob.client.renderer.RenderSnark;
import cn.lambdacraft.mob.client.renderer.RenderTurret;
import cn.lambdacraft.mob.client.renderer.RenderVortigauntRay;
import cn.lambdacraft.mob.entity.EntityAlienSlave;
import cn.lambdacraft.mob.entity.EntityBarnacle;
import cn.lambdacraft.mob.entity.EntityHLZombie;
import cn.lambdacraft.mob.entity.EntityHeadcrab;
import cn.lambdacraft.mob.entity.EntityHoundeye;
import cn.lambdacraft.mob.entity.EntitySentry;
import cn.lambdacraft.mob.entity.EntityShockwave;
import cn.lambdacraft.mob.entity.EntitySnark;
import cn.lambdacraft.mob.entity.EntityVortigauntRay;
import cn.lambdacraft.mob.register.CBCMobItems;
import cn.liutils.api.client.render.LIRenderMob;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

/**
 * @author WeAthFolD
 * 
 */
public class ClientProxy extends Proxy {

	@Override
	public void init() {
		RenderingRegistry.registerEntityRenderingHandler(EntitySnark.class,
				new LIRenderMob(new ModelSnark(), 0.2F));
		RenderingRegistry.registerEntityRenderingHandler(EntityHeadcrab.class,
				new LIRenderMob(new ModelHeadcrab(), 0.4F));
		RenderingRegistry.registerEntityRenderingHandler(EntityBarnacle.class, new RenderBarnacle());
		RenderingRegistry.registerEntityRenderingHandler(EntityHLZombie.class, new LIRenderMob(new ModelHLZombie(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityHoundeye.class, new RenderHoundeye());
		RenderingRegistry.registerEntityRenderingHandler(EntitySentry.class, new RenderTurret());
		RenderingRegistry.registerEntityRenderingHandler(EntityShockwave.class, new RenderShockwave());
		RenderingRegistry.registerEntityRenderingHandler(EntityVortigauntRay.class, new RenderVortigauntRay());
		RenderingRegistry.registerEntityRenderingHandler(EntityAlienSlave.class, new RenderAlienSlave());
		
		MinecraftForgeClient.registerItemRenderer(CBCMobItems.weapon_snark, new RenderSnark());
		ClientRegistry.bindTileEntitySpecialRenderer(TileSentryRay.class,
				new RenderSentryRay());
	}
	
	@Override
	public void preInit() {
	}
	
}
