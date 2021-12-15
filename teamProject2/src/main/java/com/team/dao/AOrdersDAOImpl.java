package com.team.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.team.domain.AEventDTO;
import com.team.domain.AMemberDTO;
import com.team.domain.AOrdersDTO;
import com.team.domain.ListDTO;

@Repository
public class AOrdersDAOImpl implements AOrdersDAO{

	@Inject
	private SqlSession sqlSession;
	
	private static final String namespace="com.team.mapper.AOrdersMapper";

	@Override
	public ListDTO getProductinfo(int goodsNo) {
		return sqlSession.selectOne(namespace + ".getproductinfo", goodsNo);
	}

	@Override
	public AMemberDTO getMemberinfo(String memId) {
		return sqlSession.selectOne(namespace + ".getmemberinfo", memId);
	}

	@Override
	public void insertOrder(AOrdersDTO AOrdersdto) {
		sqlSession.insert(namespace + ".insertorder", AOrdersdto);
	}
	
	@Override
	public List<AEventDTO> myCouponList(String memId) {
		return sqlSession.selectList(namespace + ".myCouponList", memId);
	}

	@Override
	public AEventDTO couponInfo(int coupon_code) {
		return sqlSession.selectOne(namespace + ".couponInfo", coupon_code);
	}
}