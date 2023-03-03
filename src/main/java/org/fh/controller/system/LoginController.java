package org.fh.controller.system;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.fh.controller.base.BaseController;
import org.fh.entity.system.User;
import org.fh.service.system.FHlogService;
import org.fh.service.system.UsersService;
import org.fh.util.Const;
import org.fh.util.Jurisdiction;
import org.fh.util.RedisUtil;
import org.fh.util.Tools;
import org.fh.entity.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONObject;

/**
 * 说明：登录处理类
 * 作者：FH Admin Q313596790
 * 官网：www.fhadmin.org
 */
@Controller
@RequestMapping("/admin")
public class LoginController extends BaseController implements Runnable{
	
	@Autowired
    private UsersService usersService;
	@Autowired
    private FHlogService FHLOG;
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	/**请求登录验证用户接口
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/check", produces="application/json;charset=UTF-8")
	@ResponseBody
	public Object check()throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String errInfo = "success";
		String KEYDATA[] = pd.getString("KEYDATA").replaceAll("qq313596790fh", "").split(",fh,");
		if(null != KEYDATA && KEYDATA.length == 2){
			String USERNAME = KEYDATA[0];							//登录过来的用户名
			String PASSWORD  = KEYDATA[1];							//登录过来的密码
			UsernamePasswordToken token = new UsernamePasswordToken(USERNAME, new SimpleHash("SHA-1", USERNAME, PASSWORD).toString());
			Subject subject = SecurityUtils.getSubject();
			try {
				subject.login(token);								//这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中
	        }catch(UnknownAccountException uae){
	            errInfo = "usererror";
	        }catch(IncorrectCredentialsException ice){
	        	errInfo = "usererror";
	        }catch(LockedAccountException lae){
	        	errInfo = "usererror";
	        }catch(ExcessiveAttemptsException eae){
	        	errInfo = "usererror4";
	        }catch (DisabledAccountException sae){
	        	errInfo = "usererror";
	        }catch(AuthenticationException ae){
	        	errInfo = "usererror";
	        }
	        if(subject.isAuthenticated()){							//验证是否登录成功
	        	removeSession(USERNAME);
	        	Session session = Jurisdiction.getSession();
	        	pd.put("USERNAME", USERNAME);
	            pd = usersService.findByUsername(pd);
	            User user = new User();
	            user.setUSER_ID(pd.getString("USER_ID"));
				user.setUSERNAME(pd.getString("USERNAME"));
				user.setPASSWORD(pd.getString("PASSWORD"));
				user.setNAME(pd.getString("NAME"));
				user.setROLE_ID(pd.getString("ROLE_ID"));
				user.setLAST_LOGIN(pd.getString("LAST_LOGIN"));
				user.setIP(pd.getString("IP"));
				user.setSTATUS(pd.getString("STATUS"));
				session.setAttribute(Const.SESSION_USER, user);		//把当前用户放入session
				FHLOG.save(USERNAME, "成功登录系统");				//记录日志
				Thread t = new Thread(this); 
				t.start();
	        }else{
	            token.clear();
	            errInfo = "usererror";
	        }
	        if(!"success".equals(errInfo))FHLOG.save(USERNAME, "尝试登录系统失败,用户名密码错误,无权限");
		}else{
			errInfo = "error";										//缺少参数
		}
		map.put("result", errInfo);
		return map;
	}
	
	/**系统用户注册接口
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/register", produces="application/json;charset=UTF-8")
	@ResponseBody
	public Object register(@RequestParam("callback") String callback) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String result = "00";
		if(Tools.checkKey("USERNAME", pd.getString("FKEY"))){	//检验请求key值是否合法
			pd.put("USER_ID", this.get32UUID());	//ID 主键
			pd.put("ROLE_ID", "fhadminzhuche");		//角色ID fhadminzhuche 为注册用户
			pd.put("NUMBER", "");					//编号
			pd.put("PHONE", "");					//手机号
			pd.put("BZ", "注册用户");				//备注
			pd.put("LAST_LOGIN", "");				//最后登录时间
			pd.put("IP", "");						//IP
			pd.put("STATUS", "0");					//状态
			pd.put("SKIN", "assets/windows/images/bg_01.jpg");					//用户默认皮肤
			pd.put("EMAIL", "");
			pd.put("ROLE_IDS", "");
			pd.put("PASSWORD", new SimpleHash("SHA-1", pd.getString("USERNAME"), pd.getString("PASSWORD")).toString());	//密码加密
			if(null == usersService.findByUsername(pd)){	//判断用户名是否存在
				usersService.saveUser(pd); 					//执行保存
				FHLOG.save(pd.getString("USERNAME"), "新注册");
			}else{
				result = "01"; 	//用户名已存在
			}
		}else{
			result = "05";		//不合法的注册
		}
		map.put("result", result);
		JSONObject sresult = JSONObject.fromObject(map);;
		return callback+"("+sresult.toString()+")";
	}
	
	/**redis是否链接上
	 * @return
	 */
	public void isRedisConn() {
		Session session = Jurisdiction.getSession();
		RedisUtil redis = new RedisUtil(redisTemplate);
		if(redis.set("fhadmin","fhadmin")) {		//检验redis是否链接上
			session.setAttribute(Const.SESSION_REDIS, true);		//连接上
		}else {
			session.setAttribute(Const.SESSION_REDIS, false);	//没链接上
		}
	}
	
	/**判断是否登录状态
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/islogin")
	@ResponseBody
	public Object islogin()throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		Session session = Jurisdiction.getSession();
		if(null == session.getAttribute(Const.SESSION_USERNAME)) {
			errInfo = "errer";
		}
		map.put("result", errInfo);
		return map;
	}
	
	/**
	 * 清理session
	 */
	public void removeSession(String USERNAME){
		Session session = Jurisdiction.getSession();	//以下清除session缓存
		session.removeAttribute(Const.SESSION_USER);
		session.removeAttribute(USERNAME + Const.SESSION_ROLE_RIGHTS);
		session.removeAttribute(USERNAME + Const.SESSION_ALLMENU);
		session.removeAttribute(USERNAME + Const.SHIROSET);
		session.removeAttribute(Const.SESSION_USERNAME);
		session.removeAttribute(Const.SESSION_U_NAME);
		session.removeAttribute(Const.SESSION_USERROL);
		session.removeAttribute(Const.SESSION_RNUMBERS);
		session.removeAttribute(Const.SESSION_REDIS);
		session.removeAttribute(Const.SKIN);
	}

	@Override
	public void run() {
		isRedisConn();
	}
	
}
