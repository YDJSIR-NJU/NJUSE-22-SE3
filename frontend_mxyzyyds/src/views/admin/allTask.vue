<template>
  <div class="wrapper">
    <div class="title">
      所有任务
    </div>

    <div class="app-container">
      <h3>此处展示的是本系统中有记录的所有任务。</h3>
      <el-pagination
        :total='numOfTasks'
        :page-count='pagination.total'
        :page-sizes="[10]"
        layout="total, sizes, prev, pager, next, jumper"
        @current-change="handleCurrentChange"
      >
      </el-pagination>
      <el-table :data="tableData"
                class="table"
                stripe
                style="width: 80%"
                @row-click="handle">
        <el-table-column
          label="任务ID"
          prop="id"
          width="90"
          align="center">
        </el-table-column>
        <el-table-column
          label="任务描述"
          prop="taskDiscribe"
        >
        </el-table-column>
        <el-table-column
          label="人数"
          prop="totalNum"
          width="60"
          align="center">
        </el-table-column>
        <el-table-column
          label="类型"
          width="150"
          align="center"
        >
          <template slot-scope="scope">
            <el-tag>{{scope.row.type}}</el-tag>
          </template>
        </el-table-column>
        <el-table-column
          label="开始时间"
          width="240"
          align="center"
        >
          <template slot-scope="scope">
            <div style="text-align: center">
              <el-date-picker
                v-model="scope.row.startTime"
                format="yyyy年MM月dd hh:mm"
                placeholder="选择日期"
                type="date"
                disabled

                value-format="yyyy-MM-dd hh:mm:ss">
              </el-date-picker>
              <i class="el-icon-bottom" style="padding: 10px"></i>
              <el-date-picker
                v-model="scope.row.finishTime"
                format="yyyy年MM月dd hh:mm"
                placeholder="选择日期"
                type="date"
                value-format="yyyy-MM-dd hh:mm:ss">
              </el-date-picker>
            </div>

          </template>
        </el-table-column>
        <el-table-column
          label="操作"
          width="90"
          align="center"
        >
          <el-button type="primary">查看</el-button>
        </el-table-column>
      </el-table>
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
      numOfTasks: 0,
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
        that.numOfTasks = response.data.total
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
.title {
  font-weight: 500;
  font-size: 3rem;
  padding: 3rem;
  text-align: center;
  background-image: linear-gradient(-225deg, #5dFFc7 0%, #9b9aFF 100%);
  color: #2d2d2d;
}

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
