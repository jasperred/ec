package com.sunmw.web.bean.item;

import java.util.Map;

public interface GenerateItemCodeServices {
	
	/**
	 * 生成编码
	 * @param param
	 * @return
	 */
	public Map generateItemCode(Map param);
	
	/**
	 * 查询生成编码的商品
	 * @param param
	 * @param currentPage
	 * @param pageRow
	 * @return
	 */
	public Map searchGenerateItem(Map param, int currentPage, int pageRow);
	
	/**
	 * 导出生成编码的商品
	 * @param param
	 * @return
	 */
	public Map exportGenerateItem(Map param);
	
	/**
	 * 商品编码类型维护
	 * @param param
	 * @return
	 */
	public Map saveGenerateType(Map param);
	
	/**
	 * 商品编码类型删除
	 * @param param
	 * @return
	 */
	public Map deleteGenerateType(Map param);
	
	/**
	 * 查询商品编码类型
	 * @param param
	 * @return
	 */
	public Map getGenerateType(Map param);
	
	/**
	 * 保存商品信息并生成商品编码
	 * @param param
	 * @return
	 */
	public Map saveItemCode(Map param);

	/**
	 * 保存商品图片
	 * @param param
	 * @return
	 */
	public Map saveItemImage(Map param);

	/**
	 * 得到商品信息
	 * @param param
	 * @return
	 */
	public Map getItemInfo(Map param);
	
	/**
	 * 得到商品的品番
	 * @param year
	 * @return
	 */
	public Integer getItemSerial(String year);

}
