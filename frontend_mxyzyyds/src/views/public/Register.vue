<template>
  <div style="background-color: #87CEFA; width: 100%; height: 100%; background-repeat: repeat; ">
    <NavigateMenu></NavigateMenu>
    <div
      style="background-color: #87CEFA; width: 100%; height: fit-content; background-repeat: repeat; margin: 0 auto; padding-top: 120px; padding-bottom: 100px">
      <el-card class="wrap">
        <div class="fle">
          <h1 style="margin-bottom: auto">COLLECT-注册</h1>
          <div class="container">
            <div class="register-container" style="width: 100%">
              <div
                style=" text-align:left;border-left: 4px solid #39a9ff;padding-left: 8px;line-height: initial;font-size: initial;">
                <h4>基本信息</h4>
              </div>
              <el-form label-width="100px">
                <el-form-item label="用户名" required>
                  <el-input v-model="form.name" class="input_box" placeholder="用户名"></el-input>
                </el-form-item>
                <el-form-item label="用户密码" required>
                  <el-input v-model="form.password" class="input_box" placeholder="密码" show-password
                  ></el-input>
                </el-form-item>
                <el-form-item label="注册邮箱" required>
                  <el-input v-model="form.email" class="input_box" placeholder="邮箱"></el-input>
                </el-form-item>

                <span style="font-size: 0.9rem;  padding: 10px">用户类型</span>
                <el-select v-model="form.userType" placeholder="请选择用户类型" style="margin-top: 20px">
                  <el-option label="WORKER" value="WORKER"></el-option>
                  <el-option label="PUBLISHER" value="PUBLISHER"></el-option>
                </el-select>
                <i class="el-icon-right" style="padding: 10px"></i>
                <el-select v-model="form.testType" placeholder="请选择喜欢的测试种类" style="margin-top: 20px">
                  <el-option label="FUNCTION" value="FUNCTION"></el-option>
                  <el-option label="PERFORMANCE" value="PERFORMANCE"></el-option>
                </el-select>
                <span style="font-size: 0.9rem; padding: 10px">偏好类型</span>

              </el-form>
              <el-divider></el-divider>
              <el-form ref="basicInformation" :model="basicInformation" label-width="100px">
                <div
                  style=" text-align:left;border-left: 4px solid #39a9ff;padding-left: 8px;line-height: initial;font-size: initial;">
                  <h4>设备基本信息</h4>
                </div>
                <el-row v-for="(item,index) in basicInformation.purPeoList" :key="index" :gutter="20">
                  <el-row :gutter="10">
                    <el-col :span="8">
                      <el-form-item label="设备品牌">
                        <el-select v-model="basicInformation.purPeoList[index].deviceBrand" placeholder="请选择">
                          <el-option
                            v-for="i in options_brand"
                            :key="i.value"
                            :label="i.label"
                            :value="i.value">
                          </el-option>
                        </el-select>
                      </el-form-item>
                    </el-col>
                    <el-col :span="8">
                      <el-form-item label="设备系统">
                        <el-select v-model="basicInformation.purPeoList[index].operatingSystem" placeholder="请选择">
                          <el-option
                            v-for="i in options_os"
                            :key="i.value"
                            :label="i.label"
                            :value="i.value">
                          </el-option>
                        </el-select>
                      </el-form-item>
                    </el-col>
                    <el-col :span="8">
                      <el-button v-if="basicInformation.purPeoList.length >0" plain size="small" type="danger"
                                 @click="removeRow(index)">
                        删除
                      </el-button>
                    </el-col>
                  </el-row>
                </el-row>

                <el-col :push="18" :span="6">
                  <el-form-item>
                    <el-button size="small" @click="addBuyerInformation">新增设备</el-button>
                  </el-form-item>
                </el-col>

                <el-row :gutter="20" style="margin-top:50px;">
                  <el-col :push="18" :span="6">
                    <el-form-item label-width="0">

                    </el-form-item>
                  </el-col>
                </el-row>
              </el-form>
            </div>
          </div>
        </div>
        <!--        <div style="width: 60% ;margin-left: 20%;">-->
        <!--          -->
        <!--        </div>-->

        <el-button class="button" type="success" @click="onSubmit">注册</el-button>
        <div>
          <el-link @click="login" style="margin: 1rem">已有账号？立即登录！</el-link>
        </div>
      </el-card>
    </div>
  </div>
</template>
<script>
import {registerUser} from '@/api/user'
import NavigateMenu from "@/components/NavigateMenu";

export default {
  components: {NavigateMenu},
  data() {
    return {
      //购房人基本信息属性
      basicInformation: {
        purPeoList: [
          // {
          //   userId:'',
          //   deviceBrand: "",
          //   operatingSystem: "",
          //   id:''
          // },
        ],
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

      options_brand: [{
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
      ,

      form: {
        name: '',
        password: '',
        email: '',
        userType: '',
        testType: ''
      }
    }
  },

  methods: {
    login() {
      this.$router.push({path: '/login'})
    },
    onSubmit() {
      console.log('submit!')
      var that = this
      const registerInfo = {
        userVo: {
          id: null,
          uname: this.form.name,
          password: this.form.password,
          email: this.form.email,
          userRole: this.form.userType,
          createTime: '',
          credit: 0,
          level: 0,
          preferTypes: this.form.testType
        },
        userDeviceVos: this.basicInformation.purPeoList
      }
      console.log(registerInfo)
      registerUser(registerInfo).then(function (response) {
        console.log(response)
        if (response.data.code === 1) {
          that.$message({message: '恭喜你注册成功', type: 'success'})
          that.$router.push('/login')
        } else {
          that.$alert('该邮箱已被占用，请重试')
        }
      })
    },

    addBuyerInformation() {
      const that = this;
      that.basicInformation.purPeoList.push({userId: null, id: null});
    },
//删除购房人基本信息
    removeRow(index) {
      if (index >= 0) {
        this.basicInformation.purPeoList.splice(index, 1);
      }
    },


  }
}
</script>

<style scoped>
.fle {
  width: 50%;
  margin-left: 25%;
  margin-right: 25%;
}

.wrap {
  width: 60%;
  margin: 0 auto;
}

.container {
  margin-top: 40px;
  /*border: 2px solid rgb(70, 104, 167);*/
  /*border-radius: 10px;*/
  background-color: white;
  background-repeat: repeat;
  /*box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);*/

}

.register-container {

}

.input_box {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)
}
</style>
