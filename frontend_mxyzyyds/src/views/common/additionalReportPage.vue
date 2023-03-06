<template>
  <div style="height: 100%">
    <el-container style="height: 100%; border: 1px solid #eee;">
      <el-aside width="50%" style=" background-color: #e9eef3">
        <h1 style="text-align: left; font-size: 1.5rem; margin-left: 2rem">针对测试报告{{
            defectReportId
          }}的补充报告{{ this.$route.query.id }}详情</h1>
        <el-descriptions :column="8" border direction="vertical" style="width: 90%;margin: 0 auto; font-size: 1rem">
          <el-descriptions-item :span="2" label="发布者">
            <el-tooltip class="item" effect="dark" content="查看该众包工人信息" placement="bottom">
              <el-button type="primary" @click="goToUserInfo(publisherID)">众包工人{{ publisherID }}</el-button>
            </el-tooltip>
          </el-descriptions-item>
          <el-descriptions-item :span="2" label="报告创建时间">{{ createTime }}</el-descriptions-item>
          <el-descriptions-item :span="2" label="操作系统">
            <el-tag>{{ os }}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item :span="2" label="设备品牌">
            <el-tag>{{ brand }}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item :span="4" label="报告描述">
            {{ desc }}
          </el-descriptions-item>
          <el-descriptions-item label="补充截图">
            <!--        <div v-for="pic in finalpics" :key="pic"><img :src="pic" alt="img"></div>-->
            点击图片可查看大图
            <div class="images1" v-viewer="{inline: false}" @click="show1">
              <img :src="pic" alt="img" v-for="pic in finalpics" :key="pic" style="width: 100%">
            </div>
          </el-descriptions-item>
        </el-descriptions>
      </el-aside>

      <el-container style="border-left: 2px solid #39a9ff">
        <el-aside width="100%">
          <h1 style="text-align: left; font-size: 1.5rem; margin-left: 2rem">测试报告{{
              nowReport.id
            }}：{{ nowReport.title }} 内容</h1>
          <el-descriptions :column="10" border direction="vertical" style="width: 90%;margin: 0 auto; font-size: 1rem">
            <el-descriptions-item :span="2" label="发布者ID">
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
            <el-descriptions-item :span="4" label="报告描述">{{ nowReport.desc }}</el-descriptions-item>
            <el-descriptions-item label="补充截图">
              点击图片可查看大图
              <div class="images2" v-viewer="{inline: false}" @click="show2">
                <img :src="pic" alt="img" v-for="pic in nowReport.finalpics" :key="pic" style="width: 100%">
              </div>
            </el-descriptions-item>
          </el-descriptions>
        </el-aside>
      </el-container>
    </el-container>
  </div>

</template>

<script>
import { getAdditionalDetail } from '../../api/additionalReport'
import { reportDetail } from '@/api/report'
import { getTestRecord } from '@/api/task'

export default {
  name: "additionalReportPage",
  data() {
    return {
      additionalReportId: this.$route.query.id,
      publisherID: 0,
      desc: '',
      piclist: [],
      picListStr: '',
      head: 'https://se3-1309398890.cos.ap-shanghai.myqcloud.com/upload/',
      finalpics: [],
      defectReportId: 0,
      brand: '',
      os: '',
      createTime: '',
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
    }
  },
  created() {
    var that = this
    getAdditionalDetail(window.localStorage.getItem('id'), this.$route.query.id)
      .then(function (res) {
        console.log(res)
        that.picListStr = res.data.data.screenshotPathList
        that.desc = res.data.data.description
        that.os = res.data.data.operatingSystem
        that.brand = res.data.data.deviceBrand
        that.createTime = res.data.data.createTime
        that.defectReportId = res.data.data.defectReportId
        that.publisherID = res.data.data.userId
        console.log(that.picListStr)
        that.piclist = JSON.parse(that.picListStr)
        var i = 0
        for (i; i < that.piclist.length; i++) {
          that.finalpics.push(that.head + that.piclist[i])
        }
        // console.log(that.finalpics)
        reportDetail(window.localStorage.getItem('id'), that.defectReportId)
          .then(function (res) {
            // console.log(res)
            that.nowReport.id = that.defectReportId
            that.nowReport.picListStr = res.data.data.screenshotPathList
            that.nowReport.desc = res.data.data.description
            that.nowReport.testRecordId = res.data.data.testRecordId
            that.nowReport.status = res.data.data.status
            that.nowReport.title = res.data.data.title
            that.nowReport.operatingSystem = res.data.data.operatingSystem
            that.nowReport.deviceBrand = res.data.data.deviceBrand
            that.nowReport.createTime = res.data.data.createTime

            console.log(that.nowReport.picListStr)
            that.nowReport.piclist = JSON.parse(that.nowReport.picListStr)
            var i = 0
            for (i; i < that.nowReport.piclist.length; i++) {
              that.nowReport.finalpics.push(that.nowReport.head + that.nowReport.piclist[i])
            }
            getTestRecord(window.localStorage.getItem('id'), that.nowReport.testRecordId).then(function (res) {
              // console.log(res)
              that.nowReport.taskId = res.data.data.taskId
              that.nowReport.userId = res.data.data.userId
              that.nowReport.commitTime = res.data.data.commitTime
            })
          })
      })
  },
  methods: {
    goToUserInfo(userId) {
      this.$router.push({path: "/userInfo", query: {userId: userId}})
    },
    show1() {
      // console.log("Hi")
      const viewer = this.$el.querySelector('.images1').$viewer
      viewer.show()
    },
    show2() {
      // console.log("Hi")
      const viewer = this.$el.querySelector('.images2').$viewer
      viewer.show()
    },
  }
}
</script>

<style scoped>

</style>
