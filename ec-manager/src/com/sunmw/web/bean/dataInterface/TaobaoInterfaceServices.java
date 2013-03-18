
package com.sunmw.web.bean.dataInterface;

import java.util.List;
import java.util.Map;

public interface TaobaoInterfaceServices {
	
	/**
	 * 同步本地Taobao退货单到Erp库中
	 * @param param
	 * @return
	 */
	public Map syncTaobaoRefundToErp(Map param);
	
	/**
	 * 同步本地Taobao订单到Erp库中
	 * @param param
	 * @return
	 */
	public Map syncTaobaoOrderToErp(Map param);
	
	/**
	 * 漏单检查
	 * @param param
	 * @return
	 */
	public Map checkTaobaoOrderMiss(Map param);
	
	/**
	 * 增量订单
	 * @param param
	 * @return
	 */
	public Map taobaoTradesSoldIncrement(Map param);
	/**
	 * 增量订单
	 * @param param
	 * @return
	 */
	public Map taobaoTradesSoldIncrementV(Map param);

	/**
	 * 按日期得到订单,3个月内
	 * @param params
	 * @return
	 */
	public Map taobaoTradesSoldGet(Map param);
	
	/**
	 * 单个淘宝订单的详细信息
	 * @param param
	 * @return
	 */
	public Map taobaoTradeFullinfoGet(Map param);
	
	/**
	 * 下载退货信息
	 * @param params
	 * @return
	 */
	public Map taobaoRefundsReceiveGet(Map param);
	
	/**
	 * 单个退货信息
	 * @param params
	 * @return
	 */
	public Map taobaoRefundGet(Map params);
	
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
	 * 获得淘宝运费模板
	 */
	public Map taobaoDeliveryTemplate(Map param);

	/**
	 * 获取后台供卖家发布商品的标准商品类目
	 */
	public Map taobaoItemCats(Map param);
	
	/**
	 * 淘宝分销订单下载
	 */
	public Map taobaoFenxiaoOrdersGet(Map param);
	
	/**
	 * 获取主动通知业务哪些用户丢弃了消息 
	 */
	public Map taobaoCometDiscardInfo(Map param);
	
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
	
	/**
	 * 每月销售报表
	 * @param param
	 * @return
	 */
	public Map monthTaobaoSaleReport(Map param);
	
	/**
	 * 每月退货报表
	 * @param param
	 * @return
	 */
	public Map monthTaobaoRefundReport(Map param);
}
