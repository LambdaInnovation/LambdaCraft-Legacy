package cn.lambdacraft.crafting.register;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

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
import cn.lambdacraft.api.energy.item.ICustomEnItem;
import cn.lambdacraft.core.item.LCGenericItem;
import cn.lambdacraft.crafting.item.Bullet_9mm;
import cn.lambdacraft.crafting.item.Bullet_steelbow;
import cn.lambdacraft.crafting.item.HLSpray;
import cn.lambdacraft.crafting.item.IngotUranium;
import cn.lambdacraft.crafting.item.ItemBullet;
import cn.lambdacraft.crafting.item.ItemMaterial;
import cn.lambdacraft.crafting.item.ItemMaterial.EnumMaterial;
import cn.lambdacraft.crafting.item.ItemSpray;
import cn.lambdacraft.crafting.item.LCRecord;
import cn.lambdacraft.crafting.item.SteelBar;
import cn.lambdacraft.deathmatch.item.ArmorHEV;
import cn.lambdacraft.deathmatch.item.ItemAttachment;
import cn.lambdacraft.deathmatch.item.ItemBattery;
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

	public static LCGenericItem ingotSteel;

	public static ItemMaterial materials;
	public static LCGenericItem lambdaChip;
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
	public static void init(Configuration conf) {

		ammo_uranium = new Ammo_uranium();
		ammo_9mm = new Ammo_9mm();
		ammo_9mm2 = new Ammo_9mm2();
		ammo_357 = new Ammo_357();
		ammo_bow = new Ammo_bow();
		ammo_rpg = new Ammo_rpg();
		ammo_argrenade = new Ammo_argrenade();
		ammo_shotgun = new Ammo_shotgun();
		
		bullet_9mm = new Bullet_9mm();
		bullet_steelbow = new Bullet_steelbow();

		materials = new ItemMaterial();

		ironBar = new SteelBar();
		lambdaChip = new LCGenericItem().setIAndU("lambdachip");
		
		ingotUranium = new IngotUranium();
		ingotSteel = new LCGenericItem().setIAndU("steel");

		halfLife01 = new LCRecord("hla", 0);
		halfLife02 = new LCRecord("hlb", 1);
		halfLife03 = new LCRecord("hlc", 2);
		
		spray1 = new ItemSpray(0);
		spray2 = new ItemSpray(1);
		
		spray = new HLSpray();

		tin = new LCGenericItem().setIAndU("tin");
		copper = new LCGenericItem().setIAndU("copper");
		chip = new LCGenericItem().setIAndU("chip");
		xenCrystal = new LCGenericItem().setIAndU("xencrystal");
		
		battery = new ItemBattery();
		
		GameRegistry.registerItem(ammo_uranium, "ammo_uranium");
		GameRegistry.registerItem(ammo_9mm, "ammo_9mm");
		GameRegistry.registerItem(ammo_9mm2, "ammo_9mm2");
		GameRegistry.registerItem(ammo_357, "ammo_357");
		GameRegistry.registerItem(ammo_bow, "ammo_bow");
		GameRegistry.registerItem(ammo_rpg, "ammo_rpg");
		GameRegistry.registerItem(ammo_argrenade, "ammo_argrenade");
		GameRegistry.registerItem(ammo_shotgun, "ammo_shotgun");
		GameRegistry.registerItem(bullet_9mm, "bullet_9mm");
		GameRegistry.registerItem(bullet_steelbow, "bullet_steelbow");
		GameRegistry.registerItem(materials, "materials");
		GameRegistry.registerItem(ironBar, "ironBar");
		GameRegistry.registerItem(lambdaChip, "lambdaChip");
		GameRegistry.registerItem(ingotUranium, "ingotUranium");
		GameRegistry.registerItem(ingotSteel, "ingotSteel");
		GameRegistry.registerItem(halfLife01, "halfLife01");
		GameRegistry.registerItem(halfLife02, "halfLife02");
		GameRegistry.registerItem(halfLife03, "halfLife03");
		GameRegistry.registerItem(spray1, "spray1");
		GameRegistry.registerItem(spray2, "spray2");
		GameRegistry.registerItem(spray, "spray");
		GameRegistry.registerItem(tin, "lc_tin");
		GameRegistry.registerItem(copper, "lc_copper");
		GameRegistry.registerItem(chip, "lc_chip");
		GameRegistry.registerItem(xenCrystal, "xenCrystal");
		GameRegistry.registerItem(battery, "lc_battery");
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
				Items.redstone), 
				iSlightStoneDust = new ItemStack(Items.glowstone_dust),
				iStnt = new ItemStack(Blocks.tnt), iSblockLapis = new ItemStack(
				Blocks.lapis_block), iSdiamond = new ItemStack(Items.diamond), iSblazePowder = new ItemStack(
				Items.blaze_powder), iSglass = new ItemStack(Blocks.glass), iSlambdaChip = new ItemStack(
				CBCItems.lambdaChip), iScoal = new ItemStack(Items.coal), iSgunpowder = new ItemStack(
				Items.gunpowder), iSingotGold = new ItemStack(Items.gold_ingot), iSblockRedstone = new ItemStack(
				Blocks.redstone_block), iSfurnace = new ItemStack(
				Blocks.furnace), iSchest = new ItemStack(Blocks.chest), iSweaponCrafter = new ItemStack(
				CBCBlocks.weaponCrafter), iSadvCrafter = new ItemStack(
				CBCBlocks.advCrafter), iSwire = new ItemStack(CBCBlocks.wire), iSemerald = new ItemStack(
				Items.emerald), iSchip = new ItemStack(chip), iSelectricCrafter = new ItemStack(
				CBCBlocks.elCrafter), iSbattery = new ItemStack(battery), iSgenFire = new ItemStack(
				CBCBlocks.genFire), iSstorageS = new ItemStack(
				CBCBlocks.storageS), iSstorageL = new ItemStack(
				CBCBlocks.storageL), iSgenLava = new ItemStack(
				CBCBlocks.genLava), iSbucketEmpty = new ItemStack(
				Items.bucket), iSgenSolar = new ItemStack(
				CBCBlocks.genSolar), iSnetherQuartz = new ItemStack(
				Items.nether_star), sfspieye = new ItemStack(
				Items.fermented_spider_eye), srotten = new ItemStack(
				Items.rotten_flesh), smagma = new ItemStack(Items.magma_cream), sendereye = new ItemStack(
				Items.ender_eye), smedkit = new ItemStack(DMItems.medkit);

		Object input[][] = {
				{ "ABA", "AAA", 'A', "ingotTin", 'B', iSglass },
				{ "AAA", "BCB", "DED", 'A', iSglass, 'B', iSblockRedstone, 'C', "blockRefinedIron", 'D', iSfurnace, 'E', iSchest },
				{ "AAA", "BCB", "DED", 'A', iSglass, 'B', iSdiamond, 'C', iSweaponCrafter, 'D', "ingotCopper", 'E', "blockRefinedIron" },
				{ "ABA", "ABA", "ABA", 'A', Blocks.wool, 'B', "ingotCopper" },
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
				new ItemStack(XenBlocks.xenPortal),
				new ItemStack(CBCMobItems.sentrySyncer, 2),
				new ItemStack(spray1),
				new ItemStack(spray2),
				new ItemStack(spray)
		};
		
		Object[][] input2 = {
				{ " A ", "BCB", "DED", 'A', iSstorageS, 'B', iSglass, 'C', "blockRefinedIron", 'D', iSblockLapis, 'E', iSfurnace },
				{ " A ", "BCB", "DED", 'A', iSglass, 'B', iSblazePowder, 'C', iSbucketEmpty, 'D', "ingotRefinedIron", 'E', iSgenFire },
				{ "AAA", "BCB", "DED", 'A', iSglass, 'B', iSnetherQuartz, 'C', iSchip, 'D', "ingotRefinedIron", 'E', iSgenFire },
		};
		
		ItemStack[] output2 = { iSgenFire, iSgenLava, iSgenSolar };
		addOreRecipes(output2, input2);
		addOreRecipes(output, input);
		
		GameRegistry.addShapelessRecipe(new ItemStack(halfLife01), lambdaChip, Items.diamond);
		GameRegistry.addShapelessRecipe(new ItemStack(halfLife02), lambdaChip, Items.emerald);
		GameRegistry.addShapelessRecipe(new ItemStack(halfLife03), lambdaChip, Items.ender_eye);
		GameRegistry.addShapelessRecipe(new ItemStack(XenBlocks.crystal), iSlightStoneDust, iSdiamond);
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
		GameRegistry.addSmelting(Items.iron_ingot, new ItemStack(ingotSteel, 1, 0), 3.0F);
		GameRegistry.addSmelting(CBCBlocks.uraniumOre, new ItemStack(ingotUranium), 5);
		GameRegistry.addSmelting(CBCBlocks.oreCopper, new ItemStack(copper), 2);
		GameRegistry.addSmelting(CBCBlocks.oreTin, new ItemStack(tin), 2);
		GameRegistry.addSmelting(XenBlocks.crystal, new ItemStack(xenCrystal), 3);
		
		//ChestGen
		WeightedRandomChestContent gens_dungeon[] = {
				new WeightedRandomChestContent(new ItemStack(halfLife01), 1, 1, 5),
				new WeightedRandomChestContent(new ItemStack(halfLife02), 1, 1, 5),
				new WeightedRandomChestContent(new ItemStack(halfLife03), 1, 1, 5),
				new WeightedRandomChestContent(new ItemStack(ironBar), 3, 8, 50),
				new WeightedRandomChestContent(new ItemStack(DMItems.physExcalibur), 1, 1, 1),
				new WeightedRandomChestContent(new ItemStack(DMItems.weapon_crowbar_el), 1, 1, 10)
		};
		for(WeightedRandomChestContent gen : gens_dungeon) {
			ChestGenHooks.addItem("dungeonChest", gen);
			ChestGenHooks.addItem("villageBlacksmith", gen);
		}
		
		WeightedRandomChestContent gens_desert[] = {
				new WeightedRandomChestContent(new ItemStack(ironBar), 3, 8, 50),
				new WeightedRandomChestContent(new ItemStack(DMItems.physExcalibur), 1, 1, 1),
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
					if (s.getItem() == itemToRepair) {
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
					} else if (s.getItem() == repairMat.getItem())
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
