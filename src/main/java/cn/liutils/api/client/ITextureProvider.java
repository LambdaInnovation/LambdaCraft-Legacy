/**
 * 
 */
package cn.liutils.api.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.ResourceLocation;

/**
 * @author WeAthFolD
 *
 */
public interface ITextureProvider {
    @SideOnly(Side.CLIENT)
    ResourceLocation getTexture();
}
