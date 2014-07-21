package cn.lambdacraft.deathmatch.flashlight;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import java.util.EnumSet;
import cn.lambdacraft.core.CBCPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.EnumSkyBlock;

public class ClientTickHandler implements ITickHandler {
	public static int ticks = 0;

	/* the flag if the flashlight button has been pressed */
	public boolean flag = false;

	@Override
	public void tickStart(EnumSet var1, Object... var2) {
	}

	@Override
	public void tickEnd(EnumSet var1, Object... var2) {
		if (var1.equals(EnumSet.of(TickType.RENDER))) {
			this.onRenderTick();
		} else if (var1.equals(EnumSet.of(TickType.CLIENT))) {
			GuiScreen var3 = Minecraft.getMinecraft().currentScreen;
			if (var3 != null) {
				this.onTickInGUI(var3);
			} else {
				this.onTickInGame();
			}
		}
	}

	@Override
	public EnumSet ticks() {
		return EnumSet.of(TickType.RENDER, TickType.CLIENT);
	}

	@Override
	public String getLabel() {
		return "Flashlight.ClientTickHandler";
	}

	public void onRenderTick() {
	}

	public void onTickInGUI(GuiScreen var1) {
	}

	public void onTickInGame() {
		boolean var1 = false;
		int var5;
		++ticks;

		if (ticks >= 5) {
			Minecraft mc = Minecraft.getMinecraft();
			EntityPlayerSP player = mc.thePlayer;
			ticks = 0;
			LightValue.resetAll();
			LightValue.emptyData();
			
			MovingObjectPosition result = player.rayTrace(36.0D, 1.0F);
			if (flag && CBCPlayer.armorStat[3] && result != null)
			{
				int blockX = result.blockX;
				int blockY = result.blockY;
				int blockZ = result.blockZ;
				var5 = result.sideHit;

				if (var5 == 0) {
					--blockY;
				} else if (var5 == 1) {
					++blockY;
				} else {
					if (var5 == 2) {
						--blockZ;
					}

					if (var5 == 3) {
						++blockZ;
					}

					if (var5 == 4) {
						--blockX;
					}

					if (var5 == 5) {
						++blockX;
					}
				}

				LightValue.addData(blockX, blockY, blockZ);
				Minecraft.getMinecraft().theWorld.setLightValue(EnumSkyBlock.Block, blockX, blockY, blockZ, 15);
				Minecraft.getMinecraft().theWorld.updateLightByType(EnumSkyBlock.Block, blockX, blockY + 1, blockZ);
				//Minecraft.getMinecraft().theWorld.updateLightByType(EnumSkyBlock.Block, var9, var10 - 1, var11);
				Minecraft.getMinecraft().theWorld.updateLightByType(EnumSkyBlock.Block, blockX - 1, blockY, blockZ);
				//Minecraft.getMinecraft().theWorld.updateLightByType(EnumSkyBlock.Block, var9 + 1, var10, var11);
				Minecraft.getMinecraft().theWorld.updateLightByType(EnumSkyBlock.Block, blockX, blockY, blockZ - 1);
				//Minecraft.getMinecraft().theWorld.updateLightByType(EnumSkyBlock.Block, var9, var10, var11 + 1);
			}
		}
		/*TODO:注意效率问题……
		if (ticks >= 10) {
			ticks = 0;
			LightValue.resetAll2();
			LightValue.emptyData2();
			AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(player.posX - 10, player.posY - 10, player.posZ - 10, player.posX + 10, player.posY + 10, player.posZ + 10);
			Iterator var2 = world.getEntitiesWithinAABBExcludingEntity(null, aabb, GenericUtils.selectorPlayer).iterator();
			while (var2.hasNext()) {
				Object var3 = var2.next();

				if (var3 instanceof EntityPlayer) {
					EntityLiving var4 = (EntityLiving) var3;
					TODO:你偷懒了⑨，这里的效果是你一开灯所有玩家都开灯啊>. <
					卧槽不会吧？
					if (flag && CBCPlayer.armorStat[3] && var4.rayTrace(200.0D, 1.0F) != null) {
						var5 = var4.rayTrace(200.0D, 1.0F).blockX;
						int var6 = var4.rayTrace(200.0D, 1.0F).blockY;
						int var7 = var4.rayTrace(200.0D, 1.0F).blockZ;
						int var8 = var4.rayTrace(200.0D, 1.0F).sideHit;

						if (var8 == 0) {
							--var6;
						} else if (var8 == 1) {
							++var6; 
						} else {
							if (var8 == 2) {
								--var7;
							}

							if (var8 == 3) {
								++var7;
							}

							if (var8 == 4) {
								--var5;
							}

							if (var8 == 5) {
								++var5;
							}
						}

						LightValue.addData2(var5, var6, var7);
						Minecraft.getMinecraft().theWorld.setLightValue(EnumSkyBlock.Block, var5, var6, var7, 15);
						Minecraft.getMinecraft().theWorld.updateLightByType(EnumSkyBlock.Block, var5, var6 + 1, var7);
						/*Minecraft.getMinecraft().theWorld.updateLightByType(EnumSkyBlock.Block, var5, var6 - 1, var7);
						Minecraft.getMinecraft().theWorld.updateLightByType(EnumSkyBlock.Block, var5 - 1, var6, var7);
						Minecraft.getMinecraft().theWorld.updateLightByType(EnumSkyBlock.Block, var5 + 1, var6, var7);
						Minecraft.getMinecraft().theWorld.updateLightByType(EnumSkyBlock.Block, var5, var6, var7 - 1);
						Minecraft.getMinecraft().theWorld.updateLightByType(EnumSkyBlock.Block, var5, var6, var7 + 1);
					}
				}
			}
		}
		*/
		
	}
}
