package com.sunmw.web.bean.order;

import java.util.List;
import java.util.Map;

import com.taobao.api.domain.Trade;

public interface OrderServices {
	

	/**
	 * 新增订单
	 * @param param
	 * @return
	 */
	public Map newOrder(Map param);
	
	/**
	 * 订单查询
	 * @param param
	 * @param currentPage
	 * @param pageRow
	 * @return
	 */
	public Map searchOrder(Map param, int currentPage, int pageRow);
	
	/**
	 * 检查订单
	 * @param orderHeadId
	 * @return
	 */
	public String auditedOrder(int orderHeadId);
	
	/**
	 * 批量审核订单
	 * @param orderHeadIds
	 * @return
	 */
	public String auditedOrders(String orderHeadIds);
	
	/**
	 * 保留订单
	 * @param orderHeadId
	 * @param receiveMessage
	 * @return
	 */
	public String receiveOrder(int orderHeadId,String receiveMessage);
	
	/**
	 * 批量保留订单
	 * @param orderHeadIds
	 * @param receiveMessage
	 * @return
	 */
	public String receiveOrders(String orderHeadIds,String receiveMessage);

	/**
	 * 获得订单信息
	 * @param orderHeadId
	 * @return
	 */
	public Map getOrderInfo(int orderHeadId);
	
	/**
	 * 通过淘宝订单号得到订单信息
	 * @param tbOrderNo
	 * @return
	 */
	public Map getOrderInfoByTbOrderNo(String tbOrderNo);
	
	/**
	 * 保存订单备注
	 * @param orderHeadId
	 * @param noteType
	 * @param note
	 * @return
	 */
	public String saveOrderNote(int orderHeadId,String noteType,String note);
	
	/**
	 * 修改收货人信息
	 * @param orderHeadId
	 * @param param
	 * @return
	 */
	public String updateReveiverAddress(int orderHeadId,Map<String,String> param);
	
	/**
	 * 取消订单
	 * @param orderHeadId
	 * @return
	 */
	public String cancelOrder(int orderHeadId);
	
	/**
	 * 恢复订单
	 * @param orderHeadId
	 * @return
	 */
	public String recoverOrder(int orderHeadId);
	
	/**
	 * 保存发票信息
	 * @param orderHeadId
	 * @param invoice
	 * @param invoiceMessage
	 * @return
	 */
	public String saveInvoice(int orderHeadId,String invoice,String invoiceMessage);
	
	/**
	 * 导入淘宝订单
	 * @param tbOrderList
	 * @return 成功导入的淘宝订单号
	 */
	public String updateOrderFromTb(List<Map> tbOrderList);
	
	/**
	 * 导入单个淘宝订单
	 * @param trade
	 * @param storeId
	 * @return
	 */
	public Map updateSingleOrderFromTb(Map trade, Integer storeId);
	
	/**
	 * 导入单个拍拍订单
	 * @param trade
	 * @param storeId
	 * @return
	 */
	public Map updateSingleOrderFromPp(Map order, Integer storeId);
	
	/**
	 * 查询订单明细
	 * @param param
	 * @param currentPage
	 * @param pageRow
	 * @return
	 */
	public Map searchOrderItem(Map param, int currentPage, int pageRow);
	
	/**
	 * 得到淘宝未发货订单
	 * @param param
	 * @return
	 */
	public Map tbDeliveryOrder(Map param);
	
	/**
	 * 更新淘宝发货状态
	 * @param param
	 * @return
	 */
	public Map updateTbDeliveryStatus(Map param);
	
	/**
	 * 导出发货指示
	 * @param param
	 * @return
	 */
	public Map exportDeliveryRequest(Map param);
	
	/**
	 * 导入发货实绩
	 * @param param
	 * @return
	 */
	public Map importWmsDelivery(Map param);

	/**
	 * 导出退货指示
	 * @param param
	 * @return
	 */
	public Map exportReturnRequest(Map param);
	
	/**
	 * 导入退货实绩
	 * @param param
	 * @return
	 */
	public Map importWmsReturn(Map param);
	
	/**
	 * 得到拍拍未发货订单
	 * @param param
	 * @return
	 */
	public Map ppDeliveryOrder(Map param);
	
	/**
	 * 更新拍拍发货状态
	 * @param param
	 * @return
	 */
	public Map updatePpDeliveryStatus(Map param);
}
