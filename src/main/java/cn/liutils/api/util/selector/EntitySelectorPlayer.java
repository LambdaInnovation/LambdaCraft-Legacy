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
package cn.liutils.api.util.selector;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class EntitySelectorPlayer implements IEntitySelector {

    EntityPlayer exclusion;

    public EntitySelectorPlayer(EntityPlayer playerToExclude) {
        exclusion = playerToExclude;
    }

    public EntitySelectorPlayer() {

    }

    @Override
    public boolean isEntityApplicable(Entity entity) {
        if (entity instanceof EntityPlayer && entity != exclusion)
            return true;
        return false;
    }
}
