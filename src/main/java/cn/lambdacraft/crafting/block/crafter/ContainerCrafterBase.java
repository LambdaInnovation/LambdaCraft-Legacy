package cn.lambdacraft.crafting.block.crafter;

import cn.lambdacraft.crafting.block.SlotLocked;
import cn.lambdacraft.crafting.block.SlotOutput;
import cn.lambdacraft.crafting.block.SlotResult;
import cn.lambdacraft.crafting.block.crafter.BlockWeaponCrafter.CrafterIconType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerCrafterBase extends Container {

    public final TileCrafterBase tileEntity;
    
    public ContainerCrafterBase(TileCrafterBase te) {
        this.tileEntity = te;
    }

    public ContainerCrafterBase(InventoryPlayer inventoryPlayer,
            TileCrafterBase te) {
        tileEntity = te;
        addSlots(te);
        bindPlayerInventory(inventoryPlayer);
    }

    protected void addSlots(TileCrafterBase te) {
        // Crafting recipe slot
        for (int i = 0; i < 3; i++) {
            // output:0 4 8
            Slot s = addSlotToContainer(new SlotOutput(te, 9 + i, 88,
                    19 + 22 * i));
            // input :123 567 9.10.11
            for (int j = 0; j < 3; j++) {
                addSlotToContainer(new SlotLocked(te, j + i * 3, 12 + 22 * j,
                        19 + 22 * i));
            }

        }

        addSlotToContainer(new Slot(te, 13, 136, 63));
        Slot s = addSlotToContainer(new SlotResult(te, 12, 136, 19));
        // Block Storage
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 9; j++) {
                addSlotToContainer(new Slot(te, 14 + 9 * i + j, 8 + 21 * j,
                        100 + 22 * i));
            }
        }
    }

    protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9,
                        8 + j * 21, 156 + i * 22));
            }
        }
        for (int i = 0; i < 9; i++) {
            addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 21, 229));
        }
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (int i = 0; i < this.crafters.size(); ++i) {
            ICrafting icrafting = (ICrafting) this.crafters.get(i);
            icrafting.sendProgressBarUpdate(this, 0, tileEntity.currentPage);
            icrafting.sendProgressBarUpdate(this, 1, tileEntity.iconType.ordinal());
            icrafting.sendProgressBarUpdate(this, 2, tileEntity.heat);
            if(tileEntity.currentRecipe != null)
                icrafting.sendProgressBarUpdate(this, 3, tileEntity.currentRecipe.getHeatConsumed());
            else icrafting.sendProgressBarUpdate(this, 3, 0);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int par1, int par2) {
        if (par1 == 0) {
            tileEntity.currentPage = Math.abs(par2);
        } else if (par1 == 1) {
            tileEntity.iconType = CrafterIconType.values()[par2];
        } else if (par1 == 2) {
            tileEntity.heat = par2;
        } else if (par1 == 3)
            tileEntity.heatRequired = par2;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return tileEntity.isUseableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
        ItemStack stack = null;
        Slot slotObject = (Slot) inventorySlots.get(slot);
        // null checks and checks if the item can be stacked (maxStackSize > 1)
        if (slotObject != null && slotObject.getHasStack()) {
            ItemStack stackInSlot = slotObject.getStack();
            stack = stackInSlot.copy();

            // 将玩家物品栏中的物品放到TileEntity中
            if (slot >= 32) {
                if (!this.mergeItemStack(stackInSlot, 14, 32, true)) {
                    return null;
                }
            }
            // 将TileEntity中的物品放到玩家物品栏中
            else if (slot >= 12) {
                if (!this.mergeItemStack(stackInSlot, 32, 67, false))
                    return null;
            }

            if (stackInSlot.stackSize == 0) {
                slotObject.putStack(null);
            } else {
                slotObject.onSlotChanged();
            }

            if (stackInSlot.stackSize == stack.stackSize) {
                return null;
            }
            slotObject.onPickupFromSlot(player, stackInSlot);
        }
        return stack;
    }

}
