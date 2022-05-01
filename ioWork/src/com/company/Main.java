package com.company;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class Main {

    public static void writeFileOne(String fileName, String content){
        File file = new File(fileName);
        OutputStream out = null;
        try {
//            System.out.println("以字节为单位写入文件内容，一次写一个字节：");
//            // 底层是逐个字节写入
            out = new FileOutputStream(file);
            out.write(content.getBytes());
//            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     */
    public static void readFileByLines(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                SumTask task = new SumTask(tempString.toCharArray(), 0, tempString.toCharArray().length);
                Map<Character,Integer> result = ForkJoinPool.commonPool().invoke(task);
                int sum=0;
                for (Map.Entry<Character,Integer> entry : result.entrySet()) {
                    sum+=entry.getValue();
                }
                System.out.println(result);
                System.out.println("sum : "+sum);
//                // 显示行号
//                System.out.println("line " + line + ": " + tempString);
//                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ignored) {
                }
            }
        }
    }
    public static void main(String[] args) {
//        for (int i = 0; i < 10; i++) {
//            int finalI = i;
//            new Thread(){
//                @Override
//                public void run() {
//                    Random random=new Random();
//                    StringBuilder stringBuilder=new StringBuilder();
//                    for (int j = 0; j <1000; j++) {
//                        stringBuilder.append((char) (random.nextInt(26)+65));
//                    }
//                    writeFileOne("src/com/company/jj/t" +finalI+".txt",stringBuilder.toString());
//                }
//            }.start();
//        }

        readFileByLines("src/com/company/jj/t1.txt");
    }
}
class SumTask extends RecursiveTask<Map<Character,Integer>> {
    static final int limit = 250;
    char[] array;
    int start;
    int end;

    SumTask(char[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Map<Character,Integer> compute() {
        if (end - start <= limit) {
            Map<Character,Integer> mm=new HashMap<Character,Integer>();
            //TODO:计算
            for (int i = start; i < end; i++) {
                if (mm.containsKey(this.array[i])){
                    Integer integer = mm.get(this.array[i]);
                    mm.put(this.array[i],integer+1);
                }else {
                    mm.put(this.array[i],1);
                }
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
            return mm;
        }
        //拆分
        int middle = (end + start) / 2;
        System.out.printf("split %d---%d  ===> %d---%d,%d---%d%n", start, end, start, middle, middle, end);
        SumTask sumTask1 = new SumTask(this.array, start, middle);
        SumTask sumTask2 = new SumTask(this.array, middle, end);
        invokeAll(sumTask1, sumTask2);
        Map<Character,Integer> r1 = sumTask1.join();
        Map<Character,Integer> r2 = sumTask2.join();
        return Merge(r1,r2);
    }
    public Map<Character,Integer> Merge(Map<Character,Integer> m1,Map<Character,Integer> m2){
        Map<Character,Integer> mm=new HashMap<Character,Integer>();
        for (Map.Entry<Character,Integer> entry : m1.entrySet()) {
            if (m2.containsKey(entry.getKey())){
                Integer integer = m2.get(entry.getKey());
                mm.put(entry.getKey(),integer+entry.getValue());
            }else{
                mm.put(entry.getKey(),entry.getValue());
            }
        }
        for (Map.Entry<Character,Integer> entry : m2.entrySet()) {
            if (!mm.containsKey(entry.getKey())){
                mm.put(entry.getKey(),entry.getValue());
            }
        }
        return mm;
    }
}
