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
import org.fh.util.GraphicsSeal;
import org.fh.util.Jurisdiction;
import org.fh.util.PathUtil;
import org.fh.util.Tools;
import org.fh.entity.PageData;
import org.fh.entity.system.Role;
import org.fh.service.fhoa.SealService;
import org.fh.service.system.RoleService;

/** 
 * 说明：电子印章管理
 * 作者：FH Admin QQ313596790
 * 官网：www.fhadmin.org
 */
@Controller
@RequestMapping("/seal")
public class SealController extends BaseController {
	
	@Autowired
	private SealService sealService;
	@Autowired
    private RoleService roleService;
	
	/**新增
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/add")
	@RequiresPermissions("seal:add")
	@ResponseBody
	public Object add() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		String errInfo = "success";
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("SEAL_ID", this.get32UUID());	//主键
		String TITLE = pd.getString("TITLE");			//印章公司名称
		String centerName = pd.getString("centerName");	//印章用途
		String fnumber = pd.getString("fnumber");		//编码
		String fileName = DateUtil.getSdfTimes();
		String filePath = PathUtil.getProjectpath() + Const.FILEPATHIMG + fileName + ".png";	//文件上传路径
		GraphicsSeal.creatSeal(500, 500, TITLE, centerName, fnumber, filePath);			//生成印章
		pd.put("TITLE", TITLE);			//印章公司名称
		pd.put("FILEPATH", Const.FILEPATHIMG + fileName + ".png");	//图片路径
		sealService.save(pd);
		map.put("result", errInfo);
		return map;
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	@RequiresPermissions("seal:del")
	@ResponseBody
	public Object delete() throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = sealService.findById(pd);	//根据ID读取
		String FILEPATH = pd.getString("FILEPATH");
		if(Tools.notEmpty(FILEPATH)){
			DelFileUtil.delFolder(PathUtil.getProjectpath()+ FILEPATH.trim()); //删除文件
		}
		sealService.delete(pd);
		map.put("result", errInfo);				//返回结果
		return map;
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	@RequiresPermissions("seal:edit")
	@ResponseBody
	public Object edit() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		String errInfo = "success";
		PageData pd = new PageData();
		pd = this.getPageData();
		sealService.edit(pd);
		map.put("result", errInfo);
		return map;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	@RequiresPermissions("seal:list")
	@ResponseBody
	public Object list(Page page) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		String errInfo = "success";
		PageData pd = new PageData();
		pd = this.getPageData();
		String KEYWORDS = pd.getString("KEYWORDS");						//关键词检索条件
		if(Tools.notEmpty(KEYWORDS))pd.put("KEYWORDS", KEYWORDS.trim());
		pd.put("ROLE_ID", Jurisdiction.getRoleid());					//当前登录用户的主职角色ID
		pd.put("USERNAME", Jurisdiction.getUsername());					//用户名
		page.setPd(pd);
		List<PageData>	varList = sealService.list(page);	//列出Seal列表
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
	@ResponseBody
	public Object goEdit() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		String errInfo = "success";
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("ROLE_ID", "1");
		List<Role> roleList = roleService.listAllRolesByPId(pd);	//列出所有系统用户角色
		pd = sealService.findById(pd);	//根据ID读取
		String AUTHORIZED = pd.getString("AUTHORIZED");				//角色ID
		if(Tools.notEmpty(AUTHORIZED)){
			String arryROLE_ID[] = AUTHORIZED.split(",");
			for(int i=0;i<roleList.size();i++){
				Role role = roleList.get(i);
				String roleId = role.getROLE_ID();
				for(int n=0;n<arryROLE_ID.length;n++){
					if(arryROLE_ID[n].equals(roleId)){
						role.setRIGHTS("1");	//此时的目的是为了修改信息上，角色能看到哪些被选中
						break;
					}
				}
			}
		}
		map.put("pd", pd);
		map.put("roleList", roleList);
		map.put("result", errInfo);
		return map;
	}	
	
}
