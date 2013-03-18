package com.sunmw.taobao.bean.product.impl;

import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sunmw.taobao.bean.product.ProductServices;

public class ProductServicesImpl extends HibernateDaoSupport implements
		ProductServices {

	@Override
	public String deleteProduct(int productId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getProductImages(int productId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map getProductInfo(int productId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getProductTask(int productId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map noProductSearch(Map param, int currentPage, int pageRow) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map saveProduct(Map param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String saveProductImage(Map param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String saveProductTask(Map<String, String> param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map searchProduct(Map param, int currentPage, int pageRow) {
		// TODO Auto-generated method stub
		return null;
	}

}
