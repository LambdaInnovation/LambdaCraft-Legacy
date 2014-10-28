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
package cn.lambdacraft.crafting.recipe;

import net.minecraft.command.WrongUsageException;
import net.minecraft.item.ItemStack;

public class CrafterRecipeNormal implements ICrafterRecipe {
	
	protected ItemStack[] input;
	protected ItemStack output;
	protected int heatRequired;

	/**
	 * 加载一个武器合成机合成表。
	 * 
	 * @param out 输出结果
	 * @param heat 需要的热量（参考：煤炭700，撬棍700，手枪系列1500左右，步枪系列3000Heat左右，弹药系列700~4000
	 *            Heat左右，装甲6000~8000Heat左右)
	 * @param in 输入的ItemStack，最多为3个
	 */
	public CrafterRecipeNormal(ItemStack out, int heat, ItemStack... in) {
		if (in == null)
			throw new WrongUsageException("dont register null!");
		if (in.length > 3)
			throw new WrongUsageException("length must be within 3", input[0]);

		output = out;
		input = in;
		heatRequired = heat;
	}

	@Override
	public int getHeatConsumed() {
		return heatRequired;
	}

	@Override
	public ItemStack doCrafting(ItemStack[] inv, boolean isVirtual) {
		int[] req = buildReqTable();
		
		//检查输出槽状态
		{
			ItemStack outStack = inv[0];
			if(outStack != null && (outStack.getItem() != output.getItem() || outStack.getMaxStackSize() == outStack.stackSize))
				return null;
		}
		
		for(int i = 2; i <20; ++i) {
			ItemStack invStack = inv[i];
			if(invStack == null) 
				continue;
			
			//迭代输入并消耗~~
			for(int j = 0; j < 3 && j < input.length; ++j) {
				ItemStack inp = input[j];
				if(inp == null)
					continue;
				
				if(inp.getItem() == invStack.getItem() &&
					inp.getItemDamage() == invStack.getItemDamage()) {
					int consumption = Math.min(invStack.stackSize, req[j]);
					
					if(!isVirtual) {
						invStack.stackSize -= consumption;
						if(invStack.stackSize == 0) //Clear if used up
							inv[i] = null;
					}
					
					req[j] -= consumption;
					break; //Used by this input, not possible for another
				}
			}
		}
		
		for(int i = 0; i < 3; ++i) {
			if(req[i] > 0)
				return null;
		}
		
		return output.copy();
	}
	
	protected int[] buildReqTable() {
		//建立每个输入需要的stacksize之表
		int[] left = new int[3];
		for (int j = 0; j < input.length; j++) {
			if (input[j] != null) {
				left[j] = input[j].stackSize;
			} else
				left[j] = 0;
		}
		return left;
	}
	
	@Override
	public String toString() {
		StringBuilder out = new StringBuilder("[");
		out.append(output.getDisplayName()).append("]");
		return out.toString();
	}

	@Override
	public ItemStack[] getInputForDisplay() {
		return input;
	}

	@Override
	public ItemStack getOutputForDisplay() {
		return output;
	}
}
