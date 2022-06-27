package com.assignment.collect.prob;

import com.hankcs.hanlp.algorithm.MaxHeap;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.summary.KeywordExtractor;
import com.hankcs.hanlp.summary.TextRankKeyword;

import java.util.*;

public class newWordCloud extends KeywordExtractor {
    static final float d = 0.85F;
    static final float min_diff = 0.001F;
    public static int max_iter = 200;

    public newWordCloud(Segment defaultSegment) {
        super(defaultSegment);
    }

    public newWordCloud() {
    }

    public static List<String> getKeywordList(String document, int size) {
        TextRankKeyword textRankKeyword = new TextRankKeyword();
        return textRankKeyword.getKeywords(document, size);
    }

    public static float sigMoid(float value) {
        return (float) (1.0D / (1.0D + Math.exp((double) (-value))));
    }

    /**
     * @deprecated
     */
    public List<String> getKeyword(String content) {
        return this.getKeywords(content);
    }

    public Map<String, Float> getTermAndRank(String content) {
        assert content != null;

        List<Term> termList = this.defaultSegment.seg(content);
        return this.getTermAndRank(termList);
    }

    public Map<String, Float> getTermAndRank(String content, int size) {
        Map<String, Float> map = this.getTermAndRank(content);
        Map<String, Float> result = this.top(size, map);
        return result;
    }

    private Map<String, Float> top(int size, Map<String, Float> map) {
        Map<String, Float> result = new LinkedHashMap();
        Iterator var4 = (new MaxHeap(size, new Comparator<Map.Entry<String, Float>>() {
            public int compare(Map.Entry<String, Float> o1, Map.Entry<String, Float> o2) {
                return ((Float) o1.getValue()).compareTo((Float) o2.getValue());
            }
        })).addAll(map.entrySet()).toList().iterator();

        while (var4.hasNext()) {
            Map.Entry<String, Float> entry = (Map.Entry) var4.next();
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    public Map<String, Float> getTermAndRank(List<Term> termList) {
        List<String> wordList = new ArrayList(termList.size());
        Iterator var3 = termList.iterator();

        while (var3.hasNext()) {
            Term t = (Term) var3.next();
            if (this.shouldInclude(t)) {
                wordList.add(t.word);
            }
        }

        Map<String, Set<String>> words = new TreeMap();
        Queue<String> que = new LinkedList();
        Iterator var5 = wordList.iterator();

        while (var5.hasNext()) {
            String w = (String) var5.next();
            if (!words.containsKey(w)) {
                words.put(w, new TreeSet());
            }

            if (que.size() >= 5) {
                que.poll();
            }

            Iterator var7 = que.iterator();

            while (var7.hasNext()) {
                String qWord = (String) var7.next();
                if (!w.equals(qWord)) {
                    ((Set) words.get(w)).add(qWord);
                    ((Set) words.get(qWord)).add(w);
                }
            }

            que.offer(w);
        }

        Map<String, Float> score = new HashMap();
        Iterator var19 = words.entrySet().iterator();

        while (var19.hasNext()) {
            Map.Entry<String, Set<String>> entry = (Map.Entry) var19.next();
            score.put(entry.getKey(), sigMoid((float) ((Set) entry.getValue()).size()));
        }

        for (int i = 0; i < max_iter; ++i) {
            Map<String, Float> m = new HashMap();
            float max_diff = 0.0F;

            String key;
            for (Iterator var9 = words.entrySet().iterator(); var9.hasNext(); max_diff = Math.max(max_diff, Math.abs((Float) m.get(key) - (score.get(key) == null ? 0.0F : (Float) score.get(key))))) {
                Map.Entry<String, Set<String>> entry = (Map.Entry) var9.next();
                key = (String) entry.getKey();
                Set<String> value = (Set) entry.getValue();
                m.put(key, 0.14999998F);
                Iterator var13 = value.iterator();

                while (var13.hasNext()) {
                    String element = (String) var13.next();
                    int size = ((Set) words.get(element)).size();
                    if (!key.equals(element) && size != 0) {
                        m.put(key, (Float) m.get(key) + 0.85F / (float) size * (score.get(element) == null ? 0.0F : (Float) score.get(element)));
                    }
                }
            }

            score = m;
            if (max_diff <= 0.001F) {
                break;
            }
        }

        return score;
    }

    public List<String> getKeywords(List<Term> termList, int size) {
        Set<Map.Entry<String, Float>> entrySet = this.top(size, this.getTermAndRank(termList)).entrySet();
        List<String> result = new ArrayList(entrySet.size());
        Iterator var5 = entrySet.iterator();

        while (var5.hasNext()) {
            Map.Entry<String, Float> entry = (Map.Entry) var5.next();
            result.add(entry.getKey());
        }

        return result;
    }
}