package compress;/**
 * Description : 
 * Created by YangZH on 16-8-16
 *  上午1:29
 */

import easyproxy.compress.Compressor;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

/**
 * Description :
 * Created by YangZH on 16-8-16
 * 上午1:29
 */

public class CompressTest {

//    @Test
    public static void main(String n[]) throws Exception {
        Compressor compressor = new Compressor();
        FileInputStream fis = new FileInputStream("/home/lqc/文档/后半部分（13-24）/13.png");
        FileInputStream fis1 = new FileInputStream("/home/lqc/文档/后半部分（13-24）/14.png");
        FileInputStream fis2 = new FileInputStream("/home/lqc/文档/后半部分（13-24）/16.png");
        FileInputStream fis3 = new FileInputStream("/home/lqc/音乐/2019说好不哭  送蓝光MV/周杰伦-说好不哭 (with 五月天阿信).flac");
        BufferedInputStream bis = new BufferedInputStream(fis3);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(baos);
//        fis.read()
        byte[] bytes = new byte[10240];
        int len=0;
        while ((len=bis.read(bytes))!=-1){
            bos.write(bytes,0,len);
        }
//        baos.write(bytes,0,fis.available());
        System.out.println("origin size:"+baos.toByteArray().length/1024+"Kb");
        byte[] result = compressor.gzip(baos.toByteArray());
        System.out.println("compressed size:"+result.length/1024+"Kb");
        System.out.println("sub: "+(baos.toByteArray().length-result.length)/1024+"Kb");
        byte[] result2 = compressor.unGzip(result);
        System.out.println("uncompressed size:"+result2.length/1024+"Kb");
//        System.out.println(new String(result2));
    }
}
