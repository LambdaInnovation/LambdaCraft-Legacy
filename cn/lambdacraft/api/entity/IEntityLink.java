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
package cn.lambdacraft.api.entity;

import net.minecraft.entity.Entity;

/**
 * 在Entity中链接另外一个Entity（一般为玩家）的实用类。
 * 
 * @author WeAthFolD
 * @see  cn.lambdacraft.mob.util.MobHelper#spawnCreature
 * @param <T> Entity类型
 */
public interface IEntityLink<T extends Entity> {

	/**
	 * 获取链接的Entity。
	 * 
	 * @return
	 */
	public T getLinkedEntity();

	/**
	 * 设置连接的Entity。
	 * 
	 * @param entity
	 */
	public void setLinkedEntity(T entity);

}
