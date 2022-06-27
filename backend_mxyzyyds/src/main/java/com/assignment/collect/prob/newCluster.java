package com.assignment.collect.prob;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.mining.cluster.ClusterAnalyzer;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class newCluster {
    public static void main(String[] args) throws IOException {
        //聚类器分析器
        ClusterAnalyzer<String> analyzer = new ClusterAnalyzer<String>();
        String tages = null;
        try {
            tages = new String(("论文调研中比较有亮点的地方：\n" +
                    "LOCKSMITH （ACM Transactions）方法引入了形式化语言λB作为C语言并发的抽象并对并发操作，在此基础上使用标签流约束和抽象控制流图约束信息进行锁集分析，增加了结果的准确率。\n" +
                    "Goblint （31th IEEE/ACM）使用了抽象域的数据流分析来近似系统的行为，通过构建并发错误触发的模式规范，分析程序的数据流图和控制流图，来判断所给程序是否满足给定的并发错误模型。\n" +
                    "FSAM（CGO '16）引入了线程间值流分析（THREAD-VF）和稀疏流敏感分析，从而达到降低分析时空复杂度的效果。\n" +
                    "Eraser （ ACM Transactions）通过动态维护共享变量的锁集信息以及监测共享变量的访问和线程操作来检测数据竞争。\n" +
                    "Djit+ 算法（SOSP'05）为每个线程和共享变量以及同步变量之间维护一组时钟矢量，当发生访存操作时，会根据时钟矢量来比较几个线程的happens-before关系。\n" +
                    "Racertrack （SOSP'05）为每一个共享变量x维护一个并发访问集Sx，Sx使用Happens-Before关系来检测两个访问操作是否并发，并且通过修改虚拟机来监测程序的整个执行过程。\n" +
                    "SUPA（FSE'16）通过在一个混合的多个阶段分析框架中不断细化前一阶段计算的不精确的value-flow信息来进行强更新分析，通过上一阶段分析产生的结果和当前阶段给的预算来确定当前阶段分析的精度。\n" +
                    "Tord-Solver(PLDI 2021)将程序进行布尔抽象，并将程序中的变量和语句的关系抽象成一组变量的偏序关系并构建事件图，引入了事件图的双向搜索机制和基于增量更新的传播算法。\n" +
                    "AIRaceTest(计算机科学)使用happens-before关系和lockset算法的分析结果训练多线程数据竞争随机森林检测模型，并在PIN上实现了该工具。\n" +
                    "AtexRace(ESEC/FSE 2017)基于采样策略来检测数据竞争，只保存最近经常观察到的函数对，根据监控到的函数对获取内存访问信息。\n" +
                    "Hardekopf等人（CGO 2011）引入了等价分析合并值流图中的等价边来减少def-use链的爆炸性增长。\n" +
                    "Vineet等人（CAV'07）将分治策略与功能总结相结合，并且明显比现有的基于 BDD 的技术更有效。").getBytes(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        List<String> lists = new ArrayList<>();
        String[] tag = tages.split("\n");
//        System.out.println(tag);

        for (int i = 0; i < tag.length; i++) {
            analyzer.addDocument(String.valueOf(i), Arrays.asList(tag[i].split(" ")));
        }
        List<Set<String>> re = analyzer.repeatedBisection(1.0);
        for (Set<String> set : re) {
            System.out.println(set);
        }
//        List<Set<String>> list = analyzer.kmeans(3);
//        System.out.println(list.size());
//        for (int j=0;j<3;j++){
//                System.out.println(String.valueOf(list.get(j)));
//        }
        List<Term> title = StandardTokenizer.segment(tages);
        for (Term t : title) {
            lists.add(t.word);
        }
        Map<String, Float> result = new newWordCloud().getTermAndRank(tages, 10);
        for (String string : result.keySet()) {
            System.out.println(string + ":" + result.get(string));
        }
        System.out.println(HanLP.extractKeyword(tages, 12));
//        System.out.println(lists);
//
//        for (Term term:title) {
//            System.out.println(term.word);
//            System.out.println(term.nature);
//            System.out.println(term.getFrequency());
//        }
//            System.out.println(analyzer.kmeans(3));

//        System.out.println(HanLP.segment("你好，欢迎使用HanLP汉语处理包！"));
        //读入数据

    }
}
