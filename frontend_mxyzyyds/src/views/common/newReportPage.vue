<template>
  <div>
    <el-button circle icon="el-icon-chat-line-square" size="medium"
               style="width:6rem;height: 6rem;position:fixed;right:20px;bottom: 70px;font-size:25px"
               v-if="role!=='PUBLISHER'"
               type="primary"
               @click="drawer = true">评价
    </el-button>
    <el-button icon="el-icon-document-checked" size="medium"
               style="width:fit-content;height:70px;position:fixed;left:0px;bottom:0px;
               font-size:25px;border-radius:0px 0px 0px 0px"
               type="primary"
               @click="drawerLeft = true">补充报告
    </el-button>
    <el-container style="height: 93.5vh; border: 1px solid #eee">
      <el-drawer
        :visible.sync="drawerLeft"
        :with-header="false"
        direction="rtl"
        title="我是标题">
        <h1>报告{{ nowReport.id }}的补充报告列表</h1>

        <div v-for="(item,index) in additionalReport" style="cursor:pointer;"
             @click="handleClickAdditionalReport(item)">
          <el-card style="text-align: left; width: 90%;margin-left: 5%;margin-bottom: 15px">
            <div style="font-size: 1.5rem;margin-bottom: 10px">补充报告id: {{ item.id }}</div>
            <div style="font-size: 1.2rem;margin-bottom: 10px">{{ item.description }}</div>
            <div style="font-size: 1rem;color: #999999">
              <el-tag>{{ item.deviceBrand }}</el-tag> &nbsp;
              <el-tag>{{ item.operatingSystem }}</el-tag>
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

      </el-drawer>


      <el-drawer
        :visible.sync="drawer"
        :with-header="false"
        direction="ltr"
        style="z-index: 10;text-align: left"
        title="我是标题">
        <h2 style="margin-left: 2rem">报告{{ nowReport.id }}的众包工人评价</h2>
        <el-card style="margin: 8%;text-align: left">
          <h3>发布评价</h3>
          <span style="margin-right: 10px">点击评分</span>
          <el-rate v-model="comment.myScoreValue" :max=10 style="display: inline;"></el-rate>
          <div style="margin-top: 20px">请输入评论</div>
          <el-input
            v-model="comment.text"
            :rows="6"
            maxlength="200"
            placeholder="请输入内容"
            resize="none"
            show-word-limit
            style="margin-top: 20px;width: 100%;margin-bottom: 20px;"
            type="textarea"
          >
          </el-input>
          <el-button style="float:right;margin-right: 20px;margin-bottom: 20px " type="primary" @click="addComment">
            提交评价
          </el-button>
        </el-card>
        <div v-for="(es,index) in estimate"
             v-bind:key="index"
             style="width: 84%; margin-left: 8%;margin-bottom: 20px"
        >
          <el-card>
            用户ID：{{ es.id }}
            <el-tag v-if="es.id == uid" type="success">我的评价</el-tag>
            <br>
            发布时间：{{ es.time }}
            <br>
            <el-rate
              v-model="es.score"
              :max=10
              disabled
              show-score
              style="display: inline;"
              text-color="#ff9900"
            >
            </el-rate>
            <div>
              {{ es.desc }}
            </div>
          </el-card>

        </div>
      </el-drawer>


      <el-aside width="50%">
        <h1 style="text-align: left; font-size: 1.5rem; margin-left: 2rem">任务{{ nowReport.taskId }}的的测试报告{{
            nowReport.id
          }}：{{ nowReport.title }}</h1>
        <div style="text-align: left; margin-bottom: 1rem; margin-left: 2.5rem">



          <span>众包工人评价均分</span>
            <el-rate
              v-model="avgScore"
              :max=10
              disabled
              show-score
              style="display: inline;margin-left: 20px"
              text-color="#ff9900"
            >
            </el-rate>
            <div>

            </div>



        </div>

        <el-descriptions :column="10" border direction="vertical" style="width: 90%;margin: 0 auto;font-size: 1rem">
          <el-descriptions-item :span="2" label="发布者">
            <el-tooltip class="item" effect="dark" content="查看该众包工人信息" placement="bottom">
              <el-button type="primary" @click="goToUserInfo(nowReport.userId)">众包工人{{ nowReport.userId }}</el-button>
            </el-tooltip>

          </el-descriptions-item>
          <el-descriptions-item :span="1" label="操作系统">
            <el-tag>{{ nowReport.operatingSystem }}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item :span="1" label="设备品牌">
            <el-tag>{{ nowReport.deviceBrand }}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item :span="2" label="接收任务时间">
            {{ nowReport.commitTime }}
          </el-descriptions-item>
          <el-descriptions-item :span="2" label="报告创建时间">
            {{ nowReport.createTime }}
          </el-descriptions-item>
          <el-descriptions-item :span="2" label="报告状态">
            <el-tag>{{ nowReport.status }}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item :span="4" label="报告描述">
            <div v-html="nowReport.desc"></div>
          </el-descriptions-item>
          <el-descriptions-item label="补充截图">
            点击图片可查看大图
            <div v-viewer="{inline: false}" class="images" @click="show">
              <img v-for="pic in nowReport.finalpics" :key="pic" :src="pic" alt="img" style="width: 100%">
            </div>
          </el-descriptions-item>
        </el-descriptions>
      </el-aside>


      <el-container>
        <el-aside style="text-align: left;" width="100%">
          <!--          border-left-style:solid;border-width:2px; border-color: #666666-->
          <div style="text-align: center">
            <h4 style="font-size: 1.2rem; font-weight: bold; text-align: center">报告相似关系图</h4>
            <span>点击可跳转到具体报告详情页</span>
          </div>
          <div style="width: 90%">
            <div id="relation" style="height: 500px; width: 100%">
              <el-skeleton :rows="3" animated/>
              <span style="font-size: 32px; width: 100%"> 报告相似关系加载中……</span>
              <el-skeleton :rows="3" animated/>
            </div>
          </div>

          <el-card v-if="role==='WORKER'" style="margin: 0.5rem">
            <div style="margin-left: 20px;font-size: 1.5rem;margin-bottom: 10px">可协作报告列表（协作阈值：{{
                cooperationMin
              }}）
            </div>
            <p style="margin-left: 20px;">{{ collaborateNotice }}</p>
            <div v-for="(collaborateTasksReports,index) in collaborateTasksReports"
                 v-bind:key="index"
                 style="cursor:pointer;"
                 @click="handleCollaborateClick(collaborateTasksReports.reportId)"
            >
              <collaborate-card :item="collaborateTasksReports"></collaborate-card>
            </div>
          </el-card>

          <el-card
            v-if="isReviewer && needReview && nowReport.status !== 'REJECTED' && nowReport.status !== 'PROCESSING'">
            <div style="margin-left: 20px;">
              <div style="font-size: 1.5rem;margin-bottom: 10px">报告评审</div>
              <div>
                <span style="border-left: 4px solid #39a9ff;padding-left: 8px; ">评审结果</span>
                <el-radio-group v-model="reviewResultType" style="margin-left: 20px;" @change="resultChange">
                  <el-radio-button label="REJECTED"><i class="el-icon-close"></i>&nbsp;拒绝报告</el-radio-button>
                  <el-radio-button label="BUG_DUP"><i class="el-icon-minus"></i>&nbsp;BUG重复</el-radio-button>
                  <el-radio-button label="NEW_BUG"><i class="el-icon-plus"></i>&nbsp;发现BUG</el-radio-button>
                </el-radio-group>
                <div v-if="dupbug" style="margin-top: 10px;">
                  <span>点击以选择本报告与下列哪份报告的BUG重复。</span>
                  <el-tag v-if="review.dupTag!==-1" style="margin-left: 1rem">{{ review.dupTag }}</el-tag>
                </div>
                <p style="margin-left: 6px">
                  审核不通过即认为该份报告不仅没有发现BUG，质量也不达标，予以拒绝。<br>
                  BUG重复即虽然该报告发现了BUG，但是并不是首次发现该BUG，可供参考。<br>
                  发现新BUG即认为该份报告的确发现了新的BUG。
                </p>
              </div>
              <div v-if="dupbug">
                <el-card v-if="item.id == review.dupTag" v-for="(item,index) in dupbugList" :key="index"
                         style="padding: 0; margin-top: 0.5rem; background-color: #87CEFA"
                         @click.native="handleClick(item.id)">
                  <el-descriptions size="mini" direction="vertical" :column="4" style="padding: 0; margin: 0 auto"
                                   border>
                    <el-descriptions-item label="报告id" label-class-name="my-label" content-class-name="my-content">
                      <el-tag v-if="item.id == review.dupTag" type="success">{{ item.id }}</el-tag>
                    </el-descriptions-item>
                    <el-descriptions-item label="设备品牌" style="padding: 0; margin: 0 auto">
                      {{ item.deviceBrand }}
                    </el-descriptions-item>
                    <el-descriptions-item label="操作系统" style="padding: 0; margin: 0 auto">
                      {{ item.operatingSystem }}
                    </el-descriptions-item>
                    <el-descriptions-item label="创建时间" style="padding: 0; margin: 0 auto">
                      {{ item.createTime }}
                    </el-descriptions-item>
                    <el-descriptions-item label="报告描述" style="padding: 0; margin: 0 auto">
                      {{ item.description }}
                    </el-descriptions-item>
                  </el-descriptions>
                </el-card>
                <el-card v-if="item.id != review.dupTag" v-for="(item,index) in dupbugList" :key="index"
                         style="padding: 0; margin-top: 0.5rem;"
                         @click.native="handleClick(item.id)">
                  <el-descriptions size="mini" direction="vertical" :column="4" style="padding: 0; margin: 0 auto"
                                   border>
                    <el-descriptions-item label="报告id" label-class-name="my-label" content-class-name="my-content">
                      <el-tag v-if="item.id != review.dupTag" type="primary">{{ item.id }}</el-tag>
                    </el-descriptions-item>
                    <el-descriptions-item label="设备品牌" style="padding: 0; margin: 0 auto">
                      {{ item.deviceBrand }}
                    </el-descriptions-item>
                    <el-descriptions-item label="操作系统" style="padding: 0; margin: 0 auto">
                      {{ item.operatingSystem }}
                    </el-descriptions-item>
                    <el-descriptions-item label="创建时间" style="padding: 0; margin: 0 auto">
                      {{ item.createTime }}
                    </el-descriptions-item>
                    <el-descriptions-item label="报告描述" style="padding: 0; margin: 0 auto">
                      {{ item.description }}
                    </el-descriptions-item>
                  </el-descriptions>
                </el-card>
              </div>
              <div style="margin-top: 1rem">
                <span style="border-left: 4px solid #39a9ff;padding-left: 8px; ">报告评分</span>
                <el-rate v-model="review.score" :max=10 show-score
                         style="display: inline; margin-left: 20px;"></el-rate>
              </div>
              <el-button style="margin-top: 1.5rem; " type="primary" @click="submitReview"><i
                class="el-icon-upload2"></i>&nbsp;提交结果
              </el-button>
            </div>
          </el-card>

          <el-card v-if="isReviewer && !needReview">
            <div style="margin-left: 20px;">
              <div style="font-size: 1.5rem;margin-bottom: 10px">评审结果</div>
              <div>
                <span style="border-left: 4px solid #39a9ff;padding-left: 8px; margin-right: 1rem">评审结果</span>
                <el-tag v-if="(review.isRepeatBug === false) && review.accepted === true" type="success">发现新BUG</el-tag>
                <el-tag v-if="(review.isRepeatBug === true) && review.accepted === true" type="info">重复的BUG</el-tag>
                <el-tag v-if="review.accepted === false" type="info">已被拒绝</el-tag>
              </div>
              <div style="margin-top: 1rem">
                <span style="border-left: 4px solid #39a9ff;padding-left: 8px; ">我的打分</span>
                <el-rate v-model="review.score" :max=10 show-score
                         style="display: inline; margin-left: 20px;" disabled></el-rate>
              </div>
            </div>
          </el-card>

        </el-aside>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import CollaborateCard from '../../components/collaborateCard'
import * as echarts from 'echarts'
import {
  getAvgScore,
  getCorporateReport,
  getReportRemarkByReport,
  getReviewStatus,
  getSimConfig,
  getSimReport,
  releaseReportRemark,
  reportDetail,
  reviewReport
} from '@/api/report'
import { getAdditionalByReport } from '../../api/additionalReport'
import { getTestRecord, taskDetail } from '@/api/task'
import { getDistinctReport } from '../../api/report'

export default {
  name: "newReportPage",
  components: {CollaborateCard},
  data() {
    return {
      dupbugList: '',
      dupbug: false,
      uid: window.localStorage.getItem('id'),
      role: window.localStorage.getItem('userRole'),
      isReviewer: false,
      needReview: true,
      review: {
        id: 0,
        reportId: this.$route.query.id,
        bugNums: 0,
        score: 5,
        isRepeatBug: false,
        accepted: true,
        createTime: '',
        dupTag: -1
      },
      reviewResultType: '',
      additionalReport: [],
      pagination: {
        total: 0,
        pageSize: 10
      },
      pageNumber: 1,
      collaborateNotice: "",
      item: {},
      avgScore: 0,
      nowReport: {
        desc: '',
        id: this.$route.query.id,
        testRecordId: 0,
        picList: [],
        picListStr: '',
        title: '',
        operatingSystem: '',
        deviceBrand: '',
        createTime: '',
        status: "PASSED",
        head: 'https://se3-1309398890.cos.ap-shanghai.myqcloud.com/upload/',
        finalpics: [],
        taskId: 0,
        userId: 0,
        commitTime: ''
      },
      cooperationMin: 0,
      comment: {
        myScoreValue: '',
        text: ''
      },
      value: 0,
      textarea: '',
      drawer: false,
      drawerLeft: false,
      collaborateTasksReports: [
        {
          reportId: 23333,
          title: '报告加载中',
          desc: '报告描述加载中',
          score: 9.9
        },
      ],
      estimate: [
        {
          id: '2333',
          score: 8,
          desc: '评价加载中',
          time: new Date()
        },
      ],
      simReportDraw: [],
      simReportDrawLink: [],
      simReport: [],
    }

  }
  ,
  methods: {
    handleClick(id) {
      this.review.dupTag = id
    },
    resultChange(value) {
      if (value === 'BUG_DUP') {
        console.log(this.dupbugList)
        if (this.dupbugList === null) {
          alert("之前提交的报告中还没有发现bug的哦，请您再仔细审查下")
        } else {
          this.dupbug = true
        }

      } else {
        this.dupbug = false
      }

    },
    goToUserInfo(userId) {
      this.$router.push({path: "/userInfo", query: {userId: userId}})
    },

    submitReview() {
      const that = this
      switch (this.reviewResultType) {
        case "REJECTED":
          this.review.bugNums = 0
          this.review.isRepeatBug = true
          this.review.accepted = false
          break
        case "BUG_DUP":
          this.review.bugNums = 1
          this.review.isRepeatBug = true
          this.review.accepted = true
          break
        case "NEW_BUG":
          this.review.bugNums = 1
          this.review.isRepeatBug = false
          this.review.accepted = true
          break
        default:
          this.$message.warning("请选择评审结果类型！")
      }
      var formData = new FormData()
      formData.append('reportReviewDto', (JSON.stringify(this.review)))
      var param = "{'id':" + this.review.id + ", 'reportId':" + this.review.reportId + ",'bugNums':" + this.review.bugNums + ",'score':" + this.review.score + ",'isRepeatBug':" + this.review.isRepeatBug
        + ",'accepted':" + this.review.accepted + ",'createTime':''" + ",'dupTag':" + this.review.dupTag + "}"

      console.log(param)
      reviewReport(window.localStorage.getItem('id'), param).then(function (res) {
        if (res.data.code === 1) {
          that.review = res.data.data
          location.reload()
        }
      })
    },

    getAvgScore() {
      const that = this
      console.log("GET")
      getAvgScore(window.localStorage.getItem('id'), this.nowReport.id).then(function (res) {
        if (res.data.code === 1) {
          that.avgScore = parseFloat(that.round2digit(res.data.data))


          console.log(that.avgScore)
        }
      })
    },

    addComment() {
      var that = this
      var userId, score, description, commentVo
      userId = window.localStorage.getItem('id')
      score = this.comment.myScoreValue
      description = this.comment.text
      commentVo = {
        userId: userId,
        defectReportId: this.nowReport.id,
        score: score,
        createTime: null,
        description: description
      }
      releaseReportRemark(userId, commentVo).then(function (res) {
        that.$message({message: res.data.msg, type: 'success'});
        that.updateRemarkList()
      }).catch(function (error) {
          that.$message.alert(res.data.msg);
        }
      )


    },

    round2percent(num) {
      num = Math.round(num * Math.pow(10, 4)) / Math.pow(10, 4)
      num = Number(num * 100).toFixed(2)
      return num + ''
    },

    round2digit(num) {
      num = Math.round(num * Math.pow(10, 4)) / Math.pow(10, 4)
      num = Number(num).toFixed(2)
      return num + ''
    },

    initData() {
      var that = this

      this.updateDefectReportDetail()
      this.updateCorparateReport()
      this.updateRemarkList()
      this.getAdditionalReports()
      this.getSimConfig()
      this.getDistinctReportList()


    },
    getDistinctReportList() {
      var that = this
      getDistinctReport(this.$route.query.id).then(res => {

        that.dupbugList = res.data.data
        console.log(that.dupbugList)

      })
    },
    getSimConfig() {
      const that = this
      getSimConfig(window.localStorage.getItem('id')).then(function (res) {
        // console.log(res)
        that.cooperationMin = (res.data.data.cooperationMin * 100) + '%'
      })
    },

    getAdditionalReports() {
      var that = this
      getAdditionalByReport(window.localStorage.getItem('id'), 1, this.nowReport.id).then(function (res) {
        // console.log(res)
        that.additionalReport = res.data.list
        that.pagination.total = res.data.pages
      })
    },

    show() {
      // console.log("Hi")
      const viewer = this.$el.querySelector('.images').$viewer
      viewer.show()
    },

    updateSimPicture() {
      var that = this
      getSimReport(window.localStorage.getItem('id'), this.nowReport.id, 5)
        .then(function (res) {
          // console.log(res)

          let minScore = 255, maxScore = -255
          for (var i = 0; i < res.data.data.length; i++) {
            let defectReportVo, score;
            defectReportVo = res.data.data[i].defectReportVo
            score = res.data.data[i].score
            // console.log(defectReportVo)
            if (score > maxScore) maxScore = score
            if (score < minScore) minScore = score
            that.simReport.push(defectReportVo)
            //"……" +  (defectReportVo.description+'').substr(-30, 30)
            that.simReportDraw.push({
              name: "报告" + defectReportVo.id,
              symbolSize: that.round2percent(score),
              des: defectReportVo.title,
              color: 'rgba(' + (that.getHash(defectReportVo.title) % 256 / 2) + ',' + ((that.getHash(defectReportVo.title) % 8167) % 256 / 2) + ',' + ((that.getHash(defectReportVo.title) % 9973) % 256) + ', 0.7)'
            })
            that.simReportDrawLink.push({
              source: "报告" + defectReportVo.id,
              target: "当前报告",
              value: (that.round2percent(score) + '%')
            })
          }
          that.simReportDraw.forEach(item => {
            if (item.name !== "当前报告")
              item.symbolSize = ((item.symbolSize - minScore * 100) / (100 * (maxScore - minScore) + 1)) * 50 + 80
          })
          that.drawChart()
        })
    },

    getHash(str, seed = 0) {
      let h1 = 0xdeadbeef ^ seed, h2 = 0x41c6ce57 ^ seed
      for (let i = 0, ch; i < str.length; i++) {
        ch = str.charCodeAt(i)
        h1 = Math.imul(h1 ^ ch, 2654435761)
        h2 = Math.imul(h2 ^ ch, 1597334677)
      }
      h1 = Math.imul(h1 ^ (h1 >>> 16), 2246822507) ^ Math.imul(h2 ^ (h2 >>> 13), 3266489909)
      h2 = Math.imul(h2 ^ (h2 >>> 16), 2246822507) ^ Math.imul(h1 ^ (h1 >>> 13), 3266489909)
      return 4294967296 * (2097151 & h2) + (h1 >>> 0)
    },

    updateCorparateReport() {
      var that = this
      getCorporateReport(window.localStorage.getItem('id'), this.nowReport.id, 5).then(function (res) {
        that.collaborateTasksReports = []
        for (var i = 0; i < res.data.data.length; i++) {
          let defectReportVo, score;
          defectReportVo = res.data.data[i].defectReportVo
          score = res.data.data[i].score
          that.collaborateTasksReports.push({
            reportId: defectReportVo.id,
            title: defectReportVo.title,
            desc: defectReportVo.description,
            score: that.round2percent(score)
          })
        }
        if (that.collaborateTasksReports.length === 0) {
          that.collaborateNotice = "很抱歉，暂无可协作任务！"
        }
      })
    },

    updateDefectReportDetail() {
      var that = this
      reportDetail(window.localStorage.getItem('id'), this.nowReport.id)
        .then(function (res) {
          console.log(res)
          that.nowReport.picListStr = res.data.data.screenshotPathList
          that.nowReport.desc = res.data.data.description.replace(/。/, '。<br>')
          that.nowReport.testRecordId = res.data.data.testRecordId
          that.nowReport.status = res.data.data.status
          that.nowReport.title = res.data.data.title
          that.nowReport.operatingSystem = res.data.data.operatingSystem
          that.nowReport.deviceBrand = res.data.data.deviceBrand
          that.nowReport.createTime = res.data.data.createTime

          // console.log(that.nowReport.picListStr)
          that.nowReport.piclist = JSON.parse(that.nowReport.picListStr)
          var i = 0
          for (i; i < that.nowReport.piclist.length; i++) {
            that.nowReport.finalpics.push(that.nowReport.head + that.nowReport.piclist[i])
          }
          if (that.nowReport.status === "PASSED") {
            that.simReportDraw.push(
              {
                name: "当前报告",
                symbolSize: 100,
                des: "报告ID：" + that.nowReport.id,
                color: "#eba844"
              }
            )
          } else if (that.nowReport.status === "PROCESSING") {
            that.simReportDraw.push(
              {
                name: "相似度计算中",
                symbolSize: 200,
                des: "报告ID：" + that.nowReport.id,
                color: "#444444"
              }
            )
          } else if (that.nowReport.status === "REJECTED") {
            that.simReportDraw.push(
              {
                name: "报告已被拒绝",
                symbolSize: 200,
                des: "报告ID：" + that.nowReport.id,
                color: "#DD2222"
              }
            )
          } else if (that.nowReport.status === "AUDITING") {
            that.simReportDraw.push(
              {
                name: "等待发包方审核",
                symbolSize: 200,
                des: "报告ID：" + that.nowReport.id,
                color: "#2233DD"
              }
            )
            // console.log("RIGHT")
          }
          getTestRecord(window.localStorage.getItem('id'), that.nowReport.testRecordId).then(function (res) {
            // console.log(res)
            that.nowReport.taskId = res.data.data.taskId
            that.nowReport.userId = res.data.data.userId
            that.nowReport.commitTime = res.data.data.commitTime
            const that2 = that
            taskDetail(that.nowReport.taskId, window.localStorage.getItem('id')).then(function (res) {
              // console.log(res)
              if (res.data.data.userId == window.localStorage.getItem('id')) { // 判断是不是对应发包方
                that2.isReviewer = true
                console.log("IS REVIEWER")
              }
            })
            that.getAvgScore()
          })
          getReviewStatus(window.localStorage.getItem('id'), that.nowReport.id).then(function (res) {
            if (res.data.code === 1) {
              console.log(res.data)

              that.review = res.data.data
              that.needReview = false
              console.log(that.review)
            } else {
              if (that.role === 'PUBLISHER' && that.nowReport.status !== "REJECTED") {
                that.$message.info("该份缺陷报告仍未评审，请尽快处理！")
              }
            }
          })
          that.updateSimPicture()
        })
    },

    updateRemarkList() {
      const that = this
      getReportRemarkByReport(window.localStorage.getItem('id'), 1, this.nowReport.id).then(function (res) {
        var remarkList = []
        remarkList = res.data.list
        that.estimate = []
        remarkList.forEach(item => {
          that.estimate.push({id: item.userId, score: item.score, desc: item.description, time: item.createTime})
        })
        console.log('estimate')
        console.log(that.estimate)
      })
    },
    handleCurrentChange(currentPage) {
      this.pageNumber = currentPage
      var that = this
      getAdditionalByReport(window.localStorage.getItem('id'), 1, this.nowReport.id).then(function (response) {
        that.myReport = response.data.list
        that.pagination.total = response.data.pages
      })

    },

    handleCollaborateClick(reportId) {
      this.$router.push({path: '/collaborateReportPage', query: {id: reportId}})

    },
    handleClickAdditionalReport(item) {
      this.$router.push({path: '/additionalReportPage', query: {id: item.id}})
    },

    drawChart() {
      // 基于准备好的dom，初始化echarts实例
      let myChart = echarts.init(document.getElementById("relation"));
      const that = this
      // 指定图表的配置项和数据
      let option = {
        tooltip: {
          formatter: function (param) {
            // console.log("输出",param);
            //tooltip这里的formatter参数param可以得到series中的data数据
            return param.data.des;
          }
        },
        series: [
          {
            type: "graph",
            layout: "force",
            symbolSize: 80,
            focusNodeAdjacency: true, //是否在鼠标移到节点上的时候突出显示节点以及节点的边和邻接节点。
            roam: true, //是否开启鼠标缩放和平移漫游。默认不开启。如果只想要开启缩放或者平移，可以设置成 'scale' 或者 'move'。设置成 true 为都开启
            edgeSymbol: ["none", "arrow"], //边两端的标记类型，可以是一个数组分别指定两端，也可以是单个统一指定。默认不显示标记，常见的可以设置为箭头
            edgeSymbolSize: [10, 10],
            force: {
              repulsion: 3000, //节点之间的斥力因子
              edgeLength: [20, 30]
            },
            draggable: true,
            //定义节点的样式
            itemStyle: {
              color: function (param) {
                return param.data.color
              },
            },
            //连线的样式
            lineStyle: {
              width: 2,
              color: "#000"
            },
            //连线上的标记样式
            edgeLabel: {
              show: true,
              formatter: function (param) {
                //tooltip这里的formatter参数param可以得到series中的data数据
                return param.data.value;
              },
              color: "#000"
            },
            //节点上是否显示文字
            label: {
              show: true,
              fontWeight: "bold",
              fontSize: 18
            },
            data: this.simReportDraw,
            links: this.simReportDrawLink
          }
        ]
      };
      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(option);
      myChart.on('click', function (param) {
        var name = param.data.name
        var ids = name.match(/[0-9]+/g)
        // console.log(ids)
        if (ids == null || ids.length === 0) {
          return;
        }
        var id = ids[0]
        if (id == null || id == 0) {
          return
        }
        window.location.href = window.location.protocol + "//" + window.location.host + '/reportPage?id=' + id
      })
    }
  },
  mounted() {
    this.initData();
  }
}

</script>

<style scoped>
body, html {
  width: 100%;
  height: 100%;
}



</style>
