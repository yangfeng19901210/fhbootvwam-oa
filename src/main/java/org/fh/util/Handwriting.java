package org.fh.util;

import java.net.URLEncoder;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 说明：手写文字识别
 * 作者：FH Admin Q313596790
 * 官网：www.fhadmin.org
 */
public class Handwriting {

    /**
     * @param imgBase64Str
     * @param accessToken
     * @return
     */
    public static String handwriting(String imgBase64Str, String accessToken) {
    	
        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/handwriting";	 //请求url
        try {
            String imgParam = URLEncoder.encode(imgBase64Str, "UTF-8");
            String param = "image=" + imgParam;
            String result = HttpUtil.post(url, accessToken, param);
            JSONObject jsonMsg = JSONObject.fromObject(result);
            int words_result_num = Integer.parseInt(jsonMsg.getString("words_result_num"));	//识别结果数，表示words_result的元素个数
            if(words_result_num > 0) {
            	StringBuffer strb = new StringBuffer();
            	JSONArray jsonArray= jsonMsg.getJSONArray("words_result");
            	for(int i=0;i<jsonArray.size();i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    String words = object.getString("words");
                    strb.append(words);
                    strb.append("\r\n");
                }
            	result = strb.toString();
            }else {
            	result = "null";
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
        
    }

}