package com.team.dao;

import com.team.domain.AMemberDTO;
import com.team.domain.AOrdersDTO;
import com.team.domain.ListDTO;
import java.util.List;
import com.team.domain.AEventDTO;

public interface AOrdersDAO {
	
	public ListDTO getProductinfo(int goodsNo);
	
	public AMemberDTO getMemberinfo(String memId);
	
	public void insertOrder(AOrdersDTO AOrdersdto);
	
	public List<AEventDTO> myCouponList(String memId);
	
	public AEventDTO couponInfo(int coupon_code);
}
