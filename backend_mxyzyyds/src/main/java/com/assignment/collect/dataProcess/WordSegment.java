package com.assignment.collect.dataProcess;

import com.huaban.analysis.jieba.JiebaSegmenter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WordSegment {
    private static JiebaSegmenter segmenter;
    private static Set<String> stopWordList;

    //静态初始化，读取停用词表
    static {
        try {
            segmenter = new JiebaSegmenter();

            stopWordList = new HashSet<String>();
            BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/assignment/collect/model/stopWords.txt"));
            String str = "";
            while ((str = br.readLine()) != null) {
                stopWordList.add(str.trim());
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> segmentWord(String str) {
        if (str == null || str.length() == 0) {
            return new ArrayList<>();
        }

        List<String> words = segmenter.sentenceProcess(str);
//        System.out.println(words);
        return removeStopWords(words);
    }

    public static List<String> removeStopWords(List<String> words) {
        ArrayList<String> remainWords = new ArrayList<>();
        for (int i = 0; i < words.size(); i++) {
            if (words.get(i) == null || words.get(i).trim().equals(""))
                continue;
            else if (stopWordList.contains(words.get(i)))  // 去除停用词
                continue;
            else
                remainWords.add(words.get(i).trim());
        }
        return remainWords;
    }

//    public static void main(String[] args) {
//        System.out.println(segmentWord("北京京天威科技发展有限公司大庆车务段的装车数量"));
//    }
}
