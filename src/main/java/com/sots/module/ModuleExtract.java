package com.sots.module;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import com.sots.tiles.TileRoutedPipe;
import com.sots.util.References;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public class ModuleExtract extends ModuleBase implements IModule{
	
	private int ticksTillOp = References.MOD_BASE_OPERATION_RATE;

	public ModuleExtract(UUID ID) {
		super(ID);
	}

	@Override
	public boolean execute(TileRoutedPipe te) {
		
		if(ticksTillOp!=0) {
			ticksTillOp-=1;
		}else {
			
			ticksTillOp=References.MOD_BASE_OPERATION_RATE;
			if(te.hasInventory()) {
				boolean hasWorked = false;
				int tryDest = 0;
				int tryItem = 0;
				ArrayList<ItemStack> stacks = te.getItemTypesInInventory(te.network.getDirectionForDestination(te.nodeID));
				try {
					if(!stacks.isEmpty()) {
						ArrayList<UUID> nodes = te.network.getStorageNodesForItem(stacks.get(tryItem).getItem());
						UUID nodeT;
						while(!hasWorked) {
							if(nodes.isEmpty()) {
								tryItem+=1;
								nodes = te.network.getStorageNodesForItem(stacks.get(tryItem).getItem());
							}else {
								nodeT = nodes.get(tryDest);
								if(nodeT.equals(te.nodeID)) {
									tryDest+=1;
									nodeT = te.network.getStorageNodesForItem(stacks.get(tryItem).getItem()).get(tryDest);
									continue;
								}
								
								ItemStack stack = stacks.get(tryItem).copy();
								if(stack.getCount()>References.MOD_EXTRACT_BASE_COUNT) {
									stack.setCount(References.MOD_EXTRACT_BASE_COUNT);
								}
								te.routeItemTo(nodeT, stack);
							}
							hasWorked=true;
						}
					}
				} catch (Exception e) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public boolean canExecute() {return true;}

	@Override
	public boolean canInsert() {return true;}

	@Override
	public ModuleType modType() {return ModuleType.EXTRACT;}

}
