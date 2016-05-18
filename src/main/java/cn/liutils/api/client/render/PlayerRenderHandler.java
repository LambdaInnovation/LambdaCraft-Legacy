/**
 * Code by Lambda Innovation, 2013.
 */
package cn.liutils.api.client.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * Attached to the player and activate whenever player model is rendered.
 * Used for rendering custom stuffs.
 * @author WeAthFolD
 */
@SideOnly(Side.CLIENT)
public interface PlayerRenderHandler {
    
    /**
     * Get whether the render process is activated or not
     * @param player
     * @param world
     * @return
     */
    boolean isActivated(EntityPlayer player, World world);
    
    void renderHead(EntityPlayer player, World world);
    
    void renderBody(EntityPlayer player, World world);
}
