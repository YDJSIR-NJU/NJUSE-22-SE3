<template>
  <div class="wrapper">
    <div class="a">
      <h1>{{ pageTitle }}
      </h1>
    </div>
    <report-word-cloud :taskId='taskid' style="width: 70%;margin-left: 15%"></report-word-cloud>
    <div class="app-container">
      <el-table :data="tableData"
                class="table"
                stripe
                style="width: 70%"
                @row-click="handle">
        <el-table-column
          label="报告ID"
          prop="id"
          width="180">
        </el-table-column>
        <el-table-column
          label="测试记录ID"
          prop="testRecordId"
          width="180">
        </el-table-column>
        <el-table-column
          label="设备类型"
          prop="deviceBrand"
          width="180">
        </el-table-column>
        <el-table-column
          label="测试系统"
          prop="operatingSystem"
          width="180">
        </el-table-column>
        <el-table-column
          label="报告描述"
          prop="description"
          width="180">
        </el-table-column>
        <el-table-column
          label="报告状态"
          prop="status">
        </el-table-column>
        <el-table-column
          label="操作">
          <el-button type="primary">查看</el-button>
        </el-table-column>
      </el-table>
      <el-pagination
        :page-count='pagination.total'
        layout="prev, pager, next, jumper"
        @current-change="handleCurrentChange"
      >
      </el-pagination>
    </div>

  </div>
</template>
<script>
//import { getList } from '@/api/table'
import {reportList} from '@/api/report'
import ReportWordCloud from "../../components/reportWordCloud";

export default {

  components: {ReportWordCloud},
  filters: {
    statusFilter(status) {
      const statusMap = {
        published: 'success',
        draft: 'gray',
        deleted: 'danger'
      }
      return statusMap[status]
    }
  },
  data() {
    return {
      taskid:this.$route.query.id,
      pageTitle: "",
      tableData: [],
      //listLoading: true
      pagination: {
        total: 0,
        pageSize: 10
      },
      pageNumber: 1
    }
  },
  beforeCreate() {


  },
  created() {
    console.log(this.taskid)
    this.fetchData(1)
  },
  methods: {
    fetchData(page) {
      var that = this
      //this.listLoading = false
      const id = window.localStorage.getItem('id')
      const queryID = this.$route.query.id

      this.$data.pageTitle = "测试任务" + this.$route.query.id + "的测试报告列表"
      reportList(id, page, queryID).then(response => {
        console.log(response)
        that.tableData = response.data.list
        that.pagination.total = response.data.pages
      })
    },
    // add click method
    onClick(index) {
      console.log(index)
      this.$router.push({path: '/reportPage', query: {id: index}})
    },
    handle(row, event, column) {
      console.log(row.id)
      this.$router.push({path: '/reportPage', query: {id: row.id}})
    },
    handleCurrentChange(currentPage) {
      console.log(this.pagination.total)
      this.pageNumber = currentPage
      console.log(this.pageNumber)
      console.log(currentPage)
      this.fetchData(currentPage)
    }
  }
}
</script>
<style scoped>
.a {
  margin-left: 20px;
}

.wrapper {
  margin: 0 auto;
  text-align: center
}

.table {
  margin: 0 auto;
}
</style>
