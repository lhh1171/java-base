package easyproxy;/**
 * Description : 
 * Created by YangZH on 16-8-15
 *  上午12:17
 */

import easyproxy.server.NodeSelector;
import easyproxy.util.Config;
import easyproxy.util.JedisUtil;
import easyproxy.util.XmlUtil;

import java.io.IOException;
import java.net.InetSocketAddress;

/*需要打开redis*/
public class Main {

    public static void main(String[] args) throws IOException {
//        XmlUtil xmlUtil = new XmlUtil("/home/lqc/proxy2/src/main/resources/proxy.xml");
//        xmlUtil.listAll();
        NodeSelector.init("/home/lqc/proxy2/src/main/resources/proxy.xml");
        InetSocketAddress address = NodeSelector.weight();
        System.out.println("host: "+address.getHostName());
        Config config = new Config("/home/lqc/proxy2/src/main/resources/proxy.xml");
        for (int i = 0; i < 14; i++) {
            InetSocketAddress address1 = Config.roundRobin();
            System.out.println("host: " + address.getHostName());
        }
        System.out.println("memory:"+ JedisUtil.getMemoryUsed());
    }
}
