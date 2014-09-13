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
package cn.lambdacraft.crafting.entity;

import cn.lambdacraft.core.prop.ClientProps;
import cn.liutils.api.util.Color4I;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

/**
 * 狼：Minecraft类是要在Server端生成的时候绝对不能引用的类，千万注意 如果要获取玩家实体，可以用dataWatcher
 * mkpoli：哦，我错了QAQ
 * 
 * @author mkpoli
 */
public class EntitySpray extends EntityHanging {

	public final static int[] GRIDS_HEIGHTS = { 2, 2 };
	public final static int[] GRIDS_WIDTHS = { 10, 2 };

	// TODO: make player hear sound
	// public int hangingDirection;
	// public int blockPosX;
	// public int blockPosY;
	// public int blockPosZ;
	public int tId = -1;
	public Color4I color;

	public EntitySpray(World world) {
		super(world);
		this.setSize(1.0F, 1.0F);
	}

	public EntitySpray(World world, int x, int y, int z, int direction,
			int tId, EntityPlayer thePlayer) {
		this(world);
		this.field_146063_b = x;
		this.field_146064_c = y;
		this.field_146062_d = z;
		this.hangingDirection = direction;
		this.tId = tId;
		this.color = new Color4I(ClientProps.sprayR, ClientProps.sprayG,
				ClientProps.sprayB);

		this.saveParams();
		this.initParams();

		// this.player = thePlayer;
	}

	@Override
	protected void entityInit() {
		this.dataWatcher.addObject(18, 0);
		this.dataWatcher.addObject(19, 0);
		this.dataWatcher.addObject(20, 0);
		this.dataWatcher.addObject(21, 0);
		this.dataWatcher.addObject(22, 0);
		this.dataWatcher.addObject(23, 0);
		this.dataWatcher.addObject(24, 0);
		this.dataWatcher.addObject(25, 0);
	}

	@Override
	public void onUpdate() {
		if (worldObj.isRemote) {
			this.hangingDirection = this.dataWatcher.getWatchableObjectInt(18);
			this.field_146063_b = this.dataWatcher.getWatchableObjectInt(19);
			this.field_146064_c = this.dataWatcher.getWatchableObjectInt(20);
			this.field_146062_d = this.dataWatcher.getWatchableObjectInt(21);
			this.tId = this.dataWatcher.getWatchableObjectInt(22);
			this.loadColor();
		} else {
            if (!this.isDead && !this.onValidSurface())
            {
                this.setDead();
                this.onBroken((Entity)null);
            }
		}
	}

	/*
	 * public EntitySpray(World world, int x, int y, int z, int direction, int
	 * title_id, EntityPlayer thePlayer, Color4I color) { this(world);
	 * 
	 * this.block_pos_x = x; this.block_pos_y = y; this.block_pos_z = z;
	 * this.hanging_direction = direction; this.title_id = title_id; this.color
	 * = color;
	 * 
	 * this.save_params2(); this.initParams();
	 * 
	 * this.player = thePlayer; }
	 */

	public void loadColor() {
		if (color == null)
			this.color = new Color4I(
					this.dataWatcher.getWatchableObjectInt(23),
					this.dataWatcher.getWatchableObjectInt(24),
					this.dataWatcher.getWatchableObjectInt(25));
		else {
			color.setValue(this.dataWatcher.getWatchableObjectInt(23),
					this.dataWatcher.getWatchableObjectInt(24),
					this.dataWatcher.getWatchableObjectInt(25), 255);
		}
	}

	private void saveParams() {
		this.dataWatcher.updateObject(18, this.hangingDirection);
		this.dataWatcher.updateObject(19, this.field_146063_b);
		this.dataWatcher.updateObject(20, this.field_146064_c);
		this.dataWatcher.updateObject(21, this.field_146062_d);
		this.dataWatcher.updateObject(22, this.tId);
		if (this.tId >= 2) {
			if (this.color == null)
				this.color = new Color4I(ClientProps.sprayR,
						ClientProps.sprayG, ClientProps.sprayB);
			this.dataWatcher.updateObject(23, this.color.red);
			this.dataWatcher.updateObject(24, this.color.green);
			this.dataWatcher.updateObject(25, this.color.blue);
		}
	}

	public void loadParams() {
		this.hangingDirection = this.dataWatcher.getWatchableObjectInt(18);
		this.field_146063_b = this.dataWatcher.getWatchableObjectInt(19);
		this.field_146064_c = this.dataWatcher.getWatchableObjectInt(20);
		this.field_146062_d = this.dataWatcher.getWatchableObjectInt(21);
		this.tId = this.dataWatcher.getWatchableObjectInt(22);

		this.initParams();
	}

	/**
	 * NBT Processing.
	 */

	@Override
	public void readEntityFromNBT(NBTTagCompound nbtTag) {
		this.hangingDirection = nbtTag.getByte("Direction");
		this.field_146063_b = nbtTag.getInteger("TileX");
		this.field_146064_c = nbtTag.getInteger("TileY");
		this.field_146062_d = nbtTag.getInteger("TileZ");
		this.tId = nbtTag.getInteger("TitleId");

		if (tId >= 2) {
			this.color = new Color4I(nbtTag.getInteger("ColorR"),
					nbtTag.getInteger("ColorG"), nbtTag.getInteger("ColorB"));
		}

		initParams();
		saveParams();
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbtTag) {
		nbtTag.setByte("Direction", (byte) this.hangingDirection);
		nbtTag.setInteger("TileX", this.field_146063_b);
		nbtTag.setInteger("TileY", this.field_146064_c);
		nbtTag.setInteger("TileZ", this.field_146062_d);
		nbtTag.setInteger("TitleId", this.tId);

		if (tId >= 2) {
			nbtTag.setInteger("ColorR", this.color.red);
			nbtTag.setInteger("ColorG", this.color.green);
			nbtTag.setInteger("ColorB", this.color.blue);
		}
	}

	/**
	 * Initialize params.
	 */
	public void initParams() {
		int direction = this.hangingDirection;

		float half_width;
		float half_height;

		if (tId >= 2) {
			half_width = 0.5F;
			half_height = 0.5F;
		} else {
			half_width = GRIDS_WIDTHS[this.tId] / 2.0F;
			half_height = GRIDS_HEIGHTS[this.tId] / 2.0F;
		}

		float entity_pos_x;
		float entity_pos_y = this.field_146064_c + half_height;
		float entity_pos_z;

		float horizontal_off = 0.0625F; // 1/16

		if (direction == 2) {
			entity_pos_x = this.field_146063_b + 1.0F - half_width;
			entity_pos_z = this.field_146062_d - horizontal_off;

			this.rotationYaw = this.prevRotationYaw = 180;
			this.setPosition(entity_pos_x, entity_pos_y, entity_pos_z);
			this.boundingBox.setBounds(entity_pos_x - half_width, entity_pos_y
					- half_height, entity_pos_z - horizontal_off, entity_pos_x
					+ half_width, entity_pos_y + half_height, entity_pos_z
					+ horizontal_off);
			return;
		}

		if (direction == 1) {
			entity_pos_x = this.field_146063_b - horizontal_off;
			entity_pos_z = this.field_146062_d + half_width;

			this.rotationYaw = this.prevRotationYaw = 270;
			this.setPosition(entity_pos_x, entity_pos_y, entity_pos_z);
			this.boundingBox.setBounds(entity_pos_x - horizontal_off,
					entity_pos_y - half_height, entity_pos_z - half_width,
					entity_pos_x + horizontal_off, entity_pos_y + half_height,
					entity_pos_z + half_width);
			return;
		}

		if (direction == 0) {
			entity_pos_x = this.field_146063_b + half_width;
			entity_pos_z = this.field_146062_d + 1.0F + horizontal_off;

			this.rotationYaw = this.prevRotationYaw = 0;
			this.setPosition(entity_pos_x, entity_pos_y, entity_pos_z);
			this.boundingBox.setBounds(entity_pos_x - half_width, entity_pos_y
					- half_height, entity_pos_z - horizontal_off, entity_pos_x
					+ half_width, entity_pos_y + half_height, entity_pos_z
					+ horizontal_off);
			return;
		}

		if (direction == 3) {
			entity_pos_x = this.field_146063_b + 1.0F + horizontal_off;
			entity_pos_z = this.field_146062_d + 1.0F - half_width;

			this.rotationYaw = this.prevRotationYaw = 90;
			this.setPosition(entity_pos_x, entity_pos_y, entity_pos_z);
			this.boundingBox.setBounds(entity_pos_x - horizontal_off,
					entity_pos_y - half_height, entity_pos_z - half_width,
					entity_pos_x + horizontal_off, entity_pos_y + half_height,
					entity_pos_z + half_width);
			return;
		}
	}

	/**
	 * Make the spray disapper.
	 */
	@Override
	public boolean hitByEntity(Entity par1Entity) {
		return par1Entity instanceof EntityPlayer ? this.attackEntityFrom(
				DamageSource.causePlayerDamage((EntityPlayer) par1Entity), 0)
				: false;
	}

	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
		if (this.isEntityInvulnerable())
			return false;

		if (!this.isDead && !this.worldObj.isRemote) {
			this.setDead();
			this.setBeenAttacked();
		}
		return true;
	}

	@Override
	public int getWidthPixels() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getHeightPixels() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void onBroken(Entity entity) {
	}
}