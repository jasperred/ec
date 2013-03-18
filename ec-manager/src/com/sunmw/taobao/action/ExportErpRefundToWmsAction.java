package com.sunmw.taobao.action;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.sunmw.web.bean.data.RefundTaskServices;
import com.sunmw.web.util.WebConfigProperties;
import com.sunmw.web.util.WebUtil;

public class ExportErpRefundToWmsAction {

	private RefundTaskServices refundTaskServices;
	private int storeId;

	public RefundTaskServices getRefundTaskServices() {
		return refundTaskServices;
	}

	public void setRefundTaskServices(RefundTaskServices refundTaskServices) {
		this.refundTaskServices = refundTaskServices;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}
	public void exportRefundForWMS() {
		try {
			// 数据
			List<Map> orderList = refundTaskServices.exportRefund(storeId);
			if(orderList==null||orderList.size()==0)
				return;
			//文件名称
			String fileName = WebConfigProperties
					.getProperties("refund.to_wms.filename");
			String path = WebConfigProperties
					.getProperties("refund.export.path");
			//备份
			String bak = WebConfigProperties
			.getProperties("refund.export.bak.path");
			//模板文件
			String template= WebConfigProperties.getProperties("refund.export.template");
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
			StringBuffer orderNos = new StringBuffer();
			// 写入文件
			if (orderList != null) {
				int r = 1;// 从第二行开始写
				int c = 0;// 计数商品数
				int q = 0;//明细数量合计,赠品使用
				String orderNo = null;
				String[] keys = new String[] { "", 
						"OrderNo","Origin", "RefundOrderNo", "RefundStatus",
						"RefOrderNo", "TbOrderStatus", "GoodStatus",
						"HasGoodReturn", "BuyerName",
						"RefundDate", "ActualTotalAmt",
						"PaymentAmt", "RefundAmt", "REFUND_REASON","REFUND_DESC","" ,"ReceiverName" ,"ReceiverAddress" ,"ReceiverPhone" ,"ItemCd" ,"SkuCd" ,"Name" ,"Price" ,"Qty" };
				for (Map m : orderList) {
					int a=0,b=keys.length;
					//订单
					if(orderNo==null||!m.get("OrderNo").equals(orderNo))
					{
						orderNo = (String) m.get("OrderNo");
						if(orderNos.length()>0)
							orderNos.append(",");
						orderNos.append("'"+orderNo+"'");
						a=0;
						b=keys.length;
					}
					//只写明细
					else
					{
						a=19;
						b=24;
					}
					//写订单
					for(int j=a;j<b;j++)
					{
						if(m.get(keys[j]) instanceof java.lang.Integer ||m.get(keys[j]) instanceof java.lang.Double)
						{
							ws.addCell(new jxl.write.Number(j, r,
									m.get(keys[j])==null?0:Double.parseDouble(m.get(keys[j]).toString())));
						}
						else
						{
							String s = null;
							//地址合并
							if(keys[j].equals("ReceiverAddress"))
							{
								s = (String)m.get("ReceiverState")+m.get("ReceiverCity")+m.get("ReceiverDistrict")+m.get("ReceiverAddress")+"("+m.get("ReceiverAZip")+")";
							}
							//电话合并
							else if(keys[j].equals("ReceiverPhone"))
							{
								s = m.get("ReceiverPhone")+" "+m.get("ReceiverMobile");
							}
							else
							{
								s = m.get(keys[j])==null?"":m.get(keys[j]).toString();
							}
							
							
							ws.addCell(new jxl.write.Label(j, r,
									s));
						}
					}
					r++;
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
			refundTaskServices.updateRefundWmsStatus(orderNos.toString());
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

}
