package com.sunmw.taobao.bean.task;

import java.util.List;
import java.util.Map;

public interface TaobaoTaskServices {
	
	/**
	 * 增量订单
	 * @param param
	 * @return
	 */
	public Map taobaoTradesSoldIncrement(Map param);

	/**
	 * 按日期得到订单,3个月内
	 * @param params
	 * @return
	 */
	public Map taobaoTradesSoldGet(Map param);
	
	/**
	 * 下载退货信息
	 * @param params
	 * @return
	 */
	public Map taobaoRefundsReceiveGet(Map param);
	
	/**
	 * 商家店铺类目
	 * @param params
	 * @return
	 */
	public Map taobaoSellercatsListGet(Map param);
	
	/**
	 * 下载淘宝产品
	 * @param param
	 * @return
	 */
	public Map taobaoProductsGet(Map param);
	
	/**
	 * 下载淘宝商品
	 * @param param
	 * @return
	 */
	public Map taobaoItemDownload(Map param);
	
	/**
	 * 下载淘宝物流公司
	 * @param param
	 */
	public Map downloadTbLogisticsCompany(Map param);
	
	/**
	 * 下载淘宝分类
	 * @param param
	 */
	public Map downloadTbCat(Map param);
	
	/**
	 * 库存更新
	 * @param param
	 * @return
	 */
	public Map taobaoItemQuantityUpdate(Map param);
	
	/**
	 * 淘宝SKU价格更新
	 */
	public Map taobaoSkusPriceUpdate(Map param);	

	/**
	 * 上传物流信息
	 */
	public Map taobaoDeliverySend(Map param);
	
	/**
	 * 每日销售报表
	 * @param param
	 * @return
	 */
	public Map dailyTaobaoSaleReport(Map param);
	
	/**
	 * 每日退货报表
	 * @param param
	 * @return
	 */
	public Map dailyTaobaoRefundReport(Map param);
}
