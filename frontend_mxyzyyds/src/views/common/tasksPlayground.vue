<template>
  <div style="width: 100%; height: 100%; ">
    <div class="b">
      <div class="d">任务广场</div>
    </div>
    <div class="wrapper">
      <h3>此处展示的是未招募满、未关闭且未结束的任务。</h3>
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
    <COLLECTFooter></COLLECTFooter>
  </div>
</template>
<script>
import { taskPlayground } from '@/api/task'
import COLLECTFooter from '../../components/Footer'

export default {
  name: "asd",
  components: { COLLECTFooter },
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
    // add click method
    onClick(index) {
      console.log(index)
      this.$router.push({path: '/taskInfo', query: {id: index}})
    },
    handle(row, event, column) {
      console.log(row.id)
      this.$router.push({path: '/taskInfo', query: {id: row.id}})
    },
    fetchData(page) {
      var that = this
      taskPlayground(window.localStorage.id, page).then(
        function (response) {
          console.log(response)
          that.tableData = response.data.list
          that.numOfTasks = response.data.total
          that.pagination.total = response.data.pages
        }
      )
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
  background-image: linear-gradient(120deg, #c3d8fc 0%, #ecfae3 100%);
  padding: 2rem;
  margin-bottom: 3rem;
}


.b {
  padding: 3rem;
  background-image: linear-gradient(120deg, #00d8fc 0%, #ecfa00 100%);
  margin-bottom: 3rem;
}

.d {
  font-size: 3rem;
  color: #2d2d2d;
}

.wrapper {
  margin: 0 auto;
  text-align: center;
  margin-top: -30px;
  width: 100%;
  height: fit-content;
}

.table {
  margin: 0 auto;
}
</style>
