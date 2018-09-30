package com.dco.eureka.client.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dco.eureka.client.common.Const;
import com.dco.eureka.client.service.ThirdBasicInfoService;

@RestController
public class DcoEurekaController {

	private static final Logger log=LoggerFactory.getLogger(DcoEurekaController.class);
	
	@Autowired
	ThirdBasicInfoService thirdBasicInfoService;
	@Autowired
	Third
	
	@RequestMapping(value="/api/user_mgr/authLogin.json")
	public void 
	
	@RequestMapping(value="/api/user_mgr/getUserInfo.json",produces="application/json;charset=UTF-8",method=RequestMethod.POST)
	@ResponseBody
	public String getUserInfo(String paraName,String paraValue,HttpServletResponse response){
		String result="{}";
		int uId=-1;
		JSONObject r=null;
		try{
			Map<String,Object> uMap=null;
			if(paraName.equalsIgnoreCase(Const.PARANAMEUSERID)){
				uId=Integer.parseInt(paraValue);
				uMap=thirdBasicInfoService.getThirdCountByUserId(uId);
			}else if(paraName.equalsIgnoreCase(Const.PARANAMETHIRDACCOUNTID)){
				uMap=thirdBasicInfoService.getThirdCountByThirdAccountId(paraValue);
			}
			if(uMap!=null){
				int tid=Integer.parseInt(uMap.get("tid").toString());
				uMap.remove("tid");
				r=JSONObject.parseObject(JSON.toJSONString(uMap));
				List<Map<String,Object>> hl=thirdHotPubInfoService.queryHotPubByTid(tid);
				JSONArray jr=new JSONArray();
				if(hl.size()>0){
					jr=JSONArray.parseArray(JSON.toJSONString(hl));
				}
				r.put("hotPubList", jr.toString());
				result=r.toString();
			}
		}catch(NumberFormatException e){
			r=new JSONObject();
			r.put("error", "userId为正整数,请输入正确的userId:"+e.getMessage());
			result=r.toString();
			logger.error(result);
		}
		return result;
	}

}
