<template>
  <div style="height: 100%">

    <el-container style="height: 100%; border: 1px solid #eee">

      <el-aside width="50%">
        <h1 style="text-align: left; font-size: 1.5rem; margin-left: 2rem">新增协作报告-测试报告{{
            nowReport.id
          }}：{{ nowReport.title }}</h1>
        <el-descriptions :column="10" border direction="vertical" style="width: 90%;margin: 0 auto; font-size: 1rem">
          <el-descriptions-item :span="2" label="发布者">
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
          <el-descriptions-item :span="4" label="报告描述">
            {{ nowReport.desc }}
          </el-descriptions-item>
          <el-descriptions-item label="补充截图">
            点击图片可查看大图
            <div class="images" v-viewer="{inline: false}" @click="show">
              <img :src="pic" alt="img" v-for="pic in nowReport.finalpics" :key="pic" style="width: 100%">
            </div>
          </el-descriptions-item>
        </el-descriptions>
      </el-aside>

      <el-container style="border-left: 2px solid #39a9ff">
        <el-aside width="100%" style="background-color: #e9eef3">
          <div style="width: 90%">
            <div style="text-align: center">
              <h4 style="font-size: 1.2rem; font-weight: bold; text-align: center">报告协作关系图</h4>
              <span>点击可跳转到补充报告详情页</span>
            </div>
            <div id="relation" style="height: 500px; width: 100%">
              <el-skeleton :rows="3" animated/>
              <span style="font-size: 32px; width: 100%"> 报告相似关系加载中……</span>
              <el-skeleton :rows="3" animated/>
            </div>

            <el-card class="form-card">
              <span style="font-size: 1.5rem; font-weight: bold">发布补充报告</span><br></br>
              <span style="margin-bottom: 20px;margin-top: 20px; border-left: 4px solid #39a9ff;padding-left: 8px;">补充报告描述</span>
              <el-input
                v-model="formContext.textarea"
                :rows="6"
                maxlength="300"
                placeholder="请输入内容"
                resize="none"
                show-word-limit
                style="margin-top: 20px;width: 100%;margin-bottom: 20px;"
                type="textarea"
              >
              </el-input>
              <div style="margin-bottom: 20px;">
                <span style="margin-bottom: 20px;margin-top: 20px; border-left: 4px solid #39a9ff;padding-left: 8px;">设备类型</span>
                <el-select v-model="formContext.deviceBrand" placeholder="请选择" style="margin-left: 1rem">
                  <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </div>

              <div style="margin-top: 20px; ">
                <span style="border-left: 4px solid #39a9ff;padding-left: 8px;">系统类型</span>
                <el-radio-group v-model="formContext.operatingSystem" style="margin-left: 1rem">
                  <el-radio-button label="WINDOWS"></el-radio-button>
                  <el-radio-button label="LINUX"></el-radio-button>
                  <el-radio-button label="MACOS"></el-radio-button>
                  <el-radio-button label="ANDROID"></el-radio-button>
                  <el-radio-button label="IOS"></el-radio-button>
                  <el-radio-button label="IPADOS"></el-radio-button>
                  <el-radio-button label="OTHER"></el-radio-button>
                </el-radio-group>
              </div>

              <span style="border-left: 4px solid #39a9ff;padding-left: 8px;">补充截图</span>
              <input multiple="multiple" name="files" style="margin-top: 20px; margin-left: 1rem" type="file"
                     @change="getFiles($event)"/>
              <div style="margin: 0.5rem">
                <el-tag
                  v-for="file in files"
                  :key="file.name"
                  closable
                  style="margin-right: 0.5rem"
                  @close="handleCancelUpload(file)"
                >
                  {{ file.name }}
                </el-tag>
              </div>
              <el-button style="margin-top: 20px;float: right;margin-right: 30px;margin-bottom: 20px" type="primary"
                         v-loading.fullscreen.lock="fullscreenLoading" @click="upload">发布补充报告
              </el-button>
            </el-card>
          </div>
        </el-aside>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import CollaborateCard from "../../components/collaborateCard";
import * as echarts from 'echarts'
import {getAdditionalByReport, submitAdditionalReport} from "@/api/additionalReport";
import {reportDetail} from "@/api/report";
import {getTestRecord} from "@/api/task";

// import 'viewerjs/dist/viewer.css'

export default {
  name: "collaborateReportPage",
  components: {CollaborateCard},
  data() {
    return {
      fullscreenLoading: false,
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
      files: [],
      formContext: {
        value: '',
        textarea: '',
        deviceBrand: 'DELL',
        operatingSystem: 'WINDOWS'
      },
      value: '',
      textarea: '',
      drawer: false,
      corpReportDraw: [],
      corpReportDrawLink: [],
      corpReport: [],
      options: [
        {
          value: 'LENOVO',
          label: 'LENOVO'
        }, {
          value: 'DELL',
          label: 'DELL'
        }, {
          value: 'HP',
          label: 'HP'
        }, {
          value: 'ASUS',
          label: 'ASUS'
        }, {
          value: 'GIGABYTE',
          label: 'GIGABYTE'
        },
        {
          value: 'MSI',
          label: 'MSI'
        },
        {
          value: 'HUAWEI',
          label: 'HUAWEI'
        }, {
          value: 'XIAOMI',
          label: 'XIAOMI'
        }, {
          value: 'APPLE',
          label: 'APPLE'
        }, {
          value: 'OPPO',
          label: 'OPPO'
        }, {
          value: 'VIVO',
          label: 'VIVO'
        },
        {
          value: 'ONEPLUS',
          label: 'ONEPLUS'
        },
        {
          value: 'IQOO',
          label: 'IQOO'
        }, {
          value: 'REALME',
          label: 'REALME'
        }, {
          value: 'PIXEL',
          label: 'PIXEL'
        },
        {
          value: 'LG',
          label: 'LG'
        }, {
          value: 'NOKIA',
          label: 'NOKIA'
        },
        {
          value: 'OTHER',
          label: 'OTHER'
        }
      ]
    }
  },

  mounted() {
    this.initData();
    this.drawChart();
  },

  methods: {

    goToUserInfo(userId) {
      this.$router.push({path: "/userInfo", query: {userId: userId}})
    },

    drawChart() {
      // 基于准备好的dom，初始化echarts实例
      let myChart = echarts.init(document.getElementById("relation"));
      // 指定图表的配置项和数据
      let option = {
        tooltip: {
          formatter: function (param) {
            // console.log("输出",param);
            //tooltip这里的formatter参数param可以得到series中的data数据
            return param.data.des;
          }
        },
        series: [
          {
            type: "graph",
            layout: "force",
            symbolSize: 80,
            focusNodeAdjacency: true, //是否在鼠标移到节点上的时候突出显示节点以及节点的边和邻接节点。
            roam: true, //是否开启鼠标缩放和平移漫游。默认不开启。如果只想要开启缩放或者平移，可以设置成 'scale' 或者 'move'。设置成 true 为都开启
            edgeSymbol: ["none", "arrow"], //边两端的标记类型，可以是一个数组分别指定两端，也可以是单个统一指定。默认不显示标记，常见的可以设置为箭头
            edgeSymbolSize: [10, 10],
            force: {
              repulsion: 3000, //节点之间的斥力因子
              edgeLength: [20, 30]
            },
            draggable: true,
            //定义节点的样式
            itemStyle: {
              color: function (param) {
                return param.data.color
              },
            },
            //连线的样式
            lineStyle: {
              width: 2,
              color: "#000"
            },
            //连线上的标记样式
            edgeLabel: {
              show: true,
              formatter: function (param) {
                //tooltip这里的formatter参数param可以得到series中的data数据
                return param.data.value;
              },
              color: "#000"
            },
            //节点上是否显示文字
            label: {
              show: true,
              fontWeight: "bold",
              fontSize: 18
            },
            data: this.corpReportDraw,
            links: this.corpReportDrawLink
          }
        ]
      };
      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(option);
      myChart.on('click', function (param) {
        var name = param.data.name
        var id = name.match(/[0-9]+/g)[0]
        console.log(id)
        window.location.href = window.location.protocol + "//" + window.location.host + '/additionalReportPage?id=' + id
      })
    },
    getFiles: function (event) {
      var files = event.target.files;
      console.log(files)
      for (var i = 0; i < files.length; i++) {
        this.files.push(files[i])
      }
      console.log(this.files)
    },

    upload: function () {
      var that = this
      var formData = new FormData()
      for (var i = 0; i < this.files.length; i++) {
        formData.append('files', this.files[i])
      }

      console.log(this.formContext.textarea)
      if (this.formContext.textarea === '') {
        this.$message.warning("请填写报告描述")
        return;
      }
      // if (this.form.title === '') {
      //   this.$message.warning("请填写报告标题")
      //   return;
      // }
      if (this.formContext.deviceBrand === '') {
        this.$message.warning("请填写设备类型")
        return;
      }
      if (this.formContext.operatingSystem === '') {
        this.$message.warning("请填写操作系统")
        return;
      }
      if (this.files.length === 0) {
        this.$message.warning("请上传测试截图")
        return;
      }

      var additionalReportVo = '{id:null' + ', testRecordId:' + this.nowReport.testRecordId + ', ' +
        'screenshotPathList: null' + ', description:' + '"' + this.formContext.textarea + '"' + ', operatingSystem:' +
        '"' + this.formContext.operatingSystem + '"' + ',deviceBrand:' + '"' + this.formContext.deviceBrand +
        '"' + ',createTime:"2003-03-28 23:22:49", userId:' + window.localStorage.getItem('id') + ', defectReportId: '
        + this.nowReport.id + '}'
      //var taskVo = '{finishTime: 1645938341278, startTime: 1645938341278, taskDiscribe: "test", totalNum: 20, type: "PERFORMANCE", userId: 1, currentNum: 0}';
      formData.append('additionalReportVo', additionalReportVo)
      console.log(additionalReportVo)

      this.$data.fullscreenLoading = true;

      submitAdditionalReport(window.localStorage.getItem('id'), formData).then(function (response) {
        // console.log(response)
        that.$data.fullscreenLoading = false
        if (response.data.code == 1) {
          that.$message({message: "发布成功！", type: 'success'})
          that.$router.push({path: '/additionalReportPage?id=' + response.data.data.id})
          that.updateCorparatePic()
        } else {
          that.$message({message: "发布失败！" + response.data.msg, type: 'error'})
        }
      }, function (err) {
        that.$message({message: "发布失败！" + err, type: 'error'})
        that.$data.fullscreenLoading = false
      })
    },

    show() {
      console.log("Hi")
      const viewer = this.$el.querySelector('.images').$viewer
      viewer.show()
    },

    handleCancelUpload(file) {
      var index = this.$data.files.indexOf(file)
      this.$data.files.splice(index, 1)
      console.log(this.$data.files)
    },

    initData() {
      this.updateDefectReportDetail()
    },

    updateDefectReportDetail() {
      var that = this
      reportDetail(window.localStorage.getItem('id'), this.nowReport.id)
        .then(function (res) {
          // console.log(res)
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
          if (that.nowReport.status === "PASSED") {
            that.corpReportDraw.push(
              {
                name: "当前报告",
                symbolSize: 100,
                des: "报告ID：" + that.nowReport.id,
                color: "#eba844"
              }
            )
            that.drawChart()
          } else if (that.nowReport.status === "PROCESSING") {
            that.corpReportDraw.push(
              {
                name: "相似度正在计算中，请稍后刷新",
                symbolSize: 300,
                des: "报告ID：" + that.nowReport.id,
                color: "#444444"
              }
            )
          } else {
            that.corpReportDraw.push(
              {
                name: "报告已被拒绝",
                symbolSize: 300,
                des: "报告ID：" + that.nowReport.id,
                color: "#DD2222"
              }
            )
          }
          getTestRecord(window.localStorage.getItem('id'), that.nowReport.testRecordId).then(function (res) {
            // console.log(res)
            that.nowReport.taskId = res.data.data.taskId
            that.nowReport.userId = res.data.data.userId
            that.nowReport.commitTime = res.data.data.commitTime
          })
          that.updateCorparatePic()
        })
    },

    updateCorparatePic() {
      var that = this
      getAdditionalByReport(window.localStorage.getItem('id'), 1, this.nowReport.id)
        .then(function (res) {
          var drawT = []
          console.log(res.data)
          for (var i = 0; i < res.data.list.length; i++) {
            let additionalReportVo, score;
            additionalReportVo = res.data.list[i]
            that.corpReport.push(additionalReportVo)
            //"……" +  (defectReportVo.description+'').substr(-30, 30)
            that.corpReportDraw.push({
              name: "补充报告" + additionalReportVo.id,
              symbolSize: 80,
              des: ("测试记录" + additionalReportVo.testRecordId + "：……" + (additionalReportVo.description + '').substr(-10, 10)),
              color: 'rgba(' + (that.getHash(additionalReportVo.description) % 256 / 2) + ',' + ((that.getHash(additionalReportVo.description) % 8167) % 256 / 2) + ',' + ((that.getHash(additionalReportVo.description) % 9973) % 256) + ', 0.7)'
            })
            that.corpReportDrawLink.push({
              source: "补充报告" + additionalReportVo.id,
              target: "当前报告",
              value: "用户" + additionalReportVo.userId
            })
          }
          console.log(that.corpReportDraw)
          console.log(drawT)

          that.drawChart()
        })
    },

    getHash(str, seed = 0) {
      let h1 = 0xdeadbeef ^ seed, h2 = 0x41c6ce57 ^ seed
      for (let i = 0, ch; i < str.length; i++) {
        ch = str.charCodeAt(i)
        h1 = Math.imul(h1 ^ ch, 2654435761)
        h2 = Math.imul(h2 ^ ch, 1597334677)
      }
      h1 = Math.imul(h1 ^ (h1 >>> 16), 2246822507) ^ Math.imul(h2 ^ (h2 >>> 13), 3266489909)
      h2 = Math.imul(h2 ^ (h2 >>> 16), 2246822507) ^ Math.imul(h1 ^ (h1 >>> 13), 3266489909)
      return 4294967296 * (2097151 & h2) + (h1 >>> 0)
    },
  }
}
</script>

<style scoped>
body, html {
  width: 100%;
  height: 100%;
}

.form-card {
  margin-left: 5%;
  width: 95%;
  height: auto;
  text-align: left;
  margin-bottom: 50px;
}

</style>
