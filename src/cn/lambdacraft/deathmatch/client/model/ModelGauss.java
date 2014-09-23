package cn.lambdacraft.deathmatch.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import cn.liutils.api.client.model.IItemModel;

/**
 * Tau Cannon
 * 
 * @author Sabo970, WeAthFolD
 * 
 */
public class ModelGauss extends ModelBase implements IItemModel {

	// fields
	ModelRenderer Shape1;
	ModelRenderer Shape2;
	ModelRenderer Shape3;
	ModelRenderer Shape4;
	ModelRenderer Shape5;
	ModelRenderer Shape6;
	ModelRenderer Shape7;
	ModelRenderer Shape8;
	ModelRenderer Shape9;
	ModelRenderer Shape10;
	ModelRenderer Shape12;
	ModelRenderer Shape13;
	ModelRenderer Shape14;
	ModelRenderer Shape15;
	ModelRenderer Shape16;
	ModelRenderer Shape17;
	ModelRenderer Shape18;
	ModelRenderer Shape11;
	ModelRenderer Shape19;
	ModelRenderer Shape20;

	public ModelGauss() {
		textureWidth = 64;
		textureHeight = 32;

		Shape1 = new ModelRenderer(this, 0, 4);
		Shape1.addBox(0F, 0F, 0F, 1, 4, 4);
		Shape1.setRotationPoint(0F, 20F, 0F);
		Shape1.setTextureSize(64, 32);
		Shape1.mirror = true;
		setRotation(Shape1, 0F, 0F, 0F);
		Shape2 = new ModelRenderer(this, 0, 12);
		Shape2.addBox(0F, -1F, 1F, 1, 2, 7);
		Shape2.setRotationPoint(0F, 19F, 0F);
		Shape2.setTextureSize(64, 32);
		Shape2.mirror = true;
		setRotation(Shape2, 0F, 0F, 0F);
		Shape3 = new ModelRenderer(this, 30, 15);
		Shape3.addBox(0F, 0F, 6F, 1, 4, 2);
		Shape3.setRotationPoint(0F, 20F, 0F);
		Shape3.setTextureSize(64, 32);
		Shape3.mirror = true;
		setRotation(Shape3, 0F, 0F, 0F);
		Shape4 = new ModelRenderer(this, 50, 9);
		Shape4.addBox(-2F, -2.5F, -1F, 5, 5, 2);
		Shape4.setRotationPoint(0.5F, 21.5F, 0F);
		Shape4.setTextureSize(64, 32);
		Shape4.mirror = true;
		setRotation(Shape4, 0F, 0F, 0F);
		Shape5 = new ModelRenderer(this, 47, 0);
		Shape5.addBox(0F, 1F, -7F, 1, 1, 6);
		Shape5.setRotationPoint(0F, 20F, 0F);
		Shape5.setTextureSize(64, 32);
		Shape5.mirror = true;
		setRotation(Shape5, 0F, 0F, 0F);
		Shape6 = new ModelRenderer(this, 47, 0);
		Shape6.addBox(0F, 0F, -10F, 3, 3, 6);
		Shape6.setRotationPoint(-1F, 20F, -2F);
		Shape6.setTextureSize(64, 32);
		Shape6.mirror = true;
		setRotation(Shape6, 0F, 0F, 0F);
		Shape7 = new ModelRenderer(this, -1, 21);
		Shape7.addBox(-2F, -1F, 8F, 5, 5, 2);
		Shape7.setRotationPoint(0F, 20F, 0F);
		Shape7.setTextureSize(64, 32);
		Shape7.mirror = true;
		setRotation(Shape7, 0F, 0F, 0.0349066F);
		Shape8 = new ModelRenderer(this, 21, 21);
		Shape8.addBox(0F, 2F, 0F, 1, 3, 7);
		Shape8.setRotationPoint(0F, 20F, 12F);
		Shape8.setTextureSize(64, 32);
		Shape8.mirror = true;
		setRotation(Shape8, -0.1396263F, 0F, 0F);
		Shape9 = new ModelRenderer(this, 4, 0);
		Shape9.addBox(-0.05F, 0F, 0F, 1, 2, 2);
		Shape9.setRotationPoint(1F, 19F, 1F);
		Shape9.setTextureSize(64, 32);
		Shape9.mirror = true;
		setRotation(Shape9, 0F, 0F, 0F);
		Shape10 = new ModelRenderer(this, 0, 0);
		Shape10.addBox(0F, 1F, 0F, 1, 3, 1);
		Shape10.setRotationPoint(1F, 17F, 1F);
		Shape10.setTextureSize(64, 32);
		Shape10.mirror = true;
		setRotation(Shape10, 0F, 0F, 0F);
		Shape12 = new ModelRenderer(this, 60, 0);
		Shape12.addBox(0F, 0F, -3F, 1, 2, 1);
		Shape12.setRotationPoint(1F, 18F, -5F);
		Shape12.setTextureSize(64, 32);
		Shape12.mirror = true;
		setRotation(Shape12, 0F, 0F, 0F);
		Shape13 = new ModelRenderer(this, 4, 0);
		Shape13.addBox(0F, 0F, 0F, 1, 2, 2);
		Shape13.setRotationPoint(-0.9F, 19F, 1F);
		Shape13.setTextureSize(64, 32);
		Shape13.mirror = true;
		setRotation(Shape13, 0F, 0F, 0F);
		Shape14 = new ModelRenderer(this, 0, 0);
		Shape14.addBox(-1F, -2F, 1F, 1, 3, 1);
		Shape14.setRotationPoint(0F, 20F, 0F);
		Shape14.setTextureSize(64, 32);
		Shape14.mirror = true;
		setRotation(Shape14, 0F, 0F, 0F);
		Shape15 = new ModelRenderer(this, 47, 0);
		Shape15.addBox(-1F, -3F, -7F, 1, 1, 8);
		Shape15.setRotationPoint(0F, 20F, 0F);
		Shape15.setTextureSize(64, 32);
		Shape15.mirror = true;
		setRotation(Shape15, 0F, 0F, 0F);
		Shape16 = new ModelRenderer(this, 60, 0);
		Shape16.addBox(-1F, -2F, -8F, 1, 2, 1);
		Shape16.setRotationPoint(0F, 20F, 0F);
		Shape16.setTextureSize(64, 32);
		Shape16.mirror = true;
		setRotation(Shape16, 0F, 0F, 0F);
		Shape17 = new ModelRenderer(this, 16, 12);
		Shape17.addBox(0F, 1F, 4F, 5, 3, 2);
		Shape17.setRotationPoint(-2F, 20F, 0F);
		Shape17.setTextureSize(64, 32);
		Shape17.mirror = true;
		setRotation(Shape17, 0F, 0F, 0F);
		Shape18 = new ModelRenderer(this, 16, 17);
		Shape18.addBox(0F, 0F, 0F, 0, 1, 1);
		Shape18.setRotationPoint(0F, 20F, 5F);
		Shape18.setTextureSize(64, 32);
		Shape18.mirror = true;
		setRotation(Shape18, 0F, 0F, 0F);
		Shape11 = new ModelRenderer(this, 47, 0);
		Shape11.addBox(0F, 0F, 0F, 1, 1, 8);
		Shape11.setRotationPoint(1F, 17F, -7F);
		Shape11.setTextureSize(64, 32);
		Shape11.mirror = true;
		setRotation(Shape11, 0F, 0F, 0F);
		Shape19 = new ModelRenderer(this, 54, 0);
		Shape19.addBox(0F, 1F, -16F, 1, 1, 4);
		Shape19.setRotationPoint(0F, 20F, 0F);
		Shape19.setTextureSize(64, 32);
		Shape19.mirror = true;
		setRotation(Shape19, 0F, 0F, 0F);
		Shape20 = new ModelRenderer(this, 13, 21);
		Shape20.addBox(0F, 0F, 9F, 1, 2, 3);
		Shape20.setRotationPoint(0F, 23F, 0F);
		Shape20.setTextureSize(64, 32);
		Shape20.mirror = true;
		setRotation(Shape20, 0F, 0F, 0F);
	}

	@Override
	public void render(Entity e, float f, float f1, float f2, float f3,
			float f4, float f5) {
		super.render(e, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, e);
		Shape1.render(f5);
		Shape2.render(f5);
		Shape3.render(f5);
		Shape4.render(f5);
		Shape5.render(f5);
		Shape6.render(f5);
		Shape7.render(f5);
		Shape8.render(f5);
		Shape9.render(f5);
		Shape10.render(f5);
		Shape12.render(f5);
		Shape13.render(f5);
		Shape14.render(f5);
		Shape15.render(f5);
		Shape16.render(f5);
		Shape17.render(f5);
		Shape18.render(f5);
		Shape11.render(f5);
		Shape19.render(f5);
		Shape20.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3,
			float f4, float f5, Entity e) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
	}

	@Override
	public void render(ItemStack is, float f5, float f) {
		setRotationAngles(is, 0, 0, 0, f);
		Shape1.render(f5);
		Shape2.render(f5);
		Shape3.render(f5);
		Shape4.render(f5);
		Shape5.render(f5);
		Shape6.render(f5);
		Shape7.render(f5);
		Shape8.render(f5);
		Shape9.render(f5);
		Shape10.render(f5);
		Shape12.render(f5);
		Shape13.render(f5);
		Shape14.render(f5);
		Shape15.render(f5);
		Shape16.render(f5);
		Shape17.render(f5);
		Shape18.render(f5);
		Shape11.render(f5);
		Shape19.render(f5);
		Shape20.render(f5);
	}

	@Override
	public void setRotationAngles(ItemStack is, double posX, double posY,
			double posZ, float f) {
		Shape4.rotateAngleZ = f;
	}

}
