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
package cn.lambdacraft.terrain.proxy;

import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import cn.lambdacraft.terrain.client.EntityXenPortalFX;
import cn.lambdacraft.terrain.client.model.ModelXenLight;
import cn.lambdacraft.terrain.client.renderer.RenderItemPortal;
import cn.lambdacraft.terrain.client.renderer.RenderTileXenAmethyst;
import cn.lambdacraft.terrain.client.renderer.RenderXenPortal;
import cn.lambdacraft.terrain.client.renderer.RenderXenPortalFX;
import cn.lambdacraft.terrain.register.XenBlocks;
import cn.lambdacraft.terrain.tileentity.TileEntityXenAmethyst;
import cn.lambdacraft.terrain.tileentity.TileEntityXenLight;
import cn.lambdacraft.terrain.tileentity.TileEntityXenPortal;
import cn.liutils.api.client.render.RenderTileEntityModel;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

/**
 * @author Administrator
 *
 */
public class TRClientProxy extends TRCommonProxy {
	@Override
	public void init() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityXenPortal.class, new RenderXenPortal());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityXenLight.class, new RenderTileEntityModel(new ModelXenLight()));
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityXenAmethyst.class, new RenderTileXenAmethyst());
		RenderingRegistry.registerEntityRenderingHandler(EntityXenPortalFX.class, new RenderXenPortalFX());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(XenBlocks.xenPortal), new RenderItemPortal());
	}
}

