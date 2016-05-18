package cn.weaponmod.core.client.keys;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cn.liutils.api.client.key.IKeyHandler;
import cn.liutils.api.client.util.ClientUtils;
import cn.weaponmod.api.feature.IClickHandler;
import cn.weaponmod.core.WeaponMod;
import cn.weaponmod.core.event.ItemControlHandler;
import cn.weaponmod.core.network.MessageWMKey;

/**
 * SubKey Handler, delegating information to ItemControlHandler.
 * @author WeAthFolD
 *
 */
public class WMKeyHandler implements IKeyHandler {

    private final int id;
    
    public WMKeyHandler(int i) {
        id = i;
    }

    @Override
    public void onKeyDown(int keyCode, boolean isEnd) {
        if(isEnd || ClientUtils.isOpeningGUI())
            return;
        
        job(true);
    }

    @Override
    public void onKeyUp(int keyCode, boolean isEnd) {
        if(isEnd || ClientUtils.isOpeningGUI())
            return;
        
        job(false);
    }
    
    private void job(boolean b) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityClientPlayerMP player = mc.thePlayer;
        ItemStack stack = player.getCurrentEquippedItem();
        if (stack != null) {
            Item item = stack.getItem();
            if (item instanceof IClickHandler) {
                WeaponMod.netHandler.sendToServer(new MessageWMKey(id, b));
                if(b) {
                    ItemControlHandler.onKeyPressed(player, id);
                } else {
                    ItemControlHandler.onKeyReleased(player, id);
                }
            }
        }
    }

    @Override
    public void onKeyTick(int keyCode, boolean tickEnd) {
    }

}
