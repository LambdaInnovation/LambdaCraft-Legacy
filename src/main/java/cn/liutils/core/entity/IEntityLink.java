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
package cn.liutils.core.entity;

import net.minecraft.entity.Entity;

/**
 * A utility class to enable one entity to link to another.
 * @author WeAthFolD
 * @see  cn.lambdacraft.mob.util.MobHelper#spawnCreature
 * @param <T> Entity type
 */
public interface IEntityLink<T extends Entity> {

    /**
     * Get the linked entity.
     * @return
     */
    public T getLinkedEntity();

    /**
     * Set the linked entity.
     * @param entity
     */
    public void setLinkedEntity(T entity);

}
