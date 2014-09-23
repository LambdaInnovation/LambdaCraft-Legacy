package cn.lambdacraft.deathmatch.entity;

import java.lang.reflect.Field;
import java.util.List;

import cn.lambdacraft.deathmatch.item.ItemMedkit;
import cn.lambdacraft.deathmatch.register.DMItems;
import cn.liutils.api.util.selector.EntitySelectorPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityMedkit extends Entity {

	private boolean spawnItem = true;
	
	Field fldDuration;

	public EntityMedkit(World world) {
		super(world);
		this.setSize(1.0F, 0.2F);
	}

	public EntityMedkit(World world, EntityPlayer entityPlayer, double x,
			double y, double z, ItemStack itemStack) {
		super(world);
		this.setPosition(x, y, z);
		writePotionInf(itemStack);
		this.setSize(0.8F, 0.4F);
		
		try {
			fldDuration = PotionEffect.class.getDeclaredField("duration");
			fldDuration.setAccessible(true);
		} catch(Exception e) {}
		
		if (entityPlayer.capabilities.isCreativeMode)
			spawnItem = false;
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		this.motionY -= 0.03999999910593033D;
		this.moveEntity(this.motionX, this.motionY, this.motionZ);
		this.motionX *= 0.9800000190734863D;
		this.motionY *= 0.9800000190734863D;
		this.motionZ *= 0.9800000190734863D;

		if (this.onGround) {
			this.motionX *= 0.699999988079071D;
			this.motionZ *= 0.699999988079071D;
			this.motionY *= -0.5D;
		}

		if (++this.ticksExisted < 20 || worldObj.isRemote)
			return;

		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(posX - 0.15,
				posY - 0.3, posZ - 0.15, posX + 0.15, posY + 0.3, posZ + 0.15);
		List<EntityPlayer> list = worldObj
				.getEntitiesWithinAABBExcludingEntity(this, box,
						new EntitySelectorPlayer());
		if (list == null || list.size() == 0)
			return;
		EntityPlayer player = list.get(0);
		applyEffects(player);

	}

	private void applyEffects(EntityPlayer player) {
		for (int i = 0; i < ItemMedkit.MAX_STORE; i++) {
			PotionEffect eff = getEffect(i);
			if (eff != null) {
				PotionEffect ef = player.getActivePotionEffect(Potion.potionTypes[eff.getPotionID()]);
				if(ef != null) {
					try {
						fldDuration.set(ef, ef.getDuration() + eff.getDuration());
					} catch (Exception e)  {}
					player.addPotionEffect(ef);
				} else
					player.addPotionEffect(eff);
			}
		}
		this.playSound("cbc.entities.medkit", 0.5F, 1.0F);
		int damage = this.getEntityData().getInteger("damage");
		if (damage < 25 && spawnItem)
			worldObj.spawnEntityInWorld(new EntityItem(worldObj, posX, posY,
					posZ, new ItemStack(DMItems.medkit, 1, ++damage)));
		this.setDead();
	}

	private PotionEffect getEffect(int slot) {
		NBTTagCompound nbt = getEntityData();
		int id = nbt.getInteger("id" + slot);
		if (id == 0)
			return null;
		int duration = nbt.getInteger("duration" + slot);
		int amplifier = nbt.getInteger("amplifier" + slot);

		return new PotionEffect(id, duration, amplifier);
	}

	@Override
	protected void entityInit() {

	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		posX = nbt.getDouble("posX");
		posY = nbt.getDouble("posY");
		posZ = nbt.getDouble("posZ");
	}

	private void writePotionInf(ItemStack medkit) {
		NBTTagCompound nbt = this.getEntityData(), nbtItem = loadCompound(medkit);

		for (int i = 0; i < ItemMedkit.MAX_STORE; i++) {
			nbt.setInteger("id" + i, nbtItem.getInteger("id" + i));
			nbt.setInteger("duration" + i, nbtItem.getInteger("duration" + i));
			nbt.setInteger("amplifier" + i, nbtItem.getInteger("amplifier" + i));
		}
		nbt.setInteger("damage", medkit.getItemDamage());
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setDouble("posX", posX);
		nbt.setDouble("posY", posY);
		nbt.setDouble("posZ", posZ);
	}

	private static NBTTagCompound loadCompound(ItemStack stack) {
		if (stack.stackTagCompound == null)
			stack.stackTagCompound = new NBTTagCompound();
		return stack.stackTagCompound;
	}

}
