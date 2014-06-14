package cn.lambdacraft.xen.util;

import java.util.EnumSet;

import cn.lambdacraft.xen.ModuleXen;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class XenWorldTime implements ITickHandler{

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData)
	{
		if(!(Minecraft.getMinecraft().theWorld == null))
		{
			if(Minecraft.getMinecraft().theWorld.provider.getDimensionName().equals("XEN"))
			{
			MinecraftServer.getServer().worldServerForDimension(ModuleXen.dimensionId).setWorldTime(19000L);
			Minecraft.getMinecraft().theWorld.setWorldTime(19000L);
			}
		}
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.WORLD);
	}

	@Override
	public String getLabel() {
		return "XenWorldTime";
	}
}
