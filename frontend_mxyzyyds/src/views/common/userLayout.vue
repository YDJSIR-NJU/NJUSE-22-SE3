<template>
  <div>
    <div class="head">
    </div>
    <div class="title" style="">
      {{ Hello }}，
      <span v-if="msg==='WORKER'" style="color: #E0FFFF">{{ name }}</span>
      <span v-if="msg==='PUBLISHER'" style="color: #98FB98">{{ name }}</span>
      <span v-if="msg==='ADMINISTRATOR'" style="color: #DC143C">{{ name }}</span>
      ！
    </div>

    <div style="margin: 0 auto; width: 100%; padding-top: 1.5rem">
      <div v-if="msg==='PUBLISHER'">

        &nbsp;
        <el-button class="" type="success" @click="addTask"><i class="el-icon-edit"></i>
          发布新测试任务
        </el-button>
        &nbsp;
        <el-tag style="margin-bottom: 0.5rem;" type="success">
          +
        </el-tag>
        &nbsp;→&nbsp;
        <el-tag style="margin-bottom: 0.5rem;" type="primary">
          {{ numOfTasks }}
        </el-tag>
        &nbsp;
        <el-button class="" type="primary" @click="mytask"><i class="el-icon-date"></i>
          查看已发布任务
        </el-button>
        &nbsp;
      </div>

      <div v-if="msg==='WORKER'">

        <el-button class="" type="success" @click="seeDoneTask"><i class="el-icon-check"></i>
          查看已完成任务
        </el-button>
        &nbsp;
        <el-tag style="margin-bottom: 0.5rem;" type="success">
          {{ historyNum }}
        </el-tag>
        &nbsp;
        :
        &nbsp;
        <el-tag style="margin-bottom: 0.5rem;" type="primary">
          {{ processingNum }}
        </el-tag>
        &nbsp;
        <el-button class="" type="primary" @click="seeOnTask"><i class="el-icon-more"></i>
          查看执行中任务
        </el-button>

      </div>

      <div v-if="msg==='ADMINISTRATOR'">
        <el-button class="" type="success" @click="triggerActivenessUpdate"><i
          class="el-icon-edit"></i>
          更新活跃度数据
        </el-button>
        <el-button class="" type="primary" @click="seeAllTask"><i class="el-icon-edit"></i>
          查看所有的任务
        </el-button>

      </div>

      <el-divider></el-divider>
    </div>

    <el-container v-if="msg==='WORKER'" style="width: 95%; margin: 0 auto">
      <el-aside width="40%">
        <h2 style="width: 90%; text-align: left">管理我的设备</h2>
        <el-card style="width: 90%; margin-top: 1rem; margin-bottom: 1rem;">
          <i class="el-icon-circle-plus-outline" style="font-size: 1.5rem; " title="点击新增设备"
             @click="handleAddDevice()"></i>
        </el-card>
        <el-card v-for="(device,index) in myDevices" v-bind:key="index"
                 style="width: 90%; margin-top: 0.5rem; margin-bottom: 0.5rem;text-align: left">
          设备品牌
          <el-tag style="margin-left: 0.5rem; margin-right: 0.5rem; width: 4rem; text-align: center">
            {{ device.deviceBrand }}
          </el-tag>
          操作系统
          <el-tag style="margin-left: 0.5rem; margin-right: 0.5rem; width: 5rem; text-align: center">
            {{ device.operatingSystem }}
          </el-tag>
          <el-button size="mini" style="float: right; " type="danger" @click="handleDeleteDevice(device.id)">删除
          </el-button>
          <el-button size="mini" style="float: right; margin-right: 1rem" type="primary"
                     @click="handleClickchange(device.deviceBrand,device.operatingSystem,device.id)">修改
          </el-button>
        </el-card>
      </el-aside>

      <el-container style="width: 100%">
        <el-aside width="100%" style="">
          <h2 style="width: 90%; text-align: left">我的信息</h2>
          <WorkerInfo :userId="uid"></WorkerInfo>
        </el-aside>
      </el-container>

    </el-container>

    <el-container v-if="msg==='PUBLISHER'" style="width: 95%; margin: 0 auto">

      <el-aside width="50%">
        <h2 style="width: 90%; text-align: left">已发布任务</h2>
        <el-pagination
          :total='numOfTasks'
          :page-count='pagination.total'
          :page-sizes="[10]"
          layout="total, sizes, prev, pager, next, jumper"
          @current-change="handleCurrentChange"
        ></el-pagination>
        <el-table :data="tableData"
                  class="table"
                  stripe
                  style="width: 95%"
                  @row-click="handle"
        >
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
      </el-aside>

      <el-container style="width: 100%">
        <el-aside width="100%" style="">
          <h2 style="width: 90%; text-align: left">我的信息</h2>
          <WorkerInfo :userId="uid"></WorkerInfo>
        </el-aside>
      </el-container>

    </el-container>

    <el-dialog
      title="新增设备"
      :visible.sync="addDeviceDialogVisible"
      width="50%">
      <el-form ref="form" :model="form" class="tmp" label-width="100px">
        <el-form-item class="e" label="设备/系统" required>
          <el-select v-model="form.device" placeholder="请选择测试设备">
            <el-option label="LENOVO" value="LENOVO"></el-option>
            <el-option label="DELL" value="DELL"></el-option>
            <el-option label="HP" value="HP"></el-option>
            <el-option label="ASUS" value="ASUS"></el-option>
            <el-option label="GIGABYTE" value="GIGABYTE"></el-option>
            <el-option label="MSI" value="MSI"></el-option>
            <el-option label="HUAWEI" value="HUAWEI"></el-option>
            <el-option label="XIAOMI" value="XIAOMI"></el-option>
            <el-option label="SAMSUNG" value="SAMSUNG"></el-option>
            <el-option label="IPHONE" value="IPHONE"></el-option>
            <el-option label="OPPO" value="OPPO"></el-option>
            <el-option label="VIVO" value="VIVO"></el-option>
            <el-option label="ONEPLUS" value="ONEPLUS"></el-option>
            <el-option label="IQOO" value="IQOO"></el-option>
            <el-option label="REALME" value="REALME"></el-option>
            <el-option label="PIXEL" value="PIXEL"></el-option>
            <el-option label="LG" value="LG"></el-option>
            <el-option label="NOKIA" value="NOKIA"></el-option>
            <el-option label="OTHER" value="OTHER"></el-option>
          </el-select>
          <i class="el-icon-right" style="padding: 10px"></i>
          <div style="height: 30px"></div>
          <!--        <el-select v-model="form.system" placeholder="请选择测试系统">-->
          <!--          <el-option label="WINDOWS" value="WINDOWS"></el-option>-->
          <!--          <el-option label="LINUX" value="LINUX"></el-option>-->
          <!--          <el-option label="MACOS" value="MACOS"></el-option>-->
          <!--          <el-option label="ANDROID" value="ANDROID"></el-option>-->
          <!--          <el-option label="IOS" value="IOS"></el-option>-->
          <!--          <el-option label="IPADOS" value="IPADOS"></el-option>-->
          <!--          <el-option label="OTHERS" value="OTHERS"></el-option>-->
          <!--        </el-select>-->
          <el-radio-group v-model="form.system">
            <el-radio-button label="WINDOWS"></el-radio-button>
            <el-radio-button label="LINUX"></el-radio-button>
            <el-radio-button label="MACOS"></el-radio-button>
            <el-radio-button label="ANDROID"></el-radio-button>
            <el-radio-button label="IOS"></el-radio-button>
            <el-radio-button label="IPADOS"></el-radio-button>
            <el-radio-button label="OTHER"></el-radio-button>
          </el-radio-group>
        </el-form-item>
        <el-button @click="handleSubmitAddDevice">提交</el-button>
      </el-form>

    </el-dialog>

    <el-dialog
      title="修改设备"
      :visible.sync="changeDeviceDialogVisible"
      width="50%">
      <el-form ref="form" :model="changeForm" class="tmp" label-width="100px">
        <el-form-item class="e" label="设备/系统" required>
          <el-select v-model="changeForm.device" placeholder="请选择测试设备">
            <el-option label="LENOVO" value="LENOVO"></el-option>
            <el-option label="DELL" value="DELL"></el-option>
            <el-option label="HP" value="HP"></el-option>
            <el-option label="ASUS" value="ASUS"></el-option>
            <el-option label="GIGABYTE" value="GIGABYTE"></el-option>
            <el-option label="MSI" value="MSI"></el-option>
            <el-option label="HUAWEI" value="HUAWEI"></el-option>
            <el-option label="XIAOMI" value="XIAOMI"></el-option>
            <el-option label="SAMSUNG" value="SAMSUNG"></el-option>
            <el-option label="IPHONE" value="IPHONE"></el-option>
            <el-option label="OPPO" value="OPPO"></el-option>
            <el-option label="VIVO" value="VIVO"></el-option>
            <el-option label="ONEPLUS" value="ONEPLUS"></el-option>
            <el-option label="IQOO" value="IQOO"></el-option>
            <el-option label="REALME" value="REALME"></el-option>
            <el-option label="PIXEL" value="PIXEL"></el-option>
            <el-option label="LG" value="LG"></el-option>
            <el-option label="NOKIA" value="NOKIA"></el-option>
            <el-option label="OTHER" value="OTHER"></el-option>
          </el-select>
          <i class="el-icon-right" style="padding: 10px"></i>
          <div style="height: 30px"></div>
          <el-radio-group v-model="changeForm.system">
            <el-radio-button label="WINDOWS"></el-radio-button>
            <el-radio-button label="LINUX"></el-radio-button>
            <el-radio-button label="MACOS"></el-radio-button>
            <el-radio-button label="ANDROID"></el-radio-button>
            <el-radio-button label="IOS"></el-radio-button>
            <el-radio-button label="IPADOS"></el-radio-button>
            <el-radio-button label="OTHER"></el-radio-button>
          </el-radio-group>
        </el-form-item>
        <el-button @click="handleSubmitUpdate">提交</el-button>
      </el-form>

    </el-dialog>

    <div v-if="msg==='ADMINISTRATOR'">
      <div>
        <h2>设定任务推荐策略</h2>
        <div v-if="nowStrategy===1">
          <div class="left">
            <el-card style="width: 70%;height: 290px;margin-left: 28%;background-color: #90EE90"
                     @click.native="clickStrategy1()">
              <h3 style="text-align: left;margin-left: 20px">
                <el-tag effect="dark" type="success">当前策略</el-tag> &nbsp; 推荐策略1
              </h3>
              <div style="text-align: left;margin-left: 20px;margin-right: 20px;height: 120px">{{ des1 }}</div>
              <el-button @click="dialogChangeStr1Visible = true" type="primary">
                <i class="el-icon-edit"></i>
                修改并切换到策略1
              </el-button>
            </el-card>
          </div>

          <div class="right">
            <el-card style="width: 70%;height: 290px;margin-left: 2%" @click.native="clickStrategy2()">
              <h3 style="text-align: left;margin-left: 20px">推荐策略2</h3>
              <div style="text-align: left;margin-left: 20px;margin-right: 20px;height: 120px">{{ des2 }}</div>
              <el-button @click="dialogChangeStr2Visible = true" style="right: 2px;bottom: 2px" type="primary">
                <i class="el-icon-edit"></i>
                修改并切换到策略2
              </el-button>
            </el-card>
          </div>
        </div>
        <div v-else>
          <div class="left">
            <el-card style="width: 70%;height: 290px;margin-left: 28%;" @click.native="clickStrategy1">
              <h3 style="text-align: left;margin-left: 20px">推荐策略1</h3>
              <div style="text-align: left;margin-left: 20px;margin-right: 20px;height: 120px">{{ des1 }}</div>
              <el-button @click="dialogChangeStr1Visible = true" type="primary">
                <i class="el-icon-edit"></i>
                修改并切换到策略1
              </el-button>
            </el-card>
          </div>

          <div class="right">
            <el-card style="width: 70%;height: 290px;margin-left: 2%;background-color: #90EE90"
                     @click.native="clickStrategy2()">
              <h3 style="text-align: left;margin-left: 20px">
                <el-tag effect="dark" type="success">当前策略</el-tag> &nbsp; 推荐策略2
              </h3>
              <div style="text-align: left;margin-left: 20px;margin-right: 20px;height: 120px">{{ des2 }}</div>
              <el-button @click="dialogChangeStr2Visible = true" style="right: 2px;bottom: 2px" type="primary">
                <i class="el-icon-edit"></i>
                修改并切换到策略2
              </el-button>
            </el-card>
          </div>
        </div>

      </div>

      <el-divider></el-divider>
      <el-dialog
        title="修改推荐策略1"
        :visible.sync="dialogChangeStr1Visible"
        width="30%">
        <el-form>
          <el-form-item label="推荐报告最大数量" label-width="150px">
            <el-input v-model="N1" placeholder="请输入推荐报告最大数量" style="width: 100%"></el-input>
          </el-form-item>
        </el-form>
        <h4>策略所需参数</h4>
        <div v-for="(item,index) in config" style="width:100%; margin: 0 auto">
          <!--          <span>{{ index }}</span>-->
          <el-form>
            <el-form-item :label=index label-width="150px">
              <el-slider :min=0 :max=1 :step=0.1 v-model="config[index]" show-input></el-slider>
            </el-form-item>
          </el-form>
        </div>

        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogChangeStr1Visible = false">取 消</el-button>
          <el-button type="primary" @click="submitChangeStr1">确 定</el-button>
        </span>
      </el-dialog>


      <el-dialog
        title="修改推荐策略2"
        :visible.sync="dialogChangeStr2Visible"
        width="30%"
      >
        推荐最大数量：
        <el-input v-model="N2" placeholder="请输入推荐报告最大数量" style="width: 50%"></el-input>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogChangeStr2Visible = false">取 消</el-button>
          <el-button type="primary" @click="submitChangeStr2">确 定</el-button>
        </span>
      </el-dialog>

      <h2>设定相似度阈值</h2>

      <el-card style="width: 72%;margin-left: 14%;">
        <h2 style="text-align: left;margin-left: 20px">相似度阈值说明</h2>
        <div style="text-align: left;margin-left: 20px; margin-bottom: 20px">
          协作规则共有两个阈值参数：拒绝阈值与协作阈值。当提交的报告与已存在报告的相似度使用TF-IDF算法计算相似度，若结果大于拒绝阈值，则会拒绝这份报告。
          如该报告未被拒绝，则会利用Text2Vec计算相似度并持久化到数据库中。若提交的报告与已存在报告的相似度大于协作阈值，则安排这两份报告进行协作。
        </div>
        <el-form>
          <el-form-item label="拒绝阈值" label-width="100px">
            <el-slider :min=0 :max=1 :step=0.01 v-model="rejectedScore" show-input
                       @change="handleLimitChange"></el-slider>
          </el-form-item>
          <el-form-item label="协作阈值" label-width="100px">
            <el-slider :min=0 :max=1 :step=0.01 v-model="cooperationMin" show-input
                       @change="handleLimitChange"></el-slider>
          </el-form-item>
        </el-form>
        <el-button @click="submitChangeRej" type="primary" disabled ref="changeLimit">
          <i class="el-icon-edit"></i>
          点击修改阈值
        </el-button>
      </el-card>
    </div>
    <COLLECTFooter></COLLECTFooter>
  </div>
</template>
<script>
import { changeStrategy, getStrategies, modifyStrategy } from '@/api/recommend'
import { getSimConfig, setSimConfig } from '../../api/report'
import { addDevice, deleteDevice, getMyDevice, updateDevice } from '../../api/user'
import WorkerInfo from '@/components/workerInfo'
import { triggerUpdateMon, triggerUpdateWk } from '@/api/userActiveness'
import { myHistry, myProcessing, myTask } from '@/api/task'
import COLLECTFooter from '../../components/Footer'

export default {
  components: {
    COLLECTFooter,
    WorkerInfo},
  data() {
    return {
      tableData: [],
      processingNum: 0,
      historyNum: 0,
      numOfTasks: 0,
      pagination: {
        total: 0,
        pageSize: 10
      },
      pageNumber: 1,
      uid: window.localStorage.getItem('id'),
      changeDeviceDialogVisible: false,
      addDeviceDialogVisible: false,
      myDevices: '',
      msg: window.localStorage.getItem('userRole'),
      name: window.localStorage.getItem('uname'),
      Hello: null,
      strategy: [{inUse: false, N: '', config: '', description: '', strategyId: ''}, {
        inUse: false, N: '', config: '', description: '', strategyId: ''
      }],
      config: {},
      nowStrategy: -1,
      booleanStrategy: false,
      str1: false,
      str2: false,
      des1: '',
      des2: '',
      dialogChangeStr1Visible: false,
      dialogChangeStr2Visible: false,
      N1: 5,
      N2: 5,
      rejectNum: '',
      changeRejectVisible: false,
      rejectedScore: '',
      cooperationMin: '',
      form: {
        device: '',
        system: ''
      },
      changeForm: {
        device: '',
        system: '',
        id: ''
      }
    }
  },
  mounted() {
    const that = this;
    if(this.msg == "PUBLISHER"){
      myTask(localStorage.getItem('id'), 1).then(function (response) {
        console.log(response)
        that.tableData = response.data.list
        for (var item of that.tableData) {
          if(item.taskDiscribe.length > 100){
            item.taskDiscribe = item.taskDiscribe.substr(0, 100) + "……"
          }
        }
        that.numOfTasks = response.data.total
        that.pagination.total = response.data.pages
      })
    }
    if(this.msg == "WORKER") {
      getMyDevice(localStorage.getItem('id'), 1).then(function (res) {
        that.myDevices = res.data.list
        console.log(that.myDevices)
      })
      myProcessing(localStorage.getItem('id'), 1).then(function (response) {
        that.processingNum = response.data.total
      })
      myHistry(localStorage.getItem('id'), 1).then(function (response) {
        that.historyNum = response.data.total
      })
    }
    if(this.msg == "ADMINISTRATOR"){
      getStrategies().then(function (res) {
        console.log(res.data.data)
        if (res.data.data[0].strategyId == 2) {
          that.config = res.data.data[1].config
          that.str2 = res.data.data[0].inUse;
          that.des2 = res.data.data[0].description;
          that.str1 = res.data.data[1].inUse;
          that.des1 = res.data.data[1].description;
          that.N1 = res.data.data[1].N;
          that.N2 = res.data.data[0].N;
        } else {
          that.config = res.data.data[0].config
          that.str2 = res.data.data[1].inUse;
          that.des2 = res.data.data[1].description;
          that.str1 = res.data.data[0].inUse;
          that.des1 = res.data.data[0].description;
          that.N1 = res.data.data[0].N;
          that.N2 = res.data.data[1].N;
        }
        if (that.str2 == true) {
          that.nowStrategy = 2;
        } else {
          that.nowStrategy = 1;
        }
      })
      getSimConfig(window.localStorage.getItem('id')).then(function (res) {
        console.log(res)
        that.rejectedScore = res.data.data.rejectedScore
        that.cooperationMin = res.data.data.cooperationMin
      })
    }
  },
  created() {
    // console.log(window.localStorage.getItem("token"))
    var d = new Date();
    if (d.getHours() < 11 && d.getHours() >= 5) {
      this.Hello = '早上好'
    } else if (d.getHours() >= 11 && d.getHours() <= 13) {
      this.Hello = '中午好'
    } else if (d.getHours() > 13 && d.getHours() <= 17) {
      this.Hello = '下午好'
    } else {
      this.Hello = '晚上好'
    }
  },
  methods: {
    fetchData(page) {
      var that = this
      //this.listLoading = false
      const id = window.localStorage.getItem('id')
      myTask(id, page).then(function (response) {
        console.log(response)
        that.tableData = response.data.list
        for (var item of that.tableData) {
          if(item.taskDiscribe.length > 100){
            item.taskDiscribe = item.taskDiscribe.substr(0, 100) + "……"
          }
        }
        that.numOfTasks = response.data.total
        that.pagination.total = response.data.pages
      })
    },
    // add click method
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
    },
    triggerActivenessUpdate() {
      const that = this
      triggerUpdateWk(window.localStorage.getItem('id')).then(res => {
        console.log(res)
        if (res.data.code === 1) {
          that.$message.success("触发全局周活跃度成功！")
        }
      })
      triggerUpdateMon(window.localStorage.getItem('id')).then(res => {
        console.log(res)
        if (res.data.code === 1) {
          that.$message.success("触发全局月活跃度成功！")
        }
      })
    },

    handleClickchange(brand, system, id) {
      this.changeDeviceDialogVisible = true;
      this.changeForm.device = brand
      this.changeForm.system = system
      this.changeForm.id = id
    },

    handleLimitChange() {
      this.$refs.changeLimit.disabled = false
    },

    handleSubmitUpdate() {
      updateDevice(this.changeForm.device, this.changeForm.system, this.changeForm.id, parseInt(localStorage.getItem('id')))
      this.changeDeviceDialogVisible = false
      alert("修改成功！")
      this.$router.go(0)
    },
    handleSubmitAddDevice() {
      addDevice(parseInt(localStorage.getItem('id')), this.form.system, this.form.device, 0)
      this.addDeviceDialogVisible = false;
      alert("添加成功！")
      this.$router.go(0)
    },
    handleAddDevice(device, system) {
      this.addDeviceDialogVisible = true;

    },
    handleDeleteDevice(id) {
      var that = this;
      deleteDevice(window.localStorage.getItem('id'), id).then(res => {
        alert("已删除")
        that.$router.go(0)
      })
    },
    handleChangeDevice(id) {

    },
    addTask() {
      console.log(this)
      this.$router.push({path: '/addTask'})
    },
    seeDoneTask() {
      this.$router.push({path: '/myhistory'})
    },
    seeOnTask() {
      this.$router.push({path: '/myProcessing'})
    },
    seePlayground() {
      this.$router.push({path: '/playground'})
    },
    mytask() {
      this.$router.push({path: '/myPublished'})
    },
    seeAllTask() {
      this.$router.push({path: '/allTasks'})
    },
    change() {
      /*var now=this.nowStrategy;
      var that=this;
      if(now==1){
        changeStrategy(2).then(function (res){
          that.$alert("切换成功！当前为策略2！")
          console.log(res)

        })
        this.nowStrategy=2;
        this.str1=false;
        this.str2=true;
      }else {
        changeStrategy(1).then(function (res){
          that.$alert("切换成功！当前为策略1！")
          console.log(res)
        })
        this.str2=false;
        this.str1=true;
        this.nowStrategy=1;
      }*/
      var that = this;
      if (this.nowStrategy == 1) {
        changeStrategy(2).then(function (res) {
          that.$alert("切换成功！当前为策略2！")
          getStrategies().then(function (response) {
            console.log(response)
            that.str2 = response.data.data[1].inUse;
            that.str1 = response.data.data[0].inUse;
          })

        })
        this.nowStrategy = 2
      } else {
        changeStrategy(1).then(function (res) {
          that.$alert("切换成功！当前为策略1！")
          getStrategies().then(function (response) {
            console.log(response)
            that.str2 = response.data.data[1].inUse;
            that.str1 = response.data.data[0].inUse;
          })
        })
        this.nowStrategy = 1
      }


    },
    clickStrategy1() {
      console.log("click1")
      var that = this
      if (this.nowStrategy == 1) {
        //do nothing
      } else {
        changeStrategy(1).then(function (res) {
          that.$alert("切换成功！当前为策略1！")
          getStrategies().then(function (response) {
            console.log(response)
            that.str2 = response.data.data[1].inUse;
            that.str1 = response.data.data[0].inUse;
          })
        })
        this.nowStrategy = 1
      }
    },
    clickStrategy2() {
      console.log("click2")
      var that = this
      if (this.nowStrategy == 2) {
        //do nothing
      } else {
        changeStrategy(2).then(function (res) {
          that.$alert("切换成功！当前为策略2！")
          getStrategies().then(function (response) {
            console.log(response)
            that.str2 = response.data.data[1].inUse;
            that.str1 = response.data.data[0].inUse;
          })

        })
        this.nowStrategy = 2
      }
    },
    submitChangeStr1() {
      var that = this;
      let confi = this.config;
      modifyStrategy(confi, 1, this.N1).then(function (res) {
        that.$alert("策略1修改成功！")
      })
      this.dialogChangeStr1Visible = false;

    },
    submitChangeStr2() {
      var that = this;
      let confi = this.config;
      modifyStrategy(confi, 2, this.N2).then(function (res) {
        that.$alert("策略2修改成功！")
        console.log(res)
      })
      this.dialogChangeStr2Visible = false;
    },
    submitChangeRej() {
      var that = this;
      setSimConfig(window.localStorage.getItem('id'), this.rejectedScore, this.cooperationMin).then(function (res) {
        console.log(res)
        getSimConfig(window.localStorage.getItem('id')).then(function (response) {
          that.rejectedScore = res.data.data.rejectedScore
          that.cooperationMin = res.data.data.cooperationMin
          that.$alert("调整阈值成功！")
          that.$refs.changeLimit.disabled = true
        })
      })
      // this.changeRejectVisible = false;
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
  background-image: linear-gradient(-225deg, #FF79c7 0%, #9b9aFF 100%);
  color: #2d2d2d;
}

.btn {
  margin-top: 20px;
  margin-left: 50px;
  background-image: linear-gradient(-225deg, #6e8ad8 0%, #bdbcdb 100%);
}

.left {
  width: 50%;

  float: left;
}

.right {

  margin-left: 50%;

}

.tmp {
  width: 90%;
  margin: 0 auto;
  margin-top: 30px;
}

.e {
  margin-top: 40px;
}

</style>
