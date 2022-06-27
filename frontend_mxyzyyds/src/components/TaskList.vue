<template>
  <div class="wrap">
    <el-table
      :data="tableData"
      :header-cell-style="{'text-align':'center'}"
      style="width: 100%">
      <el-table-column
        align="center"
        label="发布时间"
        width="180px"
      >
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.date }}</span>
        </template>
      </el-table-column>
      <el-table-column
        align="left"
        label="任务标题"
      >
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.context }}</span>
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        label="发布人"
        width="180">
        <template slot-scope="scope">
          <div slot="reference" class="name-wrapper">
            <el-tag size="medium">{{ scope.row.name }}</el-tag>
          </div>
        </template>
      </el-table-column>
      <el-table-column align="center" label="操作" width="200px">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="primary"
            @click="handleInfo(scope.$index, scope.row)">详情
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  data() {
    return {
      tableData: [{
        date: '2016-05-02',
        name: 'zby',
        context: '测试1:合法的看反抗拉萨的能力撒看见饭店门口了奥积分卡计划的卡拉就开始才能简单看到就立刻擦抹',
        address: '上海市普陀区金沙江路 1518 弄'
      }, {
        date: '2016-05-04',
        name: 'zby',
        context: '测试2',
        address: '上海市普陀区金沙江路 1517 弄'
      }, {
        date: '2016-05-01',
        name: 'zby',
        context: '测试3',
        address: '上海市普陀区金沙江路 1519 弄'
      }]
    }
  },
  methods: {
    handleInfo(index, row) {
      console.log(index, row);
    }
  }
}
</script>
<style>
.wrap {
  margin-left: 20%;
  margin-right: 20%;
}
</style>
