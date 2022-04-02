package easyproxy.server;/**
 * Description : 
 * Created by YangZH on 16-5-29
 *  下午7:13
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import easyproxy.constants.Const;
import easyproxy.util.EncryptUtil;
import easyproxy.util.XmlUtil;

import java.net.InetSocketAddress;
import java.util.*;

/**
 * Description :
 * Created by YangZH on 16-5-29
 * 下午7:13
 */

public class NodeSelector {

    public enum LBMode {
        POLL, WEIGHT, IP_HASH, PERFORMANCE
    }

    static List<String> hostsname = new ArrayList<String>();
    static List<Integer> ports = new ArrayList<Integer>();
    //经过修正的权值
    static List<Integer> weights = new ArrayList<Integer>();
    static List<Map<String, Object>> proxys = new ArrayList<Map<String, Object>>();
    static int weight_sum = 0;

    //加载配置文件，并计算配置信息（主要是计算权重）
    public static void init(String path) {
        XmlUtil xmlUtil = new XmlUtil("/home/lqc/proxy2/src/main/resources/proxy.xml");
    /*{
    "localhost":"172.16.235.1",
    "proxy_pass":[{"port":"8080","host":"172.16.157.1","weight":"4"},{"port":"8081","host":"172.16.157.1","weight":"1"}],
    "lb_strategy":"weight",
    "cache_url":[{"method":"","url":""},{"method":"","url":""}],
    "cache_size":"",
    "cache_ttl":"30",
    "cache_type":"redis",
    "listen":"9524"
    }*/
        JSONArray array = JSON.parseArray(xmlUtil.getProxy_pass());

        //先把权重和IP 端口相关信息记录到内存（各个List）中，记录总权重
        for (int index = 0; index < array.size(); index++) {
            JSONObject object = array.getJSONObject(index);
            Map<String, Object> proxy = new HashMap<String, Object>();
            float weight = object.getFloatValue(Const.WEIGHT);
            int port = object.getIntValue(Const.PORT);
            String host = object.getString(Const.HOST);
            proxy.put(Const.WEIGHT, weight);
            proxy.put(Const.PORT, port);
            proxy.put(Const.HOST, host);
            weight_sum += weight;
            weights.add((int) Math.rint(weight));
            proxys.add(proxy);
        }

        //权重可能会被用户设置的过高，这时手动降低权重值的量级，维持在10以内，并把权重记录更新到权重list中
        for (int index = 0; index < proxys.size(); index++) {
            float percent = weights.get(index) / (float) weight_sum;
            int weight = 1;
            if ((10 * percent) > 0) {
                weight = (int) Math.rint(10 * percent);
            }
            weights.set(index, weight);
        }
        //重新计算总权重
        weight_sum = 0;
        //根据权重值，值是多少就给对应的IP PORT 相应的add多少次，权重越高add次数越多，被获取到的概率越高
        for (int index = 0; index < proxys.size(); index++) {
            Integer weight = weights.get(index);
            //计算新的权值和
            weight_sum += weight;
            for (int i = 0; i < weight; i++) {
                hostsname.add((String) proxys.get(index).get("host"));
                ports.add((Integer) proxys.get(index).get("port"));
            }
        }
    }

    /**
     * 随机过滤出一个地址
     *
     * @return
     */

    public static InetSocketAddress weight() {
        Random rand = new Random();
        int randCode = rand.nextInt(weight_sum);
        return new InetSocketAddress(hostsname.get(randCode), ports.get(randCode));
    }

    public static InetSocketAddress ip_hash(String ip) {
        long hash = EncryptUtil.ip_hash(ip);
        return new InetSocketAddress(hostsname.get((int) hash % hostsname.size()), ports.get((int) hash % ports.size()));
    }
}
