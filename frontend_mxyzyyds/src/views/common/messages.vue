<template>

  <div>
    <el-row style="width: 60%; margin: 0 auto; text-align: left; padding-top: 1rem">
      <el-col :span="2">
        <img src="../../assets/warn.png"
             style="margin-bottom: 0.5rem;margin-right: 0.5rem" width="50px"
             @click="changeReadStatus(item)">
      </el-col>
      <el-col :span="3">
        <div style="margin: 0 auto; padding: 15px">表示未读消息</div>
      </el-col>
      <el-col :span="2">
        <img src="../../assets/round_check.png"
             style="margin-left: 0.5rem;margin-bottom: 0.5rem;margin-right: 0.5rem"
             width="50px">
      </el-col>
      <el-col :span="3">
        <div style="margin: 0 auto; padding: 15px">表示已读消息</div>
      </el-col>
      <el-col :span="6">
        <div style="margin: 0 auto; padding: 15px">点击未读图标可以将其置为已读</div>
      </el-col>
    </el-row>
    <div v-for="(item,index) in messageList">
      <el-card class="box-card" style="text-align: left">
        <span v-if="item.status=='NEW'">
        <img src="../../assets/warn.png" style=" float:left;margin-left: 20px;margin-bottom: 20px;margin-right: 20px"
             width="50px" @click="changeReadStatus(item)">
        </span>
        <span v-else>
        <img src="../../assets/round_check.png"
             style=" float:left;margin-left: 20px;margin-bottom: 20px;margin-right: 20px"
             width="50px" @click="changeReadStatus(item)">
        </span>

        <span class="messageText">
          <span style="font-size: 20px">{{ item.content }}<br>
        </span>
          <div style="margin-top: 0.5rem">
            <el-button v-if="item.hasTaskId" type="primary" @click="goToTask(item.taskid)">查看任务</el-button>
            <el-button v-if="item.hasReportId" plain type="primary" @click="goToReport(item.reportid)">查看报告</el-button>
            <span style="float: right; margin: 0 auto; padding-top: 0.5rem">{{ item.time }}</span>
          </div>

      </span>
      </el-card>
    </div>


    <el-pagination
      :page-count='pagination.total'
      layout="prev, pager, next, jumper"
      style="margin-top: 20px;"
      @current-change="handleCurrentChange"
    >
    </el-pagination>

  </div>

</template>

<script>
import {getAllMessages, setRead} from "../../api/message";

export default {
  name: "messages",
  props: {
    item: {}
  },
  created() {
    //获取消息
    this.fetchData(1)

  },
  data() {
    return {
      hasReportId: false,
      hasTaskId: false,
      reportid: -1,
      taskid: -1,
      messageList: [],
      pagination: {
        total: 0,
        pageSize: 10
      },
      pageNumber: 1
    }
  },
  methods: {
    goToTask(taskid) {
      this.$router.push({path: '/taskInfo', query: {id: taskid}})
    },

    goToReport(reportid) {
      this.$router.push({path: '/reportPage', query: {id: reportid}})
    },

    fetchData(page) {
      var that = this
      const id = window.localStorage.id
      console.log(id)

      getAllMessages(id, page).then(response => {
        // console.log(response)
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
            console.log(item.reportId)
          } else {
            reportids = item.content.match(/报告编号：[0-9]+/g)
            if (reportids != null && reportids.length > 0) {
              item.hasReportId = true
              item.reportid = reportids[0].replace('报告编号：', '')
              console.log(item.reportId)
            } else {
            }
          }
        })
      })
    },
    handleCurrentChange(currentPage) {
      //console.log(this.pagination.total)
      this.pageNumber = currentPage
      //console.log(this.pageNumber)
      //console.log(currentPage)
      this.fetchData(currentPage)
    },
    changeReadStatus(item) {
      const that = this
      setRead(item.id, item.userId).then(response => {
        // console.log(response)
        if (response.data.code === 1) {
          item.status = 'READ'
          // that.messageList = response.data.list
          // that.pagination.total = response.data.pages
          this.$message.success("设置已读成功")
        }
      })
    }

  }


}
</script>

<style scoped>
.box-card {
  margin-left: 20%;
  margin-right: 20%;
  margin-top: 20px;
}

.messageText {
  margin: auto;
}


</style>
