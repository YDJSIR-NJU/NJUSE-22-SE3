<template>

  <div>
    <div class="title" style="">
      {{ title }}
    </div>
    <div style="margin-top: 1.5rem">
      <el-button class="" type="success" @click="setAllRead"><i class="el-icon-edit"></i>
        设置全部已读
      </el-button>
      &nbsp;
      <!--      <el-tag style="margin-bottom: 0.5rem;" type="success">-->
      <!--        - -->
      <!--      </el-tag>-->
      <!--      &nbsp;→&nbsp;-->
      <!--      <el-tag style="margin-bottom: 0.5rem;" type="primary">-->
      <!--        {{ numOfMsg }}-->
      <!--      </el-tag>-->
      <!--      &nbsp;-->
      <el-button ref="msgTypeChange" class="" type="primary" @click="handleMsgTypeChange"><i class="el-icon-date"></i>
        {{ changeShowTypeText }}
      </el-button>
    </div>
    <el-divider></el-divider>
    <!--    <el-row style="width: 60%; margin: 0 auto; text-align: left; padding-top: 1rem">-->
    <!--      <el-col :span="2">-->
    <!--        <img src="../../assets/warn.png"-->
    <!--             style="margin-bottom: 0.5rem;margin-right: 0.5rem" width="50px"-->
    <!--             @click="changeReadStatus(item)">-->
    <!--      </el-col>-->
    <!--      <el-col :span="3">-->
    <!--        <div style="margin: 0 auto; padding: 15px">表示未读消息</div>-->
    <!--      </el-col>-->
    <!--      <el-col :span="2">-->
    <!--        <img src="../../assets/round_check.png"-->
    <!--             style="margin-left: 0.5rem;margin-bottom: 0.5rem;margin-right: 0.5rem"-->
    <!--             width="50px">-->
    <!--      </el-col>-->
    <!--      <el-col :span="3">-->
    <!--        <div style="margin: 0 auto; padding: 15px">表示已读消息</div>-->
    <!--      </el-col>-->

    <!--      <el-col :span="3">-->

    <!--      </el-col>-->
    <!--    </el-row>-->
    <el-pagination
      :page-count='pagination.total'
      :page-sizes="[10]"
      :total='numOfMsg'
      layout="total, sizes, prev, pager, next, jumper"
      style="margin-top: 20px;"
      @current-change="handleCurrentChange"
    >
    </el-pagination>
    <div v-for="(item,index) in messageList">
      <el-card class="box-card" style="text-align: left">
        <span v-if="item.status==='NEW'">
        <img src="../../assets/warn.png"
             style=" float:left;margin-bottom: 20px;margin-right: 20px"
             width="75px" @click="changeReadStatus(item)">
        </span>
        <span v-else>
        <img src="../../assets/round_check.png"
             style=" float:left;margin-bottom: 20px;margin-right: 20px"
             width="75px" @click="changeReadStatus(item)">
        </span>

        <span class="messageText">
          <span style="font-size: 20px">
            <el-tag v-if="item.status==='NEW'" effect="dark" type="warning">未读</el-tag> {{ item.content }}<br>
        </span>
          <div style="margin-top: 0.5rem">
            <el-button v-if="item.hasTaskId && item.status==='NEW'" type="success"
                       @click="goToTask(item.taskid)">查看任务</el-button>
            <el-button v-if="item.hasTaskId && item.status==='READ'" plain type="success"
                       @click="goToTask(item.taskid)">查看任务</el-button>
            <el-button v-if="item.hasReportId && item.status==='NEW'" type="primary" @click="goToReport(item.reportid)">查看报告</el-button>
            <el-button v-if="item.hasReportId && item.status==='READ'" plain type="primary"
                       @click="goToReport(item.reportid)">查看报告</el-button>
            <el-date-picker
              v-model="item.time"
              disabled
              format="yyyy年MM月dd hh:mm"
              placeholder="选择日期"
              style="float: right"
              type="date"
              value-format="yyyy-MM-dd hh:mm:ss">
              </el-date-picker>
          </div>

      </span>
      </el-card>
    </div>
    <div style="padding: 3rem"></div>
    <COLLECTFooter></COLLECTFooter>
  </div>

</template>

<script>
import { getAllMessages, getUnRead, setAllRead, setRead } from '../../api/message'
import COLLECTFooter from '../../components/Footer'

export default {
  name: 'messages',
  components: { COLLECTFooter },
  props: {
    item: {}
  },
  created () {

  },
  mounted () {
    // this.fetchData(1, this.showType)
    this.fetchData(1, this.showType)
    // console.log(typeof this.fetchData)
  },
  data () {
    return {
      changeShowTypeText: '只看未读信息',
      showType: 'ALL',
      hasReportId: false,
      hasTaskId: false,
      reportid: -1,
      taskid: -1,
      messageList: [],
      pagination: {
        total: 0,
        pageSize: 10
      },
      pageNumber: 1,
      numOfMsg: 0,
      title: '全部消息'
    }
  },
  methods: {
    goToTask (taskid) {
      this.$router.push({
        path: '/taskInfo',
        query: { id: taskid }
      })
    },

    goToReport (reportid) {
      this.$router.push({
        path: '/reportPage',
        query: { id: reportid }
      })
    },

    handleMessageResponse (response) {
      var that = this
      that.numOfMsg = response.data.total
      that.messageList = response.data.list
      that.pagination.total = response.data.pages
      that.messageList.forEach(item => {
        // console.log(item)
        var taskids = item.content.match(/任务[0-9]+/g)
        if (taskids != null && taskids.length > 0) {
          item.hasTaskId = true
          item.taskid = taskids[0].replace('任务', '')
          console.log(item.taskid)
        } else {
          item.hasTaskId = false
        }
        var reportids = item.content.match(/[0-9]+号报告/g)
        if (reportids != null && reportids.length > 0) {
          item.hasReportId = true
          item.reportid = reportids[0].replace('号报告', '')
          // console.log(item.reportId)
        } else {
          reportids = item.content.match(/报告编号：[0-9]+/g)
          if (reportids != null && reportids.length > 0) {
            item.hasReportId = true
            item.reportid = reportids[0].replace('报告编号：', '')
            // console.log(item.reportId)
          } else {
          }
        }
      })
    },

    fetchData (page, type) {
      var that = this
      const id = window.localStorage.id
      // console.log(id)
      console.log(type)
      if (type == 'ALL') {
        getAllMessages(id, page).then(response => {
          // console.log(response)
          that.handleMessageResponse(response)
        })
      } else if (type === 'NEW') {
        getUnRead(id, page).then(response => {
          // console.log(response)
          that.handleMessageResponse(response)
        })

      }
    },

    handleMsgTypeChange () {
      if (this.showType === 'ALL') {
        this.showType = 'NEW'
        this.title = '未读消息'
        this.changeShowTypeText = '查看所有消息'
      } else if (this.showType === 'NEW') {
        this.showType = 'ALL'
        this.title = '全部消息'
        this.changeShowTypeText = '只看未读消息'
      }
      this.fetchData(this.pageNumber, this.showType)
    },

    handleCurrentChange (currentPage) {
      // console.log(this.pagination.total)
      this.pageNumber = currentPage
      // console.log(this.pageNumber)
      // console.log(currentPage)
      this.fetchData(currentPage, this.showType)
    },
    changeReadStatus (item) {
      const that = this
      setRead(item.id, item.userId).then(response => {
        // console.log(response)
        if (response.data.code === 1) {
          item.status = 'READ'
          // that.messageList = response.data.list
          // that.pagination.total = response.data.pages
          this.$message.success('设置已读成功')
        }
      })
    },
    setAllRead () {
      setAllRead(window.localStorage.getItem('id')).then(response => {
        if (response.data.code === 1) {
          this.$message.success('设置已读成功')
        }
        this.fetchData(this.pageNumber, this.showType)
      })

    }
  },
}
</script>

<style scoped>
.title {
  font-weight: 500;
  font-size: 3rem;
  padding: 3rem;
  text-align: center;
  background-image: linear-gradient(-225deg, #5dFFc7 0%, #9b9aFF 100%);
  color: #2d2d2d;
}

.box-card {
  margin-left: 20%;
  margin-right: 20%;
  margin-top: 20px;
}

.messageText {
  margin: auto;
}

</style>
