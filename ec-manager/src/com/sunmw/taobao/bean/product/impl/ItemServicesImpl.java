package com.sunmw.taobao.bean.product.impl;

import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sunmw.taobao.bean.product.ItemServices;

public class ItemServicesImpl extends HibernateDaoSupport implements
		ItemServices {

	@Override
	public void autoLoadItem(Map param) {
		// TODO Auto-generated method stub

	}

	@Override
	public String batchItemPriceUpdate(int pjId, int storeId,
			List<Map> itemPriceInfoList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String batchItemUpdate(int pjId, int storeId, List<Map> itemInfoList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkItemAndSku(String itemCd, String skuCd, int pjId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String deleteItem(int itemId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteItemPropImage(int itemPropImgId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteItemSku(int skuId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getItemDownloadInfo(int pjId, int storeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getItemDownloadPriceInfo(int pjId, int storeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getItemImages(int itemId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map getItemInfo(int itemId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getItemPropImages(int itemId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getItemSku(int itemId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getItemTask(int itemId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map importItem(Map param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map importItemTask(Map param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map saveItem(Map param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String saveItemImage(Map param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String saveItemPropImage(Map param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String saveItemSku(Map param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String saveItemTask(Map<String, String> param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map searchItem(Map param, int currentPage, int pageRow) {
		// TODO Auto-generated method stub
		return null;
	}

}
