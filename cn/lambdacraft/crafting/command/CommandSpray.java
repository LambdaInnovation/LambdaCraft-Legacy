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
package cn.lambdacraft.crafting.command;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.EnumChatFormatting;
import cn.lambdacraft.core.prop.ClientProps;
import cn.liutils.api.command.LICommandBase;

/**
 * @author WeAthFolD
 *
 */
public class CommandSpray extends LICommandBase {

	/* (non-Javadoc)
	 * @see net.minecraft.command.ICommand#getCommandName()
	 */
	@Override
	public String getCommandName() {
		return "spray";
	}
	
    @Override
	public String getCommandUsage(ICommandSender par1ICommandSender)
    {
        return "/spray color <R> <G> <B> | /spray id <i> | /spray color | /spray id";
    }

	/* (non-Javadoc)
	 * @see net.minecraft.command.ICommand#processCommand(net.minecraft.command.ICommandSender, java.lang.String[])
	 */
	@Override
	public void processCommand(ICommandSender ics, String[] astring) {
		if(astring.length == 0) {
			sendChat(ics, getCommandUsage(ics));
			return;
		}
		if(astring[0].equals("color")) {
			if(astring.length != 4) {
				if(astring.length == 1) {
					notifyAdmins(ics, "spray.color.name", new Object[] { String.valueOf(ClientProps.sprayR) , String.valueOf(ClientProps.sprayG), 
							String.valueOf(ClientProps.sprayB)} );
				} else 
					sendChat(ics, "spray.argument.name");
			} else {
				try {
					int r = Integer.valueOf(astring[1]);
					int g = Integer.valueOf(astring[2]);
					int b = Integer.valueOf(astring[3]);
					ClientProps.setSprayColor(r, g, b, 255);
				} catch(NumberFormatException e) {
					sendChat(ics, "spray.format.name");
				}
				sendChat(ics, "spray.successful.name");
			}
		} else if(astring[0].equals("id")){
			if(astring.length != 2) {
				if(astring.length == 1) {
					notifyAdmins(ics, "spray.id.name", new Object[] {String.valueOf(ClientProps.getSprayId())});
				} else
					sendChat(ics, "spray.argument.name");
			} else {
				try {
					int id = Integer.valueOf(astring[1]);
					ClientProps.setSprayId(id);
				} catch(NumberFormatException e) {
					sendChat(ics, "spray.format.name");
				}
				sendChat(ics, "spray.successful.name");
			}
		} else {
			sendChat(ics, EnumChatFormatting.RED + getCommandUsage(ics));
		}
	}

}
