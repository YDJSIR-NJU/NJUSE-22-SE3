<template>
  <div>
    <el-button circle size="medium"
               style="width:70px;height:70px;position:fixed;right:20px;bottom: 70px;font-size:20px; z-index:10"
               type="primary"
               @click="ratingDrawer = true">评分
    </el-button>
    <el-button v-if="hasSelected===true" size="medium"
               style="width:fit-content;height:70px;position:absolute;left:20px; right: 20px; bottom: 70px;font-size:20px; z-index:10"
               type="primary"
               icon="el-icon-s-claim"
               @click="lowQualityDrawer = !lowQualityDrawer">
      协作低质量报告
    </el-button>

    <el-drawer
      :visible.sync="lowQualityDrawer"
      :with-header="false"
      direction="rtl"
      title="低质量报告">
      <h2>低质量报告列表</h2>
      <div v-for="(item,index) in lowQualitysReports" :key="index" @click="handleClickLowQuality(item.id)">
        <low-quality-card :Title="item.title" :description="item.description"
                          :score="scoresAVG[index]"></low-quality-card>
        <!--        {{item.title}}-->
      </div>
      <!--      <el-pagination-->
      <!--        :page-count='pagination.total'-->
      <!--        layout="prev, pager, next, jumper"-->
      <!--        @current-change="handleCurrentChange0"-->
      <!--      >-->
      <!--      </el-pagination>-->


    </el-drawer>

    <el-drawer
      :visible.sync="ratingDrawer"
      :with-header="false"
      direction="ltr"
      title="任务评分">
      <h2 style="text-align: left; margin-left: 1.5rem">任务{{ fileList.taskid }}的众测工人评分</h2>
      <div v-if="hasSelected&&!hasRated">
        <el-rate v-model="rate" :max=10 style="margin-bottom: 20px"></el-rate>
        <el-button @click="submitRating">提交评分</el-button>
      </div>
      <div v-for="(item,index) in ratingsData.ratingList">
        <el-card style="text-align: left; width: 90%;margin-left: 2rem;margin-bottom: 10px">
          <div style="font-size: 25px;margin-bottom: 10px">用户ID:{{ item.userId }}</div>
          <el-rate
            v-model="item.score"
            :max=10
            disabled
            show-score

            text-color="#ff9900">
          </el-rate>

        </el-card>
      </div>

      <el-pagination
        :page-count='ratingsData.pagination.total'
        layout="prev, pager, next, jumper"
        style="margin-top: 30px;width: 60%;margin-left: 30%"
        @current-change="handleCurrentRatingsChange"
      >
      </el-pagination>
    </el-drawer>

    <el-row style="margin: 0 auto; width: 90%">
      <el-col :span="6">
        <div v-if="role==='WORKER'" class="float-left">
          <h2>推荐任务</h2>
          <span>点击可跳转到对应任务详情页</span>
          <div v-for="(recomTasks,index) in recommend"
               v-bind:key="index"
               @click="handleClickRecommend(recomTasks)"
               style="margin-top: 1rem"
          >
            <recommend-task-card :item="recomTasks" style="cursor:pointer;"></recommend-task-card>
          </div>
        </div>

        <div v-if="role==='PUBLISHER'" class="float-left">
          <h2>推荐用户</h2>
          <div v-for="(uid,index) in recommendUsers"
               v-bind:key="index"
          >
            <div
              style="width: 80%;margin-left: 10%;margin-bottom: 10px;border:1px solid #BDBDBD;text-align: left;padding:10px;border-radius: 5px;height: auto;font-size: 1.2rem">
              众包工人{{ uid }}
              <el-button size="mini" type="success" style="float: right; margin-left: 1rem"
                         @click="handleInviteUser(uid)">邀请
              </el-button>
              <el-button size="mini" type="primary" style="float: right; margin-left: 1rem"
                         @click="handleClickUserInfo(uid)">详情
              </el-button>
            </div>
          </div>
        </div>
      </el-col>
      <el-col :span="18">
        <div class="middle">
          <h2 style="text-align: left;">任务{{ fileList.taskid }} 详情页</h2>
          <div style="width: 100%;margin: 0 auto;">
            <el-descriptions :column="6" border direction="vertical">
              <el-descriptions-item label="发布者">{{ fileList.publisherID }}</el-descriptions-item>
              <el-descriptions-item label="任务类型">{{ fileList.tasktype }}</el-descriptions-item>
              <el-descriptions-item label="招募人数">{{ fileList.numOfWorker }}</el-descriptions-item>
              <el-descriptions-item label="已有人数">{{ fileList.num }}</el-descriptions-item>
              <el-descriptions-item label="任务介绍">{{ fileList.taskInfo }}</el-descriptions-item>

            </el-descriptions>
          </div>
          <div style="width: 70%;margin-left: 15%;margin-top: 30px;">

          </div>
          <div style="margin-bottom: 30px">
            <el-button class="f" type="info" @click="download">
              <i class="el-icon-download"></i>
              点击下载资源文件
            </el-button>
            <el-button v-if="role==='WORKER'&&!hasSelected" class="f" type="primary" @click="choose">
              <i class="el-icon-document-add"></i>
              点击选择并接受任务
            </el-button>
            <el-button v-if="(role==='PUBLISHER' && isOwner) || role==='ADMINISTRATOR' " class="f" type="primary"
                       @click="checkReports">
              <i class="el-icon-document"></i>
              点击查看所有报告
            </el-button>
            <el-button v-if="(role==='PUBLISHER' && isOwner&&isClose===false) || role==='ADMINISTRATOR' " class="f"
                       type="danger" @click="closeTask">
              <i class="el-icon-document"></i>
              点击关闭任务
            </el-button>
            <el-button v-if="(role==='PUBLISHER' && !isOwner)" class="f" disabled type="danger">
              <i class="el-icon-document"></i>
              这不是您发布的任务
            </el-button>
            <el-button v-if="role==='WORKER'&&hasSelected" class="f" type="success" @click="submitReport">
              <i class="el-icon-upload2"></i>
              点击提交新测试报告
            </el-button>
            <el-button v-if="role==='WORKER'&&hasFinished" type="primary" @click="drawer=true">
              <i class="el-icon-document"></i>
              点击查看自己的报告
            </el-button>
          </div>

          <iframe
            :src=websource
            onload="this.style.height= (window.innerHeight * 0.7) +'px';"
            style="width: 100%; border: hidden"
            title="任务说明"
          ></iframe>

          <el-drawer
            :size="drawerWidth"
            :visible.sync="drawer"
            :with-header="false"
            style="z-index: 10;text-align: left" title="我的报告列表">
            <h2 style="margin-left: 7%">当前任务下我的报告列表</h2>

            <div v-if="hasFinished==true">
              <div v-for="(item,index) in myReport" style="cursor:pointer;" @click="handleClickMyReport(item)">
                <el-card style="text-align: left; width: 86%;margin-left: 7%;margin-bottom: 10px;border-radius: 12px;border-style:solid;
    border-color:#D3D3D3;border-width:0.5px" shadow="hover">
                  <div style="font-size: 22px;margin-bottom: 10px">{{ item.title }}<el-tag v-if="item.status === 'REJECTED'" effect="dark" type="danger" style="margin-left: 10px">已被拒绝</el-tag>
                    <el-tag v-if="item.status === 'PASSED'" effect="dark" type="success" style="margin-left: 10px">成功提交</el-tag>
                    <el-tag v-if="item.status === 'PROCESSING'" effect="dark" type="info"style="margin-left: 10px">成功提交</el-tag></div>

                  <div style="font-size: 18px;margin-bottom: 10px">{{ item.description }}</div>
                  <div style="font-size: 15px;color: #999999">{{ item.deviceBrand }} | {{ item.operatingSystem }} |
                    {{ item.createTime }}
                  </div>
                </el-card>
              </div>


              <el-pagination
                :page-count='pagination.total'
                layout="prev, pager, next, jumper"
                style="margin-top: 30px;width: 60%;margin-left: 30%"
                @current-change="handleCurrentChange"
              >
              </el-pagination>
            </div>
            <div v-else>
              你当前没有在该任务下提交任何报告哦~
            </div>

          </el-drawer>


        </div>
      </el-col>
    </el-row>

  </div>
</template>

<script>
import {TASK_MODULE} from '@/api/_prefix'
import RecommendTaskCard from "../../components/recommendTaskCard";
import {getRecommendations} from "../../api/recommend";
import {checkTaskStatus, selectTask, taskDetail} from "@/api/task";
import {getReportListByTestRecordId, getReportScoreAvg, lowQuality} from "../../api/report";
import {getRatingStatus, getTaskRatings, submitRating} from "../../api/taskRating";
import {closeTask, getCloseInfo, getRecommendUsers} from "../../api/task";
import {sendInviteMsg} from "../../api/message";
import lowQualityCard from "../../components/lowQualityCard";

export default {
  name: "testPage",
  components: {RecommendTaskCard, lowQualityCard},
  data() {
    return {
      lowQualityPage: {
        pagination: {
          total: 0,
          pageSize: 10
        },
        pageNumber: 1
      },
      lowQualitysReports: [
        {
          testRecordId: 23333,
          title: '报告加载中',
          description: '报告描述加载中',
          score: 9.9
        },
      ],
      scoresAVG: [],

      lowQualityDrawer: false,
      isClose: false,
      recommendUsers: [16, 17, 18],
      isOwner: false,
      websource: "#",
      rate: 0,
      hasRated: false,
      ratingDrawer: false,
      uid: '',
      recommend: [],
      role: window.localStorage.getItem('userRole'),
      fileList: {
        taskid: '243',
        tasktype: 'aaa',
        taskInfo: "c",
        numOfWorker: '20',
        num: '2',
        publisherID: '0',
      },
      hasSelected: false,
      hasFinished: false,
      drawer: false,
      drawerWidth: '800px',
      task_record_id: '',


      myReport: [],
      pagination: {
        total: 0,
        pageSize: 10
      },
      pageNumber: 1
      ,


      ratingsData: {
        ratingList: [],
        pagination: {
          total: 0,
          pageSize: 10
        },
        pageNumber: 1
      }
    }
  },
  mounted() {

    this.uid = window.localStorage.getItem('id')
    var that = this;
    getRecommendations(this.uid).then(function (res) {
      that.recommend = res.data.data
      //console.log(that.recommend)
      var i = 0
      for (i = 0; i < that.recommend.length; i++) {
        var tmp = that.recommend[i].taskDiscribe
        that.recommend[i].taskDiscribe = tmp.slice(0, 22) + '...'
      }
    })
    //console.log(that.recommend)
    getRecommendUsers(this.fileList.taskid).then(function (res) {
      console.log(res)
      that.recommendUsers = res.data;//此处是一维list形式
    })


  },
  created() {
    this.getInfo()
    this.querySelect()
    //此处获得用户的报告列表


  },
  methods: {
    handleClickLowQuality(id) {
      this.$router.push({path: '/collaborateReportPage', query: {id: id}})
    },
    handleClickUserInfo(id) {
      this.$router.push({path: '/userInfo', query: {userId: id}})
    },
    closeTask() {
      closeTask(this.fileList.taskid).then(res => {
        alert("关闭任务成功！")
        //this.$router.go(0)
      })
    },
    handleInviteUser(uid) {

      sendInviteMsg(uid, "你好！用户" + window.localStorage.getItem('id') + "邀请你参与任务" + this.fileList.taskid + "，可以前往任务广场领取任务哦~").then(function (res) {
        console.log(res)
        if (res.data.code == 1) {
          alert("邀请成功！")
        } else {
          alert("好像邀请失败了QAQ")
        }
      })
    },
    getInfo() {
      var that = this
      taskDetail(this.$route.query.id, window.localStorage.id)
        .then(function (response) {
          //console.log(response)
          that.websource = "https://se3-1309398890.cos.ap-shanghai.myqcloud.com/upload/" + JSON.parse(response.data.data.discribFilePath)[0]
          console.log(that.websource)
          that.fileList.taskid = response.data.data.id
          that.fileList.tasktype = response.data.data.type
          that.fileList.taskInfo = response.data.data.taskDiscribe
          that.fileList.numOfWorker = response.data.data.totalNum
          that.fileList.num = response.data.data.currentNum
          that.fileList.publisherID = response.data.data.userId
          if (response.data.data.userId == window.localStorage.getItem('id')) {
            that.isOwner = true
            // console.log(response.data.data.userId)
          }
          lowQuality(that.fileList.taskid, localStorage.getItem('id'), 1).then(res => {
            that.lowQualitysReports = res.data.list
            var i
            for (i = 0; i < that.lowQualitysReports.length; i++) {
              console.log(that.lowQualitysReports[i].id)
              const that2 = that
              getReportScoreAvg(window.localStorage.getItem('id'), that.lowQualitysReports[i].id).then(r => {
                console.log(r.data.data)
                that2.scoresAVG.push(r.data.data)
              })
            }
            console.log(that.lowQualitysReports)
          })
        })

    },
    querySelect() {
      var that = this
      checkTaskStatus(window.localStorage.getItem('id'), this.$route.query.id)
        .then(function (res) {
          //console.log(res)
          that.hasFinished = res.data.data.finished
          if (res.data.code === 1) {
            that.hasSelected = true
            that.task_record_id = res.data.data.id
            getReportListByTestRecordId(that.task_record_id, window.localStorage.getItem('id'), 1).then(function (response) {
              //console.log(response)
              that.myReport = response.data.list
              that.pagination.total = response.data.pages
            })

          }
          getTaskRatings(window.localStorage.getItem('id'), 1, that.$route.query.id).then(function (r) {
            //console.log(r)
            that.ratingsData.ratingList = r.data.list
            that.ratingsData.pagination.total = r.data.pages
          })
          getRatingStatus(window.localStorage.getItem('id'), that.$route.query.id).then(function (re) {
            //console.log(re)
            if (re.data.data == null) {
              that.hasRated = false;
            } else {
              that.hasRated = true
            }
          })
          getCloseInfo(that.$route.query.id).then(res => {
            console.log(res)
            that.isClose = res.data.data;
          })
        })

    },
    download() {
      // window.location.href = window.location.protocol + "//" + window.location.host + TASK_MODULE + '/downloadDocs?userId=' + window.localStorage.getItem('id') + '&taskId=' + this.fileList.taskid
      // downloadDocs(window.localStorage.getItem('id'), this.fileList.taskid)
      const url = window.location.protocol + "//" + window.location.host + TASK_MODULE + '/downloadDocs?userId=' + window.localStorage.getItem('id') + '&taskId=' + this.fileList.taskid
      var xhr = new XMLHttpRequest();
      xhr.open("get", url, true); // get、post都可
      xhr.responseType = "blob";  // 转换流
      xhr.setRequestHeader("Authorization", window.localStorage.getItem("token")); // token键值对
      const that = this
      xhr.onload = function () {
        if (this.status == 200) {
          var blob = this.response;
          var a = document.createElement("a")
          var url = window.URL.createObjectURL(blob)
          a.href = url
          a.download = "Resources_of_task_" + that.fileList.taskid + ".zip"
        }
        a.click()
        window.URL.revokeObjectURL(url)
      }
      xhr.send();
    },

    choose() {
      var that = this
      selectTask(window.localStorage.getItem('id'), this.fileList.taskid)
        .then(function (res) {
          console.log(res)
          that.$message({message: "选择成功！", type: 'success'})
          that.$router.go(0)

        })
    },
    checkReports() {
      this.$router.push({path: '/reportList', query: {id: this.fileList.taskid}})
    },
    submitReport() {
      this.$router.push({path: '/submitReport', query: {taskid: this.fileList.taskid}})
    },
    handleClickRecommend(recomTasks) {
      this.$router.push({path: '/taskInfo', query: {id: recomTasks.id}})
    },
    handleCurrentChange(currentPage) {
      this.pageNumber = currentPage
      var that = this
      getReportListByTestRecordId(this.task_record_id, window.localStorage.getItem('id'), this.pageNumber).then(function (response) {
        that.myReport = response.data.list
        that.pagination.total = response.data.pages
      })
    },
    handleCurrentChange0(currentPage) {
      //console.log(this.pagination.total)
      this.lowQualityPage.pageNumber = currentPage
      //console.log(this.pageNumber)
      //console.log(currentPage)
      var that = this
      lowQuality(that.fileList.taskid, localStorage.getItem('id'), this.lowQualityPage.pageNumber).then(res => {
        that.lowQualitysReports = res.data.list
        that.scoresAVG = []
        var i
        for (i = 0; i < that.lowQualitysReports.length; i++) {
          getReportScoreAvg(window.localStorage.getItem('id'), that.lowQualitysReports[i].testRecordId).then(r => {
            that.scoresAVG.push(r.data.data)
          })
        }
        console.log(that.lowQualitysReports)
        console.log(that.scoresAVG)
      })

    },
    handleCurrentRatingsChange(currentPage) {
      this.ratingsData.pageNumber = currentPage
      var that = this
      getTaskRatings(window.localStorage.getItem('id'), this.pageNumber, that.$route.query.id).then(function (r) {
        console.log(r)
        that.ratingsData.ratingList = r.data.list
        that.ratingsData.pagination.total = r.data.pages
      })

    },
    submitRating() {
      submitRating({
        userId: window.localStorage.getItem('id'),
        taskId: this.$route.query.id,
        score: this.rate
      }).then(function (res) {
        console.log(res)
      })
    },
    handleClickMyReport(item) {
      this.$router.push({path: '/reportPage', query: {id: item.id}})
    }

  },
  watch: {
    '$route'(to, from) {
      // 路由发生变化页面刷新
      this.$router.go(0);
    }
  },

}
</script>

<style scoped>
.float-left {
  border-radius: 0px 0px 5px 5px;
  width: auto;
  height: auto;
  padding-bottom: 2rem;
  border: 1px solid #BDBDBD;
  background-color: #fefefe;
  box-shadow: 1px 1px 2px #999999;
}

.middle {
  border-radius: 5px;
  width: 95%;
  height: auto;
  margin-left: 4rem;
  background-color: #ffffff;
  font-size: 30px;
}

html, body {
  height: 100%;
}

/deep/ .el-rate__icon {
  font-size: 25px;
}

</style>
