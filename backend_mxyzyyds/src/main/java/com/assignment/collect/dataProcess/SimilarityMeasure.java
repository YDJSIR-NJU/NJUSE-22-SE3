package com.assignment.collect.dataProcess;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class SimilarityMeasure {
    //to compute the relevance between task description and worker's domain knowledge
    public static Double cosinSimilarity(HashMap<String, Integer> vector1, HashMap<String, Integer> vector2) {
        if (vector1 == null || vector2 == null || vector1.size() == 0 || vector2.size() == 0) {
            return 0.0;
        }
        HashSet<String> totalTermList = new HashSet<String>();

        Set<String> keySet1 = vector1.keySet();
        Set<String> keySet2 = vector2.keySet();
        totalTermList.addAll(keySet1);
        totalTermList.addAll(keySet2);

        int v1sum = 0, v2sum = 0, multiply = 0;
        for (String term : totalTermList) {
            int v1 = 0, v2 = 0;
            if (vector1.containsKey(term))
                v1 = vector1.get(term);
            if (vector2.containsKey(term))
                v2 = vector2.get(term);

            v1sum += v1 * v1;
            v2sum += v2 * v2;
            multiply += v1 * v2;
        }

        double sim = (1.0 * multiply) / (Math.sqrt(1.0 * v1sum) * Math.sqrt(1.0 * v2sum));
        return sim;
    }
}
