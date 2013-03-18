package com.sunmw.taobao.bean.product;

import java.util.List;
import java.util.Map;

public interface ItemServices {
	
	public boolean checkItemAndSku(String itemCd,String skuCd,int pjId);
	/**
	 * 查询商品
	 * @param param
	 * @param currentPage
	 * @param pageRow
	 * @return
	 */
	public Map searchItem(Map param,int currentPage,int pageRow);
	
	/**
	 * 保存商品
	 * @param param
	 * @return
	 */
	public Map saveItem(Map param);
	
	/**
	 * 保存商品图片
	 * @param param
	 * @return
	 */
	public String saveItemImage(Map param);

	/**
	 * 保存商品属性图片
	 * @param param
	 * @return
	 */
	public String saveItemPropImage(Map param);
	/**
	 * 获得商品信息
	 * @param productId
	 * @return
	 */
	public Map getItemInfo(int itemId);
	
	/**
	 * 获得商品图片
	 * @param productId
	 * @return
	 */
	public List getItemImages(int itemId);

	/**
	 * 获得商品的属性图片
	 * @param productId
	 * @return
	 */
	public List getItemPropImages(int itemId);
	/**
	 * 保存SKU
	 * @param param
	 * @return
	 */
	public String saveItemSku(Map param);
	/**
	 * 获得ITEM的SKU
	 * @param param
	 * @return
	 */
	public List getItemSku(int itemId);
	
	/**
	 * 删除ITEM
	 * @param itemId
	 * @return
	 */
	public String deleteItem(int itemId);
	
	/**
	 * 删除SKU
	 * @param skuId
	 * @return
	 */
	public String deleteItemSku(int skuId);

	/**
	 * 删除属性图片
	 * @param itemPropImgId
	 * @return
	 */
	public String deleteItemPropImage(int itemPropImgId);
	
	/**
	 * 获得商品的任务列表
	 * @param productId
	 * @return
	 */
	public List getItemTask(int itemId);
	
	/**
	 * 保存商品任务
	 * @param param
	 * @return
	 */
	public String saveItemTask(Map<String,String> param);
	
	/**
	 * 获得商品下载信息
	 * @param pjId
	 * @param storeId
	 * @return
	 */
	public List getItemDownloadInfo(int pjId,int storeId);
	
	/**
	 * 商品批处理更新
	 * @param pjId
	 * @param storeId
	 * @param itemInfoList
	 * @return
	 */
	public String batchItemUpdate(int pjId,int storeId,List<Map> itemInfoList);
	
	/**
	 * 获得商品价格信息
	 * @param pjId
	 * @param storeId
	 * @return
	 */
	public List getItemDownloadPriceInfo(int pjId, int storeId);
	
	/**
	 * 商品价格更新
	 * @param pjId
	 * @param storeId
	 * @param itemPriceInfoList
	 * @return
	 */
	public String batchItemPriceUpdate(int pjId, int storeId, List<Map> itemPriceInfoList);
	
	/**
	 * 商品导入
	 * @param param
	 * @return
	 */
	public Map importItem(Map param);
	
	/**
	 * 自动匹配产品和商品的图片
	 * @param param
	 */
	public void autoLoadItem(Map param) ;
	
	/**
	 * 商品任务导入
	 * @param param
	 * @return
	 */
	public Map importItemTask(Map param);

}
