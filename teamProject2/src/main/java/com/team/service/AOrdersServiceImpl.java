package com.team.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.team.dao.AOrdersDAO;
import com.team.domain.AEventDTO;
import com.team.domain.AMemberDTO;
import com.team.domain.AOrdersDTO;
import com.team.domain.ListDTO;

@Service
public class AOrdersServiceImpl implements AOrdersService{

	@Inject
	private AOrdersDAO AOrdersDAO;
	
	@Override
	public ListDTO getProductinfo(int goodsNo) {
		return AOrdersDAO.getProductinfo(goodsNo);
	}

	@Override
	public AMemberDTO getMemberinfo(String memId) {
		return AOrdersDAO.getMemberinfo(memId);
	}

	@Override
	public void insertOrder(AOrdersDTO AOrdersdto) {
		AOrdersDAO.insertOrder(AOrdersdto);
	}
	
	@Override
	public List<AEventDTO> myCouponList(String memId) {
		return AOrdersDAO.myCouponList(memId);
	}


	@Override
	public AEventDTO couponInfo(int coupon_code) {
		return AOrdersDAO.couponInfo(coupon_code);
	}

}
