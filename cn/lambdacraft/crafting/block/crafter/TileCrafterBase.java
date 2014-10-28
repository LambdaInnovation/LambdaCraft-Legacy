/**
 * 
 */
package cn.lambdacraft.crafting.block.crafter;

import cn.lambdacraft.core.block.CBCTileEntity;
import cn.lambdacraft.crafting.block.crafter.BlockWeaponCrafter.CrafterIconType;
import cn.lambdacraft.crafting.recipe.ICrafterRecipe;
import cn.lambdacraft.crafting.recipe.MachineRecipes;
import cn.lambdacraft.crafting.recipe.RecipeWeapons;
import cn.lambdacraft.crafting.register.CBCBlocks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;

/**
 * @author WeathFolD
 */
public abstract class TileCrafterBase extends CBCTileEntity implements IInventory {
	
	public static final int 
		BUFFER_SPEED = 20;
	
	public CrafterIconType iconType = CrafterIconType.NONE; //当前提示icon状态
	public ItemStack[] inventory = new ItemStack[20];
	public ItemStack[] craftingStacks = new ItemStack[12];
	protected MachineRecipes recipes; //合成数据表

	protected int heat, maxHeat; //热量和最大存储热量
	public int scrollFactor = 0; //滚动进度(0 ~ max{0, pageSize - 3})
	public int currentPage = 0; //页面序号
	public ICrafterRecipe currentRecipe; //当前合成表
	public int heatForRendering = 0; //缓存显示以防止突变
	
	protected long lastActionTime = 0; //上次动作的时间
	private boolean isHeatBuffering; //热量显示是否缓冲中
	protected boolean isCrafting; //是否在进行合成
	protected boolean isLoaded; //是否被正确加载

	public int heatRequired = 0; //用于客户端同步，是当前recipe所需热量

	/**
	 * inventory: 1-18：材料存储 19:燃料槽 20:合成结果槽
	 * craftingStacks: 4*3 的合成表显示槽。
	 */
	public TileCrafterBase() {}
	
	public int getHeat() {
		return heat;
	}

	/**
	 * 被Container所调用，开始合成进程。
	 */
	public void startCrafting(int slot) {
		ICrafterRecipe r = getRecipeBySlot(slot);
		if (r == null)
			return;
		System.out.print("Crafting->" + r.getOutputForDisplay().getDisplayName() + " " + worldObj.isRemote);
		
		if (r.doCrafting(inventory, true) != null) {
			System.out.println(" START");
			resetCraftingState();
			iconType = CrafterIconType.CRAFTING;
			this.isCrafting = true;
			this.currentRecipe = r;
			this.heatRequired = r.getHeatConsumed();
		} else {
			System.out.println(" BAD");
			iconType = CrafterIconType.NOMATERIAL;
			heatRequired = 0;
		}
		lastActionTime = worldObj.getWorldTime();
	}

	/**
	 * 热量达到要求后进行实际的合成操作
	 */
	protected void craftItem() {
		if (this.currentRecipe == null) {
			resetCraftingState();
			return;
		}
		
		ItemStack output = currentRecipe.doCrafting(inventory, false);
		
		lastActionTime = worldObj.getWorldTime();
		iconType = CrafterIconType.NOMATERIAL;
		if(output != null) {
			if(inventory[0] != null) {
				inventory[0].stackSize += output.stackSize;
			} else
				inventory[0] = output;
			iconType = CrafterIconType.NONE;
		}
		
		resetCraftingState();
	}

	protected void writeRecipeInfoToSlot() {
		clearRecipeInfo();
		
		int length = recipes.getRecipeSize(currentPage);

		for (int i = 0; i < length - scrollFactor && i < 3; i++) {
			ICrafterRecipe r = recipes.queryRecipe(currentPage, i + scrollFactor);
			if (r == null)
				return;
			
			for (int j = 0; j < 3; j++) {
				if (r.getInputForDisplay().length > j)
					this.setInventorySlotContents(j + i * 3, r.getInputForDisplay()[j]);
			}
			this.setInventorySlotContents(9 + i, r.getOutputForDisplay());
		}
	}

	protected void clearRecipeInfo() {
		for (int i = 0; i < 12; i++) {
			this.setInventorySlotContents(i, null);
		}
	}
	
	protected void resetCraftingState() {
		isCrafting = false;
		currentRecipe = null;
		iconType = CrafterIconType.NONE;
		heatRequired = 0;
	}

	/**
	 * 根据点击的槽获取对应的合成表
	 * @param slot
	 * @param factor
	 * @return
	 */
	protected ICrafterRecipe getRecipeBySlot(int slot) {
		int i = slot == 0 ? 0 : slot == 4 ? 1 : 2;
		return recipes.queryRecipe(this.currentPage, i + scrollFactor);
	}
	
	//----------BUTTONRESPONSE------------------
	
	public void addPage(boolean isForward) {
		if (isForward) {
			if (currentPage < recipes.getPageSize() - 1) {
				currentPage++;
			}
		} else {
			if (currentPage > 0) {
				currentPage--;
			}
		}
		scrollFactor = 0;
		
		this.writeRecipeInfoToSlot();
	}

	public void addScrollFactor(boolean isForward) {
		int direction = isForward ? 1 : -1;
		scrollFactor += direction;
		if(scrollFactor < 0)
			scrollFactor = 0;
		else if(scrollFactor > 
			recipes.getMaxScrollFactor(currentPage))
			scrollFactor = recipes.getMaxScrollFactor(currentPage);
		
		this.writeRecipeInfoToSlot();
	}
	
	//------------------TICKUPDATE-------------------------
	@Override
	public void updateEntity() {
		
		if (!isLoaded) {
			if(onCrafterLoad()) {
				this.writeRecipeInfoToSlot();
				isLoaded = true;
			}
		}
		
		if(worldObj.isRemote) {
			updateClient();
			return;
		}
		//System.out.println("SVR: " + isCrafting);

		//热量下降
		if (heat > 0)
			heat--;

		//图标状态更新
		if (iconType == CrafterIconType.NOMATERIAL &&
			worldObj.getWorldTime() - lastActionTime > 20) {
			iconType = isCrafting ? CrafterIconType.CRAFTING : CrafterIconType.NONE;
		}

		//合成状态检查
		if (isCrafting) {
			if (currentRecipe.getHeatConsumed() <= this.heat
			 && currentRecipe.doCrafting(inventory, true) != null) {
				craftItem();
			}
		}
		
	}
	
	/**
	 * 加载最大热量和recipe表
	 */
	protected abstract boolean onCrafterLoad();
	
	protected void updateClient() {
		//热量显示缓冲
		if(isHeatBuffering) {
			if(heatForRendering < heat) {
				heatForRendering += BUFFER_SPEED;
				if(heatForRendering >= heat) {
					heatForRendering = heat;
					isHeatBuffering = false;
				}
			} else {
				heatForRendering -= BUFFER_SPEED;
				if(heatForRendering <= heat) {
					heatForRendering = heat;
					isHeatBuffering = false;
				}
			}
		} else if(Math.abs(heat - heatForRendering) > 300) {
			isHeatBuffering = true;
		} else heatForRendering = heat;
	}
	
	//---------------------MISC---------------------
	@Override
	public abstract String getInventoryName();

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}
	
	@Override
	public int getSizeInventory() {
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		if (i >= 12)
			return inventory[i - 12];
		return craftingStacks[i];
	}
	
	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		ItemStack stack = getStackInSlot(slot);
		if (stack != null) {
			setInventorySlotContents(slot, null);
		}
		return stack;
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return entityplayer.getDistanceSq(
				xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) <= 64;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return true;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		if (i < 12)
			craftingStacks[i] = itemstack;
		else
			inventory[i - 12] = itemstack;
	}

	@Override
	public void openInventory() {}

	@Override
	public void closeInventory() {}
	
	@Override
	public ItemStack decrStackSize(int slot, int amt) {
		ItemStack stack = getStackInSlot(slot);
		if (stack != null) {
			if (stack.stackSize <= amt) {
				setInventorySlotContents(slot, null);
			} else {
				stack = stack.splitStack(amt);
				if (stack.stackSize == 0) {
					setInventorySlotContents(slot, null);
				}
			}
		}
		return stack;
	}
	
	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		inventory = restoreInventory(nbt, "inv", 20);
		this.currentPage = nbt.getByte("page");
		this.scrollFactor = nbt.getShort("scroll");
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		storeInventory(nbt, inventory, "inv");
		nbt.setByte("page", (byte) currentPage);
		nbt.setShort("scroll", (short) scrollFactor);
	}


}
