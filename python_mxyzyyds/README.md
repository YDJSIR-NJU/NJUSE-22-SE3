

# MXYZyyds-COLLECT报告相似度匹配系统

## 运行说明

### 本地开发

请使用Python3.9。下面的依赖安装流程已经在干净的Python3.9环境上测试通过。

```
pip3 install torch
python3 setup.py install
```

在Windows环境下，如果想启动服务器测试，请输入如下命令。

```bash
flask run -p 5000 --host=0.0.0.0
```

Linux环境下大概也许可以和容器用一样的启动方式，但那在Windows下不可用。

### 容器运行

只要容器一启动，`gunicorn`服务就会起来的。详情看日志。大不了进容器后台手动启动。

```bash
gunicorn -b 0.0.0.0:5000 app:app --timeout 600 --log-level debug
```

docker run --rm -d -p 5000:5000 --name sim text2vec_test /bin/bash -c "gunicorn -b 0.0.0.0:5000 app:app --timeout 600 --log-level debug; tail -f /dev/null"

## 代码说明

见`app.py`。



## CICD

目前本仓库已经可以打包到带`gunicorn`的容器中，监听5000端口。Jenkins流水线和与后端项目的整合仍未完成。



## Reference

### 实现参考

- https://github.com/shibing624/text2vec
- https://github.com/nigestream/cosSim

### 原理参考

- [将句子表示为向量（上）：无监督句子表示学习（sentence embedding）](https://www.cnblogs.com/llhthinker/p/10335164.html)
- [将句子表示为向量（下）：无监督句子表示学习（sentence embedding）](https://www.cnblogs.com/llhthinker/p/10341841.html)
- [A Simple but Tough-to-Beat Baseline for Sentence Embeddings[Sanjeev Arora and Yingyu Liang and Tengyu Ma, 2017]](https://openreview.net/forum?id=SyK00v5xx)
- [四种计算文本相似度的方法对比[Yves Peirsman]](https://zhuanlan.zhihu.com/p/37104535)
- [Improvements to BM25 and Language Models Examined](http://www.cs.otago.ac.nz/homepages/andrew/papers/2014-2.pdf)
- [CoSENT：比Sentence-BERT更有效的句向量方案](https://kexue.fm/archives/8847)
- [谈谈文本匹配和多轮检索](https://zhuanlan.zhihu.com/p/111769969)
- [Sentence-transformers](https://www.sbert.net/examples/applications/computing-embeddings/README.html)
