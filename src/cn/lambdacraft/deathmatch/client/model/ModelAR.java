/**
 * 
 */
package cn.lambdacraft.deathmatch.client.model;

import cn.liutils.api.client.model.IItemModel;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

/**
 * @author WeAthFolD, 擎兽
 * 
 */
public class ModelAR extends ModelBase implements IItemModel {

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
	ModelRenderer Shape11;
	ModelRenderer Shape12;
	ModelRenderer Shape13;
	ModelRenderer Shape14;
	ModelRenderer Shape15;
	ModelRenderer Shape16;

	public ModelAR() {
		textureWidth = 64;
		textureHeight = 32;

		Shape1 = new ModelRenderer(this, 9, 8);
		Shape1.addBox(0F, 0F, 0F, 1, 1, 13);
		Shape1.setRotationPoint(0F, 0F, -0.5F);
		Shape1.setTextureSize(64, 32);
		Shape1.mirror = true;
		setRotation(Shape1, 0F, 0F, 0.7853982F);
		Shape2 = new ModelRenderer(this, 28, 13);
		Shape2.addBox(0F, 0F, 0F, 1, 1, 10);
		Shape2.setRotationPoint(0F, 1F, 2F);
		Shape2.setTextureSize(64, 32);
		Shape2.mirror = true;
		setRotation(Shape2, 0F, 0F, 0.7853982F);
		Shape3 = new ModelRenderer(this, 41, 13);
		Shape3.addBox(0F, 0F, 0F, 1, 3, 6);
		Shape3.setRotationPoint(-0.5F, 0.5F, 6F);
		Shape3.setTextureSize(64, 32);
		Shape3.mirror = true;
		setRotation(Shape3, 0.0698132F, 0F, 0F);
		Shape4 = new ModelRenderer(this, 0, 21);
		Shape4.addBox(0F, 0F, 0F, 1, 2, 4);
		Shape4.setRotationPoint(-0.5F, 0.5F, 3F);
		Shape4.setTextureSize(64, 32);
		Shape4.mirror = true;
		setRotation(Shape4, -0.1396263F, 0F, 0F);
		Shape5 = new ModelRenderer(this, 56, 18);
		Shape5.addBox(0F, 0F, 0F, 1, 3, 1);
		Shape5.setRotationPoint(-0.5F, -0.2F, 16.2F);
		Shape5.setTextureSize(64, 32);
		Shape5.mirror = true;
		setRotation(Shape5, 0.122173F, 0F, 0F);
		Shape6 = new ModelRenderer(this, 23, 23);
		Shape6.addBox(0F, 0F, 0F, 1, 3, 1);
		Shape6.setRotationPoint(-0.5F, 2.5F, 6.7F);
		Shape6.setTextureSize(64, 32);
		Shape6.mirror = true;
		setRotation(Shape6, -0.3665191F, 0F, 0F);
		Shape7 = new ModelRenderer(this, 19, 17);
		Shape7.addBox(0F, 0F, 0F, 1, 3, 0);
		Shape7.setRotationPoint(-0.5F, -1.5F, 2.2F);
		Shape7.setTextureSize(64, 32);
		Shape7.mirror = true;
		setRotation(Shape7, 0F, 0F, 0F);
		Shape8 = new ModelRenderer(this, 46, 25);
		Shape8.addBox(0F, 0F, 0F, 1, 3, 1);
		Shape8.setRotationPoint(-0.5F, 3F, 10.5F);
		Shape8.setTextureSize(64, 32);
		Shape8.mirror = true;
		setRotation(Shape8, 0.4363323F, 0F, 0F);
		Shape9 = new ModelRenderer(this, 23, 28);
		Shape9.addBox(0F, 0F, 0F, 1, 2, 1);
		Shape9.setRotationPoint(-0.5F, 4.9F, 5.8F);
		Shape9.setTextureSize(64, 32);
		Shape9.mirror = true;
		setRotation(Shape9, -0.7853982F, 0F, 0F);
		Shape10 = new ModelRenderer(this, 41, 25);
		Shape10.addBox(0F, 0F, 0F, 0, 2, 2);
		Shape10.setRotationPoint(0F, 2.2F, 9F);
		Shape10.setTextureSize(64, 32);
		Shape10.mirror = true;
		setRotation(Shape10, 0.0872665F, 0F, 0F);
		Shape11 = new ModelRenderer(this, 27, 14);
		Shape11.addBox(0F, 0F, 0F, 0, 1, 5);
		Shape11.setRotationPoint(0.3F, 0.3F, 12F);
		Shape11.setTextureSize(64, 32);
		Shape11.mirror = true;
		setRotation(Shape11, 0F, 0F, 0F);
		Shape12 = new ModelRenderer(this, 19, 14);
		Shape12.addBox(0F, 0F, 0F, 1, 2, 0);
		Shape12.setRotationPoint(-0.5F, -1F, 12F);
		Shape12.setTextureSize(64, 32);
		Shape12.mirror = true;
		setRotation(Shape12, 0F, 0F, 0F);
		Shape13 = new ModelRenderer(this, 27, 14);
		Shape13.addBox(0F, 0F, 0F, 0, 1, 5);
		Shape13.setRotationPoint(-0.3F, 0.3F, 12F);
		Shape13.setTextureSize(64, 32);
		Shape13.mirror = true;
		setRotation(Shape13, 0F, 0F, 0F);
		Shape14 = new ModelRenderer(this, 12, 19);
		Shape14.addBox(0F, 0F, 0F, 2, 0, 1);
		Shape14.setRotationPoint(0F, 0.7F, 3F);
		Shape14.setTextureSize(64, 32);
		Shape14.mirror = true;
		setRotation(Shape14, 0F, 0F, -0.2617994F);
		Shape15 = new ModelRenderer(this, 10, 23);
		Shape15.addBox(0F, 0F, 0F, 1, 1, 5);
		Shape15.setRotationPoint(0F, 2F, 0F);
		Shape15.setTextureSize(64, 32);
		Shape15.mirror = true;
		setRotation(Shape15, 0F, 0F, 0.7853982F);
		Shape16 = new ModelRenderer(this, 11, 25);
		Shape16.addBox(0F, 0F, 0F, 0, 1, 1);
		Shape16.setRotationPoint(0F, 2.7F, 5F);
		Shape16.setTextureSize(64, 32);
		Shape16.mirror = true;
		setRotation(Shape16, 0F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3,
			float f4, float f5) {
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
		Shape11.render(f5);
		Shape12.render(f5);
		Shape13.render(f5);
		Shape14.render(f5);
		Shape15.render(f5);
		Shape16.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void render(ItemStack is, float f5, float f) {
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
		Shape11.render(f5);
		Shape12.render(f5);
		Shape13.render(f5);
		Shape14.render(f5);
		Shape15.render(f5);
		Shape16.render(f5);
	}

	@Override
	public void setRotationAngles(ItemStack is, double posX, double posY,
			double posZ, float f) {

	}

}
