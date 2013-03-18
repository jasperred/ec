package com.sunmw.taobao.bean.notify;

import com.taobao.api.internal.stream.connect.ConnectionLifeCycleListener;

public class ConnectionLifeCycleListenerImpl implements
		ConnectionLifeCycleListener {

	private int storeId;	

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	@Override
	public void onBeforeConnect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onException(Throwable arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMaxReadTimeoutException() {
		// TODO Auto-generated method stub
		
	}
	

}
