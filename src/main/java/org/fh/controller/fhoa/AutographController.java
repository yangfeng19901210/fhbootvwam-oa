package org.fh.controller.fhoa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import org.fh.controller.base.BaseController;
import org.fh.entity.Page;
import org.fh.util.Const;
import org.fh.util.DateUtil;
import org.fh.util.DelFileUtil;
import org.fh.util.ImageAnd64Binary;
import org.fh.util.Jurisdiction;
import org.fh.util.PathUtil;
import org.fh.util.Tools;
import org.fh.entity.PageData;
import org.fh.service.fhoa.AutographService;

/** 
 * 说明：电子签名管理
 * 作者：FH Admin QQ313596790
 * 官网：www.fhadmin.org
 */
@Controller
@RequestMapping("/autograph")
public class AutographController extends BaseController {
	
	@Autowired
	private AutographService autographService;
	
	/**新增
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/add")
	@RequiresPermissions("autograph:add")
	@ResponseBody
	public Object add() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		String errInfo = "success";
		PageData pd = new PageData();
		pd = this.getPageData();
		String base64Img = pd.getString("base64Img").replace("data:image/jpeg;base64,", "").replace("data:image/png;base64,", "");
		String fileName = DateUtil.getSdfTimes();
		String filePath = PathUtil.getProjectpath() + Const.FILEPATHIMG + fileName + ".png";	//文件上传路径
		ImageAnd64Binary.generateImage(base64Img, filePath);
		pd.put("AUTOGRAPH_ID", this.get32UUID());		//主键
		pd.put("USERNAME", Jurisdiction.getUsername());	//用户名
		pd.put("FILEPATH", Const.FILEPATHIMG + fileName + ".png");	//图片路径
		autographService.save(pd);
		map.put("result", errInfo);
		return map;
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	@RequiresPermissions("autograph:del")
	@ResponseBody
	public Object delete() throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = autographService.findById(pd);	//根据ID读取
		String FILEPATH = pd.getString("FILEPATH");
		if(Tools.notEmpty(FILEPATH)){
			DelFileUtil.delFolder(PathUtil.getProjectpath()+ FILEPATH.trim()); //删除文件
		}
		autographService.delete(pd);
		map.put("result", errInfo);				//返回结果
		return map;
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	@RequiresPermissions("autograph:edit")
	@ResponseBody
	public Object edit() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		String errInfo = "success";
		PageData pd = new PageData();
		pd = this.getPageData();
		autographService.edit(pd);
		map.put("result", errInfo);
		return map;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	@RequiresPermissions("autograph:list")
	@ResponseBody
	public Object list(Page page) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		String errInfo = "success";
		PageData pd = new PageData();
		pd = this.getPageData();
		String KEYWORDS = pd.getString("KEYWORDS");				//关键词检索条件
		if(Tools.notEmpty(KEYWORDS))pd.put("KEYWORDS", KEYWORDS.trim());
		pd.put("USERNAME", Jurisdiction.getUsername());			//用户名
		page.setPd(pd);
		List<PageData>	varList = autographService.list(page);	//列出Autograph列表
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
	@RequiresPermissions("autograph:edit")
	@ResponseBody
	public Object goEdit() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		String errInfo = "success";
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = autographService.findById(pd);	//根据ID读取
		map.put("pd", pd);
		map.put("result", errInfo);
		return map;
	}	
	
}
