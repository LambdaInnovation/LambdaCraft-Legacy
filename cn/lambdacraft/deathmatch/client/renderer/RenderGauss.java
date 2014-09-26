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
package cn.lambdacraft.deathmatch.client.renderer;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import cn.lambdacraft.core.prop.ClientProps;
import cn.lambdacraft.deathmatch.client.model.ModelGauss;
import cn.lambdacraft.deathmatch.register.DMItems;
import cn.liutils.api.client.model.IItemModel;
import cn.liutils.api.client.render.RenderModelItem;

/**
 * 果然父类是很重要的
 * @author WeAthFolD
 *
 */
public class RenderGauss extends RenderModelItem {
	
	Tessellator t = Tessellator.instance;;
	static IItemModel model = new ModelGauss();

	public RenderGauss() {
		super(model, ClientProps.GAUSS_ITEM_PATH);
		this.setInventorySpin(false);
		this.setOffset(0.0F, 1.5F, 0.0F);
		this.setInvScale(0.8F);
		this.setStdRotation(0F, 180F, 0F);
		this.setInvOffset(-0.588F, 0.114F);
		this.setInvScale(0.618F);
		this.setEquipOffset(.78F, .0F, 0F);
		this.setInvRotation(-41.F, -76.744F, 0.0F);
	}
	
	@Override
	protected float getModelAttribute(ItemStack item, EntityLivingBase entity) {
		if(!(entity instanceof EntityPlayer)) return 0F;
		return DMItems.gauss.getRotation(item);
	}

}
