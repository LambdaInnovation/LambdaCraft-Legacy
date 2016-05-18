/**
 * Copyright (C) Lambda-Innovation, 2013-2014
 * This code is open-source. Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer. 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 */
package cn.liutils.api.client.util;

import net.minecraft.client.Minecraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Client-Side judgement helper and other stuffs.
 * @author WeAthFolD
 */
@SideOnly(Side.CLIENT)
public class ClientUtils {

    public static boolean isOpeningGUI() {
        Minecraft mc = Minecraft.getMinecraft();
        return mc.currentScreen != null || mc.thePlayer == null;
    }

}
