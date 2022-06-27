<template>
  <div class="wrapper">
    <div class="a">
      <h1>所有任务
      </h1>
    </div>
    <div class="app-container">
      <el-table :data="tableData"
                class="table"
                stripe
                style="width: 70%"
                @row-click="handle">
        <el-table-column
          label="id"
          prop="id"
          width="180">
        </el-table-column>
        <el-table-column
          label="任务描述"
          prop="taskDiscribe">
        </el-table-column>
        <el-table-column
          label="人数"
          prop="totalNum"
          width="180">
        </el-table-column>
        <el-table-column
          label="类型"
          prop="type">
        </el-table-column>
        <el-table-column
          label="开始时间"
          prop="startTime">
        </el-table-column>
        <el-table-column
          label="截至时间"
          prop="finishTime">
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
import {allTask} from '@/api/task'

export default {
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
      tableData: [],
      //listLoading: true
      pagination: {
        total: 0,
        pageSize: 10
      },
      pageNumber: 1
    }
  },
  created() {
    this.fetchData(1)
  },
  methods: {
    fetchData(page) {
      var that = this
      const id = window.localStorage.id
      console.log(id)
      allTask(id, page).then(response => {
        console.log(response)
        that.tableData = response.data.list
        that.pagination.total = response.data.pages
      })
    },
    // add click method
    onClick(index) {
      console.log(index)
      this.$router.push({path: '/taskInfo', query: {id: index}})
    },
    handle(row, event, column) {
      console.log(row.id)
      this.$router.push({path: '/taskInfo', query: {id: row.id}})
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
