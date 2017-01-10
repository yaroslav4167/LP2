package com.sots.pipe;

import com.sots.tiles.TileNetworkCore;
import com.sots.util.AccessHelper;
import com.sots.util.References;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class NetworkCore extends BlockGenericPipe{

	public NetworkCore() {
		super(Material.IRON);
		setUnlocalizedName(References.NAME_NETWORK_CORE);
		setRegistryName(References.RN_NETWORK_CORE);
		setCreativeTab(CreativeTabs.TRANSPORTATION);
		setHardness(3.0f);
		setResistance(15.0f);
		setSoundType(SoundType.METAL);
		setHarvestLevel("pickaxe",3);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void initModel() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
		
	}
	
	@Override
	public boolean hasTileEntity() { return true;}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		TileNetworkCore self = new TileNetworkCore();
		self.makeNetwork();
		return self;
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		TileNetworkCore self = (TileNetworkCore) AccessHelper.getTileSafe(worldIn, pos);
		self.updateNetwork();
		
		return true;
	}

}