package org.fh.mapper.dsno1.fhoa;

import org.fh.entity.PageData;

/** 
 * 说明： 组织数据权限Mapper
 * 创建人：FH Q313596790
 * 官网：www.fhadmin.org
 */
public interface DatajurMapper{

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**取出某用户的组织数据权限
	 * @param pd
	 * @throws Exception
	 */
	public PageData getDEPARTMENT_IDS(String USERNAME)throws Exception;
	
}

