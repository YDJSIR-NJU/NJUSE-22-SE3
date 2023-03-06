<template>
  <div style="margin: 0.5rem">
    <el-card class="partInfo">
      <span style="border-left: 4px solid #39a9ff; padding-left: 8px; width: 100%; font-size: large">用户基础信息</span>
      <el-descriptions :column="10" border direction="vertical"
                       style="width: 100%;margin: 0 auto; font-size: 1rem; margin-top: 1rem;">
        <el-descriptions-item :span="2" label="用户名">{{ userInfo.uname }}</el-descriptions-item>
        <el-descriptions-item :span="2" label="注册邮箱">
          {{ userInfo.email }}
        </el-descriptions-item>
        <el-descriptions-item :span="2" label="注册时间">{{ userInfo.createTime }}</el-descriptions-item>
        <el-descriptions-item :span="2" label="任务偏好"><el-tag>{{ userInfo.preferTypes }}</el-tag> </el-descriptions-item>
        <!--        <el-descriptions-item :span="2" label="用户角色">{{ userInfo.userRole }}</el-descriptions-item>-->
        <!--        <el-descriptions-item :span="2" label="用户等级">{{ userInfo.level }}</el-descriptions-item>-->
        <el-descriptions-item :span="1" label="用户积分">{{ userInfo.credit }}</el-descriptions-item>
      </el-descriptions>
    </el-card>

    <el-row v-if="userInfo.userRole==='WORKER'">
      <el-col :span="10">
        <el-card class="partInfo">
          <span style="border-left: 4px solid #39a9ff; padding-left: 8px; width: 100%; font-size: large">众包工人能力评价</span>
          <el-descriptions :column="6" border direction="vertical"
                           style="width: 100%;margin: 0 auto; font-size: 1rem; margin-top: 1rem;">
            <el-descriptions-item :span="3" label="平均报告得分">{{ capabilities.averageRemark }}</el-descriptions-item>
            <el-descriptions-item :span="3" label="审查打分偏差">{{ capabilities.censorshipAbility }}</el-descriptions-item>
            <el-descriptions-item :span="6" label="平均协作次数">{{ capabilities.collaboration }}</el-descriptions-item>

            <el-descriptions-item :span="2" label="报告被拒比率">{{
                capabilities.reportRepeatability
              }}%
            </el-descriptions-item>
            <el-descriptions-item :span="2" label="报告BUG重复率">{{ capabilities.duplicateBugPer }}%</el-descriptions-item>
            <el-descriptions-item :span="2" label="总BUG发现数">{{ capabilities.totalBugNums }}</el-descriptions-item>

            <!--            <el-descriptions-item :span="2" label="最近一次接受任务的时间">{{ activeness.lasttasktime }}</el-descriptions-item>-->
            <!--            <el-descriptions-item :span="2" label="周接受任务数">{{ activeness.numtasksWeek }}</el-descriptions-item>-->
            <!--            <el-descriptions-item :span="2" label="月接受任务数">{{ activeness.numtasksMonth }}</el-descriptions-item>-->

          </el-descriptions>
        </el-card>
      </el-col>

      <el-col :span="14">
        <el-card class="partInfo">
          <span
            style="border-left: 4px solid #39a9ff; padding-left: 8px; width: 100%; font-size: large">众包工人活跃度情况</span>
          <el-descriptions :column="6" border direction="vertical"
                           style="width: 100%;margin: 0 auto; font-size: 1rem; margin-top: 1rem;">
            <el-descriptions-item :span="2" label="最近一次发现BUG的时间">{{ activeness.lastbugtime }}</el-descriptions-item>
            <el-descriptions-item :span="2" label="周发现BUG数">{{ activeness.numbugsWeek }}</el-descriptions-item>
            <el-descriptions-item :span="2" label="月发现BUG数">{{ activeness.numbugsMonth }}</el-descriptions-item>

            <el-descriptions-item :span="2" label="最近一次提交报告的时间">{{ activeness.lastreporttime }}</el-descriptions-item>
            <el-descriptions-item :span="2" label="周提交报告数">{{ activeness.numreportsWeek }}</el-descriptions-item>
            <el-descriptions-item :span="2" label="月提交报告数">{{ activeness.numreportsMonth }}</el-descriptions-item>

            <el-descriptions-item :span="2" label="最近一次接受任务的时间">{{ activeness.lasttasktime }}</el-descriptions-item>
            <el-descriptions-item :span="2" label="周接受任务数">{{ activeness.numtasksWeek }}</el-descriptions-item>
            <el-descriptions-item :span="2" label="月接受任务数">{{ activeness.numtasksMonth }}</el-descriptions-item>

          </el-descriptions>
        </el-card>
      </el-col>

    </el-row>

    <el-row style="height: 15rem" v-if="userInfo.userRole==='WORKER'">
      <el-col :span="8">
        <el-card class="partInfo" style="text-align: left">
          <span
            style="border-left: 4px solid #39a9ff; padding-left: 8px; width: 100%; font-size: large">众包工人报告能力词云</span>
          <div style="display: inline-block;height:15rem;width:100%; margin-top: 1rem">
            <div ref="wordcloud" class="wordCloud"
                 style="height:100%;width:100%; text-align: center"></div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card class="partInfo" style="text-align: left">
          <span
            style="border-left: 4px solid #39a9ff; padding-left: 8px; width: 100%; font-size: large">已完成任务类型比例</span>
          <div style="display: inline-block;height:15rem;width:100%; margin-top: 1rem">
            <div id="taskTypeRatio" ref="taskTypeRatio" style="height:100%;width:100%; text-align: center"></div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card class="partInfo" style="text-align: left">
          <span
            style="border-left: 4px solid #39a9ff; padding-left: 8px; width: 100%; font-size: large">已发布报告设备比例</span>
          <div style="display: inline-block;height:15rem;width:100%; margin-top: 1rem">
            <div id="reportOSRatio" ref="reportOSRatio" style="height:100%;width:100%; text-align: center"></div>
          </div>
        </el-card>
      </el-col>
    </el-row>


  </div>
</template>

<script>
import { getFinishedTaskSum, getReportDevicesSum, getUserCapabilitiesDetail } from '@/api/userCapabilities'
import { getActivenessDetail } from '@/api/userActiveness'
import { getUser } from '@/api/user'
import { getUserWords } from '@/api/report'
import * as echarts from 'echarts'
import 'echarts-wordcloud/dist/echarts-wordcloud'
import 'echarts-wordcloud/dist/echarts-wordcloud.min'

export default {
  name: "workerInfo",
  //该页面用于用户能力展示
  data() {
    return {

      role: 'WORKER',
      userInfo: {
        id: 0,
        createTime: '',
        credit: 0,
        email: '',
        level: 0,
        preferTypes: '',
        uname: '',
        userRole: '',
      },
      taskTypeStatistic: [],
      deviceStatistic: [],
      capabilities: {
        averageRemark: 0,
        censorshipAbility: 0.0,
        collaboration: 0.0,
        duplicateBugPer: 0,
        professionalAbility: 1,
        reportRepeatability: 0.0,
        totalBugNums: 0
      },
      activeness: {
        lastbugtime: '',
        lastreporttime: '',
        lasttasktime: '',
        numbugsMonth: 0,
        numbugsWeek: 0,
        numreportsMonth: 0,
        numreportsWeek: 0,
        numtasksMonth: 0,
        numtasksWeek: 0
      },
      wordList: [],
    }
  },
  mounted() {
    this.initData()
    // this.drawChart()
  },
  methods: {
    round2percent(num) {
      num = Math.round(num * Math.pow(10, 4)) / Math.pow(10, 4)
      num = Number(num * 100).toFixed(2)
      return num
    },
    round2(num) {
      num = Math.round(num * Math.pow(10, 4)) / Math.pow(10, 4)
      num = Number(num).toFixed(2)
      return num
    },
    initData() {
      const that = this
      getUser(this.userId).then(res => {
        console.log("User")
        // console.log(res)
        that.userInfo = res.data.data
        if (that.userInfo.userRole === 'WORKER') {
          getUserCapabilitiesDetail(this.userId).then(res => {
            console.log("User Capabilities")
            // console.log(res)
            that.capabilities = res.data.data
            that.capabilities.censorshipAbility = that.round2(that.capabilities.censorshipAbility)
            that.capabilities.averageRemark = that.round2(that.capabilities.averageRemark)
            that.capabilities.collaboration = that.round2(that.capabilities.collaboration)
            that.capabilities.reportRepeatability = that.round2percent(that.capabilities.reportRepeatability)
            that.capabilities.duplicateBugPer = that.round2percent(that.capabilities.duplicateBugPer)
          })
          getReportDevicesSum(this.userId).then(res => {
            console.log("Report Devices Sum")
            // console.log(res)
            var typeStatistic = res.data.data
            // console.log(wordlist)
            for (let key in typeStatistic) {
              // console.log(key) // name
              // console.log(wordlist[key]) // value
              if (key.charAt(0) < 'a') {
                that.deviceStatistic.push({'name': key, 'value': parseInt(this.round2percent(typeStatistic[key]))})
              }
            }
            console.log(that.deviceStatistic)
            that.drawPieChartDeviceOS()
          })
          getFinishedTaskSum(this.userId).then(res => {
            console.log("Finished Task Sum")
            // console.log(res)
            var typeStatistic = res.data.data
            // console.log(wordlist)
            for (let key in typeStatistic) {
              // console.log(key) // name
              // console.log(wordlist[key]) // value
              if (key.charAt(0) < 'a') {
                that.taskTypeStatistic.push({'name': key, 'value': parseInt(this.round2percent(typeStatistic[key]))})
              }
            }
            // console.log(that.taskTypeStatistic)
            that.drawPieChartTaskType()
          })
          getActivenessDetail(this.userId).then(res => {
            console.log("Activeness")
            // console.log(res)
            that.activeness = res.data.data
          })
          getUserWords(this.userId, 1, 20).then(res => {
            var wordlist = res.data.data[0].wordcloud
            // console.log(wordlist)
            for (let key in wordlist) {
              // console.log(key) // name
              // console.log(wordlist[key]) // value
              that.wordList.push({'name': key, 'value': parseInt(this.round2percent(wordlist[key]))})
            }
            // console.log(that.wordList)
            that.initWordCloud()
          })
        }
      })

    },
    drawPieChartTaskType() {
      const taskTypeRatio = echarts.init(this.$refs.taskTypeRatio)
      const options = {
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        legend: {
          top: '5%',
          left: 'center'
        },
        series: [
          {
            name: '占比',
            type: 'pie',
            radius: ['30%', '50%'],
            avoidLabelOverlap: true,
            itemStyle: {
              borderRadius: 10,
              borderColor: '#fff',
              borderWidth: 2
            },
            label: {
              normal: {
                show: false,
                formatter: '{b} {d}%'
              }
            },
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            },
            labelLine: {
              normal: {
                top: '1%',
                show: true
              }
            },
            data: this.taskTypeStatistic
          }
        ]
      }
      taskTypeRatio.setOption(options)
      console.log("DONE")
    },
    drawPieChartDeviceOS() {
      const taskTypeRatio = echarts.init(this.$refs.reportOSRatio)
      const options = {
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        legend: {
          top: '5%',
          left: 'center'
        },
        series: [
          {
            name: '占比',
            type: 'pie',
            radius: ['30%', '50%'],
            avoidLabelOverlap: true,
            itemStyle: {
              borderRadius: 10,
              borderColor: '#fff',
              borderWidth: 2
            },
            label: {
              normal: {
                show: false,
                formatter: '{b} {d}%'
              }
            },
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            },
            labelLine: {
              normal: {
                height: '2rem',
                show: true
              }
            },
            data: this.deviceStatistic
          }
        ]
      }
      taskTypeRatio.setOption(options)
      console.log("DONE")
    },
    initWordCloud() {
      let myChart = echarts.init(this.$refs.wordcloud);
      // console.log(this.wordList)
      myChart.setOption({
        series: [
          {
            type: "wordCloud",
            gridSize: 10,
            sizeRange: [14, 25],
            rotationRange: [0, 0],
            textStyle: {
              color: function () {
                return (
                  "rgb(" +
                  Math.round(Math.random() * 210) +
                  ", " +
                  Math.round(Math.random() * 210) +
                  ", " +
                  Math.round(Math.random() * 210) +
                  ")"
                );
              },
              emphasis: {
                shadowBlur: 10,
                shadowColor: '#333'
              }
            },
            backgroundColor: 'rgba(105,105,105,.0)',
            //位置相关设置
            left: "center",
            top: "center",
            right: null,
            bottom: null,
            width: "200%",
            height: "200%",
            //数据
            data: this.wordList
          }
        ]
      })
    }
  },
  props: {
    userId: {
      type: Number,
      default: 0
    }
  }
}
</script>

<style scoped>
.wordCloud {
  height: 10rem;
}

.partInfo {
  margin: 0.5rem;
  text-align: left;
}
</style>
