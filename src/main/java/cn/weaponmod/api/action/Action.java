/**
 * 
 */
package cn.weaponmod.api.action;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cn.weaponmod.api.information.InfWeapon;

/**
 * InfWeapon-Handled executable action.
 * Used to do all sorts of stuffs, including shooting, reloading, mode changing... etc.
 * Notice that a single Action instance is supported to be executed for multiple times.
 * @author WeAthFolD
 */
public abstract class Action {
    
    public int maxTick;
    
    /**
     * The unique id for this action. 
     * Notice actions with the same ID can exist in the information only once at any time.
     */
    public String name;
    
    protected static final int 
            TIME_INFINITY = Integer.MAX_VALUE,
            TIME_INSTANT = 0;
    
    public Action(int ticks, String name) {
        maxTick = ticks;
        this.name = name;
    }
    
    /**
     * Called when action is added first. If the action is instant, only this function will be called.
     * @param world The world that user is in
     * @param player The player
     * @param information Mother information
     * @return true if action is successfully executed.
     */
    public boolean onActionBegin(World world, EntityPlayer player, InfWeapon information) { 
        return false;
    }
    
    public boolean onActionEnd(World world, EntityPlayer player, InfWeapon information) {
        return false;
    }
    
    public boolean onActionEnd(World world, EntityPlayer player, InfWeapon inf, boolean isRemoved) {
        return onActionEnd(world, player, inf);
    }
    
    public boolean onActionTick(World world, EntityPlayer player, InfWeapon inf) {
        return false;
    }
    
    /**
     * 获取该动作的优先级（用于决定在动作被加入时是否抢占原来的动作
     * @return
     */
    public abstract int getPriority();
    
    /**
     * 是否允许和其他的动作一同进行。
     */
    public abstract boolean doesConcurrent(Action other);
    
    @SideOnly(Side.CLIENT)
    /**
     * 获取该动作的渲染优先级。如果不需要渲染，返回-1。
     */
    public abstract int getRenderPriority();
    
    @SideOnly(Side.CLIENT)
    /**
     * 进行渲染器的叠加渲染。和物品渲染一样，实际绘制的范围在（0, 0, 0）到（1, 1, 1）
     * 注意所有的变换行为都会影响到整个物品的渲染。
     */
    public void applyRenderEffect(World world, EntityPlayer player, InfWeapon inf, boolean firstPerson) {
        
    }
    
    public boolean needSwing() { 
        return false;
    }
    
    /**
     * Return if the action is currently available for executing. Usually associated with ticker processing.
     * @param world
     * @param player
     * @param inf
     * @return
     */
    public boolean isAvailable(World world, EntityPlayer player, InfWeapon inf) {
        return true;
    }
}
