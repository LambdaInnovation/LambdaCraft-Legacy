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
package cn.lambdacraft.deathmatch.entity.fx;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

/**
 * @author WeAthFolD
 */
public class GaussParticleFX extends Entity {

	public float currentAlpha;
	
	/**
	 * @param par1World
	 * @param par2 x
	 * @param par4 y 
	 * @param par6 z
	 */
	public GaussParticleFX(World par1World, double par2, double par4,
			double par6) {
		super(par1World);
		this.setPosition(par2, par4, par6);
	}
	
	public GaussParticleFX(World world) {
		super(world);
	}
	
	@Override
	protected void entityInit() {
		currentAlpha = 1.0F;
	}

	@Override
	public void onUpdate() {
		currentAlpha -= 0.02F;
		if(currentAlpha <= 0.0F)
			this.setDead();
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbttagcompound) {}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbttagcompound) {}

}
