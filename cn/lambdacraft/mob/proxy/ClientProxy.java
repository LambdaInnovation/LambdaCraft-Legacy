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
import cn.liutils.core.client.register.LISoundRegistry;
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
		
		MinecraftForgeClient.registerItemRenderer(CBCMobItems.weapon_snark.itemID, new RenderSnark());
		ClientRegistry.bindTileEntitySpecialRenderer(TileSentryRay.class,
				new RenderSentryRay());
	}
	
	@Override
	public void preInit() {
		for (String s : SND_MOBS) {
			LISoundRegistry.addSoundPath("lambdacraft", "mobs/" + s);
		}
		LISoundRegistry.addSoundWithVariety("lambdacraft", "mobs/he_alert", 3);
		LISoundRegistry.addSoundWithVariety("lambdacraft", "mobs/he_attack", 3);
		LISoundRegistry.addSoundWithVariety("lambdacraft", "mobs/he_blast", 3);
		LISoundRegistry.addSoundWithVariety("lambdacraft", "mobs/he_die", 3);
		LISoundRegistry.addSoundWithVariety("lambdacraft", "mobs/he_hunt", 4);
		LISoundRegistry.addSoundWithVariety("lambdacraft", "mobs/he_idle", 4);
		LISoundRegistry.addSoundWithVariety("lambdacraft", "mobs/he_pain", 5);
		LISoundRegistry.addSoundWithVariety("lambdacraft", "mobs/tu_die", 3);
		LISoundRegistry.addSoundWithVariety("lambdacraft", "mobs/tu_active", 2);
		LISoundRegistry.addSoundWithVariety("lambdacraft", "mobs/slv_alert", 3);
		LISoundRegistry.addSoundWithVariety("lambdacraft", "mobs/slv_die", 2);
		LISoundRegistry.addSoundWithVariety("lambdacraft", "mobs/slv_pain", 2);
		LISoundRegistry.addSoundWithVariety("lambdacraft", "mobs/slv_word", 8);
		LISoundRegistry.addSoundPath("lambdacraft", "mobs/zapa");
		LISoundRegistry.addSoundPath("lambdacraft", "mobs/zapd");
	}
	
	public static final String SND_MOBS[] = { "sqk_blast", "sqk_hunta",
		"sqk_huntb", "sqk_huntc", "sqk_die", "sqk_deploy", "hc_attacka", "hc_attackb",
		"hc_attackc", "hc_idlea", "hc_idleb", "hc_idlec", "hc_idled", "hc_idlee",
		"hc_diea", "hc_dieb", "hc_paina", "hc_painb", "hc_painc",
		"bcl_tongue", "bcl_chewa", "bcl_chewb", "bcl_chewc", "bcl_alert", "bcl_bite", "zo_alerta",
		"zo_alertb", "zo_alertc", "zo_attacka", "zo_attackb", "zo_claw_missa", "zo_claw_missb", 
		"zo_claw_strikea", "zo_claw_strikeb", "zo_claw_strikec", "zo_idlea", "zo_idleb", "zo_idlec",
		"zo_paina", "zo_painb", "zo_moan_loopa", "zo_moan_loopb", "zo_moan_loopc", "zo_moan_loopd", "zo_diea", "zo_dieb", "zo_diec", "tu_spinup",
		"tu_spindown", "tu_ping", "tu_fire", "tu_deploy", "tu_alert" };
	
}
