package cn.lambdacraft.crafting.register;

import ic2.api.item.ISpecialElectricItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import net.minecraft.block.Block;
import net.minecraft.command.WrongUsageException;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cn.lambdacraft.core.item.CBCGenericItem;
import cn.lambdacraft.crafting.item.Bullet_9mm;
import cn.lambdacraft.crafting.item.Bullet_steelbow;
import cn.lambdacraft.crafting.item.HLSpray;
import cn.lambdacraft.crafting.item.IngotUranium;
import cn.lambdacraft.crafting.item.ItemMaterial;
import cn.lambdacraft.crafting.item.ItemMaterial.EnumMaterial;
import cn.lambdacraft.crafting.item.ItemSpray;
import cn.lambdacraft.crafting.item.LCRecord;
import cn.lambdacraft.crafting.item.SteelBar;
import cn.lambdacraft.deathmatch.item.ArmorHEV;
import cn.lambdacraft.deathmatch.item.ItemAttachment;
import cn.lambdacraft.deathmatch.item.ItemBattery;
import cn.lambdacraft.deathmatch.item.RecipeMedkitFill;
import cn.lambdacraft.deathmatch.item.ammos.Ammo_357;
import cn.lambdacraft.deathmatch.item.ammos.Ammo_9mm;
import cn.lambdacraft.deathmatch.item.ammos.Ammo_9mm2;
import cn.lambdacraft.deathmatch.item.ammos.Ammo_argrenade;
import cn.lambdacraft.deathmatch.item.ammos.Ammo_bow;
import cn.lambdacraft.deathmatch.item.ammos.Ammo_rpg;
import cn.lambdacraft.deathmatch.item.ammos.Ammo_shotgun;
import cn.lambdacraft.deathmatch.item.ammos.Ammo_uranium;
import cn.lambdacraft.deathmatch.item.ammos.ItemAmmo;
import cn.lambdacraft.deathmatch.register.DMBlocks;
import cn.lambdacraft.deathmatch.register.DMItems;
import cn.lambdacraft.mob.register.CBCMobItems;
import cn.lambdacraft.terrain.register.XenBlocks;
import cn.liutils.api.util.RegUtils;
//import cn.lambdacraft.terrain.register.XenBlocks;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * core和crafting模块中所有的物品注册。
 * 
 * @author WeAthFolD, mkpoli
 */
public class CBCItems {

	public static Item 
		ammo_uranium,
		ammo_9mm,
		ammo_357,
		ammo_9mm2,
		ammo_bow,
		ammo_rpg,
		ammo_argrenade,
		ammo_shotgun,
		bullet_9mm, 
		bullet_steelbow,
		ingotSteel,
		lambdaChip,
		ironBar,
		ingotUranium,
		halfLife01,
		halfLife02,
		halfLife03,
		tin,
		copper,
		chip,
		xenCrystal,
		spray1[],
		spray3;
	
	public static ItemMaterial materials;
	public static ItemBattery battery;

	/**
	 * 实际注册，请在Init中调用。
	 * 
	 * @param conf
	 */
	public static void init(Configuration conf) {

		ammo_uranium = RegUtils.reg(Ammo_uranium.class, "lc_uranium_ingot");
		ammo_9mm = RegUtils.reg(Ammo_9mm.class, "lc_9mmammo");
		ammo_9mm2 = RegUtils.reg(Ammo_9mm2.class, "lc_9mmammo2");
		ammo_357 = RegUtils.reg(Ammo_357.class, "lc_357ammo");
		ammo_bow = RegUtils.reg(Ammo_bow.class, "lc_bowammo");
		ammo_rpg = RegUtils.reg(Ammo_rpg.class, "lc_rpgammo");
		ammo_argrenade = RegUtils.reg(Ammo_argrenade.class, "lc_argrenade");
		ammo_shotgun = RegUtils.reg(Ammo_shotgun.class, "lc_sgammo");
		
		bullet_9mm = RegUtils.reg(Bullet_9mm.class, "lc_9mmblt");
		bullet_steelbow = RegUtils.reg(Bullet_steelbow.class, "lc_steelbow");

		materials = (ItemMaterial) RegUtils.reg(ItemMaterial.class, "lc_mats");

		ironBar = RegUtils.reg(SteelBar.class, "lc_ironbar");
		lambdaChip = ((CBCGenericItem)RegUtils.reg(CBCGenericItem.class, "lc_lchip")).setIAndU("lambdachip");
		
		ingotUranium = RegUtils.reg(IngotUranium.class, "lc_radioactive");
		ingotSteel = ((CBCGenericItem)RegUtils.reg(CBCGenericItem.class, "lc_universalrule")).setIAndU("steel");

		halfLife01 = new LCRecord("hla", 0);
		halfLife02 = new LCRecord("hlb", 1);
		halfLife03 = new LCRecord("hlc", 2);
		GameRegistry.registerItem(halfLife01, "lc_rec0");
		GameRegistry.registerItem(halfLife02, "lc_rec1");
		GameRegistry.registerItem(halfLife03, "lc_rec2");
		
		spray1 = RegUtils.reg(ItemSpray.class, 2, "lc_spray");
		spray3 = RegUtils.reg(HLSpray.class, "lc_hlspray");

		tin = ((CBCGenericItem)RegUtils.reg(CBCGenericItem.class, "lc_tin")).setIAndU("tin");
		copper = ((CBCGenericItem)RegUtils.reg(CBCGenericItem.class, "lc_copper")).setIAndU("copper");
		chip = ((CBCGenericItem)RegUtils.reg(CBCGenericItem.class, "lc_chip")).setIAndU("chip");
		xenCrystal = ((CBCGenericItem)RegUtils.reg(CBCGenericItem.class, "lc_xcrystal")).setIAndU("xencrystal");
		
		battery = (ItemBattery) RegUtils.reg(ItemBattery.class, "lc_battery");
	}

	/**
	 */
	public static void addItemRecipes() {
		// Recipes

		OreDictionary.registerOre("oreUranium", CBCBlocks.uraniumOre);
		OreDictionary.registerOre("oreTin", CBCBlocks.oreTin);
		OreDictionary.registerOre("oreCopper", CBCBlocks.oreCopper);
		
		OreDictionary.registerOre("ingotUranium", CBCItems.ingotUranium);
		OreDictionary.registerOre("ingotRefinedIron", CBCItems.ingotSteel);
		OreDictionary.registerOre("blockRefinedIron", CBCBlocks.blockRefined);
		OreDictionary.registerOre("blockMFE", CBCBlocks.storageS);
		OreDictionary.registerOre("blockMFSU", CBCBlocks.storageL);
		OreDictionary.registerOre("ingotTin", tin);
		OreDictionary.registerOre("ingotCopper", copper);

		//Resources
		ItemStack 
			smaterials_1_0 = new ItemStack(CBCItems.materials, 1, 0), 
			smaterials_1_1 = new ItemStack(CBCItems.materials, 1, 1), 
			smaterials_1_2 = new ItemStack(CBCItems.materials, 1, 2), 
			smaterials_1_3 = new ItemStack(CBCItems.materials, 1, 3), 
			smaterials_1_4 = new ItemStack(CBCItems.materials, 1, 4), 
			smaterials_1_5 = new ItemStack(CBCItems.materials, 1, 5), 
			smaterials_1_6 = new ItemStack(CBCItems.materials, 1, 6), 
			smaterials_1_7 = new ItemStack(CBCItems.materials, 1, 7), 
			smaterials_1_8 = new ItemStack(CBCItems.materials, 1, 8),
			smaterials_1_9 = new ItemStack(CBCItems.materials, 1, 9), 
			sredstone = new ItemStack(Items.redstone), 
			slightStoneDust = new ItemStack(Items.glowstone_dust),
			stnt = new ItemStack(Blocks.tnt), 
			sblockLapis = new ItemStack(Blocks.lapis_block), 
			sdiamond = new ItemStack(Items.diamond), 
			sblazePowder = new ItemStack(Items.blaze_powder), 
			sglass = new ItemStack(Blocks.glass), 
			slambdaChip = new ItemStack(CBCItems.lambdaChip),
			scoal = new ItemStack(Items.coal), 
			sgunpowder = new ItemStack(Items.gunpowder), 
			singotGold = new ItemStack(Items.gold_ingot), 
			sblockRedstone = new ItemStack(Blocks.redstone_block), 
			sfurnace = new ItemStack(Blocks.furnace), 
			schest = new ItemStack(Blocks.chest), 
			sweaponCrafter = new ItemStack(CBCBlocks.weaponCrafter), 
			sadvCrafter = new ItemStack(CBCBlocks.advCrafter),
			swire = new ItemStack(CBCBlocks.wire), semerald = new ItemStack(Items.emerald),
			schip = new ItemStack(chip), 
			selectricCrafter = new ItemStack(CBCBlocks.elCrafter), 
			sbattery = new ItemStack(battery), 
			sgenFire = new ItemStack(CBCBlocks.genFire), 
			sstorageS = new ItemStack(CBCBlocks.storageS), 
			sstorageL = new ItemStack(CBCBlocks.storageL), 
			sgenLava = new ItemStack(CBCBlocks.genLava), 
			sbucketEmpty = new ItemStack(Items.bucket),
			sgenSolar = new ItemStack(CBCBlocks.genSolar), 
			snetherQuartz = new ItemStack(Items.quartz), 
			sfspieye = new ItemStack(Items.fermented_spider_eye), 
			srotten = new ItemStack(Items.rotten_flesh), 
			smagma = new ItemStack(Items.magma_cream), 
			sendereye = new ItemStack(Items.ender_eye),
			smedkit = new ItemStack(DMItems.medkit);

		Object input[][] = {
				{ "ABA", "AAA", 'A', "ingotTin", 'B', sglass },
				{ "AAA", "BCB", "DED", 'A', sglass, 'B', sblockRedstone, 'C', "blockRefinedIron", 'D', sfurnace, 'E', schest },
				{ "AAA", "BCB", "DED", 'A', sglass, 'B', sdiamond, 'C', sweaponCrafter, 'D', "ingotCopper", 'E', "blockRefinedIron" },
				{ "ABA", "ABA", "ABA", 'A', Blocks.wool, 'B', "ingotCopper" },
				{ "ABA", "CDC", "AEA", 'A', slightStoneDust, 'B', semerald, 'C', sdiamond, 'D', schip, 'E', sglass },
				{ "ABA", "CDC", "EAE", 'A', sglass, 'B', swire, 'C', slambdaChip, 'D', sadvCrafter, 'E', slightStoneDust },
				{ "ABA", "ACA", "ACA", 'A', "ingotTin", 'B', swire, 'C', slightStoneDust },
				{ "AAA", "BCB", "DED", 'A', sglass, 'B', battery, 'C', sredstone, 'D', schip, 'E', "blockRefinedIron" },
				{ "ABA", "CDC", "ABA", 'A', swire, 'B', snetherQuartz, 'C', sredstone, 'D', "ingotRefinedIron" },
				{ "AAA", "CDC", "BEB", 'A', sglass, 'B', smedkit, 'C', slambdaChip, 'D', "blockMFE", 'E', "blockRefinedIron" },
				{ "AAA", "CDC", "BEB", 'A', sglass, 'B', slambdaChip, 'C', battery, 'D', "blockMFSU", 'E', "blockRefinedIron" },
				{ "AAA", "AAA", "AAA", 'A', "ingotRefinedIron" } ,
				{"A  ", "A  ", 'A', "ingotRefinedIron"},
				{ "ABA", "CDC", "AEA", 'A', sglass, 'B', sdiamond, 'C', "blockMFSU", 'D', slambdaChip, 'E', semerald},
				{" A ", "BCB", 'A', sglass, 'B', swire, 'C', schip},
				{" AA", "ABA", "ABA", 'A', "ingotCopper", 'B', sredstone},
				{" AA", "ABA", "ABA", 'A', "ingotTin", 'B', sredstone},
				{" AA", "ABA", "ABA", 'A', "ingotTin", 'B', slightStoneDust}
		};

		ItemStack output[] = { 
				ItemMaterial.newStack(materials, 10, EnumMaterial.BOX),
				sweaponCrafter,
				sadvCrafter, 
				new ItemStack(CBCBlocks.wire, 6),
				new ItemStack(lambdaChip, 2),
				selectricCrafter, 
				sbattery,
				sstorageS, 
				schip,
				new ItemStack(DMBlocks.healthCharger),
				new ItemStack(DMBlocks.armorCharger),
				new ItemStack(CBCBlocks.blockRefined),
				new ItemStack(CBCItems.ironBar, 5),
				new ItemStack(XenBlocks.xenPortal),
				new ItemStack(CBCMobItems.sentrySyncer, 2),
				new ItemStack(spray1[0]),
				new ItemStack(spray1[1]),
				new ItemStack(spray3)
		};
		addOreRecipes(output, input);
		
		Object[][] input2 = {
				{ " A ", "BCB", "DED", 'A', sstorageS, 'B', sglass, 'C', "blockRefinedIron", 'D', sblockLapis, 'E', sfurnace },
				{ " A ", "BCB", "DED", 'A', sglass, 'B', sblazePowder, 'C', sbucketEmpty, 'D', "ingotRefinedIron", 'E', sgenFire },
				{ "AAA", "BCB", "DED", 'A', sglass, 'B', snetherQuartz, 'C', schip, 'D', "ingotRefinedIron", 'E', sgenFire },
		};
		
		ItemStack[] output2 = { sgenFire, sgenLava, sgenSolar };
		addOreRecipes(output2, input2);
		
		GameRegistry.addShapelessRecipe(new ItemStack(halfLife01), lambdaChip, Items.diamond);
		GameRegistry.addShapelessRecipe(new ItemStack(halfLife02), lambdaChip, Items.emerald);
		GameRegistry.addShapelessRecipe(new ItemStack(halfLife03), lambdaChip, Items.ender_eye);
		GameRegistry.addShapelessRecipe(new ItemStack(XenBlocks.crystal), slightStoneDust, sdiamond);
		GameRegistry.addShapelessRecipe(new ItemStack(DMItems.weapon_crowbar_el), lambdaChip, new ItemStack(DMItems.weapon_crowbar));
		GameRegistry.addShapelessRecipe(sstorageL, CBCBlocks.storageS, lambdaChip);
		GameRegistry.addRecipe(new RecipeHEVAttach());
		GameRegistry.addRecipe(new RecipeMedkitFill());
		GameRegistry.addRecipe(new RecipeRepair(ammo_uranium, new ItemStack(ingotUranium)).setRepairAmt(34));
		GameRegistry.addRecipe(new RecipeRepair(ammo_bow, new ItemStack(bullet_steelbow)));
		GameRegistry.addRecipe(new RecipeRepair(ammo_9mm, new ItemStack(bullet_9mm)));
		GameRegistry.addRecipe(new RecipeRepair(ammo_9mm2, new ItemStack(bullet_9mm)));
		
		//Materials
		IRecipe recipes[] = {
				new ShapelessOreRecipe(ItemMaterial.newStack(materials, 2, EnumMaterial.ARMOR), smaterials_1_0, "blockRefinedIron", sdiamond, slambdaChip),
				new ShapelessOreRecipe(ItemMaterial.newStack(materials, 4, EnumMaterial.AMMUNITION), smaterials_1_0, "ingotCopper", sredstone, sgunpowder),
				new ShapelessOreRecipe(ItemMaterial.newStack(materials, 4, EnumMaterial.ACCESSORIES), smaterials_1_0, "ingotCopper", sredstone, scoal),
				new ShapelessOreRecipe(ItemMaterial.newStack(materials, 4, EnumMaterial.EXPLOSIVE), smaterials_1_0, "ingotRefinedIron", stnt, sgunpowder),
				new ShapelessOreRecipe(ItemMaterial.newStack(materials, 2, EnumMaterial.HEAVY), smaterials_1_0, "blockRefinedIron", sblockLapis, sblazePowder),
				new ShapelessOreRecipe(ItemMaterial.newStack(materials, 2, EnumMaterial.LIGHT), smaterials_1_0, "ingotRefinedIron", "ingotCopper", slightStoneDust),
				new ShapelessOreRecipe(ItemMaterial.newStack(materials, 2, EnumMaterial.PISTOL), smaterials_1_0, "ingotRefinedIron", "ingotCopper", "ingotRefinedIron"),
				new ShapelessOreRecipe(ItemMaterial.newStack(materials, 3, EnumMaterial.BIO), smaterials_1_0, srotten, sendereye, CBCMobItems.dna),
				new ShapelessOreRecipe(ItemMaterial.newStack(materials, 2, EnumMaterial.TECH), smaterials_1_0, sdiamond, slambdaChip, slightStoneDust),
				new RecipeRepair(spray1[0], sredstone),
				new RecipeRepair(spray1[1], sredstone),
				new RecipeRepair(spray3, slightStoneDust)
		};
		for(IRecipe r : recipes)
			GameRegistry.addRecipe(r);
		
		

		//Smelting
		GameRegistry.addSmelting(Items.iron_ingot, new ItemStack(ingotSteel, 1, 0), 3.0F);
		GameRegistry.addSmelting(CBCBlocks.uraniumOre, new ItemStack(ingotUranium), 5);
		GameRegistry.addSmelting(CBCBlocks.oreCopper, new ItemStack(copper), 2);
		GameRegistry.addSmelting(CBCBlocks.oreTin, new ItemStack(tin), 2);
		//GameRegistry.addSmelting(XenBlocks.crystal, new ItemStack(xenCrystal), 3);
		
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
				new WeightedRandomChestContent(new ItemStack(spray3), 1, 1, 4),
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

							if (itemstack.getItem() == itemstack1.getItem()) {
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
			ISpecialElectricItem item = (ISpecialElectricItem) in.getItem();
			ISpecialElectricItem item2 = (ISpecialElectricItem) is.getItem();
			int energy = item.getManager(in).discharge(in, Integer.MAX_VALUE, 5, true, true);
			item2.getManager(is).charge(is, energy, 5, true, false);
			return is;
		}

		private ItemStack findIdentifyStack(InventoryCrafting inv) {
			ItemStack item = null;
			for (int i = 0; i < 3; i++)
				for (int j = 0; j < 3; j++) {
					ItemStack s = inv.getStackInRowAndColumn(i, j);
					if (s != null && s.getItem() == source.getItem()) {
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
	 * 使用一个物品来修复另一个物品，1stack数对应物品的[repairAmt]damage。
	 * 
	 * @author WeAthFolD
	 * 
	 */
	private static class RecipeRepair implements IRecipe {

		private Item itemToRepair;
		private ItemStack repairMat;
		private int amt = 1;

		public RecipeRepair(Item item, ItemStack mat) {
			itemToRepair = item;
			repairMat = mat;
		}
		
		public RecipeRepair setRepairAmt(int a) {
			amt = a;
			return this;
		}

		@Override
		public boolean matches(InventoryCrafting inv, World world) {
			boolean b1 = false, b2 = false;
			for (int i = 0; i < inv.getSizeInventory(); i++) {
				ItemStack s = inv.getStackInSlot(i);
				if (s != null) {
					if (s.getItem() == itemToRepair) {
						if(b1) return false;
						if(s.getItemDamage() > 0)
							b1 = true;
					} else if (s.getItem() == repairMat.getItem()) {
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
					if (s.getItem() == itemToRepair) {
						if (theItem != null) {
							return null;
						}
						theItem = s;
					} else if (s.getItem() == repairMat.getItem()) {
						
						++cntRep;
					}
				}
			}
			if (theItem == null) {
				return null;
			}
			int damage = theItem.getItemDamage();
			damage -= cntRep * amt;
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
