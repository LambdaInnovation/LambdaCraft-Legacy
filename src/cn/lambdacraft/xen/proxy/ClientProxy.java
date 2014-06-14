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
package cn.lambdacraft.xen.proxy;

import net.minecraftforge.client.MinecraftForgeClient;
import cn.lambdacraft.xen.client.EntityXenPortalFX;
import cn.lambdacraft.xen.client.model.ModelXenLight;
import cn.lambdacraft.xen.client.renderer.RenderItemPortal;
import cn.lambdacraft.xen.client.renderer.RenderTileXenAmethyst;
import cn.lambdacraft.xen.client.renderer.RenderXenPortal;
import cn.lambdacraft.xen.client.renderer.RenderXenPortalFX;
import cn.lambdacraft.xen.register.XENBlocks;
import cn.lambdacraft.xen.tileentity.TileEntityXenAmethyst;
import cn.lambdacraft.xen.tileentity.TileEntityXenLight;
import cn.lambdacraft.xen.tileentity.TileEntityXenPortal;
import cn.liutils.api.client.render.RenderTileEntityModel;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

/**
 * @author Administrator
 *
 */
public class ClientProxy extends Proxy {
	@Override
	public void init() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityXenPortal.class, new RenderXenPortal());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityXenLight.class, new RenderTileEntityModel(new ModelXenLight()));
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityXenAmethyst.class, new RenderTileXenAmethyst());
		RenderingRegistry.registerEntityRenderingHandler(EntityXenPortalFX.class, new RenderXenPortalFX());
		MinecraftForgeClient.registerItemRenderer(XENBlocks.portal.blockID, new RenderItemPortal());
	}
}

