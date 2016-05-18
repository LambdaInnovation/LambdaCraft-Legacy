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
package cn.liutils.api.register;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * 实现这个接口以使用CBCGuiHandler及对应的GUI打开处理。
 * 
 * @author WeAthFOlD
 */
public interface IGuiElement {

    /**
     * 获取Server的Container，可能为Null
     * 
     * @param ID
     * @param player
     * @param world
     * @param x
     * @param y
     * @param z
     * @return 对应Container
     */
    public Object getServerContainer(EntityPlayer player, World world, int x,
            int y, int z);

    /**
     * 获取Client端的GUI。
     * 
     * @param ID
     * @param player
     * @param world
     * @param x
     * @param y
     * @param z
     * @return 对应GUI
     */
    public Object getClientGui(EntityPlayer player, World world, int x, int y,
            int z);
}
