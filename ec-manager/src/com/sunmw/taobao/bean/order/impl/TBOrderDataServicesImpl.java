package com.sunmw.taobao.bean.order.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sunmw.taobao.bean.order.TBOrderDataServices;
import com.sunmw.taobao.entity.TbOrder;
import com.sunmw.taobao.entity.TbPromotionDetail;
import com.sunmw.taobao.entity.TbTrade;
import com.sunmw.web.util.WebUtil;
import com.taobao.api.domain.Order;
import com.taobao.api.domain.PromotionDetail;
import com.taobao.api.domain.Trade;

public class TBOrderDataServicesImpl extends HibernateDaoSupport implements
		TBOrderDataServices {

	@Override
	public void insertTid(Long tid) {
		//此处同步处理，防止数据重复
		synchronized(this)
		{
			List l = this.getHibernateTemplate().find("select count(*) from TbTrade where Tid = ?",new BigDecimal(tid));
			if(l!=null&&l.size()>0&&l.get(0)!=null)
				return;
			TbTrade t = new TbTrade();
			t.setTid(new BigDecimal(tid));
			t.setUpdateFlag("I");//表示初始化标记，内容还没导入。
			t.setUpdateTime(new Date());
			this.getHibernateTemplate().save(t);
		}		
	}

	@Override
	public void updateTrade(Trade t,Integer storeId) {
		TbTrade tbTrade = null;
		List<TbTrade> tbTradeList = this.getHibernateTemplate().find(
				"from TbTrade where Tid = ?", t.getTid());
		//没有插入过Tid则不处理
		if (tbTradeList == null || tbTradeList.size() == 0)
		{
			return;
		}
		tbTrade = tbTradeList.get(0);
		Date date = new Date();
		//I标记处理，表示内容没有加载过
		if(tbTrade.getUpdateFlag()==null||tbTrade.getUpdateFlag().equals("I"))
		{
			try
			{
			tbTrade.setIsUpdateFinished(false);
			tbTrade.setEndTime(t.getEndTime());
			tbTrade.setSellerNick(t.getSellerNick());
			tbTrade.setBuyerNick(t.getBuyerNick());
			tbTrade.setTitle(t.getTitle());
			tbTrade.setType(t.getType());
			tbTrade.setCreated(t.getCreated());
			tbTrade.setIid(t.getIid());
			if (t.getPrice() != null)
				tbTrade.setPrice(BigDecimal.valueOf(Double.valueOf(t
						.getPrice())));
			tbTrade.setPicPath(t.getPicPath());
			if (t.getNum() != null)
				tbTrade.setNum(BigDecimal.valueOf(Double
						.valueOf(t.getNum())));
			if (t.getTid() != null)
				tbTrade.setTid(BigDecimal.valueOf(Double
						.valueOf(t.getTid())));
			tbTrade.setBuyerMessage(t.getBuyerMessage());
			tbTrade.setShippingType(t.getShippingType());
			tbTrade.setAlipayNo(t.getAlipayNo());
			if (t.getPayment() != null)
				tbTrade.setPayment(BigDecimal.valueOf(Double.valueOf(t
						.getPayment())));
			if (t.getDiscountFee() != null)
				tbTrade.setDiscountFee(BigDecimal.valueOf(Double.valueOf(t
						.getDiscountFee())));
			if (t.getAdjustFee() != null)
				tbTrade.setAdjustFee(BigDecimal.valueOf(Double.valueOf(t
						.getAdjustFee())));
			tbTrade.setSnapshotUrl(t.getSnapshotUrl());
			tbTrade.setSnapshot(t.getSnapshot());
			tbTrade.setStatus(t.getStatus());
			tbTrade.setSellerRate(t.getSellerRate());
			tbTrade.setBuyerRate(t.getBuyerRate());
			tbTrade.setBuyerMemo(t.getBuyerMemo());
			tbTrade.setSellerMemo(t.getSellerMemo());
			tbTrade.setTradeMemo(t.getTradeMemo());
			tbTrade.setPayTime(t.getPayTime());
			tbTrade.setModified(t.getModified());
			if (t.getBuyerObtainPointFee() != null)
				tbTrade.setBuyerObtainPointFee(BigDecimal.valueOf(Double
						.valueOf(t.getBuyerObtainPointFee())));
			if (t.getPointFee() != null)
				tbTrade.setPointFee(BigDecimal.valueOf(Double.valueOf(t
						.getPointFee())));
			if (t.getRealPointFee() != null)
				tbTrade.setRealPointFee(BigDecimal.valueOf(Double.valueOf(t
						.getRealPointFee())));
			if (t.getTotalFee() != null)
				tbTrade.setTotalFee(BigDecimal.valueOf(Double.valueOf(t
						.getTotalFee())));
			if (t.getPostFee() != null)
				tbTrade.setPostFee(BigDecimal.valueOf(Double.valueOf(t
						.getPostFee())));
			tbTrade.setBuyerAlipayNo(t.getBuyerAlipayNo());
			tbTrade.setReceiverName(t.getReceiverName());
			tbTrade.setReceiverState(t.getReceiverState());
			tbTrade.setReceiverCity(t.getReceiverCity());
			tbTrade.setReceiverDistrict(t.getReceiverDistrict());
			tbTrade.setReceiverAddress(t.getReceiverAddress());
			tbTrade.setReceiverZip(t.getReceiverZip());
			tbTrade.setReceiverMobile(t.getReceiverMobile());
			tbTrade.setReceiverPhone(t.getReceiverPhone());
			tbTrade.setConsignTime(t.getConsignTime());
			tbTrade.setBuyerEmail(t.getBuyerEmail());
			if (t.getCommissionFee() != null)
				tbTrade.setCommissionFee(BigDecimal.valueOf(Double
						.valueOf(t.getCommissionFee())));
			tbTrade.setSellerAlipayNo(t.getSellerAlipayNo());
			tbTrade.setSellerMobile(t.getSellerMobile());
			tbTrade.setSellerPhone(t.getSellerPhone());
			tbTrade.setSellerName(t.getSellerName());
			tbTrade.setSellerEmail(t.getSellerEmail());
			if (t.getAvailableConfirmFee() != null)
				tbTrade.setAvailableConfirmFee(BigDecimal.valueOf(Double
						.valueOf(t.getAvailableConfirmFee())));
			if (t.getHasPostFee() != null)
				tbTrade.setHasPostFee(t.getHasPostFee());
			if (t.getReceivedPayment() != null)
				tbTrade.setReceivedPayment(BigDecimal.valueOf(Double
						.valueOf(t.getReceivedPayment())));
			if (t.getCodFee() != null)
				tbTrade.setCodFee(BigDecimal.valueOf(Double.valueOf(t
						.getCodFee())));
			tbTrade.setCodStatus(t.getCodStatus());
			tbTrade.setTimeoutActionTime(t.getTimeoutActionTime());
			if (t.getIs3D() != null)
				tbTrade.setIs3d(t.getIs3D());
			if (t.getBuyerFlag() != null)
				tbTrade.setBuyerFlag(t.getBuyerFlag().intValue());
			if (t.getSellerFlag() != null)
				tbTrade.setSellerFlag(t.getSellerFlag().intValue());
			if (t.getNumIid() != null)
				tbTrade.setNumIid(BigDecimal.valueOf(Double.valueOf(t
						.getNumIid())));
			tbTrade.setPromotion(t.getPromotion());
			tbTrade.setInvoiceName(t.getInvoiceName());
			tbTrade.setTradeFrom(t.getTradeFrom());
			tbTrade.setAlipayUrl(t.getAlipayUrl());
			tbTrade.setHasYfx(t.getHasYfx());
			if(t.getYfxFee()!=null)
			tbTrade.setYfxFee(BigDecimal.valueOf(Double.valueOf(t.getYfxFee())));
			tbTrade.setYfxId(t.getYfxId());
			//tbTrade.setYfxType();
			if(t.getCreditCardFee()!=null)
			tbTrade.setCreditCardFee(BigDecimal.valueOf(Double.valueOf(t.getCreditCardFee())));
			tbTrade.setNutFeature(t.getNutFeature());
			//tbTrade.setStepTradeStatus();
			//tbTrade.setStepPaidFee();
			tbTrade.setMarkDesc(t.getMarkDesc());
			//tbTrade.setSendTime();
			tbTrade.setUpdateTime(date);
			tbTrade.setUpdateFlag("D");
			this.getHibernateTemplate().update(tbTrade);
			List<Order> orders = t.getOrders();
			// 无订单明细
			if (orders == null || orders.size() == 0) {
				logger.error("订单明细为空");
			} else {
				for (Order o : orders) {
					TbOrder tbOrder = new TbOrder();
					if (o.getTotalFee() != null)
						tbOrder.setTotalFee(BigDecimal.valueOf(Double
								.valueOf(o.getTotalFee())));
					if (o.getDiscountFee() != null)
						tbOrder.setDiscountFee(BigDecimal.valueOf(Double
								.valueOf(o.getDiscountFee())));
					if (o.getAdjustFee() != null)
						tbOrder.setAdjustFee(BigDecimal.valueOf(Double
								.valueOf(o.getAdjustFee())));
					tbOrder.setIid(o.getIid());
					tbOrder.setSkuId(o.getSkuId());
					tbOrder.setSkuPropertiesName(o.getSkuPropertiesName());
					tbOrder.setItemMealName(o.getItemMealName());
					if (o.getNum() != null)
						tbOrder.setNum(BigDecimal.valueOf(Double.valueOf(o
								.getNum())));
					tbOrder.setTitle(o.getTitle());
					if (o.getPrice() != null)
						tbOrder.setPrice(BigDecimal.valueOf(Double
								.valueOf(o.getPrice())));
					tbOrder.setPicPath(o.getPicPath());
					tbOrder.setSellerNick(o.getSellerNick());
					tbOrder.setBuyerNick(o.getBuyerNick());
					tbOrder.setCreated(o.getModified());
					tbOrder.setRefundStatus(o.getRefundStatus());
					if (o.getOid() != null)
						tbOrder.setOid(BigDecimal.valueOf(Double.valueOf(o
								.getOid())));
					tbOrder.setOuterIid(o.getOuterIid());
					tbOrder.setOuterSkuId(o.getOuterSkuId());
					if (o.getPayment() != null)
						tbOrder.setPayment(BigDecimal.valueOf(Double
								.valueOf(o.getPayment())));
					tbOrder.setStatus(o.getStatus());
					tbOrder.setSnapshotUrl(o.getSnapshotUrl());
					tbOrder.setSnapshot(o.getSnapshot());
					tbOrder.setTimeoutActionTime(o.getTimeoutActionTime());
					if (o.getBuyerRate() != null)
						tbOrder.setBuyerRate(o.getBuyerRate());
					if (o.getSellerRate() != null)
						tbOrder.setSellerRate(o.getSellerRate());
					if (o.getRefundId() != null)
						tbOrder.setRefundId(BigDecimal.valueOf(Double
								.valueOf(o.getRefundId())));
					tbOrder.setSellerType(o.getSellerType());
					tbOrder.setModified(o.getModified());
					if (o.getNumIid() != null)
						tbOrder.setNumIid(BigDecimal.valueOf(Double
								.valueOf(o.getNumIid())));
					if (o.getCid() != null)
						tbOrder.setCid(BigDecimal.valueOf(Double.valueOf(o
								.getCid())));
					if (o.getIsOversold() != null)
						tbOrder.setIsOversold(o.getIsOversold());
					tbOrder.setTradeId(BigDecimal.valueOf(t.getTid()));
					tbOrder.setUpdateTime(date);
					tbOrder.setUpdateFlag("D");
					tbOrder.setStoreId(storeId);
					this.getHibernateTemplate().save(tbOrder);
				}
			}
			// 促销
			List<PromotionDetail> promotionDetailList = t
					.getPromotionDetails();
			if (promotionDetailList != null
					&& promotionDetailList.size() > 0
					&& promotionDetailList.get(0) != null) {
				for (PromotionDetail pd : promotionDetailList) {
					TbPromotionDetail tbpd = new TbPromotionDetail();
					tbpd.setUpdateFlag("D");
					tbpd.setUpdateTime(date);
					tbpd.setStoreId(storeId);
					if (WebUtil.isNotNull(pd.getDiscountFee()))
						tbpd.setDiscountFee(new BigDecimal(pd
								.getDiscountFee()));
					tbpd.setGiftItemName(pd.getGiftItemName());
					tbpd.setTbPromotionDetailId(pd.getId());
					tbpd.setPromotionName(pd.getPromotionName());
					tbpd.setTradeId(BigDecimal.valueOf(t.getTid()
							.longValue()));
					this.getHibernateTemplate().save(tbpd);
				}
			}
			}
			catch(RuntimeException e)
			{
				logger.error(e.getMessage());
			}
		}
		//否则只处理可能变化的内容
		else
		{
			tbTrade.setStatus(t.getStatus());
			tbTrade.setSellerRate(t.getSellerRate());
			tbTrade.setBuyerRate(t.getBuyerRate());
			tbTrade.setBuyerMemo(t.getBuyerMemo());
			tbTrade.setSellerMemo(t.getSellerMemo());
			tbTrade.setTradeMemo(t.getTradeMemo());
			tbTrade.setBuyerAlipayNo(t.getBuyerAlipayNo());
			tbTrade.setPayTime(t.getPayTime());
			tbTrade.setModified(t.getModified());
			if (t.getBuyerObtainPointFee() != null)
				tbTrade.setBuyerObtainPointFee(BigDecimal.valueOf(Double
						.valueOf(t.getBuyerObtainPointFee())));
			if (t.getPointFee() != null)
				tbTrade.setPointFee(BigDecimal.valueOf(Double.valueOf(t
						.getPointFee())));
			if (t.getRealPointFee() != null)
				tbTrade.setRealPointFee(BigDecimal.valueOf(Double.valueOf(t
						.getRealPointFee())));
			if (t.getTotalFee() != null)
				tbTrade.setTotalFee(BigDecimal.valueOf(Double.valueOf(t
						.getTotalFee())));
			if (t.getPostFee() != null)
				tbTrade.setPostFee(BigDecimal.valueOf(Double.valueOf(t
						.getPostFee())));
			tbTrade.setBuyerMessage(t.getBuyerMessage());
			if (t.getPayment() != null)
				tbTrade.setPayment(BigDecimal.valueOf(Double.valueOf(t
						.getPayment())));
			if (t.getDiscountFee() != null)
				tbTrade.setDiscountFee(BigDecimal.valueOf(Double.valueOf(t
						.getDiscountFee())));
			if (t.getAdjustFee() != null)
				tbTrade.setAdjustFee(BigDecimal.valueOf(Double.valueOf(t
						.getAdjustFee())));
			tbTrade.setHasYfx(t.getHasYfx());
			if(t.getYfxFee()!=null)
			tbTrade.setYfxFee(BigDecimal.valueOf(Double.valueOf(t.getYfxFee())));
			tbTrade.setYfxId(t.getYfxId());
			//tbTrade.setYfxType();
			if(t.getCreditCardFee()!=null)
			tbTrade.setCreditCardFee(BigDecimal.valueOf(Double.valueOf(t.getCreditCardFee())));
			tbTrade.setNutFeature(t.getNutFeature());
			//tbTrade.setStepTradeStatus();
			//tbTrade.setStepPaidFee();
			tbTrade.setMarkDesc(t.getMarkDesc());
			//tbTrade.setSendTime();
			this.getHibernateTemplate().update(tbTrade);
		}
	}

}
