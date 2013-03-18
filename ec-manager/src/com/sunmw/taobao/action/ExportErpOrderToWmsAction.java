package com.sunmw.taobao.action;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.sunmw.web.bean.data.OrderTaskServices;
import com.sunmw.web.util.WebConfigProperties;
import com.sunmw.web.util.WebUtil;

public class ExportErpOrderToWmsAction {

	private OrderTaskServices orderTaskServices;
	private int storeId;

	public OrderTaskServices getOrderTaskServices() {
		return orderTaskServices;
	}

	public void setOrderTaskServices(OrderTaskServices orderTaskServices) {
		this.orderTaskServices = orderTaskServices;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}
	public void exportOrderForWMS() {
		try {
			// 数据
			List<Map> orderList = orderTaskServices.exportOrderToWMS(storeId);
			if(orderList==null||orderList.size()==0)
				return;
			//文件名称
			String fileName = WebConfigProperties
					.getProperties("order.to_wms.filename");
			String path = WebConfigProperties
					.getProperties("order.to_wms.path");
			//备份
			String bak = WebConfigProperties
			.getProperties("order.to_wms.bak.path");
			//模板文件
			String template= WebConfigProperties.getProperties("order.to_wms.template");
			File tem = new File(template);
			//模板文件不存在
			if(!tem.exists())
			{
				
			}
			File dir = new File(path);
			if (!dir.exists())
				dir.mkdirs();
			File file = new File(path + fileName + "_" //+ storeId + "_"
					+ WebUtil.formatDateString(new Date(), "yyyyMMddHHmmss")
					+ ".xls");
			
			if (!file.exists())
				file.createNewFile();
			//读模板文件
			Workbook wbtem = jxl.Workbook.getWorkbook(tem);
			//写入到新文件
			WritableWorkbook wwb = Workbook.createWorkbook(file,wbtem);
			//WritableSheet ws = wwb.createSheet("订单", 0);
			WritableSheet ws = wwb.getSheet(0);
			//writeFields(ws);
			//记录成功导出的订单号
			StringBuffer orderNos = new StringBuffer();
			// 写入文件
			if (orderList != null) {
				int r = 1;// 从第二行开始写
				int c = 0;// 计数商品数
				int q = 0;//明细数量合计,赠品使用
				double giftPrice = 0;//赠品分摊的优惠金额
				String orderNo = null;
				String[] keys = new String[] { "", 
						"BuyerName", "BuyerMail", "ReceiverPhone",
						"ReceiverMobile", "ReceiverName", "ReceiverState",
						"ReceiverCity", "ReceiverDistrict&ReceiverAddress",
						"ReceiverAZip", "ReceiverMobile-ReceiverPhone",
						"BuyerMessage&CustMemo&SellerMemo&TradeMemo&BuyerMemo", "ShippingType", "", };
				for (Map m : orderList) {
					String no = (String) m.get("OrderNo");
					//新订单或订单种类超过5
					if (orderNo == null || !orderNo.equals(no) || c >= 4) {
						//参数初始化
						orderNo = no;
						c = 1;
						r++;
						if(orderNos.length()>0)
							orderNos.append(",");
						orderNos.append("'"+orderNo+"'");
						q = 0;
						giftPrice = ((BigDecimal)m.get("PaymentAmt")).doubleValue()-((BigDecimal)m.get("FreightAmt")).doubleValue();
					}
					//订单明细
					else
						c++;
					//开始写订单,包括订单内容和订单明细第一行
					if (c == 1) {
						ws.addCell(new jxl.write.Label(0, r, "" + (r - 1)));
						for (int i = 1; i < keys.length; i++)
						{
							//备注信息的处理,目前有买家备注和客服留言
							if(keys[i].indexOf("&")>0)
							{
								String[] s = keys[i].split("&");
								StringBuffer ss = new StringBuffer();
								for(String k:s)
								{
									ss.append(m.get(k) == null ? "" : m
											.get(k).toString());
									
								}
								ws.addCell(new jxl.write.Label(i, r,
										ss.toString()));
							}
							//联系人电话
							else if(keys[i].indexOf("-")>0)
							{
								String[] s = keys[i].split("-");
								String ss = "";
								for(String k:s)
								{
									if(m.get(k) != null)
									{
										ss = m
										.get(k).toString();
										break;
									}
									
									
								}
								ws.addCell(new jxl.write.Label(i, r,
										ss.toString()));
							}
							//快递方式
							else if(keys[i].equals("ShippingType"))
							{
								String ship = m.get(keys[i]) == null ? "" : m
										.get(keys[i]).toString();
								String shipName = null;
								if(ship.equals("ems"))
								{
									shipName = "EMS";
								}
								else
								{
									shipName = "快递";
								}
								ws.addCell(new jxl.write.Label(i, r,shipName));
							}
							else
							ws.addCell(new jxl.write.Label(i, r,
									m.get(keys[i]) == null ? "" : m
											.get(keys[i]).toString()));
						}
						//商品明细
						ws.addCell(new jxl.write.Label(14, r,
								m.get("SkuCd") == null ? "" : m.get("SkuCd")
										.toString()));
						ws.addCell(new jxl.write.Label(15, r,
								m.get("Name") == null ? "" : m.get("Name")
										.toString()));
						ws.addCell(new jxl.write.Label(16, r, ""));
						int qty = Integer.parseInt(m.get("Qty") == null ? "0" : m.get("Qty")
								.toString());
						//写价格,要分摊掉优惠,按比例分摊
						BigDecimal price = (BigDecimal)m.get("Price");
						if(price==null)
							price = new BigDecimal(0);
						Double gift = (Double) m.get("OrderItemPriceGift");
						if(gift==null)
							gift = new Double(0);
						ws.addCell(new jxl.write.Label(17, r,
								WebUtil.round(new BigDecimal(price.doubleValue()-gift), 2).toString()));
//						ws.addCell(new jxl.write.Label(17, r,
//								m.get("Price") == null ? "0" : m.get("Price")
//										.toString()));
						
						ws.addCell(new jxl.write.Label(18, r,
								""+qty));
						//计算赠品数量
						q += Integer.parseInt(m.get("Qty") == null ? "0" : m.get("Qty")
										.toString());
						//计算赠品分摊优惠
						giftPrice = giftPrice - (price.doubleValue()-gift)*qty;
						
					} 
					//订单第二到第五行,只写订单明细
					else {
						//商品明细
						ws.addCell(new jxl.write.Label(14 + (c - 1) * 5, r, m
								.get("SkuCd") == null ? "" : m.get("SkuCd")
								.toString()));
						ws.addCell(new jxl.write.Label(15 + (c - 1) * 5, r, m
								.get("Name") == null ? "" : m.get("Name")
								.toString()));
						ws
								.addCell(new jxl.write.Label(16 + (c - 1) * 5,
										r, ""));
						//写价格,要分摊掉优惠,按比例分摊
						BigDecimal price = (BigDecimal)m.get("Price");
						if(price==null)
							price = new BigDecimal(0);
						Double gift = (Double) m.get("OrderItemPriceGift");
						if(gift==null)
							gift = new Double(0);
						ws.addCell(new jxl.write.Label(17 + (c - 1) * 5, r,
								WebUtil.round(new BigDecimal(price.doubleValue()-gift), 2).toString()));
//						ws.addCell(new jxl.write.Label(17 + (c - 1) * 5, r, m
//								.get("Price") == null ? "0" : m.get("Price")
//								.toString()));
						int qty = Integer.parseInt(m.get("Qty") == null ? "0" : m.get("Qty")
								.toString());
						ws.addCell(new jxl.write.Label(18 + (c - 1) * 5, r, ""+qty));
						//计算赠品数量
						q += qty;
						//计算赠品分摊优惠
						giftPrice = giftPrice - (price.doubleValue()-gift)*qty;
					}
					//赠品
					ws.addCell(new jxl.write.Label(34, r,"s1923P44613I0"));
					ws.addCell(new jxl.write.Label(35, r,"affiner collection 五折页"));
					ws.addCell(new jxl.write.Label(36, r, ""));
//					if(giftPrice>0)
//						ws.addCell(new jxl.write.Label(37, r,""+giftPrice));//赠品分摊金额
//					else
						ws.addCell(new jxl.write.Label(37, r,"0"));
					ws.addCell(new jxl.write.Label(38, r,""+q));
					//销售渠道,固定
					ws.addCell(new jxl.write.Label(39, r, "淘宝代销"));//affiner使用
					//ws.addCell(new jxl.write.Label(39, r, "自营店铺"));//sofiland使用
					
					//淘宝单号
					ws.addCell(new jxl.write.Label(40, r,(String)m.get("TaobaoOrderNo")));
					//收款ID
					//ws.addCell(new jxl.write.Label(41, r,""));
					//运费
					ws.addCell(new jxl.write.Number(41, r,WebUtil.isNull(m.get("FreightAmt"))?0:((BigDecimal)m.get("FreightAmt")).doubleValue()));
					//是否开票
					ws.addCell(new jxl.write.Label(42, r,WebUtil.isNull(m.get("IsInvoice"))?"":m.get("IsInvoice").toString()));
					//发票抬头
					ws.addCell(new jxl.write.Label(43, r,WebUtil.isNull(m.get("InvoiceTitle"))?"":m.get("InvoiceTitle").toString()));
					//订单金额
					//ws.addCell(new jxl.write.Label(44, r,WebUtil.isNull(m.get("PaymentAmt"))?"":m.get("PaymentAmt").toString()));
				}
			}
			wwb.write();
			// 关闭Excel工作薄对象
			wwb.close();
			//备份
			File bakdir = new File(bak);
			if(!bakdir.exists())
				bakdir.mkdirs();
			File bakfile = new File(bak+file.getName());
			if(!bakfile.exists())
				bakfile.createNewFile();
			WebUtil.fileCopy(file, bakfile);
			//更新订单状态
			orderTaskServices.updateOrderWmsStatus(orderNos.toString());
			// 日志
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 写入表头
	private void writeFields(WritableSheet ws) throws WriteException,
			RowsExceededException {
		String[] fields1 = new String[] { "", "", "用户信息", "配送信息", "商品1", "商品2",
				"商品3", "商品4-编码", "商品5", "外部渠道" };
		int[] fields1int = { 0, 1, 2, 6, 15, 20, 25, 30, 35, 40 };
		ws.mergeCells(2, 0, 5, 0);
		ws.mergeCells(6, 0, 14, 0);
		ws.mergeCells(15, 0, 19, 0);
		ws.mergeCells(20, 0, 24, 0);
		ws.mergeCells(25, 0, 29, 0);
		ws.mergeCells(30, 0, 34, 0);
		ws.mergeCells(35, 0, 39, 0);
		ws.mergeCells(40, 0, 40, 1);
		String[] fields2 = new String[] { "序号", "淘宝订单号", "用户姓名", "邮箱", "电话",
				"手机", "收货人", "地区（省）", "地区（市）", "配送地址", "邮编", "联系电话", "备注",
				"快递", "快递单号", "商品1-编码", "商品1-名称", "商品1-规格", "商品1-单价", "商品1-数量",
				"商品2-编码", "商品2-名称", "商品2-规格", "商品2-单价", "商品2-数量", "商品3-编码",
				"商品3-名称", "商品3-规格", "商品3-单价", "商品3-数量", "商品4-编码", "商品4-名称",
				"商品4-规格", "商品4-单价", "商品4-数量", "商品5-编码", "商品5-名称", "商品5-规格",
				"商品5-单价", "商品5-数量" };

		for (int i = 0; i < fields1.length; i++) {
			jxl.write.Label labelC = new jxl.write.Label(fields1int[i], 0,
					fields1[i]);
			ws.addCell(labelC);
		}
		for (int i = 0; i < fields2.length; i++) {
			jxl.write.Label labelC = new jxl.write.Label(i, 1, fields2[i]);
			ws.addCell(labelC);
		}
	}
}
