/** 
 * Copyright (c) Lambda Innovation Team, 2013
 * 版权许可：LambdaCraft 制作小组， 2013.
 * http://lambdacraft.cn/
 * 
 * The mod is open-source. It is distributed under the terms of the
 * Lambda Innovation Open Source License. It grants rights to read, modify, compile
 * or run the code. It does *NOT* grant the right to redistribute this software
 * or its modifications in any form, binary or source, except if expressively
 * granted by the copyright holder.
 *
 * 本Mod是完全开源的，你允许参考、使用、引用其中的任何代码段，但不允许将其用于商业用途，在引用的时候，必须注明原作者。
 */
package cn.weaponmod.core.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cn.weaponmod.api.feature.IClickHandler;
import cn.weaponmod.core.WeaponMod;
import cn.weaponmod.core.event.ItemControlHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

/**
 * Message for player control keys. Handles server-side key-press simulation.
 * @author WeAthFolD
 */
public class MessageWMKey implements IMessage {
    
    public int keyid;
    public boolean keyDown;
    

    /**
     * 1 : leftUse, 2 : leftStop
     * -1 : rightUse, -2 : rightStop
     * @param keyid
     * @param additional
     */
    public MessageWMKey(int keyid, boolean isDown) {
        this.keyid = keyid;
        this.keyDown = isDown;
    }
    
    public MessageWMKey() {}
    
    @Override
    public void fromBytes(ByteBuf buf) {
        keyid = buf.readByte();
        keyDown = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeByte(keyid);
        buf.writeBoolean(keyDown);
    }
    
    public static class Handler implements IMessageHandler<MessageWMKey, IMessage> {

        @Override
        public IMessage onMessage(MessageWMKey message, MessageContext ctx) {
            int type = message.keyid;
            EntityPlayer player = ctx.getServerHandler().playerEntity;
            boolean wrong = false;
            
            if(message.keyDown) { //use
                
                boolean left = type > 0;
                ItemStack stack = player.getCurrentEquippedItem();
                if(stack != null) {
                    Item item = stack.getItem();
                    if(item instanceof IClickHandler) {
                        ItemControlHandler.onKeyPressed(player, type);
                    } else wrong = true;
                } else wrong = true;
                
            } else { //stop
                
                ItemControlHandler.onKeyReleased(player, type);
                
            }
            
            if(wrong)
                WeaponMod.log.info("Coudn't find the correct player-controlling Item instance...");
            return null;
        }
        
    }
    
}
