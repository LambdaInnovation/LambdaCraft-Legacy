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
package cn.liutils.api.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentTranslation;

/**
 * @author WeAthFolD
 *
 */
public abstract class LICommandBase extends CommandBase {
    
    public LICommandBase() {
    }
    
    public static void sendChat(ICommandSender ics, String st) {
        ics.addChatMessage(new ChatComponentTranslation(st));
    }
    
    public static void sendError(ICommandSender ics, String st) {
        ics.addChatMessage(new ChatComponentTranslation("\u00a7c" + st));
    }
    
    public static void sendWithTranslation(ICommandSender ics, String unlStr, Object... args) {
        ics.addChatMessage(new ChatComponentTranslation(unlStr, args));
    }

}
