import time

from flask import Flask, request
from text2vec import text2vec as t2v
from tf_idf import cosSim as cs
from task_recommend import core as tr

app = Flask(__name__)


@app.post("/calc/cossim")
def cmpv1():
    ret = {}
    similarity = []
    defect_report_id = []
    cosSim = cs.cosSim()

    # 获得restful api的请求参数，将json格式转换为python的dict
    input_body = request.get_json()
    input_body: dict

    str1 = input_body["defect_report"]
    str2 = input_body["defect_reports"]

    for item in str2:
        similarity.append(cosSim.CalSim(str1["description"], item["description"]))
        defect_report_id.append(item["id"])

    ret["src_id"] = str1["id"]
    ret["score"] = similarity
    ret["dest_id"] = defect_report_id

    # 返回一个dict，自动将其转换为json格式
    return ret


@app.post("/calc/text2vec")
def cmpv2():
    ret = {}
    similarity = []
    defect_report_id = []

    # 获得restful api的请求参数，将json格式转换为python的dict
    input_body = request.get_json()
    input_body: dict

    str1 = input_body["defect_report"]
    str2 = input_body["defect_reports"]

    for item in str2:
        similarity.append(t2v.sbert_sim_score(str1["description"], item["description"]))
        defect_report_id.append(item["id"])

    ret["src_id"] = str1["id"]
    ret["score"] = similarity
    ret["dest_id"] = defect_report_id

    # 返回一个dict，自动将其转换为json格式
    return ret

@app.post("/calc/taskRecommend")
def recommend():
    ret = {}

    # 获得restful api的请求参数，将json格式转换为python的dict
    input_body = request.get_json()
    input_body: dict

    print(input_body)

    K = input_body["k"]
    N = input_body["n"]
    userid = input_body["user_id"]
    ret["user_id"] = userid
    ret["time"] = time.time()
    ret["recommend_task"] = tr.get_recommend(K, N, userid)
    return ret

# 仅用于测试是否在线
@app.post("/echo")
def demo_api():
    # 获得restful api的请求参数，将json格式转换为python的dict
    input_body = request.get_json()
    input_body: dict

    # 返回一个dict，自动将其转换为json格式
    return input_body