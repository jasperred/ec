package com.sunmw.web.bean.refund;

import java.util.List;
import java.util.Map;

import com.sunmw.taobao.entity.TbRefund;
import com.taobao.api.domain.Refund;

public interface RefundServices {

	/**
	 * 导入淘宝退货信息
	 * @param l
	 * @return
	 */
	public String updateRefundFromTb(List<TbRefund> l);
	
	/**
	 * 导入单个拍拍退货信息
	 * @param l
	 * @return
	 */
	public Map updateSingleRefundFromPp(Map refund,
			Integer storeId);
	
	/**
	 * 导入单个退货单
	 * @param refund
	 * @param storeId
	 * @return
	 */
	public Map updateSingleRefundFromTb(Refund refund,
			Integer storeId);
	
	/**
	 * 查询退货信息
	 * @param param
	 * @param currentPage
	 * @param pageRow
	 * @return
	 */
	public Map searchRefund(Map param, int currentPage, int pageRow);
	
	/**
	 * 退货内容
	 * @param orderHeadId
	 * @return
	 */
	public Map getRefundInfo(int orderHeadId);
	
	/**
	 * 检查订单
	 * @param orderHeadId
	 * @return
	 */
	public String auditedRefund(int orderHeadId);
	
	/**
	 * 批量审核订单
	 * @param orderHeadIds
	 * @return
	 */
	public String auditedRefunds(String orderHeadIds);
	
	/**
	 * 保留订单
	 * @param orderHeadId
	 * @param receiveMessage
	 * @return
	 */
	public String receiveRefund(int orderHeadId,String receiveMessage);
	
	/**
	 * 批量保留订单
	 * @param orderHeadIds
	 * @param receiveMessage
	 * @return
	 */
	public String receiveRefunds(String orderHeadIds,String receiveMessage);
	
	/**
	 * 保存退货单
	 * @param param
	 * @return
	 */
	public Map saveRefund(Map param);
	
	/**
	 * 删除退货单明细
	 * @param orderItemId
	 * @param orderHeadId
	 * @return
	 */
	public String deleteRefundItem(int orderItemId,int orderHeadId);
	
	/**
	 * 关闭退货单
	 * @param orderHeadId
	 * @return
	 */
	public String cancelRefund(int orderHeadId);
	
	/**
	 * 恢复退货单
	 * @param orderHeadId
	 * @return
	 */
	public String recoveryRefund(int orderHeadId);
}
