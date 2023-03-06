<template>
  <div style="background-color: #87CEFA; width: 100%; height: 100%; background-repeat: repeat; ">
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
    <div style="background-color: #87CEFA; width: 100%; height: fit-content; background-repeat: repeat; margin: 0 auto;">
      <el-card class="wrap">
        <div class="fle">
          <h1 style="margin-bottom: auto">COLLECT-登录</h1>
          <div class="container" style="text-align: center">
            <div
              style=" text-align:left;border-left: 4px solid #39a9ff;padding-left: 8px;line-height: initial;font-size: initial;">
              <h4>账户凭据</h4>
            </div>
            <el-form label-width="100px">
              <el-form-item label="注册邮箱">
                <el-input v-model="loginForm.username" class="input_box" placeholder="请输入邮箱"
                          @keyup.enter.native="handleLogin"></el-input>
              </el-form-item>
              <el-form-item label="用户密码">
                <el-input v-model="loginForm.password" class="input_box" placeholder="请输入密码" show-password
                          @keyup.enter.native="handleLogin"></el-input>
              </el-form-item>
            </el-form>
            <el-button class="button" type="primary" @click="handleLogin">登录</el-button>
            <div>
              <el-link @click="register">没有账号？立即注册！</el-link>
            </div>
            <span><br></br>
              为快速检查对应成果，请使用文档中给定的用户。
              <br></br>
              管理员用户不能注册，只有一个：邮箱admin@collect.com。<br>
              发包方用户可选publisherX@collect.com，其中X可以是1到9（含）之间的任何一个数字。建议选2。
              众包工人用户可选workerX@collect.com，其中X可以是1到9（含）之间的任何一个数字。建议选5。
            </span>
          </div>
        </div>
      </el-card>
      <COLLECTFooter></COLLECTFooter>
    </div>

  </div>
</template>
<script>
import {loginUser} from '@/api/user'
import NavigateMenu from "@/components/NavigateMenu";
import COLLECTFooter from '../../components/Footer'

export default {
  components: {
    COLLECTFooter,
    NavigateMenu},
  data() {
    return {
      loginForm: {
        username: '',
        password: ''
      }
    }
  },
  methods: {
    handleLogin() {
      var that = this
      const loginInfo = {
        id: null,
        uname: this.loginForm.username,
        password: this.loginForm.password,
        email: this.loginForm.username,
        userRole: null,
        createTime: ''
      }
      loginUser(loginInfo).then(response => {
        console.log(response)
        if (response.data.code === 0) {
          console.log("登录失败，用户名或密码不对")
          that.$message.error("登陆失败，请检查用户名或密码！")
        } else {
          console.log("登录成功")
          // that.$message({message: "登陆成功！", type: 'success'})
          window.localStorage.setItem('userRole', response.data.data.userRole)
          window.localStorage.setItem('id', response.data.data.id)
          window.localStorage.setItem('uname', response.data.data.uname)
          // that.$store.state.token = response.headers['authorization']
          // that.$store.commit('setToken', {token: response.headers['authorization']})
          // localStorage.setItem('store', JSON.stringify(this.$store.state))
          console.log(response.headers['authorization'])
          // window.localStorage.setItem('token', response.headers['authorization'])
          // console.log(window.localStorage.getItem("token"))
          that.$message.success("欢迎回来，" + response.data.data.uname)
          that.$router.push({path: '/mycenter'})
        }
      })
    },
    register() {
      this.$router.push({path: '/register'})
    }
  }
}
</script>
<style scoped>

.fle {
  width: 50%;
  margin-left: 25%;
  margin-right: 25%;
}

.container {
  margin-top: 40px;
  background-color: white;
  background-repeat: repeat;
}

.button {
  margin: 15px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)
}

.input_box {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)
}

.wrap {
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%,-50%);
  margin: 0 auto;
}

.area{
  background: #409EFF;
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
