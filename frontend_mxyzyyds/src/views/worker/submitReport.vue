<template>
  <div class="a">
    <div class="b">
      <div class="c"></div>
      <div class="d">向任务{{ this.$route.query.taskid }}提交测试报告</div>
    </div>
    <el-form ref="form" :model="form" class="tmp" label-width="100px">
      <el-form-item class="e" label="报告标题" required>
        <el-input v-model="form.title" style="width:90%;height:50px;"></el-input>
      </el-form-item>
      <el-form-item class="e" label="测试描述" required>
        <el-input v-model="form.desc" type="textarea" style="width:90%;height:100px;" :rows=4></el-input>
      </el-form-item>
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

      <el-form-item class="e" label="测试截图" required>
        <span>点击下方按钮可以选择多个文件以供上传。仅支持png、jpg/jpeg和gif。点击已选文件标签右侧的×可以取消选中该文件。</span>
        <div>
          <input multiple="multiple" name="files" accept="image/*" style="margin:10px" type="file"
                 @change="getFiles($event)"/>
          <div>
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
        </div>
      </el-form-item>
      <el-button type="primary" @click="upload" v-loading.fullscreen.lock="fullscreenLoading">创建报告</el-button>
    </el-form>
    <COLLECTFooter></COLLECTFooter>
  </div>
</template>
<script>
import {submitReport} from '@/api/report'
import {checkTaskStatus} from "@/api/task"
import COLLECTFooter from '../../components/Footer'

export default {
  components: { COLLECTFooter },
  data() {
    return {
      fullscreenLoading: false,
      files: [],
      task_record_id: '',
      form: {
        desc: '',
        system: '',
        device: '',
        title: ''
      }
    }
  },
  created() {
    var that = this
    checkTaskStatus(window.localStorage.getItem('id'), this.$route.query.taskid)
      .then(function (res) {
        console.log("task_record_id")
        console.log(res.data.data.id)
        that.task_record_id = res.data.data.id
      })
  },
  methods: {
    openFullScreen1() {
      this.fullscreenLoading = true;
      // setTimeout(() => {
      //   this.fullscreenLoading = false;
      // }, 2000);
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
      var d = new Date()
      var datestr = this.formatDate(d)

      if (this.form.desc === '') {
        this.$message.warning("请填写报告描述")
        return;
      }

      if (this.form.title === '') {
        this.$message.warning("请填写报告标题")
        return;
      }
      if (this.form.device === '') {
        this.$message.warning("请填写设备类型")
        return;
      }
      if (this.form.system === '') {
        this.$message.warning("请填写操作系统")
        return;
      }
      if (this.files.length === 0) {
        this.$message.warning("请上传测试截图")
        return;
      }

      var defectReportVo = '{id:null' + ',test_record_id:' + this.task_record_id + ',screenshot_path_list:null' + ',description:' + '"' + this.form.desc + '"' + ',operating_system:' + '"' + this.form.system + '"' + ',title:' + '"' + this.form.title + '"' + ',device_brand:' + '"' + this.form.device + '"' + ',create_time:"2003-03-28 23:22:49"}'
      //var taskVo = '{finishTime: 1645938341278, startTime: 1645938341278, taskDiscribe: "test", totalNum: 20, type: "PERFORMANCE", userId: 1, currentNum: 0}';
      formData.append('defectReportVo', defectReportVo)

      this.openFullScreen1()

      submitReport(formData).then(function (response) {
        // console.log(response)
        that.$data.fullscreenLoading = false
        if (response.data.code === 1) {
          that.$message({message: "发布成功！", type: 'success'})
          that.$router.push({path: '/mycenter'})
        } else {
          that.$message({message: "发布失败！" + response.data.msg, type: 'error'})
        }
        // 请求成功
      }, function (err) {
        that.$message({message: "发布失败！" + err, type: 'error'})
        that.$data.fullscreenLoading = false
      })
    },
    formatDate(current_datetime) {
      let formatted_date = current_datetime.getFullYear() + "-" + (current_datetime.getMonth() + 1) + "-" + current_datetime.getDate() + " " + current_datetime.getHours() + ":" + current_datetime.getMinutes() + ":" + current_datetime.getSeconds();
      return formatted_date;
    },
    handleCancelUpload(file) {
      var index = this.$data.files.indexOf(file)
      this.$data.files.splice(index, 1)
      console.log(this.$data.files)
    }

  }
}
</script>
<style scoped>
.tmp {
  width: 70%;
  margin: 0 auto;
  margin-top: 30px;
}

.a {
  margin: 0 auto;
  text-align: center;
  background-image: linear-gradient(120deg, #c3d8fc 0%, #ecfae3 100%);

  width: 100%;
  height: 100%;
}

.b {
  height: 200px;
  background-color: rgb(77, 112, 165);
}

.c {
  height: 70px;
}

.d {
  font-size: 3rem;
  color: antiquewhite;
  font-weight: 400;
}

.e {
  margin-top: 40px;
}
</style>
