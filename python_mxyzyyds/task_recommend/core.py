import datetime
import math

import pymysql


def get_data(sql):
    db = pymysql.connect(
        host="xy.ydjsir.com.cn",
        port=3306,
        user='root',
        passwd='HSsash81sahu8',
        db='collect',
        charset='utf8'
    )
    cursor = db.cursor()
    try:
        cursor.execute(sql)
        result = cursor.fetchall()
        db.commit()
        return result
    except:
        db.rollback()


def get_task_rankings_data():
    print()


# 把用户对任务的打分变成已完成
def format_data(rankings_data):
    train_data = {}
    for row in rankings_data:
        user = row[0]
        task = row[1]
        rating = row[2]

        if user not in train_data.keys():
            train_data[user] = {}
        train_data[user][task] = rating
    return train_data


def recommend(train_data, user_id, K, N):
    recommendations = {}
    near_users = get_near_users(train_data, user_id, K)

    # 获取该用户做过的任务及其评分，用于归一化
    user_ratings = train_data[user_id]

    # 计算所有其余用户的相似度
    total_distance = 0

    if len(near_users) < K:
        K = len(near_users)
    for i in range(K):
        total_distance += near_users[i][1]
    if total_distance == 0:
        total_distance = 1

    for i in range(K):
        # 相似度归一化
        weight = near_users[i][1] / total_distance
        # 相似用户的id
        neighbour = near_users[i][0]
        neighbour_ratings = train_data[neighbour]
        for task in neighbour_ratings.keys():
            if task not in user_ratings.keys():  # 如果该用户没有做过这个任务，这事实上就要求用户必须给他做过的任务打分
                if task not in recommendations:
                    recommendations[task] = neighbour_ratings[task] * weight  # 设置推荐度
                else:
                    recommendations[task] = (recommendations[task] + neighbour_ratings[task] * weight)
    recommendations = list(recommendations.items())
    recommendations.sort(key=lambda x: x[1], reverse=True)  # 按照推荐度由高到低排序
    if len(recommendations) < N:
        N = len(recommendations)
    recommendations_list = []
    for item in recommendations:
        recommendations_list += [item[0]]
    return recommendations_list[:N]  # 推荐排名靠前的N部


def get_near_users(train_data, user_id, K):
    distances = {}
    # 计算其它所有用户与user_id的皮尔逊相关系数
    for user, item in train_data.items():
        if user != user_id:
            distance = cal_pearson(train_data[user], train_data[user_id])
            distances[user] = distance

    distances = list(distances.items())
    distances.sort(key=lambda x: x[1], reverse=True)
    return distances[:K]


def cal_pearson(usr1, usr2):
    sum_xy = 0
    sum_x = 0
    sum_y = 0
    sum_xpow2 = 0
    sum_ypow2 = 0
    n = 0
    for task, rating in usr1.items():
        if task in usr2.keys():  # 两个人都做过这个任务
            n += 1
            x = rating
            y = usr2[task]
            sum_xy += x * y
            sum_x += x
            sum_y += y
            sum_xpow2 += pow(x, 2)
            sum_ypow2 += pow(y, 2)

    if n == 0:  # 未做过任何相同的任务
        return 0

    den = math.sqrt(sum_xpow2 - pow(sum_x, 2) / n) * math.sqrt(sum_ypow2 - pow(sum_y, 2) / n)
    if den == 0:
        return 0
    else:
        return (sum_xy - (sum_x * sum_y) / n) / den


def get_recommend(K, N, user_id):
    sql = "select id from task where task.finish_time > '%s'  and task.current_num < task.total_num" % (
        datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S"))
    task_data = get_data(sql)  # 获取未达到截止时间且人数未满的任务
    taskId_list = []
    for taskId in task_data:
        taskId_list += taskId  # 得到所有还在招募的任务的id

    sql = "select task_id from test_record where user_id = {}".format(user_id)
    task_hasDone = get_data(sql)  # 查询用户已经做过的任务
    if task_hasDone is not None:
        for hasDoneId in task_hasDone:
            if hasDoneId[0] in taskId_list:
                taskId_list.remove(hasDoneId[0])  # 得到所有正在招募且该用户未参与的任务

    # sql = "select * from task_rankings where task_id in ({})".format(
    #     ','.join(["'%s'" % task_id for task_id in task_data]))
    sql = "select * from task_rankings"  # 先全部拿出来，因为即便是不再招人的任务或者该用户已经做过的任务也具有计算价值
    rankings_data = get_data(sql)
    # rankings_data = ((1, 1, 4), (1, 5, 6), (1, 7, 6), (1, 6, 4), (2, 1, 6), (2, 5, 7), (2, 8, 6), (3, 5, 5), (3, 7, 8))
    if rankings_data is None:
        return taskId_list[0:N]
    train_data = format_data(rankings_data)
    if user_id not in train_data.keys():
        return taskId_list[0:N]
    recommendations = recommend(train_data, user_id, K, N)
    return recommendations


if __name__ == "__main__":
    res = get_recommend(10, 10, 1)
    print(res)
