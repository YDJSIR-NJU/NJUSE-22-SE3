<template>
  <div style="background-color: #1EFFFF; width: 100%; height: 100%; background-repeat: repeat;">
    <NavigateMenu></NavigateMenu>
    <div class="area" >
      <ul class="circles">
        <li></li>
        <li></li>
        <li></li>
        <li></li>
        <li></li>
        <li></li>
        <li></li>
        <li></li>
        <li></li>
        <li></li>
      </ul>
    </div >
    <el-card class="wrap">
        <div>
          <h1 style="margin-bottom: auto">COLLECT-注册</h1>
          <div class="container">
            <div style="width: 95%;">
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
          <el-link style="margin: 1rem" @click="login">已有账号？立即登录！</el-link>
        </div>
      </el-card>
    <COLLECTFooter></COLLECTFooter>
  </div>
</template>
<script>
import { registerUser } from '@/api/user'
import NavigateMenu from '@/components/NavigateMenu'
import COLLECTFooter from '../../components/Footer'

export default {
  components: { COLLECTFooter, NavigateMenu },
  data () {
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
  created() {
    this.addBuyerInformation ();
  },
  mounted() {
    this.setBackground();
  },
  methods: {
    login () {
      this.$router.push({ path: '/login' })
    },
    onSubmit () {
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
          that.$message({
            message: '恭喜你注册成功',
            type: 'success'
          })
          that.$router.push('/login')
        } else {
          that.$alert('该邮箱已被占用，请重试')
        }
      })
    },

    addBuyerInformation () {
      const that = this
      that.basicInformation.purPeoList.push({
        userId: null,
        id: null
      })
    },
    removeRow (index) {
      if (index >= 0) {
        this.basicInformation.purPeoList.splice(index, 1)
      }
    },
    setBackground(){
      const rand = function(min, max) {
        return Math.random() * ( max - min ) + min;
      }

      let canvas = this.$refs.canvas;
      let ctx = canvas.getContext('2d');

      canvas.width = window.innerWidth;
      canvas.height = window.innerHeight;

      window.addEventListener('resize', function() {
        canvas.width = window.innerWidth;
        canvas.height = window.innerHeight;
        ctx = canvas.getContext('2d');
        ctx.globalCompositeOperation = 'lighter';
      });
      let backgroundColors = [ '#000', '#000' ];
      let colors = [
        [ '#002aff', "#009ff2" ],
        [ '#0054ff', '#27e49b' ],
        [ '#202bc5' ,'#873dcc' ]
      ];
      let count = 70;
      let blur = [ 12, 70 ];
      let radius = [ 1, 120 ];

      ctx.clearRect( 0, 0, canvas.width, canvas.height );
      ctx.globalCompositeOperation = 'lighter';

      let grd = ctx.createLinearGradient(0, canvas.height, canvas.width, 0);
      grd.addColorStop(0, backgroundColors[0]);
      grd.addColorStop(1, backgroundColors[1]);
      ctx.fillStyle = grd;
      ctx.fillRect(0, 0, canvas.width, canvas.height);

      let items = [];

      while(count--) {
        let thisRadius = rand( radius[0], radius[1] );
        let thisBlur = rand( blur[0], blur[1] );
        let x = rand( -100, canvas.width + 100 );
        let y = rand( -100, canvas.height + 100 );
        let colorIndex = Math.floor(rand(0, 299) / 100);
        let colorOne = colors[colorIndex][0];
        let colorTwo = colors[colorIndex][1];

        ctx.beginPath();
        ctx.filter = `blur(${thisBlur}px)`;
        let grd = ctx.createLinearGradient(x - thisRadius / 2, y - thisRadius / 2, x + thisRadius, y + thisRadius);

        grd.addColorStop(0, colorOne);
        grd.addColorStop(1, colorTwo);
        ctx.fillStyle = grd;
        ctx.fill();
        ctx.arc( x, y, thisRadius, 0, Math.PI * 2 );
        ctx.closePath();

        let directionX = Math.round(rand(-99, 99) / 100);
        let directionY = Math.round(rand(-99, 99) / 100);

        items.push({
          x: x,
          y: y,
          blur: thisBlur,
          radius: thisRadius,
          initialXDirection: directionX,
          initialYDirection: directionY,
          initialBlurDirection: directionX,
          colorOne: colorOne,
          colorTwo: colorTwo,
          gradient: [ x - thisRadius / 2, y - thisRadius / 2, x + thisRadius, y + thisRadius ],
        });
      }


      function changeCanvas(timestamp) {
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        let adjX = 2;
        let adjY = 2;
        let adjBlur = 1;
        items.forEach(function(item) {

          if(item.x + (item.initialXDirection * adjX) >= canvas.width && item.initialXDirection !== 0 || item.x + (item.initialXDirection * adjX) <= 0 && item.initialXDirection !== 0) {
            item.initialXDirection = item.initialXDirection * -1;
          }
          if(item.y + (item.initialYDirection * adjY) >= canvas.height && item.initialYDirection !== 0 || item.y + (item.initialYDirection * adjY) <= 0 && item.initialYDirection !== 0) {
            item.initialYDirection = item.initialYDirection * -1;
          }

          if(item.blur + (item.initialBlurDirection * adjBlur) >= radius[1] && item.initialBlurDirection !== 0 || item.blur + (item.initialBlurDirection * adjBlur) <= radius[0] && item.initialBlurDirection !== 0) {
            item.initialBlurDirection *= -1;
          }

          item.x += (item.initialXDirection * adjX);
          item.y += (item.initialYDirection * adjY);
          item.blur += (item.initialBlurDirection * adjBlur);
          ctx.beginPath();
          ctx.filter = `blur(${item.blur}px)`;
          let grd = ctx.createLinearGradient(item.gradient[0], item.gradient[1], item.gradient[2], item.gradient[3]);
          grd.addColorStop(0, item.colorOne);
          grd.addColorStop(1, item.colorTwo);
          ctx.fillStyle = grd;
          ctx.arc( item.x, item.y, item.radius, 0, Math.PI * 2 );
          ctx.fill();
          ctx.closePath();

        });
        window.requestAnimationFrame(changeCanvas);
      }
      window.requestAnimationFrame(changeCanvas);
    }
  }
}
</script>

<style scoped>

.wrap {
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%,-50%);
  margin: 0 auto;
}

.container {
  margin-top: 40px;
  background-color: white;
  background-repeat: repeat;
}

.input_box {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)
}

.area{
  background: #67C23A;
  width: 100%;
  padding: 2rem;
  height:100vh;
}

.circles{
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  overflow: hidden;
}

.circles li{
  position: absolute;
  display: block;
  list-style: none;
  width: 50px;
  height: 50px;
  background: rgba(255, 255, 255, 0.5);
  animation: animate 25s linear infinite;
  bottom: -150px;

}

.circles li:nth-child(1){
  left: 25%;
  width: 3rem;
  height: 3rem;
  animation-delay: 0s;
}


.circles li:nth-child(2){
  left: 10%;
  width: 20px;
  height: 20px;
  animation-delay: 2s;
  animation-duration: 12s;
}

.circles li:nth-child(3){
  left: 70%;
  width: 20px;
  height: 20px;
  animation-delay: 4s;
}

.circles li:nth-child(4){
  left: 40%;
  width: 60px;
  height: 60px;
  animation-delay: 0s;
  animation-duration: 18s;
}

.circles li:nth-child(5){
  left: 65%;
  width: 20px;
  height: 20px;
  animation-delay: 0s;
}

.circles li:nth-child(6){
  left: 75%;
  width: 110px;
  height: 110px;
  animation-delay: 3s;
}

.circles li:nth-child(7){
  left: 35%;
  width: 150px;
  height: 150px;
  animation-delay: 7s;
}

.circles li:nth-child(8){
  left: 50%;
  width: 25px;
  height: 25px;
  animation-delay: 15s;
  animation-duration: 45s;
}

.circles li:nth-child(9){
  left: 20%;
  width: 15px;
  height: 15px;
  animation-delay: 2s;
  animation-duration: 35s;
}

.circles li:nth-child(10){
  left: 85%;
  width: 150px;
  height: 150px;
  animation-delay: 0s;
  animation-duration: 11s;
}



@keyframes animate {

  0%{
    transform: translateY(0) rotate(0deg);
    opacity: 1;
    border-radius: 0;
  }

  100%{
    transform: translateY(-1000px) rotate(720deg);
    opacity: 0;
    border-radius: 50%;
  }

}
</style>
