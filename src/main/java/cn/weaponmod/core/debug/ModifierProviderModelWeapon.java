/**
 * 
 */
package cn.weaponmod.core.debug;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import cn.liutils.core.debug.ModifierProviderModelRender;
import cn.weaponmod.api.client.render.RendererModelBulletWeapon;

/**
 * @author WeathFolD
 *
 */
public class ModifierProviderModelWeapon extends ModifierProviderModelRender {

    /**
     * 
     */
    public ModifierProviderModelWeapon() {
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public Object extractInstance() {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        if(player != null) {
            ItemStack stack = player.getCurrentEquippedItem();
            if(stack != null) {
                IItemRenderer renderer = MinecraftForgeClient.getItemRenderer(stack, ItemRenderType.EQUIPPED_FIRST_PERSON);
                if(renderer != null && renderer instanceof RendererModelBulletWeapon) {
                    return ((RendererModelBulletWeapon)renderer).mdlRenderer;
                }
            }
        }
        return null;
    }
    
    @Override
    public String getName() {
        return "Model Bullet Weapon";
    }

}
