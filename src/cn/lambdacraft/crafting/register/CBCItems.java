package cn.lambdacraft.crafting.register;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import cn.lambdacraft.api.energy.item.ICustomEnItem;
import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.core.item.CBCGenericItem;
import cn.lambdacraft.core.proxy.GeneralProps;
import cn.lambdacraft.crafting.item.*;
import cn.lambdacraft.crafting.item.ItemMaterial.EnumMaterial;
import cn.lambdacraft.deathmatch.item.ArmorHEV;
import cn.lambdacraft.deathmatch.item.ItemAttachment;
import cn.lambdacraft.deathmatch.item.ItemBattery;
import cn.lambdacraft.deathmatch.item.ammos.*;
import cn.lambdacraft.deathmatch.register.DMBlocks;
import cn.lambdacraft.deathmatch.register.DMItems;
import cn.lambdacraft.mob.register.CBCMobItems;
import cn.lambdacraft.xen.register.XENBlocks;
import cn.liutils.core.register.Config;
import cn.liutils.core.register.ConfigHandler;

import net.minecraft.block.Block;
import net.minecraft.command.WrongUsageException;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * core和crafting模块中所有的物品注册。
 * 
 * @author WeAthFolD, mkpoli
 */
public class CBCItems {

	public static ItemAmmo ammo_uranium;
	public static Ammo_9mm ammo_9mm;
	public static Ammo_357 ammo_357;
	public static Ammo_9mm2 ammo_9mm2;
	public static Ammo_bow ammo_bow;
	public static Ammo_rpg ammo_rpg;
	public static ItemAmmo ammo_argrenade, ammo_shotgun;

	public static ItemBullet bullet_9mm, bullet_steelbow;

	public static CBCGenericItem ingotSteel;

	public static ItemMaterial materials;
	public static CBCGenericItem lambdaChip;
	public static SteelBar ironBar;
	public static IngotUranium ingotUranium;
	public static LCRecord halfLife01, halfLife02, halfLife03;
	public static ItemSpray spray1, spray2;
	public static HLSpray spray;
	public static Item tin, copper, chip, xenCrystal;

	public static ItemBattery battery;

	/**
	 * 实际注册，请在Init中调用。
	 * 
	 * @param conf
	 */
	public static void init(Config conf) {

		ammo_uranium = new Ammo_uranium(ConfigHandler.getItemId(conf, 
				"itemAmmo_uranium", 0));
		ammo_9mm = new Ammo_9mm(ConfigHandler.getItemId(conf, "itemAmmo_9mm", 0));
		ammo_9mm2 = new Ammo_9mm2(ConfigHandler.getItemId(conf, "itemAmmo_9mm2", 0));
		ammo_357 = new Ammo_357(ConfigHandler.getItemId(conf, "itemAmmo_357", 0));
		ammo_bow = new Ammo_bow(ConfigHandler.getItemId(conf, "itemAmmo_bow", 0));
		ammo_rpg = new Ammo_rpg(ConfigHandler.getItemId(conf, "itemAmmo_RPG", 0));
		ammo_argrenade = new Ammo_argrenade(ConfigHandler.getItemId(conf, "itemAmmo_ARGrenade", 0));
		ammo_shotgun = new Ammo_shotgun(ConfigHandler.getItemId(conf, "itemBullet_Shotgun", 0));
		
		bullet_9mm = new Bullet_9mm(ConfigHandler.getItemId(conf, "itemBullet_9mm",
				0));
		bullet_steelbow = new Bullet_steelbow(ConfigHandler.getItemId(conf, 
				"steelbow", 0));

		materials = new ItemMaterial(ConfigHandler.getItemId(conf, "mat_a", 0));

		ironBar = new SteelBar(ConfigHandler.getItemId(conf, "ironBar", 0));
		lambdaChip = new CBCGenericItem(ConfigHandler.getItemId(conf, "lambdachip", 0)).setIAndU("lambdachip");
		
		ingotUranium = new IngotUranium(ConfigHandler.getItemId(conf, "ingotUranium", 0));
		ingotSteel = new CBCGenericItem(ConfigHandler.getItemId(conf, "itemRefinedIronIngot", 0)).setIAndU("steel");

		halfLife01 = new LCRecord(ConfigHandler.getItemId(conf, "halfLife01", GeneralProps.CAT_MISC), "hla", 0);
		halfLife02 = new LCRecord(ConfigHandler.getItemId(conf, "halfLife02", GeneralProps.CAT_MISC), "hlb", 1);
		halfLife03 = new LCRecord(ConfigHandler.getItemId(conf, "halfLife03", GeneralProps.CAT_MISC), "hlc", 2);
		
		spray1 = new ItemSpray(ConfigHandler.getItemId(conf, "spray1", GeneralProps.CAT_MISC), 0);
		spray2 = new ItemSpray(ConfigHandler.getItemId(conf, "spray2", GeneralProps.CAT_MISC), 1);
		
		spray = new HLSpray(ConfigHandler.getItemId(conf, "spray", GeneralProps.CAT_MISC));

		tin = new CBCGenericItem(ConfigHandler.getItemId(conf, "tin", GeneralProps.CAT_MISC)).setIAndU("tin");
		copper = new CBCGenericItem(ConfigHandler.getItemId(conf, "copper", GeneralProps.CAT_MISC)).setIAndU("copper");
		chip = new CBCGenericItem(ConfigHandler.getItemId(conf, "chip", GeneralProps.CAT_MISC)).setIAndU("chip");
		xenCrystal = new CBCGenericItem(ConfigHandler.getItemId(conf, "xencrystal", GeneralProps.CAT_MISC)).setIAndU("xencrystal");
		
		if(!CBCMod.ic2Installed) {
			battery = new ItemBattery(ConfigHandler.getItemId(conf, "battery", 3));
		}
	}

	/**
	 */
	public static void addItemRecipes() {
		// Recipes

		OreDictionary.registerOre("oreUranium", CBCBlocks.uraniumOre);
		OreDictionary.registerOre("ingotUranium", CBCItems.ingotUranium);
		OreDictionary.registerOre("ingotRefinedIron", CBCItems.ingotSteel);
		OreDictionary.registerOre("blockRefinedIron", CBCBlocks.blockRefined);
		OreDictionary.registerOre("blockMFE", CBCBlocks.storageS);
		OreDictionary.registerOre("blockMFSU", CBCBlocks.storageL);
		OreDictionary.registerOre("ingotTin", tin);
		OreDictionary.registerOre("ingotCopper", copper);

		//Resources
		ItemStack iSmaterials_1_0 = new ItemStack(CBCItems.materials, 1, 0), iSmaterials_1_1 = new ItemStack(
				CBCItems.materials, 1, 1), iSmaterials_1_2 = new ItemStack(
				CBCItems.materials, 1, 2), iSmaterials_1_3 = new ItemStack(
				CBCItems.materials, 1, 3), iSmaterials_1_4 = new ItemStack(
				CBCItems.materials, 1, 4), iSmaterials_1_5 = new ItemStack(
				CBCItems.materials, 1, 5), iSmaterials_1_6 = new ItemStack(
				CBCItems.materials, 1, 6), iSmaterials_1_7 = new ItemStack(
				CBCItems.materials, 1, 7), iSmaterials_1_8 = new ItemStack(
				CBCItems.materials, 1, 8), iSmaterials_1_9 = new ItemStack(
				CBCItems.materials, 1, 9), iSredstone = new ItemStack(
				Item.redstone), 
				iSlightStoneDust = new ItemStack(Item.glowstone),
				iStnt = new ItemStack(Block.tnt), iSblockLapis = new ItemStack(
				Block.blockLapis), iSdiamond = new ItemStack(Item.diamond), iSblazePowder = new ItemStack(
				Item.blazePowder), iSglass = new ItemStack(Block.glass), iSlambdaChip = new ItemStack(
				CBCItems.lambdaChip), iScoal = new ItemStack(Item.coal), iSgunpowder = new ItemStack(
				Item.gunpowder), iSingotGold = new ItemStack(Item.ingotGold), iSblockRedstone = new ItemStack(
				Block.blockRedstone), iSfurnace = new ItemStack(
				Block.furnaceIdle), iSchest = new ItemStack(Block.chest), iSweaponCrafter = new ItemStack(
				CBCBlocks.weaponCrafter), iSadvCrafter = new ItemStack(
				CBCBlocks.advCrafter), iSwire = new ItemStack(CBCBlocks.wire), iSemerald = new ItemStack(
				Item.emerald), iSchip = new ItemStack(chip), iSelectricCrafter = new ItemStack(
				CBCBlocks.elCrafter), iSbattery = new ItemStack(battery), iSgenFire = new ItemStack(
				CBCBlocks.genFire), iSstorageS = new ItemStack(
				CBCBlocks.storageS), iSstorageL = new ItemStack(
				CBCBlocks.storageL), iSgenLava = new ItemStack(
				CBCBlocks.genLava), iSbucketEmpty = new ItemStack(
				Item.bucketEmpty), iSgenSolar = new ItemStack(
				CBCBlocks.genSolar), iSnetherQuartz = new ItemStack(
				Item.netherQuartz), sfspieye = new ItemStack(
				Item.fermentedSpiderEye), srotten = new ItemStack(
				Item.rottenFlesh), smagma = new ItemStack(Item.magmaCream), sendereye = new ItemStack(
				Item.eyeOfEnder), smedkit = new ItemStack(DMItems.medkit);

		Object input[][] = {
				{ "ABA", "AAA", 'A', "ingotTin", 'B', iSglass },
				{ "AAA", "BCB", "DED", 'A', iSglass, 'B', iSblockRedstone, 'C', "blockRefinedIron", 'D', iSfurnace, 'E', iSchest },
				{ "AAA", "BCB", "DED", 'A', iSglass, 'B', iSdiamond, 'C', iSweaponCrafter, 'D', "ingotCopper", 'E', "blockRefinedIron" },
				{ "ABA", "ABA", "ABA", 'A', Block.cloth, 'B', "ingotCopper" },
				{ "ABA", "CDC", "AEA", 'A', iSlightStoneDust, 'B', iSemerald, 'C', iSdiamond, 'D', iSchip, 'E', iSglass },
				{ "ABA", "CDC", "EAE", 'A', iSglass, 'B', iSwire, 'C', iSlambdaChip, 'D', iSadvCrafter, 'E', iSlightStoneDust },
				{ "ABA", "ACA", "ACA", 'A', "ingotTin", 'B', iSwire, 'C', iSlightStoneDust },
				{ "AAA", "BCB", "DED", 'A', iSglass, 'B', battery, 'C', iSredstone, 'D', iSchip, 'E', "blockRefinedIron" },
				{ "ABA", "CDC", "ABA", 'A', iSwire, 'B', iSnetherQuartz, 'C', iSredstone, 'D', "ingotRefinedIron" },
				{ "ABA", "CDC", "AEA", 'A', iSglass, 'B', smedkit, 'C', iSlambdaChip, 'D', "blockMFE", 'E', "blockRefinedIron" },
				{ "AAA", "CDC", "BEB", 'A', iSglass, 'B', smedkit, 'C', iSlambdaChip, 'D', "blockMFE", 'E', "blockRefinedIron" },
				{ "AAA", "CDC", "BEB", 'A', iSglass, 'B', iSlambdaChip, 'C', battery, 'D', "blockMFSU", 'E', "blockRefinedIron" },
				{ "AAA", "AAA", "AAA", 'A', "ingotRefinedIron" } ,
				{"A  ", "A  ", 'A', "ingotRefinedIron"},
				{ "ABA", "CDC", "AEA", 'A', iSglass, 'B', iSdiamond, 'C', "blockMFSU", 'D', iSlambdaChip, 'E', iSemerald},
				{" A ", "BCB", 'A', iSglass, 'B', iSwire, 'C', iSchip},
				{" AA", "ABA", "ABA", 'A', "ingotCopper", 'B', iSredstone},
				{" AA", "ABA", "ABA", 'A', "ingotTin", 'B', iSredstone},
				{" AA", "ABA", "ABA", 'A', "ingotTin", 'B', iSlightStoneDust}
		};

		ItemStack output[] = { 
				materials.newStack(10, EnumMaterial.BOX),
				iSweaponCrafter,
				iSadvCrafter, 
				new ItemStack(CBCBlocks.wire, 6),
				new ItemStack(lambdaChip, 2),
				iSelectricCrafter, 
				iSbattery,
				iSstorageS, iSchip,
				new ItemStack(DMBlocks.medkitFiller),
				new ItemStack(DMBlocks.healthCharger),
				new ItemStack(DMBlocks.armorCharger),
				new ItemStack(CBCBlocks.blockRefined),
				new ItemStack(CBCItems.ironBar, 5),
				new ItemStack(XENBlocks.portal),
				new ItemStack(CBCMobItems.sentrySyncer, 2),
				new ItemStack(spray1),
				new ItemStack(spray2),
				new ItemStack(spray)
		};
		
		if(!CBCMod.ic2Installed) {
			//Add LC Only Recipes
			Object[][] input2 = {
					{ " A ", "BCB", "DED", 'A', iSstorageS, 'B', iSglass, 'C', "blockRefinedIron", 'D', iSblockLapis, 'E', iSfurnace },
					{ " A ", "BCB", "DED", 'A', iSglass, 'B', iSblazePowder, 'C', iSbucketEmpty, 'D', "ingotRefinedIron", 'E', iSgenFire },
					{ "AAA", "BCB", "DED", 'A', iSglass, 'B', iSnetherQuartz, 'C', iSchip, 'D', "ingotRefinedIron", 'E', iSgenFire },
			};
			
			ItemStack[] output2 = { iSgenFire, iSgenLava, iSgenSolar };
			addOreRecipes(output2, input2);
		}
		addOreRecipes(output, input);
		
		GameRegistry.addShapelessRecipe(new ItemStack(halfLife01), lambdaChip, Item.diamond);
		GameRegistry.addShapelessRecipe(new ItemStack(halfLife02), lambdaChip, Item.emerald);
		GameRegistry.addShapelessRecipe(new ItemStack(halfLife03), lambdaChip, Item.eyeOfEnder);
		GameRegistry.addShapelessRecipe(new ItemStack(XENBlocks.crystal), iSlightStoneDust, iSdiamond);
		GameRegistry.addShapelessRecipe(new ItemStack(DMItems.weapon_crowbar_el), lambdaChip, new ItemStack(DMItems.weapon_crowbar));
		GameRegistry.addShapelessRecipe(iSstorageL, CBCBlocks.storageS, lambdaChip);
		GameRegistry.addRecipe(new RecipeHEVAttach());
		
		//Materials
		IRecipe recipes[] = {
				new ShapelessOreRecipe(materials.newStack(2, EnumMaterial.ARMOR), iSmaterials_1_0, "blockRefinedIron", iSdiamond, iSlambdaChip),
				new ShapelessOreRecipe(materials.newStack(4, EnumMaterial.AMMUNITION), iSmaterials_1_0, "ingotCopper", iSredstone, iSgunpowder),
				new ShapelessOreRecipe(materials.newStack(4, EnumMaterial.ACCESSORIES), iSmaterials_1_0, "ingotCopper", iSredstone, iScoal),
				new ShapelessOreRecipe(materials.newStack(4, EnumMaterial.EXPLOSIVE), iSmaterials_1_0, "ingotRefinedIron", iStnt, iSgunpowder),
				new ShapelessOreRecipe(materials.newStack(2, EnumMaterial.HEAVY), iSmaterials_1_0, "blockRefinedIron", iSblockLapis, iSblazePowder),
				new ShapelessOreRecipe(materials.newStack(2, EnumMaterial.LIGHT), iSmaterials_1_0, "ingotRefinedIron", "ingotCopper", iSlightStoneDust),
				new ShapelessOreRecipe(materials.newStack(2, EnumMaterial.PISTOL), iSmaterials_1_0, "ingotRefinedIron", "ingotCopper", "ingotRefinedIron"),
				new ShapelessOreRecipe(materials.newStack(3, EnumMaterial.BIO), iSmaterials_1_0, srotten, sendereye, CBCMobItems.dna),
				new ShapelessOreRecipe(materials.newStack(2, EnumMaterial.TECH), iSmaterials_1_0, iSdiamond, iSlambdaChip, iSlightStoneDust),
				new RecipeRepair(spray1, iSredstone),
				new RecipeRepair(spray2, iSredstone),
				new RecipeRepair(spray, iSlightStoneDust)
		};
		for(IRecipe r : recipes)
			GameRegistry.addRecipe(r);
		
		

		//Smelting
		GameRegistry.addSmelting(Item.ingotIron.itemID, new ItemStack(ingotSteel.itemID, 1, 0), 3.0F);
		GameRegistry.addSmelting(CBCBlocks.uraniumOre.blockID, new ItemStack(ingotUranium), 5);
		GameRegistry.addSmelting(CBCBlocks.oreCopper.blockID, new ItemStack(copper), 2);
		GameRegistry.addSmelting(CBCBlocks.oreTin.blockID, new ItemStack(tin), 2);
		GameRegistry.addSmelting(XENBlocks.crystal.blockID, new ItemStack(xenCrystal), 3);
		
		//ChestGen
		WeightedRandomChestContent gens_dungeon[] = {
				new WeightedRandomChestContent(new ItemStack(halfLife01), 1, 1, 5),
				new WeightedRandomChestContent(new ItemStack(halfLife02), 1, 1, 5),
				new WeightedRandomChestContent(new ItemStack(halfLife03), 1, 1, 5),
				new WeightedRandomChestContent(new ItemStack(ironBar), 3, 8, 50),
				new WeightedRandomChestContent(new ItemStack(DMItems.physCalibur), 1, 1, 1),
				new WeightedRandomChestContent(new ItemStack(DMItems.weapon_crowbar_el), 1, 1, 10)
		};
		for(WeightedRandomChestContent gen : gens_dungeon) {
			ChestGenHooks.addItem("dungeonChest", gen);
			ChestGenHooks.addItem("villageBlacksmith", gen);
		}
		
		WeightedRandomChestContent gens_desert[] = {
				new WeightedRandomChestContent(new ItemStack(ironBar), 3, 8, 50),
				new WeightedRandomChestContent(new ItemStack(DMItems.physCalibur), 1, 1, 1),
				new WeightedRandomChestContent(new ItemStack(DMItems.weapon_crowbar_el), 1, 1, 10),
				new WeightedRandomChestContent(new ItemStack(spray), 1, 1, 4),
				new WeightedRandomChestContent(new ItemStack(lambdaChip), 2, 5, 3)
		};
		for(WeightedRandomChestContent gen : gens_desert) {
			ChestGenHooks.addItem("pyramidDesertyChest", gen);
			ChestGenHooks.addItem("pyramidJungleChest", gen);
		}
			
		
	}

	private static class RecipeElectricShapeless implements IRecipe {

		protected ArrayList<ItemStack> recipeItems = new ArrayList();
		protected ItemStack result, source;

		public RecipeElectricShapeless(ItemStack output, ItemStack original,
				ItemStack... add) {
			this.result = output;
			this.source = original;
			Collections.addAll(recipeItems, add);
		}

		/**
		 * Used to check if a recipe matches current crafting inventory
		 */
		@Override
		public boolean matches(InventoryCrafting par1InventoryCrafting,
				World par2World) {
			ArrayList arraylist = new ArrayList(this.recipeItems);
			arraylist.add(source);

			for (int i = 0; i < 3; ++i) {
				for (int j = 0; j < 3; ++j) {
					ItemStack itemstack = par1InventoryCrafting
							.getStackInRowAndColumn(j, i);

					if (itemstack != null) {
						boolean flag = false;
						Iterator iterator = arraylist.iterator();

						while (iterator.hasNext()) {
							ItemStack itemstack1 = (ItemStack) iterator.next();

							if (itemstack.itemID == itemstack1.itemID) {
								flag = true;
								arraylist.remove(itemstack1);
								break;
							}
						}

						if (!flag) {
							return false;
						}
					}
				}
			}

			return arraylist.isEmpty();
		}

		@Override
		public ItemStack getCraftingResult(InventoryCrafting inv) {
			ItemStack is = result.copy();
			ItemStack in = findIdentifyStack(inv);
			if (in == null)
				return null;
			ICustomEnItem item = (ICustomEnItem) in.getItem();
			ICustomEnItem item2 = (ICustomEnItem) is.getItem();
			int energy = item.discharge(in, Integer.MAX_VALUE, 5, true, true);
			item2.charge(is, energy, 5, true, false);
			return is;
		}

		private ItemStack findIdentifyStack(InventoryCrafting inv) {
			ItemStack item = null;
			for (int i = 0; i < 3; i++)
				for (int j = 0; j < 3; j++) {
					ItemStack s = inv.getStackInRowAndColumn(i, j);
					if (s != null && s.itemID == source.itemID) {
						if (item != null)
							return null;
						item = s;
					}
				}
			return item;
		}

		@Override
		public int getRecipeSize() {
			return this.recipeItems.size() + 1;
		}

		@Override
		public ItemStack getRecipeOutput() {
			return result;
		}

	}
	
	private static class RecipeHEVAttach implements IRecipe {

		/**
		 * Used to check if a recipe matches current crafting inventory
		 */
		@Override
		public boolean matches(InventoryCrafting inv,
				World par2World) {
			ItemStack is1 = null, is2 = null;
			for(int i = 0; i < inv.getSizeInventory(); i++) {
				ItemStack is = inv.getStackInSlot(i);
				if(is != null) {
					if(is.getItem() instanceof ItemAttachment) {
						if(is1 != null)
							return false;
						is1 = is;
					} else if(is.getItem() instanceof ArmorHEV) {
						if(is2 != null)
							return false;
						is2 = is;
					}
				}
			}
			if(is1 != null && is2 != null) {
				return ArmorHEV.isAttachAvailable(is2, ArmorHEV.attachForStack(is1));
			}
			return false;
		}

		@Override
		public ItemStack getCraftingResult(InventoryCrafting inv) {
			ItemStack hev = null, attach = null;
			for(int i = 0; i < inv.getSizeInventory(); i++) {
				ItemStack is = inv.getStackInSlot(i);
				if(is != null) {
					if(is.getItem() instanceof ItemAttachment) {
						attach = is;
					} else if(is.getItem() instanceof ArmorHEV) {
						hev = is;
					}
				}
			}
			ItemStack is = hev.copy();
			ArmorHEV.addAttachTo(is, ArmorHEV.attachForStack(attach));
			return is;
		}

		@Override
		public int getRecipeSize() {
			return 2;
		}

		@Override
		public ItemStack getRecipeOutput() {
			return null;
		}

	}

	/**
	 * 使用一个物品来修复另一个物品，1stack数对应物品的1damage。
	 * 
	 * @author WeAthFolD
	 * 
	 */
	private static class RecipeRepair implements IRecipe {

		private Item itemToRepair;
		private ItemStack repairMat;

		public RecipeRepair(Item item, ItemStack mat) {
			itemToRepair = item;
			repairMat = mat;
		}

		@Override
		public boolean matches(InventoryCrafting inv, World world) {
			boolean b1 = false, b2 = false;
			for (int i = 0; i < inv.getSizeInventory(); i++) {
				ItemStack s = inv.getStackInSlot(i);
				if (s != null) {
					if (s.itemID == itemToRepair.itemID) {
						b1 = true;
					} else if (s.itemID == repairMat.itemID) {
						b2 = true;
					}
				}
			}
			return b1 && b2;
		}

		@Override
		public ItemStack getCraftingResult(InventoryCrafting inv) {
			int cntRep = 0;
			ItemStack theItem = null;
			for (int i = 0; i < inv.getSizeInventory(); i++) {
				ItemStack s = inv.getStackInSlot(i);
				if (s != null) {
					if (s.itemID == itemToRepair.itemID) {
						if (theItem != null) {
							return null;
						}
						theItem = s;
					} else if (s.itemID == repairMat.itemID)
						++cntRep;
				}
			}
			if (theItem == null) {
				return null;
			}
			int damage = theItem.getItemDamage();
			damage -= cntRep;
			if (damage < 0)
				damage = 0;
			return new ItemStack(itemToRepair, 1, damage);
		}

		@Override
		public int getRecipeSize() {
			return 9;
		}

		@Override
		public ItemStack getRecipeOutput() {
			return null;
		}

	}

	private static void addOreRecipes(ItemStack[] output, Object[][] input) {
		if (output.length != input.length) {
			throw new WrongUsageException("Two par's size should be the same",
					input[0]);
		}
		for (int i = 0; i < output.length; i++) {
			ItemStack out = output[i];
			Object[] in = input[i];
			boolean flag = true;
			if (flag)
				GameRegistry.addRecipe(new ShapedOreRecipe(out, in));
		}
	}

	private static void addRecipes(ItemStack[] output, Object[][] input) {
		if (output.length != input.length) {
			throw new WrongUsageException("Two par's size should be the same",
					input[0]);
		}
		for (int i = 0; i < output.length; i++) {
			ItemStack out = output[i];
			Object[] in = input[i];
			boolean flag = true;
			if (flag)
				GameRegistry.addRecipe(out, in);
		}
	}

}
