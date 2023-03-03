package org.fh.controller.form;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import org.fh.controller.base.BaseController;
import org.fh.entity.Page;
import org.fh.util.DateUtil;
import org.fh.util.Jurisdiction;
import org.fh.util.ObjectExcelView;
import org.fh.util.Tools;
import org.fh.entity.PageData;
import org.fh.service.form.FormDataService;
import org.fh.service.form.FormTemplatesService;
import org.fh.service.form.MyFormService;

/** 
 * 说明：我的表单
 * 作者：FH Admin QQ313596790
 * 官网：www.fhadmin.cn
 */
@Controller
@RequestMapping("/myform")
public class MyFormController extends BaseController {
	
	@Autowired
	private MyFormService myformService;
	@Autowired
	private FormDataService formdataService;
	@Autowired
	private FormTemplatesService formtemplatesService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/add")
	@RequiresPermissions("myform:add")
	@ResponseBody
	public Object add() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		String errInfo = "success";
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("MYFORM_ID", this.get32UUID());	//主键
		pd.put("CTIME", DateUtil.date2Str(new Date()));	//创建时间
		pd.put("USERNAME", Jurisdiction.getUsername());	//创建者
		pd.put("HTMLCONTENT", formtemplatesService.findById(pd).getString("HTMLCONTENT"));
		myformService.save(pd);
		map.put("result", errInfo);
		return map;
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	@RequiresPermissions("myform:del")
	@ResponseBody
	public Object delete() throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		pd = this.getPageData();
		myformService.delete(pd);
		map.put("result", errInfo);				//返回结果
		return map;
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	@RequiresPermissions("myform:edit")
	@ResponseBody
	public Object edit() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		String errInfo = "success";
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("HTMLCONTENT", formtemplatesService.findById(pd).getString("HTMLCONTENT"));
		myformService.edit(pd);		//修改我的表单
		pd.put("fmsg", "bjpl");
		formdataService.edit(pd);	//批量修改表单数据规则
		map.put("result", errInfo);
		return map;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	@RequiresPermissions("myform:list")
	@ResponseBody
	public Object list(Page page) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		String errInfo = "success";
		PageData pd = new PageData();
		pd = this.getPageData();
		String KEYWORDS = pd.getString("KEYWORDS");						//关键词检索条件
		if(Tools.notEmpty(KEYWORDS))pd.put("KEYWORDS", KEYWORDS.trim());
		pd.put("USERNAME", Jurisdiction.getUsername());		//当前用户
		page.setPd(pd);
		List<PageData>	varList = myformService.list(page);	//列出MyForm列表
		map.put("varList", varList);
		map.put("page", page);
		map.put("result", errInfo);
		return map;
	}
	
	 /**去修改页面获取数据
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goEdit")
	@RequiresPermissions("myform:edit")
	@ResponseBody
	public Object goEdit() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		String errInfo = "success";
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = myformService.findById(pd);	//根据ID读取
		map.put("pd", pd);
		map.put("result", errInfo);
		return map;
	}
	
	 /**去查看页面获取数据
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goView")
	@RequiresPermissions("myform:cha")
	@ResponseBody
	public Object goView() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		String errInfo = "success";
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = myformService.findById(pd);	//根据ID读取
		map.put("pd", pd);
		map.put("result", errInfo);
		return map;
	}		
	
	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@RequiresPermissions("myform:del")
	@ResponseBody
	public Object deleteAll() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		String errInfo = "success";
		PageData pd = new PageData();		
		pd = this.getPageData();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(Tools.notEmpty(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			myformService.deleteAll(ArrayDATA_IDS);
			errInfo = "success";
		}else{
			errInfo = "error";
		}
		map.put("result", errInfo);				//返回结果
		return map;
	}
	
	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	@RequiresPermissions("toExcel")
	public ModelAndView exportExcel() throws Exception{
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("表单名称");	//1
		titles.add("表单内容");	//2
		titles.add("创建时间");	//3
		titles.add("表单类型");	//4
		titles.add("创建者");	//5
		titles.add("是否私有");	//6
		titles.add("上传图片");	//7
		titles.add("上传附件");	//8
		titles.add("数据私有");	//9
		dataMap.put("titles", titles);
		List<PageData> varOList = myformService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("TITLE"));	    //1
			vpd.put("var2", varOList.get(i).getString("HTMLCONTENT"));	    //2
			vpd.put("var3", varOList.get(i).getString("CTIME"));	    //3
			vpd.put("var4", varOList.get(i).getString("TYPE"));	    //4
			vpd.put("var5", varOList.get(i).getString("USERNAME"));	    //5
			vpd.put("var6", varOList.get(i).getString("ISPRIVATE"));	    //6
			vpd.put("var7", varOList.get(i).getString("ISIMAG"));	    //7
			vpd.put("var8", varOList.get(i).getString("ISFILE"));	    //8
			vpd.put("var9", varOList.get(i).getString("DATAPRIVATE"));	    //9
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	
}
