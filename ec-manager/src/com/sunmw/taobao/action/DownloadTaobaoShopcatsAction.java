package com.sunmw.taobao.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunmw.taobao.bean.shop.TaobaoShopServices;
import com.sunmw.web.util.WebUtil;
import com.taobao.api.domain.SellerCat;

public class DownloadTaobaoShopcatsAction {
	
	private TaobaoShopServices tbShopServices;
	
	List sellercatsList;
	
	private String parentNo;
	private String nick = "优衣库官方旗舰店";
	private int storeId;
	
	public TaobaoShopServices getTbShopServices() {
		return tbShopServices;
	}

	public void setTbShopServices(TaobaoShopServices tbShopServices) {
		this.tbShopServices = tbShopServices;
	}


	public List getSellercatsList() {
		return sellercatsList;
	}

	public void setSellercatsList(List sellercatsList) {
		this.sellercatsList = sellercatsList;
	}

	public String getParentNo() {
		return parentNo;
	}

	public void setParentNo(String parentNo) {
		this.parentNo = parentNo;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public String tbSellercats()
	{
		
		if(this.sellercatsList==null)
			this.sellercatsList = new ArrayList();
		this.sellercatsList.clear();
		Map params = new HashMap();
		params.put("Fields", "cid,name,parent_cid");
		params.put("Nick", nick);
		List<SellerCat> l = null;
		this.tbShopServices.taobaoSellercatsListGet( params);
		if(l==null)
			return "success";
		for(SellerCat sc:l)
		{
			if(WebUtil.isNotNull(parentNo))
			{
				if(sc.getParentCid().longValue()!=new Long(parentNo).longValue())
					continue;
			}
			Map m = new HashMap();
			m.put("No", sc.getCid());
			m.put("Name", sc.getName());
			m.put("ParentNo", sc.getParentCid());
			this.sellercatsList.add(m);
		}
		return "success";
	}

}
