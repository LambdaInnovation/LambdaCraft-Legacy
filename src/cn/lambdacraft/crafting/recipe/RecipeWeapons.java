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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.command.WrongUsageException;
import net.minecraft.item.ItemStack;

public class RecipeWeapons {

	public static List<RecipeCrafter> recipes[];
	public static List<RecipeCrafter> advancedRecipes[];
	public static List<RecipeCrafter> recipeEC[];

	public static List<String> pageDescriptions = new ArrayList();
	private static boolean finished = false;

	public static String getDescription(int page) {
		return pageDescriptions.get(page);
	}

	public static void InitializeRecipes(int pages, String[] ds) {
		if (getRecipePages() > 0)
			throw new WrongUsageException("Recipes already loaded");
		if (pages != ds.length)
			throw new WrongUsageException(
					"size does not match for adding recipe", pages);

		Collections.addAll(pageDescriptions, ds);
		recipes = new ArrayList[pages];
		advancedRecipes = new ArrayList[pages];
		for (int i = 0; i < pages; i++) {
			recipes[i] = new ArrayList<RecipeCrafter>();
			advancedRecipes[i] = new ArrayList<RecipeCrafter>();
		}
	}

	public static void initliazeECRecipes(int pages, String[] additional) {
		if (getRecipePages() == 0)
			throw new WrongUsageException(
					"Recipes not loaded while trying to load the EC recipes");
		if (pages != additional.length)
			throw new WrongUsageException(
					"size does not match for adding recipe", pages);
		recipeEC = new ArrayList[pages + pageDescriptions.size()];
		for (int i = 0; i < recipeEC.length; i++)
			recipeEC[i] = new ArrayList();
		Collections.addAll(pageDescriptions, additional);

	}

	public static void close() {
		finished = true;
	}

	public static void addNormalRecipe(int page, RecipeCrafter... entry) {
		if (finished)
			throw new RuntimeException(
					"Trying to add a weapon recipe while finished initializing");
		for (RecipeCrafter e : entry) {
			recipes[page].add(e);
			recipeEC[page].add(e);
		}
	}

	public static void addAdvancedRecipe(int page, RecipeCrafter... entry) {
		if (finished)
			throw new RuntimeException(
					"Trying to add a weapon recipe while finished initializing");
		for (RecipeCrafter e : entry) {
			advancedRecipes[page].add(e);
			recipeEC[page].add(e);
		}
	}

	/*
	 * 页面数是绝对页面数。
	 */
	public static void addECSpecificRecipe(int page, RecipeCrafter... entry) {
		if (finished)
			throw new RuntimeException(
					"Trying to add a EC weapon recipe while finished initializing");
		for (RecipeCrafter e : entry) {
			recipeEC[page].add(e);
		}
	}

	public static boolean doesNeedScrollBar(int page) {
		return recipes[page].size() > 3;
	}

	public static boolean doesAdvNeedScrollBar(int page) {
		return advancedRecipes[page].size() > 3;
	}

	public static boolean doesECNeedScrollBar(int page) {
		return recipeEC[page].size() > 3;
	}

	public static RecipeCrafter getRecipe(int page, ItemStack is) {
		if (is == null)
			return null;

		for (RecipeCrafter r : recipes[page]) {
			if (is.getItem() == r.output.getItem())
				return r;
		}
		return null;
	}

	public static RecipeCrafter getAdvancedRecipe(int page, ItemStack is) {
		if (is == null)
			return null;

		for (RecipeCrafter r : advancedRecipes[page]) {
			if (is.getItem() == r.output.getItem())
				return r;
		}
		return null;
	}

	public static RecipeCrafter getECRecipe(int page, ItemStack is) {
		if (is == null)
			return null;

		for (RecipeCrafter r : recipeEC[page]) {
			if (is.getItem() == r.output.getItem())
				return r;
		}
		return null;
	}

	public static int getRecipeLength(int page) {
		return recipes[page].size();
	}

	public static int getAdvRecipeLength(int page) {
		return advancedRecipes[page].size();
	}

	public static int getECRecipeLength(int page) {
		return recipeEC[page].size();
	}

	public static int getRecipePages() {
		return recipes != null ? recipes.length : 0;
	}

	public static int getAdvRecipePages() {
		return advancedRecipes != null ? advancedRecipes.length : 0;
	}

	public static int getECRecipePages() {
		return recipeEC != null ? recipeEC.length : 0;
	}

	public static RecipeCrafter getRecipe(int page, int i) {
		if (recipes[page].size() < i + 1)
			return null;
		return recipes[page].get(i);
	}

	public static RecipeCrafter getAdvRecipe(int page, int i) {
		if (advancedRecipes[page].size() < i + 1)
			return null;
		return advancedRecipes[page].get(i);
	}

	public static RecipeCrafter getECRecipe(int page, int i) {
		if (recipeEC[page].size() < i + 1)
			return null;
		return recipeEC[page].get(i);
	}

}
