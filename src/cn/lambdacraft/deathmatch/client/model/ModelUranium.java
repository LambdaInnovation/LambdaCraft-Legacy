/**
 * 
 */
package cn.lambdacraft.deathmatch.client.model;

import org.lwjgl.opengl.GL11;

import cn.liutils.api.client.model.IItemModel;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

/**
 * @author WeAthFolD
 * 
 */
public class ModelUranium extends ModelBase implements IItemModel {

	// fields
	ModelRenderer Shape1;
	ModelRenderer Shape2;
	ModelRenderer Shape3;
	ModelRenderer Shape4;
	ModelRenderer Shape5;
	ModelRenderer Shape6;

	public ModelUranium() {
		textureWidth = 64;
		textureHeight = 32;

		Shape1 = new ModelRenderer(this, 6, 18);
		Shape1.addBox(0F, 0F, 0F, 5, 5, 8);
		Shape1.setRotationPoint(-3F, 19F, -4F);
		Shape1.setTextureSize(64, 32);
		Shape1.mirror = true;
		setRotation(Shape1, 0F, 0F, 0F);
		Shape2 = new ModelRenderer(this, 4, 6);
		Shape2.addBox(0F, 0F, 0F, 6, 2, 9);
		Shape2.setRotationPoint(-3.5F, 18.5F, -4.5F);
		Shape2.setTextureSize(64, 32);
		Shape2.mirror = true;
		setRotation(Shape2, 0F, 0F, 0F);
		Shape3 = new ModelRenderer(this, 33, 21);
		Shape3.addBox(0F, 0F, 0F, 3, 1, 9);
		Shape3.setRotationPoint(-2F, 23F, -4.5F);
		Shape3.setTextureSize(64, 32);
		Shape3.mirror = true;
		setRotation(Shape3, 0F, 0F, 0F);
		Shape4 = new ModelRenderer(this, 26, 4);
		Shape4.addBox(0F, 0F, 0F, 2, 6, 4);
		Shape4.setRotationPoint(-3.8F, 18F, -2F);
		Shape4.setTextureSize(64, 32);
		Shape4.mirror = true;
		setRotation(Shape4, 0F, 0F, 0F);
		Shape5 = new ModelRenderer(this, 6, 12);
		Shape5.addBox(0F, 0F, 0F, 2, 1, 1);
		Shape5.setRotationPoint(0.7F, 18.3F, 2F);
		Shape5.setTextureSize(64, 32);
		Shape5.mirror = true;
		setRotation(Shape5, 0F, 0F, 0F);
		Shape6 = new ModelRenderer(this, 6, 12);
		Shape6.addBox(0F, 0F, 0F, 2, 1, 1);
		Shape6.setRotationPoint(0.7F, 18.3F, -3F);
		Shape6.setTextureSize(64, 32);
		Shape6.mirror = true;
		setRotation(Shape6, 0F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3,
			float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		Shape1.render(f5);
		Shape2.render(f5);
		Shape3.render(f5);
		Shape4.render(f5);
		Shape5.render(f5);
		Shape6.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void render(ItemStack is, float f5, float f) {
		GL11.glTranslatef(.0F, -1.5F, .0F);
		Shape1.render(f5);
		Shape2.render(f5);
		Shape3.render(f5);
		Shape4.render(f5);
		Shape5.render(f5);
		Shape6.render(f5);
	}

	@Override
	public void setRotationAngles(ItemStack is, double posX, double posY,
			double posZ, float f) {
	}

}
