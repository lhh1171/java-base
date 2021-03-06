package easyproxy.util;/**
 * Description : 
 * Created by YangZH on 16-5-29
 *  下午5:50
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description :
 * Created by YangZH on 16-5-29
 * 下午5:50
 */

public class JSONUtil {

    public static List<String> strToArray(String json) {
        return JSONArray.parseArray(json, String.class);
    }

    public static Map<String, Object> strToMap(String json) {
        JSONObject jsonObject = JSON.parseObject(json);
        Map<String, Object> params = new HashMap<String, Object>();
        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            params.put(entry.getKey(), entry.getValue());
        }
        return params;
    }

    public static boolean isJson(String string) {
        try {
            JSON.parseObject(string);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static JSONObject listToJson(List list) {
        return JSON.parseObject(JSON.toJSONString(list, true));
    }

    public static String pojoToJson(Object object) {
        return JSON.toJSONString(object, true);
    }

    public static Map<String, Object> requestParam(String str) {
        System.out.println("原生请求字符串-----------： \n");
        System.out.println(str);
        System.out.println("原生请求字符串-----------： \n");
        String[] param = str.split("&");
        Map<String, Object> params = new HashMap<String, Object>();
        for (String p : param) {
            String[] pa = p.split("=");
            params.put(pa[0], pa[1]);
        }
        return params;
    }

    public static JSONObject str2Json(String str) {
        return JSON.parseObject(str);
    }

    /**
     * 从一段json中按key获取array
     *
     * @param key
     * @param json
     * @return
     */
    public static JSONArray getArrayFromStr(String key, String json) {
        JSONObject object = JSON.parseObject(json);
        return (JSONArray) object.get(key);
    }

    public static JSONArray getArrayFromJSON(String key, JSONObject json) {
        return (JSONArray) json.get(key);
    }
}
