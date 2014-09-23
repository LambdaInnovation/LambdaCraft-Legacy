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
package cn.lambdacraft.crafting.proxy;

import net.minecraftforge.client.MinecraftForgeClient;
import cn.lambdacraft.core.prop.ClientProps;
import cn.lambdacraft.crafting.block.tile.TileWire;
import cn.lambdacraft.crafting.client.renderer.RenderSpray;
import cn.lambdacraft.crafting.client.renderer.RenderWire;
import cn.lambdacraft.crafting.entity.EntitySpray;
import cn.lambdacraft.crafting.register.CBCItems;
import cn.lambdacraft.deathmatch.client.model.ModelBattery;
import cn.liutils.api.client.render.RenderModelItem;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

/**
 * @author WeAthFolD
 * 
 */
public class ClientProxy extends Proxy {

	@Override
	public void init() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileWire.class,
				new RenderWire());
		RenderingRegistry.registerEntityRenderingHandler(EntitySpray.class,
				new RenderSpray());
		MinecraftForgeClient.registerItemRenderer(CBCItems.battery, new RenderModelItem(new ModelBattery(), ClientProps.BATTERY_PATH).setInventorySpin(false)
				.setEquipOffset(0.4F, 0.0F, 0.0F).setScale(.6F).setInvScale(1.5F).setOffset(-0.25F, 1.5F, -0.25F).setInvOffset(5.0F, 7.0F));
	}
	
}
