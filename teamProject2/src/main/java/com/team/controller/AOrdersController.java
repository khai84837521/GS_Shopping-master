
package com.team.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.team.domain.AEventDTO;
import com.team.domain.AMemberDTO;
import com.team.domain.AOrdersDTO;
import com.team.domain.ListDTO;
import com.team.domain.MemberDTO;
import com.team.service.AOrdersService;



@Controller
public class AOrdersController {
	
	@Inject
	private AOrdersService AOrdersService;
	
	@RequestMapping(value="/insertOrder")
	public void subBasicOrder(
			@RequestParam String memId ,@RequestParam String goodsNum ,@RequestParam String goodsCount ,
			@RequestParam String orderAddr ,@RequestParam String orderSub ,@RequestParam String orderZipcode ,
			@RequestParam String orderPhone ,@RequestParam Integer amount,@RequestParam String resipient) {
			
			AOrdersDTO AOrdersdto = new AOrdersDTO();
			AOrdersdto.setMemId(memId);
			String orderProduct = goodsNum + ",-" + goodsCount + ",";
			AOrdersdto.setOrderProduct(orderProduct);
			AOrdersdto.setOrderAddr(orderAddr);
			AOrdersdto.setOrderSub(orderSub);
			AOrdersdto.setOrderZipcode(orderZipcode);
			AOrdersdto.setOrderPhone(orderPhone);
			AOrdersdto.setAmount(amount);
			AOrdersdto.setResipient(resipient);
			
			String postCode = "987654";
			AOrdersdto.setPostCode(postCode);
			Timestamp date = new Timestamp(System.currentTimeMillis()); 
			AOrdersdto.setOrderDate(date);
			
			AOrdersService.insertOrder(AOrdersdto);
	}
	
	@RequestMapping(value = "/orders", method = RequestMethod.GET)
	   public String Vieworders(@RequestParam Integer goodsNum , @RequestParam Integer goodsCount , Model model , HttpSession session, HttpServletRequest re){
	      
	      ListDTO Listdto = AOrdersService.getProductinfo(goodsNum);
	      
	      model.addAttribute("ListDTO" , Listdto);
	      model.addAttribute("goodsCount", goodsCount);
	      model.addAttribute("goodsNum", goodsNum);
	      
	      MemberDTO mDTO = (MemberDTO)session.getAttribute("user");
	      String memId = mDTO.getMemId();
	      
	      AMemberDTO AMemberDTO = AOrdersService.getMemberinfo(memId);
	      
	      model.addAttribute("AMemberDTO" , AMemberDTO);
	      
	      List<AEventDTO> myCoupon =  new ArrayList<AEventDTO>();
	      List<AEventDTO> couponList =  AOrdersService.myCouponList(memId);
	      
	      for (int i = 0; i < couponList.size(); i++) {
	         AEventDTO mcDTO = couponList.get(i);
	         mcDTO.setType(AOrdersService.couponInfo(mcDTO.getCoupon_code()).getType());
	         mcDTO.setName(AOrdersService.couponInfo(mcDTO.getCoupon_code()).getName());
	         if ( (mcDTO.getUseable().equals("t") || mcDTO.getUseable().equals("i")) && !(mcDTO.getType().equals("5"))){
	            myCoupon.add(mcDTO);
	         }
	      }
	      
	      model.addAttribute("myCoupon", myCoupon);
	      return "/orders";
	   }
}