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
package cn.lambdacraft.mob.client.renderer;

import cn.lambdacraft.core.prop.ClientProps;
import cn.liutils.api.client.render.RenderIcon;
import net.minecraft.entity.Entity;

/**
 * 据说这是光球渲染
 * @author WeAthFolD
 */
public class RenderLightBall extends RenderIcon {

	public RenderLightBall() {
		super(ClientProps.LIGHT_BALL_PATH);
		this.setEnableDepth(false);
		this.setSize(1.5F);
	}

	/* (non-Javadoc)
	 * @see net.minecraft.client.renderer.entity.Render#doRender(net.minecraft.entity.Entity, double, double, double, float, float)
	 */
	@Override
	public void doRender(Entity par1Entity, double par2, double par4, double par6,
			float par8, float par9) {
		this.alpha = 0.7F;
		super.doRender(par1Entity, par2, par4, par6, par8, par9);
	}

}
