package com.sunmw.web.action.jasper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.struts2.ServletActionContext;

import com.sunmw.web.bean.order.OrderServices;
import com.sunmw.web.entity.Customer;
import com.sunmw.web.entity.OrderHead;
import com.sunmw.web.entity.OrderItem;

public class OrderAction {

	private OrderServices orderServices;

	private int orderHeadId;
	private List orderList;

	public String orderPrint() {
		if(orderList==null)
			orderList = new ArrayList();
		Map orderInfo = orderServices.getOrderInfo(orderHeadId);
		if(orderInfo==null)
			return "success";
		Map info = new HashMap();
		OrderHead oh = (OrderHead) orderInfo.get("OrderHead");
		Customer cust = (Customer) orderInfo.get("Customer");
		List<OrderItem> olList = (List) orderInfo.get("OrderItem");
		info.put("title", "JackWalk门店订购确认单");
		if(cust!=null)
			info.put("custNo", cust.getCustNo());
		if(oh!=null)
		{
			info.put("receiverAddress", oh.getReceiverAddress());
			info.put("receiverCity", oh.getReceiverCity());
			info.put("receiverDistrict", oh.getReceiverDistrict());
			info.put("receiverMobile", oh.getReceiverMobile());
			info.put("receiverName", oh.getReceiverName());
			info.put("receiverState", oh.getReceiverState());
			info.put("totalFee", oh.getTotalFee());
			info.put("postFee", oh.getPostFee());
			info.put("payment", oh.getPayment());
			info.put("discountFee", oh.getDiscount());
		}
		String subReport = ServletActionContext.getServletContext()
		.getRealPath("/application/jasper/SubReportForOrder.jasper");
		info.put("subReportName", subReport);
		if(olList!=null)
		{
			List<Map> il = new ArrayList();
			for(OrderItem oi:olList)
			{
				Map m = new HashMap();
				m.put("name", oi.getName());
				m.put("skuProp1", oi.getSkuProp1());
				m.put("skuProp2", oi.getSkuProp2());
				m.put("price", oi.getPrice());
				m.put("qty", oi.getQty());
				il.add(m);
			}
			Map mm = new HashMap();
			mm.put("orderItem", new JRBeanCollectionDataSource(il));
			List ool = new ArrayList();
			ool.add(mm);
			info.put("subReportData", new JRBeanCollectionDataSource(ool));
		}
		
		orderList.add(info);
		return "success";
	}

	public OrderServices getOrderServices() {
		return orderServices;
	}

	public void setOrderServices(OrderServices orderServices) {
		this.orderServices = orderServices;
	}

	public int getOrderHeadId() {
		return orderHeadId;
	}

	public void setOrderHeadId(int orderHeadId) {
		this.orderHeadId = orderHeadId;
	}

	public List getOrderList() {
		return orderList;
	}

	public void setOrderList(List orderList) {
		this.orderList = orderList;
	}

}
