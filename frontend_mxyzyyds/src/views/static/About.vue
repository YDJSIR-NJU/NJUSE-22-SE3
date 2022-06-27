<template>
  <el-main style="width: 100%; height: max-content; padding: 0; margin: auto">
    <el-carousel
      :interval="10000"
      arrow="always"
      height="36rem"
      style="width: 100%"
    >
      <el-carousel-item
        class="banner1"
        style="display: flex;align-items: center; color: #eeeeee; ); background-repeat: repeat; background-size: cover;"
      >
        <div
          style="
                margin: 0 auto;
                text-align: left;
                width: 100%;
                color: white;
                display: flex;
              "
        >
          <div style="margin-top: 10rem; margin-left: 5%">
            <h1 style="font-size: 380%; font-weight: bold;">COLLECT-迭代一</h1>
            <h2>用户管理、任务发布、任务浏览、任务选取、报告提交、报告展示</h2>
          </div>
        </div>
      </el-carousel-item>
      <el-carousel-item
        class="banner2"
        style="display: flex;align-items: center; color: #eeeeee; ); background-repeat: repeat; background-size: cover;"
      >
        <div
          style="
                margin: 0 auto;
                text-align: left;
                width: 100%;
                color: #2c3e50;
                display: flex;
              "
        >
          <div style="margin-top: 10rem; margin-left: 5%">
            <h1 style="font-size: 380%; font-weight: bold;">COLLECT-迭代二</h1>
            <h2>任务推荐、报告审查、报告评价、报告协作</h2>
          </div>
        </div>
      </el-carousel-item>
      <el-carousel-item
        class="banner3"
        style="display: flex;align-items: center; color: #eeeeee; ); background-repeat: repeat; background-size: cover;"
      >
        <div
          style="
                margin: 0 auto;
                text-align: left;
                width: 100%;
                color: white;
                display: flex;
              "
        >
          <div style="margin-top: 10rem; margin-left: 5%">
            <h1 style="font-size: 380%; font-weight: bold;">COLLECT-迭代三</h1>
            <h2>众测工人评分框架：开放、进阶、智能</h2>
          </div>
        </div>
      </el-carousel-item>
    </el-carousel>

    <br/>
    <div style="width: 85%; margin: 0 auto">
      <h1>
        COLLECT：Collaborative Crowdsourced Testing Platform
      </h1>
      <h3>
        本项目为2022年春季学期南京大学软件学院《软件工程与计算Ⅲ》第32组大作业。
      </h3>
      <p style="font-size: 1.2rem; text-align: left; line-height: 150%">
        &emsp;&emsp;COLLECT是一个协作式众包测试平台。众包测试对复杂真实应用场景和真实用户表现的良好模拟，具有测试周期短，测试成本低等优势，具备广阔的前景。
        本系统的开发将采取螺旋开发模型，分为三次迭代，每个迭代都是一个独立的软件制品，支持CI/CD和容器化部署。本项目选用了SpringBoot、Vue+ElementUI、gunicorn+Tensorflow等业界主流框架，在开发过程中尽可能保持软件开发规范。
      </p>

    </div>

    <div style="background: white">
      <el-row
        :gutter="0"
        style="width: 90%; margin: 0 auto; text-align: center"
      >
        <el-col
          v-for="(feature, index) in systemFeature"
          :key="index"
          :span=6
        >
          <div class="grid-content">
            <div style="padding: 30px">
              <i :class=feature.icon style="height: 4rem; width: 4rem; font-size: 4rem"></i>
              <h2>{{ feature.title }}</h2>
              <p>{{ feature.text }}</p>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>

    <div style="width: 100%; background-color: #666666; margin: 0 auto; padding: 0; color: white" >
      <span style="font-size: 2rem">事不宜迟，现在从
        <el-button size="large" type="primary" style="margin: 4rem; font-size: 1.8rem" @click="myLogin">登录/注册
        </el-button>
        开始吧！
      </span>
    </div>


    <COLLECTFooter></COLLECTFooter>

  </el-main>
</template>
<script>
import COLLECTFooter from "@/components/Footer";

export default {
  components: {COLLECTFooter},
  data() {
    return {
      msg: window.localStorage.getItem('userRole'),
      name: window.localStorage.getItem('uname'),
      systemFeature: [
        {
          icon: 'el-icon-finished',
          title: '经验值机制',
          text: '众包工人的行为会积攒经验值，以记录每一个众包工人在平台的发展。'
        },
        {
          icon: 'el-icon-document-copy',
          title: '文本查重机制',
          text: '利用TF-IDF和Text2Vec技术，可以快速筛选出重复率过高的报告并将其拒绝，并生成同一任务间报告的相似关系。'
        },
        {
          icon: 'el-icon-files',
          title: '向用户推荐任务',
          text: '利用基于用户的协同过滤和基于众包工人偏好两种推荐策略，向众包工人推荐可能喜欢的任务。'
        },
        {
          icon: 'el-icon-user',
          title: '向任务推荐用户',
          text: '根据用户历史任务偏好和任务文本，智能向发包方推荐适合其发布的任务的用户。发包方可就此发出测试邀约。'
        }
      ],
    }
  },
  created() {
  },
  methods: {
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
    myCenter() {
      this.$router.push({path: "/mycenter"})
    },
    myLogin() {
      this.$router.push({path: "/login"})
    },
    myRegister() {
      this.$router.push({path: "/register"})
    },
    myTest() {
      this.$router.push({path: '/reportPage'})
    }
  }
}
</script>
<style scoped>
.el-main {
  background-color: #e9eef3;
  color: #333;
  text-align: center;
}

.el-collapse-item.el-collapse {
  background-color: #dfe2e5;
}

.banner1 {
  background-image: url("https://ydjsir-edu.oss-cn-shanghai.aliyuncs.com/SE3/pictures/collect-1.png");
  background-size: 233%;
  animation: bg_anim 40s ease infinite;
  -webkit-animation: bg_anim 40s ease infinite;
  -moz-animation: bg_anim 40s ease infinite;
}

.banner2 {
  background-image: url("https://ydjsir-edu.oss-cn-shanghai.aliyuncs.com/SE3/pictures/collect-2.png");
  background-size: 233%;
  animation: bg_anim 40s ease infinite;
  -webkit-animation: bg_anim 40s ease infinite;
  -moz-animation: bg_anim 40s ease infinite;
}

.banner3 {
  background-image: url("https://ydjsir-edu.oss-cn-shanghai.aliyuncs.com/SE3/pictures/collect-3.png");
  background-size: 233%;
  animation: bg_anim 40s ease infinite;
  -webkit-animation: bg_anim 40s ease infinite;
  -moz-animation: bg_anim 40s ease infinite;
}

@keyframes bg_anim {
  0% {
    background-position: 0% 0%;
  }
  25% {
    background-position: 20% 0%;
  }
  50% {
    background-position: 10% 10%;
  }
  75% {
    background-position: 20% 0%;
  }
  100% {
    background-position: 0% 0%;
  }
}

h1 {
  font-size: 2em;
  font-weight: normal;
}

h2 {
  font-size: 1.75rem;
  font-weight: normal;
}

h3 {
  font-size: 1.4rem;
  font-weight: normal;
}

h4 {
  font-size: 1.1rem;
  font-weight: bold;
}

a {
  text-decoration: none;
}

.router-link-active {
  text-decoration: none;
}

.carousel2 {
  background-image: linear-gradient(to bottom, rgba(243, 230, 196, 1), rgba(244, 175, 104, 1));
  background-clip: inherit;
  color: transparent;
}
</style>
