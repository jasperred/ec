package com.sunmw.taobao.action;

import java.util.List;
import java.util.Map;

import com.sunmw.taobao.bean.inventory.TBInventoryServices;
import com.sunmw.web.bean.inventory.InventoryServices;
import com.sunmw.web.util.WebUtil;

public class UpdateTaobaoInventoryAction {
	
	private InventoryServices inventoryServices;
	private TBInventoryServices tbInventoryServices;
	
	private int storeId;
	//ADD只更新有库存的,ALL更新全部淘宝商品,无库存更新为0
	private String invUpdateFlag = "ALL";
	
	public InventoryServices getInventoryServices() {
		return inventoryServices;
	}

	public void setInventoryServices(InventoryServices inventoryServices) {
		this.inventoryServices = inventoryServices;
	}

	public TBInventoryServices getTbInventoryServices() {
		return tbInventoryServices;
	}

	public void setTbInventoryServices(TBInventoryServices tbInventoryServices) {
		this.tbInventoryServices = tbInventoryServices;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public String getInvUpdateFlag() {
		return invUpdateFlag;
	}

	public void setInvUpdateFlag(String invUpdateFlag) {
		this.invUpdateFlag = invUpdateFlag;
	}

	//更新淘宝库存
	public void updateTbInventory()
	{
		List<Map> invList = inventoryServices.getTbUpdateInventory(storeId);
		if(WebUtil.isNullForList(invList))
			return;
		//此方法无库存不更新
		tbInventoryServices.updateTaobaoInventory(storeId, invList,invUpdateFlag);
		//此方法无库存更新为0
		//tbInventoryServices.updateTaobaoInventoryForTBItem(storeId, invList);
	}

}
