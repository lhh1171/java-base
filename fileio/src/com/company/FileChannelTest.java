package com.company;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/*
* java seek_java中seek（）的用法

一二三是五六十mk~

于 2021-02-12 10:13:10 发布

1371
 收藏 1
文章标签： java seek
版权
展开全部

写段代码你看一下吧，用于从文件指定的位置开始读取，e69da5e887aa62616964757a686964616f31333332616363一般的下载工具都有断点续传功能，比如读取某个文件读取了一半，取消下载了，下次再下载的时候，从断点的位置继续下载，而不是重新下载文件，使用这个方法可以做到public class Test2 {undefined

public static void main(String[] args) throws Exception {undefined

String filepath = "E:/test.exe";

String outFile = "E:/copy.exe";

long pos = firstRead(filepath, outFile);

continueRead(filepath, outFile, pos);

}

/**

* 第一次只读取文件的一半，到目标文件

*/

//public static long firstRead(String filepath, String out) throws Exception {undefined
//
//		RandomAccessFile file = new RandomAccessFile(filepath, "r");
//
//		long fileLen = file.length();
//
//		FileOutputStream outStream = new FileOutputStream(out);
//
//		int sum = 0;  // 用于记录当前读取源文件的长度
//
//		byte[] cache = new byte[1024];
//
//		int len = -1;
//
//		while ((len = file.read(cache)) != -1 && sum < fileLen/2) {undefined
//
//		outStream.write(cache, 0, len);
//
//		sum += len;
//
//		}
//
//		outStream.close();
//
//		file.close();
//
//		return sum;   // 返回当前读取源文件的长度
//
//		}
//
///**
//
// * 从源文件指定位置继续读取文件内容，并输出到目标文件
//
// */
//
//public static void continueRead(String filepath, String out, long pos) throws Exception {undefined
//
//		RandomAccessFile file = new RandomAccessFile(filepath, "r");
//
//		file.seek(pos);
//
//// 追加到目标文件中
//
//		FileOutputStream outStream = new FileOutputStream(out, true);
//
//		byte[] cache = new byte[1024];
//
//		int len = -1;
//
//		while ((len = file.read(cache)) != -1) {undefined
//
//		outStream.write(cache, 0, len);
//
//		}
//
//		outStream.close();
//
//		file.close();
//
//		} git
//		}

public class FileChannelTest {
    public static void main(String[] args) {
        File f = new File("/disk/README.md");
        try {
            // 创建FileInputStream，以该文件输入流创建FileChannel
            FileChannel inChannel = new FileInputStream(f).getChannel();
            // 以文件输出流创建FileBuffer，用以控制输出
            FileChannel outChannel = new FileOutputStream("c.txt").getChannel();
//			outChannel.transferFrom(inChannel,0,f.length());
//			inChannel.transferTo(0,f.length(),outChannel);
            // 将FileChannel里的全部数据映射成ByteBuffer
            MappedByteBuffer buffer = inChannel.map(
                    FileChannel.MapMode.READ_ONLY, 0, f.length());
////			// 使用GBK的字符集来创建解码器
            Charset charset = Charset.forName("utf-8");
////			// 直接将buffer里的数据全部输出
//			outChannel.write(buffer);

////			// 再次调用buffer的clear()方法，复原limit、position的位置
////			buffer.clear();
////			// 创建解码器(CharsetDecoder)对象
            CharsetDecoder decoder = charset.newDecoder();
            CharsetEncoder encoder=charset.newEncoder();
//////			// 使用解码器将ByteBuffer转换成CharBuffer
            CharBuffer charBuffer = decoder.decode(buffer);
            ByteBuffer bf = encoder.encode(charBuffer);
////			// CharBuffer的toString方法可以获取对应的字符串
//			System.out.println(charBuffer);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {

        }
    }
}
