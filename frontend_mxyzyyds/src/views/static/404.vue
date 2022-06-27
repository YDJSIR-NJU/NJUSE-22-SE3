<template>
  <div class="bckg">
    <div class="head">
    </div>
    <div style="font-size: 40px">404</div>
    <img src="../../assets/404.png">
    <el-divider></el-divider>
    <div class="title">页面消失了哦o(╥﹏╥)o</div>
    <el-divider></el-divider>
    <el-button size="large" @click="myCenter">{{ fallback }}</el-button>
  </div>
</template>
<script>
export default {
  data() {
    return {
      msg: window.localStorage.getItem('userRole'),
      name: window.localStorage.getItem('uname'),
      Hello: null,
      fallback: "返回"
    }
  },
  created() {
    if (window.localStorage.getItem('id') != null) {
      this.fallback = "返回个人中心"
    } else {
      this.fallback = "返回首页"
    }
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
    myCenter() {
      if (window.localStorage.getItem('id') != null)
        this.$router.push({path: "/mycenter"})
      else {
        this.$router.push({path: "/"})
      }
    }
  }
}
</script>
<style scoped>
.head {
  width: 100%;
  height: 50px;
}

.title {
  font-weight: 500;
  font-size: 60px;
}

</style>
