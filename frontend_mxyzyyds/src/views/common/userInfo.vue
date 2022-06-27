<template>

  <div>
    <h1 style="text-align: left; margin-left: 2rem">
      {{ userInfo.userRole }}用户{{ userId }}（{{ userInfo.uname }}）的个人主页
    </h1>
    <worker-info :userId="userId"></worker-info>

  </div>

</template>

<script>

import WorkerInfo from "@/components/workerInfo";
import {getUser} from "@/api/user";

export default {
  name: "messages",
  components: {WorkerInfo},
  props: {
    item: {}
  },
  created() {

  },
  mounted() {
    this.fetchData()
  },
  data() {
    return {
      userId: this.$route.query.userId,
      userInfo: {
        id: 0,
        createTime: '',
        credit: 0,
        level: 0,
        preferTypes: '',
        uname: '',
        userRole: '',
      },
    }
  },
  methods: {
    fetchData() {
      const that = this
      getUser(this.userId).then(res => {
        // console.log("User")
        // console.log(res)
        that.userInfo = res.data.data
        if (that.userInfo.userRole === 'WORKER') {
          that.userInfo.userRole = "众包工人"
        }
      })
    },
  }


}
</script>

<style scoped>
.box-card {
  margin-left: 20%;
  margin-right: 20%;
  margin-top: 20px;
}

.messageText {
  margin: auto;
}


</style>
