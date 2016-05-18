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
package cn.liutils.core.client.register;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import cn.liutils.api.client.key.IKeyHandler;

/**
 * 统一处理按键的实用类。 请使用addKey(...)注册按键绑定。详见函数本身说明
 * @author WeAthFolD
 */
public final class LIKeyProcess {
    
    public static final int MOUSE_LEFT = -100, MOUSE_MIDDLE = -98, MOUSE_RIGHT = -99;
    
    private static Map<String, LIKeyBinding> bindingMap = new HashMap<String, LIKeyBinding>();
    private static Map<LIKeyBinding, KeyBinding> associates = new HashMap();
    
    public static class LIKeyBinding {
        public int keyCode;
        public boolean isRepeat;
        public IKeyHandler process;
        public boolean keyDown;
        public String name;
        
        public LIKeyBinding(String n, int code, boolean repeat, IKeyHandler proc) {
            name = n;
            keyCode = code;
            isRepeat = repeat;
            process = proc;
        }
    }

    /**
     * adding a key to the process list.
     * @param key the key
     * @param isRep repeatly call keyDown when pressing
     * @param handler handler
     */
    public static LIKeyBinding addKey(String name, int keyCode, boolean isRep, IKeyHandler handler) {
        LIKeyBinding binding = new LIKeyBinding(name, keyCode, isRep, handler);
        bindingMap.put(name, binding);
        return binding;
    }
    
    public static LIKeyBinding addKey(KeyBinding b, boolean isRep, IKeyHandler process) {
        LIKeyBinding bd = addKey(b.getKeyDescription(), b.getKeyCode(), isRep, process);
        associates.put(bd, b);
        return bd;
    }
    
    public static LIKeyBinding getBindingByName(String s) {
        return bindingMap.get(s);
    }
    
    //----------------INTERNAL IMPLEMENTATIONS---------------------
    public final void tickStart()
    {
        keyTick(false);
    }

    public final void tickEnd()
    {
        keyTick(true);
    }
    
    private void keyTick(boolean tickEnd)
    {
        for (LIKeyBinding kb : bindingMap.values())
        {
            int keyCode = kb.keyCode;
            boolean state = (keyCode < 0 ? Mouse.isButtonDown(keyCode + 100) : Keyboard.isKeyDown(keyCode));
            if (state != kb.keyDown || (state && kb.isRepeat))
            {
                IKeyHandler proc = kb.process;
                
                if (state)
                {
                    proc.onKeyDown(keyCode, tickEnd);
                }
                else
                {
                   proc.onKeyUp(keyCode, tickEnd);
                }
                if (tickEnd)
                {
                    kb.keyDown = state;
                }
            } else if(state) {
                IKeyHandler handler = kb.process;
                handler.onKeyTick(keyCode, tickEnd);
            }
            KeyBinding binding = associates.get(kb);
            if(binding != null) kb.keyCode = binding.getKeyCode();
        } 
    }

}
