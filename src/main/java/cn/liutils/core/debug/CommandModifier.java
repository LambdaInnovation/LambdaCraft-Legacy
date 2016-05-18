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
package cn.liutils.core.debug;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentTranslation;
import cn.liutils.api.command.LICommandBase;

/**
 * @author WeAthFolD
 *
 */
public class CommandModifier extends LICommandBase {
    
    public CommandModifier() {
    }

    /* (non-Javadoc)
     * @see net.minecraft.command.ICommand#getCommandName()
     */
    @Override
    public String getCommandName() {
        return "m";
    }

    @Override
    public String getCommandUsage(ICommandSender var1) {
        return "{v} {p [id]} {s [id]} {ds [vararg]}";
    }

    @Override
    public void processCommand(ICommandSender var1, String[] var2) {
        if(var2[0].equals("v")) {
            
            LICommandBase.sendChat(var1, "Current Provider : " + FieldModifierHandler.getProviderName());
            FieldModifier fm = FieldModifierHandler.getCurModifier();
            LICommandBase.sendChat(var1, "Current Modifier : " + 
                    (fm == null ? "NULL" : fm.getName()));
            LICommandBase.sendChat(var1, "-------Available Providers------");
            for(int i = 0; i < FieldModifierHandler.all.size(); i++) {
                LICommandBase.sendChat(var1, i + ": " + FieldModifierHandler.all.get(i).getName());
            }
            LICommandBase.sendChat(var1, "--------------------------------");
            LICommandBase.sendChat(var1, "--------Available Modifiers------");
            if(FieldModifierHandler.currentProvider != null) {
                for(int i = 0; i < FieldModifierHandler.currentProvider.getModifiers().size(); i++) {
                    LICommandBase.sendChat(var1, i + ": " + FieldModifierHandler.currentProvider.getModifiers().get(i).getName());
                }
            }
            LICommandBase.sendChat(var1, "--------------------------------");
            
        } else if(var2[0].equals("p")) {
            
            FieldModifierHandler.setProvider(Integer.valueOf(var2[1]));
            
        } else if(var2[0].equals("s")) {
            
            FieldModifierHandler.modifierID = Integer.valueOf(var2[1]);
            
        } else if(var2[0].equals("ds")) { 
            
            FieldModifierHandler.directSet(var2[1]);
            
        } else wrong(var1);
    }
    
    private void wrong(ICommandSender ics) {
        ics.addChatMessage(new ChatComponentTranslation("Illegal arguments."));
    }

}
