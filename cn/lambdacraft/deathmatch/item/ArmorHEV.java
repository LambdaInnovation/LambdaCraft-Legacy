package cn.lambdacraft.deathmatch.item;

import java.util.EnumSet;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.EnumHelper;

import org.lwjgl.opengl.GL11;

import cn.lambdacraft.core.item.ElectricArmor;
import cn.lambdacraft.core.prop.ClientProps;
import cn.lambdacraft.deathmatch.register.DMItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ArmorHEV extends ElectricArmor {

	public static int reductionAmount[] = { 5, 8, 5, 4 };
	/*
	protected static EnumArmorMaterial material = EnumHelper.addArmorMaterial(
			"armorHEV", 100000, reductionAmount, 0);*/
	private static ArmorProperties propChest = new ArmorProperties(3, 0.4,
			200), propDefault = new ArmorProperties(2, 0.3, 150),
			propNone = new ArmorProperties(2, 0.0, 0),
			propShoe = new ArmorProperties(2, 0.26, 125),
			propShoeFalling = new ArmorProperties(4, 100.0, 2500),
			propChest_defense = new ArmorProperties(3, 0.5,
					200) , propDefault_defense = new ArmorProperties(2, 0.4, 150),
					propShoe_defense = new ArmorProperties(2, 0.35, 125);
	protected static String hevMark = "IV";
	
	public ArmorHEV(int armorType) {
		super(ArmorMaterial.DIAMOND, 2, armorType);
		setUnlocalizedName("hev" + this.armorType);
		this.setIconName("hev" + armorType);
		this.setMaxCharge(100000);
		this.setTier(2);
		this.setTransferLimit(128);
		this.setEnergyPerDamage(500);
	}

	/*TODO:Seek for different implementation
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot,
			int layer) {
		if (stack.getItem() == DMItems.armorHEVLeggings) {
			return ClientProps.HEV_ARMOR_PATH[1];
		} else {
			return ClientProps.HEV_ARMOR_PATH[0];
		}
	}*/

	@Override
	public boolean canProvideEnergy(ItemStack itemStack) {
		return true;
	}

	@Override
	public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor,
			DamageSource source, double damage, int slot) {
		if (getItemCharge(armor) <= this.energyPerDamage)
			return propNone;
		EnumSet<EnumAttachment> attaches = ArmorHEV.getAttachments(armor);
		if(attaches.contains(EnumAttachment.DEFENSE)) {
			if (source == DamageSource.fall) {
				if (slot == 0 && attaches.contains(EnumAttachment.FALLING))
					return propShoeFalling;
				else
					return propNone;
			}
			return slot == 2 ? propChest_defense : (slot == 0 ? propShoe_defense : propDefault_defense);
		} else {
			if (source == DamageSource.fall) {
				if (slot == 0 && attaches.contains(EnumAttachment.FALLING))
					return propShoeFalling;
				else
					return propNone;
			}
			return slot == 2 ? propChest : (slot == 0 ? propShoe : propDefault);
		}
	}

	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		return (slot == 2) ? 8 : 4;
	}
	
	@Override
	public int getMaxDamage(ItemStack is) {
		return hasAttach(is, EnumAttachment.ELECTRICITY) ? 200000 : maxCharge;
	}
	
	@Override
	public int getDisplayDamage(ItemStack stack) {
		return hasAttach(stack, EnumAttachment.ELECTRICITY) ? getMaxCharge(stack) - getItemCharge(stack) / 2 : getMaxCharge(stack) - getItemCharge(stack);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void renderHelmetOverlay(ItemStack stack, EntityPlayer player,
			ScaledResolution resolution, float partialTicks, boolean hasScreen,
			int mouseX, int mouseY) {
		TextureManager eg = Minecraft.getMinecraft().renderEngine;
		Tessellator t = Tessellator.instance;
		int i = resolution.getScaledWidth();
		int j = resolution.getScaledHeight();
		GL11.glPushMatrix();
		GL11.glDepthMask(false);
		GL11.glBlendFunc(770, 771);
		GL11.glColor4f(1.0F,1.0F,1.0F,1.0F);
		GL11.glDisable(3008);
		GL11.glDisable(2929);
		eg.bindTexture(ClientProps.HEV_MASK_PATH);
		t.startDrawingQuads();
		t.setColorRGBA(255, 255, 255, 50);
		t.addVertexWithUV(0.0D, j, -90D, 0.0D, 1.0D);
		t.addVertexWithUV(i, j, -90D, 1.0D, 1.0D);
		t.addVertexWithUV(i, 0.0D, -90D, 1.0D, 0.0D);
		t.addVertexWithUV(0.0D, 0.0D, -90D, 0.0D, 0.0D);
		t.draw();
		GL11.glDepthMask(true);
		GL11.glEnable(2929);
		GL11.glEnable(3008);
		GL11.glPopMatrix();
	}

	
	//---------------------Attachment Part------------------------//
	public static enum EnumAttachment {
		
		LONGJUMP(1), BHOP(2), ELECTRICITY(-1), DEFENSE(-1), FALLING(3), WPNMANAGE(0);
		
		private int slot;

		public boolean isAvailableSlot(int s) {
			if(slot == -1)
				return true;
			return slot == s;
		}
		
		private EnumAttachment(int x) {
			this.slot = x;
		}

		public int getSlot() {
			return slot;
		}
		
		@Override
		public String toString() {
			return "attach." + this.name().toLowerCase() + ".name";
		}
		
	}
	
	public static EnumAttachment attachForStack(ItemStack stack) {
		if(stack.getItem() instanceof ItemAttachment)
			return EnumAttachment.values()[stack.getItemDamage()];
		return null;
	}
	
	public static boolean isAttachAvailable(ItemStack hevar, EnumAttachment attach) {
		ArmorHEV hev = (ArmorHEV) hevar.getItem();
		return attach.isAvailableSlot(hev.armorType);
	}
	
	/**
	 * 判断一个盔甲是否有对应的盔甲附件。
	 * @param is
	 * @param attach
	 * @return
	 */
	public static boolean hasAttach(ItemStack is, EnumAttachment attach) {
		if (is != null && !(is.getItem() instanceof ArmorHEV))
			return false;
		for(int i = 0; i < 5; i++) {
			EnumAttachment a = getAttachInSlot(is, i);
			if(a != null && a.equals(attach))
				return true;
		}
		return false;
	}
	
	/**
	 * 获取一个盔甲所拥有的附件。
	 * @param is
	 * @return
	 */
	public static EnumSet<EnumAttachment> getAttachments(ItemStack is) {
		if(is == null)
			return null;
		EnumSet<EnumAttachment> attaches = EnumSet.noneOf(EnumAttachment.class);
		for(int i = 0; i < 5; i++) {
			EnumAttachment a = getAttachInSlot(is, i);
			if(a != null)
				attaches.add(a);
		}
		return attaches;
	}
	
	/**
	 * 向一个盔甲添加一种特定的附件（不能重复）
	 * @param is
	 * @param attach
	 * @return 是否成功添加
	 */
	public static boolean addAttachTo(ItemStack is, EnumAttachment attach) {
		if (is != null && !(is.getItem() instanceof ArmorHEV))
			return false;
		int slotToAdd = -1;
		for(int i = 0; i < 5; i++) {
			EnumAttachment att = getAttachInSlot(is, i);
			if(slotToAdd == -1) {
				if(att == null)
					slotToAdd = i;
			}
			if(att != null && att.equals(attach))
				return false;
		}
		if(slotToAdd == -1)
			return false;
		setAttach(is, slotToAdd, attach);
		return true;
	}
	
	/**
	 * 设置一个特定槽的盔甲附件（0-4）
	 * @param is
	 * @param slot
	 * @param attach
	 */
	private static void setAttach(ItemStack is, int slot, EnumAttachment attach) {
		if(is.stackTagCompound == null)
			is.stackTagCompound = new NBTTagCompound();
		NBTTagCompound nbt = is.stackTagCompound;
		nbt.setInteger("hev" + slot, attach.ordinal() + 1);
	}
	
	/**
	 * 获取特定槽的盔甲附件，可能为null。（0-4）
	 * @param is
	 * @param slot
	 * @return
	 */
	private static EnumAttachment getAttachInSlot(ItemStack is, int slot) {
		if(slot >= 5)
			throw new UnsupportedOperationException();
		if(is.stackTagCompound == null)
			is.stackTagCompound = new NBTTagCompound();
		NBTTagCompound nbt = is.stackTagCompound;
		int a = nbt.getInteger("hev" + slot);
		if(a == 0)
			return null;
		return EnumAttachment.values()[a-1];
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		EnumSet<EnumAttachment> attaches = getAttachments(par1ItemStack);
		par3List.add(EnumChatFormatting.RED + "Mark " + ArmorHEV.hevMark);
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
		if(attaches != null)
			for(EnumAttachment a : attaches)
				par3List.add(StatCollector.translateToLocal(a.toString()));
	}

}
