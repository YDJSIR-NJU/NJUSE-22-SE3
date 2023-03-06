<template>
  <div class="navi">
    <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal"
             background-color="#4169E1"
             text-color="#fff"
             active-text-color="#ffd04b"
             @select="handleSelect"
             style="border: 0px">
      <el-menu-item index="/about"><img height="90%" src="../assets/logo.png"></el-menu-item>
      <el-menu-item v-if="userID!=null" index="/playground" style="color: #ffffff; font-size: 1rem">任务广场</el-menu-item>
      <el-menu-item v-if="userID!=null" index="/messages" style="color: #ffffff; font-size: 1rem">
        消息中心<el-badge :value=newMessageNum :max="9999" style="margin-left: 10px" :hidden="newMessageNum==0"></el-badge>
      </el-menu-item>
      <!--      <el-menu-item index="/mycenter" v-if="userID!=null">个人中心</el-menu-item>-->
      <el-menu-item v-if="role=='WORKER' && userID != null" index="/mycenter" style="color: #ffffff; font-size: 1rem">
        个人中心
      </el-menu-item>
      <el-menu-item v-if="role=='PUBLISHER' && userID != null" index="/mycenter"
                    style="color: #ffffff; font-size: 1rem">个人中心
      </el-menu-item>
      <el-menu-item v-if="role=='ADMINISTRATOR' && userID != null" index="/mycenter"
                    style="color: #ffffff; font-size: 1rem">个人中心
      </el-menu-item>

      <el-button v-if="userID==null" size=medium type="success" style="float:right;margin: 15px;margin-right: 60px"
                 @click="handleRegister">
        注册
      </el-button>
      <el-button v-if="userID==null" size=medium type="primary" style="float:right;margin: 15px;"
                 @click="handleLogin">
        登录
      </el-button>
      <el-button v-if="userID!=null" size=medium style="float:right;margin-top: 10px;margin-right: 40px" type="danger"
                 @click="handleLogout">登出{{ uname }}
      </el-button>

    </el-menu>
  </div>

</template>

<script>
import {getUnRead} from "@/api/message";

export default {
  name: 'NavigateMenu',
  created () {
    getUnRead(window.localStorage.getItem('id'), 1).then(res => {
      this.newMessageNum=res.data.total
      console.log(this.newMessageNum)
    })
  },

  data () {
    return {
      newMessageNum:0,
      role: window.localStorage.getItem('userRole'),
      userID: window.localStorage.getItem('id'),
      uname: window.localStorage.getItem('uname')
    }
  },
  methods: {
    handleSelect (key, keyPath) {
      console.log(keyPath)
      this.$router.push({ path: keyPath[0] })
      this.activeIndex = keyPath[0]
    },
    handleLogin () {
      this.$router.push({ path: '/login' })
    },
    handleMyCenter () {
      this.$router.push({ path: '/mycenter' })
    },
    handleRegister () {
      this.$router.push({ path: '/register' })
    },
    handleLogout () {
      window.localStorage.removeItem('id')
      window.localStorage.removeItem('uname')
      window.localStorage.removeItem('userId')
      this.userID = null
      this.$router.push({ path: '/about' })
      this.activeIndex = 1
      this.$message.info('已退出登录！')
    }

  }
}
</script>

<style scoped>
.el-menu-demo {
  position: fixed;
  margin-bottom: 5px;
  padding-left: 20px;
  padding-right: 20px;
  width: 100%;
  z-index: 9;
}
</style>
