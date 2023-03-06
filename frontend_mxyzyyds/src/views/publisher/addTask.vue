<template>
  <div class="bkg">
    <div class="b">
      <div class="d">发布测试任务</div>
    </div>
    <el-form ref="form" :model="form" class="tmp" label-width="100px">

      <el-form-item class="g" label="招募人数" required>
        <el-input-number size="medium" v-model="form.peopleNum"></el-input-number>
      </el-form-item>

      <el-form-item class="g" label="起止时间" required>
        <el-date-picker
          v-model="value1"
          placeholder="选择开始时间"
          type="datetime"
          value-format="timestamp">
        </el-date-picker>
        <i class="el-icon-right" style="padding: 10px"></i>
        <el-date-picker
          v-model="value2"
          placeholder="选择结束时间"
          type="datetime"
          value-format="timestamp">
        </el-date-picker>
      </el-form-item>

      <el-form-item class="g" label="测试描述" required>
        <el-input v-model="form.desc" type="textarea" style="width:90%;height:100px;" :rows=4></el-input>
      </el-form-item>
      <el-form-item class="g" label="测试类型" required>
        <el-select v-model="form.testType" placeholder="请选择测试类型">
          <el-option label="FUNCTION" value="FUNCTION"></el-option>
          <el-option label="PERFORMANCE" value="PERFORMANCE"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item class="g" label="设备/系统" required>
        <el-select v-model="form.brand" placeholder="设备类型">
          <el-option
            v-for="item in brand_options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
        <i class="el-icon-right" style="padding: 10px"></i>
        <!--        <el-select v-model="form.os" placeholder="系统类型">-->
        <!--          <el-option-->
        <!--            v-for="item in options_os"-->
        <!--            :key="item.value"-->
        <!--            :label="item.label"-->
        <!--            :value="item.value">-->
        <!--          </el-option>-->
        <!--        </el-select>-->
        <el-radio-group v-model="form.os" required>
          <el-radio-button label="WINDOWS"></el-radio-button>
          <el-radio-button label="LINUX"></el-radio-button>
          <el-radio-button label="MACOS"></el-radio-button>
          <el-radio-button label="ANDROID"></el-radio-button>
          <el-radio-button label="IOS"></el-radio-button>
          <el-radio-button label="IPADOS"></el-radio-button>
          <el-radio-button label="OTHER"></el-radio-button>
        </el-radio-group>
      </el-form-item>
      <el-form-item class="g" label="难度">
        <el-slider v-model="form.difficulty" :max=10 :min=1 :step=1></el-slider>
      </el-form-item>
      <el-form-item class="e" label="说明文档" required>
        <span>点击下方按钮可以选择单个PDF以供上传。点击已选文件标签右侧的×可以取消选中该文件。</span>
        <el-tag
          v-for="file in files"
          v-if="file.name.endsWith('.pdf')"
          :key="file.name"
          type="success"
          style="margin-right: 0.5rem"
          closable
          @close="handleCancelUpload(file)"
        >
          {{ file.name }}
        </el-tag>
        <div>
          <input name="files" style="margin:10px" type="file" accept=".pdf" @change="getPDFFiles($event)"/>
        </div>
      </el-form-item>
      <el-form-item class="e" label="待测应用" required>
        <span>点击下方按钮可以选择多个文件以供上传。可以支持各种格式。点击已选文件标签右侧的×可以取消选中该文件。</span>
        <div>
          <input multiple="multiple" name="files" style="margin:10px" type="file" @change="getFiles($event)"/>
          <div>
            <el-tag
              v-for="file in files"
              v-if="!file.name.endsWith('.pdf')"
              :key="file.name"
              style="margin-right: 0.5rem"
              closable
              @close="handleCancelUpload(file)"
            >
              {{ file.name }}
            </el-tag>
          </div>
        </div>
      </el-form-item>
      <el-button type="primary" @click="upload" v-loading.fullscreen.lock="fullscreenLoading">创建报告</el-button>
    </el-form>
    <!--    <div class="a">-->
    <!--      <div>-->
    <!--        <input multiple="multiple" name="files" type="file" @change="getFiles($event)"/><br/>-->
    <!--        <el-button style="margin:15px" type="primary" @click="upload" v-loading.fullscreen.lock="fullscreenLoading">-->
    <!--          发布任务-->
    <!--        </el-button>-->
    <!--      </div>-->
    <!--    </div>-->
    <COLLECTFooter></COLLECTFooter>
  </div>
</template>
<script>
//import { myUpLoad } from "@/api/publisher"
import {addTask} from '@/api/task'
import COLLECTFooter from '../../components/Footer'

export default {
  components: { COLLECTFooter },
  data() {
    return {
      fullscreenLoading: false,
      hasPDF: false,
      value1: '',
      value2: '',
      fileList: [],
      files: [],
      form: {
        name: '',
        delivery: false,
        testType: '',
        resource: '',
        desc: '',
        peopleNum: '10',
        brand: '',
        os: '',
        difficulty: 1
      },
      options_os: [{
        value: 'WINDOWS',
        label: 'WINDOWS'
      }, {
        value: 'LINUX',
        label: 'LINUX'
      }, {
        value: 'MACOS',
        label: 'MACOS'
      }, {
        value: 'ANDROID',
        label: 'ANDROID'
      }, {
        value: 'IOS',
        label: 'IOS'
      },
        {
          value: 'IPADOS',
          label: 'IPADOS'
        },
        {
          value: 'OTHER',
          label: 'OTHER'
        }],
      brand_options: [{
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
    };
  },
  methods: {
    openFullScreen1() {
      this.fullscreenLoading = true;
      // setTimeout(() => {
      //   this.fullscreenLoading = false;
      // }, 2000);
    },
    onSubmit() {
      //var taskVo={'finishTime':this.value2,'startTime':this.value1,'taskDescribe':this.form.desc,'totalNum':this.form.peopleNum,'type':this.form.testType,'userId':window.localStorage.getItem('id'),'currentNum':0}
      //console.log(this.value1)
    },
    getFiles: function (event) {
      var files = event.target.files;
      console.log(files)
      for (var i = 0; i < files.length; i++) {
        this.files.push(files[i])
      }
      console.log(this.files)
    },
    getPDFFiles: function (event) {
      this.$data.hasPDF = true
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

      if (this.value1 === '' || this.value2 === '' || this.value1 > this.value2) {
        this.$message.warning("请正确填写起止日期")
        return
      }
      if (this.form.desc === '') {
        this.$message.warning("请填写测试任务描述")
        return;
      }
      if (this.form.peopleNum === '') {
        this.$message.warning("请填写测试需求人数")
        return;
      }
      if (this.form.testType === '') {
        this.$message.warning("请填写测试类型")
        return;
      }
      if (this.form.brand === '') {
        this.$message.warning("请填写测试设备类型")
        return;
      }
      if (this.form.os === '') {
        this.$message.warning("请填写测试操作系统")
        return;
      }
      if (!this.$data.hasPDF) {
        this.$message.warning("请上传说明PDF文件！")
        return;
      }
      if (this.$data.files.length < 2) {
        this.$message.warning("请上传待测应用！")
        return;
      }

      var taskVo = '{finishTime:' + this.value2 + ',startTime:' + this.value1 + ',taskDiscribe:' + '"' + this.form.desc + '"' + ',totalNum:' + this.form.peopleNum + ',type:' + '"' + this.form.testType + '"' + ',userId:' + window.localStorage.getItem('id') +
        ' , currentNum: 0,deviceBrand:' + '"' + this.form.brand + '"' + ',operatingSystem:' + '"' + this.form.os + '"' + ',difficulty:' + this.form.difficulty + ',exeFilePath: null,discribFilePath:null' + '}'
      //var taskVo = '{finishTime: 1645938341278, startTime: 1645938341278, taskDiscribe: "test", totalNum: 20, type: "PERFORMANCE", userId: 1, currentNum: 0}';
      formData.append('taskVo', taskVo)

      this.openFullScreen1()

      addTask(formData).then(response => {
        that.$data.fullscreenLoading = false
        // console.log(response)
        if (response.data.code == 1) {
          that.$message({message: "发布成功！", type: 'success'})
          that.$router.push({path: '/taskInfo', query: {id: response.data.data.id}})
        } else {
          that.$message({message: "发布失败！" + response.data.msg, type: 'error'})
        }
      }, function (err) {
        that.$message({message: "发布失败！" + err, type: 'error'})
        that.$data.fullscreenLoading = false
      })
    },
    handleCancelUpload(file) {
      if (file.name.endsWith(".pdf")) {
        this.$data.hasPDF = false
      }
      var index = this.$data.files.indexOf(file)
      this.$data.files.splice(index, 1)
      console.log(this.$data.files)
    }
  }
}
</script>
<style scoped>
.a {
  margin: 20px;
}

.el-upload__tip {
  font-size: 18px;
  margin: 10px;
}

.tmp {
  width: 70%;
  margin: 0 auto;
  margin-top: 30px;
}

.g {
  margin: 20px;
}

.b {
  padding: 3rem;
  background-image: linear-gradient(120deg, #000080 0%, #4169E1 100%);
  margin-bottom: 3rem ;
}

.d {
  font-size: 3rem;
  color: #ffffff;
}

.bkg {
  background-color: #EEFFFF;
  width: 100%;
  height: 100%;
}
</style>
