package com.sunmw.taobao.bean.product;

import java.util.List;
import java.util.Map;

public interface ProductServices {
	
	/**
	 * 查询产品
	 * @param param
	 * @param currentPage
	 * @param pageRow
	 * @return
	 */
	public Map searchProduct(Map param,int currentPage,int pageRow);
	
	/**
	 * 保存产品
	 * @param param
	 * @return
	 */
	public Map saveProduct(Map param);
	
	/**
	 * 保存产品图片
	 * @param param
	 * @return
	 */
	public String saveProductImage(Map param);
	
	/**
	 * 获得产品信息
	 * @param productId
	 * @return
	 */
	public Map getProductInfo(int productId);
	
	/**
	 * 获得产品的5张主图
	 * @param productId
	 * @return
	 */
	public List getProductImages(int productId);
	
	/**
	 * 删除产品
	 * @param productId
	 * @return
	 */
	public String deleteProduct(int productId);
	
	/**
	 * 获得产品的任务列表
	 * @param productId
	 * @return
	 */
	public List getProductTask(int productId);
	
	/**
	 * 保存产品任务
	 * @param param
	 * @return
	 */
	public String saveProductTask(Map<String,String> param);
	
	/**
	 * 查询未在TaoBaoPlugIn建立Product的产品
	 * @param param
	 * @param currentPage
	 * @param pageRow
	 * @return
	 */
	public Map noProductSearch(Map param, int currentPage, int pageRow);

}
