package org.fh.controller.fhoa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.fh.controller.base.BaseController;
import org.fh.entity.Page;
import org.fh.util.Tools;
import org.fh.entity.PageData;
import org.fh.service.fhoa.MeetRoomService;

/** 
 * 说明：会议室标注
 * 作者：FH Admin QQ313596790
 * 官网：www.fhadmin.org
 */
@Controller
@RequestMapping("/meetroom")
public class MeetRoomController extends BaseController {
	
	@Autowired
	private MeetRoomService meetroomService;

	/**新增
	 * @param
	 * @throws Exception
	 */
	public void add(String BIANMA,String NTIME,String FZD) throws Exception{
		PageData pd = new PageData();
		pd.put("MEETROOM_ID", this.get32UUID());	//主键
		pd.put("BIANMA", BIANMA);	//会议室
		pd.put("NTIME", NTIME);		//日期
		pd.put("T071", "no");	//7点00
		pd.put("T072", "no");	//7点30
		pd.put("T081", "no");	//8点00
		pd.put("T082", "no");	//8点30
		pd.put("T091", "no");	//9点00
		pd.put("T092", "no");	//9点30
		pd.put("T101", "no");	//10点00
		pd.put("T102", "no");	//10点30
		pd.put("T111", "no");	//11点00
		pd.put("T112", "no");	//11点30
		pd.put("T121", "no");	//12点00
		pd.put("T122", "no");	//12点30
		pd.put("T131", "no");	//13点00
		pd.put("T132", "no");	//13点30
		pd.put("T141", "no");	//14点00
		pd.put("T142", "no");	//14点30
		pd.put("T151", "no");	//15点00
		pd.put("T152", "no");	//15点30
		pd.put("T161", "no");	//16点00
		pd.put("T162", "no");	//16点30
		pd.put("T171", "no");	//17点00
		pd.put("T172", "no");	//17点30
		pd.put("T181", "no");	//18点00
		pd.put("T182", "no");	//18点30
		pd.put("T191", "no");	//19点00
		pd.put("T192", "no");	//19点30
		pd.put("T201", "no");	//20点00
		pd.put("T202", "no");	//20点30
		pd.put("T211", "no");	//21点00
		pd.put("T212", "no");	//21点30
		pd.put("T221", "no");	//22点00
		pd.put("T222", "no");	//22点30
		pd.put("T231", "no");	//23点00
		pd.put("T232", "no");	//23点30
		pd.put(FZD, "yes");
		meetroomService.save(pd);
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public Object delete() throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		pd = this.getPageData();
		meetroomService.delete(pd);
		map.put("result", errInfo);				//返回结果
		return map;
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	@ResponseBody
	public Object edit() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		String errInfo = "success";
		PageData pd = new PageData();
		pd = this.getPageData();
		String BIANMA = pd.getString("BIANMA");
		String NTIME = pd.getString("NTIME");
		String FZD = pd.getString("FZD");
		PageData npd = new PageData();
		npd = meetroomService.findById(pd);
		if(null == npd) {
			this.add(BIANMA, NTIME, FZD);
		}else {
			if(npd.getString(FZD).equals("yes")) {
				npd.put(FZD, "no");
			}else {
				npd.put(FZD, "yes");
			}
			meetroomService.edit(npd);
		}
		map.put("result", errInfo);
		return map;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	@ResponseBody
	public Object list(Page page) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		String errInfo = "success";
		PageData pd = new PageData();
		pd = this.getPageData();
		String KEYWORDS = pd.getString("KEYWORDS");						//关键词检索条件
		if(Tools.notEmpty(KEYWORDS))pd.put("KEYWORDS", KEYWORDS.trim());
		page.setPd(pd);
		List<PageData>	varList = meetroomService.list(page);	//列出MeetRoom列表
		map.put("varList", varList);
		map.put("page", page);
		map.put("result", errInfo);
		return map;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/listAll")
	@ResponseBody
	public Object listAll() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		String errInfo = "success";
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData>	varList = meetroomService.listAll(pd);	//列出MeetRoom列表
		map.put("varList", varList);
		map.put("result", errInfo);
		return map;
	}
	
	 /**去修改页面获取数据
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goEdit")
	@ResponseBody
	public Object goEdit() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		String errInfo = "success";
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = meetroomService.findById(pd);	//根据ID读取
		map.put("pd", pd);
		map.put("result", errInfo);
		return map;
	}	
	
	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		String errInfo = "success";
		PageData pd = new PageData();		
		pd = this.getPageData();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(Tools.notEmpty(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			meetroomService.deleteAll(ArrayDATA_IDS);
			errInfo = "success";
		}else{
			errInfo = "error";
		}
		map.put("result", errInfo);				//返回结果
		return map;
	}
	
}
