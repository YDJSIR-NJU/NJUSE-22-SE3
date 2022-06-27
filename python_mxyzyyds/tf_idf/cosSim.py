import jieba
import numpy as np
import sys
import logging
import logging.handlers

from sklearn.feature_extraction.text import TfidfTransformer
from sklearn.feature_extraction.text import CountVectorizer


class cosSim:
    def __init__(self, logger=None):
        if logger is None:
            self.logger = logging.getLogger('cos_sim')
            self.logger.setLevel(logging.DEBUG)
            # 设置日志
            formater = logging.Formatter('%(asctime)s %(filename)s[line:%(lineno)d] %(levelname)s %(message)s')
            fh = logging.StreamHandler()  # 输出到终端
            fh.setLevel(level=logging.DEBUG)
            fh.setFormatter(formater)
            self.logger.addHandler(fh)
        else:
            self.logger = logger

    def cos_sim(self, vector_a, vector_b):
        """
        计算两个向量之间的余弦相似度
        :param vector_a: 向量 a
        :param vector_b: 向量 b
        :return: sim
        """
        vector_a = np.mat(vector_a)
        vector_b = np.mat(vector_b)
        num = float(vector_a * vector_b.T)
        denom = np.linalg.norm(vector_a) * np.linalg.norm(vector_b)
        cos = num / denom
        sim = 0.5 + 0.5 * cos
        return sim

    def getVocabulary(self, corpuss):
        vectorizer = CountVectorizer(max_features=500)  # 该类会将文本中的词语转换为词频矩阵，矩阵元素a[i][j] 表示j词在i类文本下的词频
        transformer = TfidfTransformer()  # 该类会统计每个词语的tf-idf权值
        tfidf = transformer.fit_transform(
            vectorizer.fit_transform(corpuss))  # 第一个fit_transform是计算tf-idf，第二个fit_transform是将文本转为词频矩阵
        words = vectorizer.get_feature_names()  # 获取词袋模型中的所有词语
        self.logger.debug("words %s", words)
        return words

    def getVector(self, corpus, vocabulary):
        self.logger.debug("corpus %s", corpus)
        vectorizer = CountVectorizer(vocabulary=vocabulary)  # 该类会将文本中的词语转换为词频矩阵，矩阵元素a[i][j] 表示j词在i类文本下的词频
        transformer = TfidfTransformer()  # 该类会统计每个词语的tf-idf权值
        self.logger.debug("tf矩阵 %s", vectorizer.fit_transform(corpus))
        tfidf = transformer.fit_transform(
            vectorizer.fit_transform(corpus))  # 第一个fit_transform是计算tf-idf，第二个fit_transform是将文本转为词频矩阵
        weight = tfidf.toarray()  # 将tf-idf矩阵抽取出来，元素a[i][j]表示j词在i类文本中的tf-idf权重
        # weight = sorted(weight[0], reverse=True)
        self.logger.debug("weight %s", weight)
        return weight

    def CalcuSim(self, texts=[]):
        """
        @:param list 需要对比的文本
        """
        if len(texts) != 2:
            raise Exception("texts长度必须为2")
        corpuss = [" ".join(jieba.cut(text)) for text in texts]
        vocabulary = self.getVocabulary(corpuss)
        v = self.getVector(corpuss, vocabulary=vocabulary)
        return self.cos_sim(v[0], v[1])

    def CalSim(self, a, b):
        texts = [a, b]
        corpuss = [" ".join(jieba.cut(text)) for text in texts]
        vocabulary = self.getVocabulary(corpuss)
        v = self.getVector(corpuss, vocabulary=vocabulary)
        return self.cos_sim(v[0], v[1])


if __name__ == "__main__":
    cosSim = cosSim()
    cosSim.CalSim("今天天气真好", "今天天气糟透了")
