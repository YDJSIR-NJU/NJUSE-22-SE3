import Vue from 'vue';
import VueRouter from 'vue-router';


Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    redirect: 'about',
  },
  {
    path: '/about',
    name: 'about',
    component: () => import('@/views/static/About'),
    meta: {
      title: 'COLLECT',
      keepAlive: true,
      needRouter:false

    }
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('@/views/public/Login'),
    meta: {
      title: '用户登录',
      keepAlive: false,
      needRouter:false
    }
  },
  {
    path: '/mycenter',
    name: "mycenter",
    component: () => import('@/views/common/userLayout'),
    meta: {
      title: '用户中心',
      keepAlive: true
    }
  },
  {
    path: "/addTask",
    name: "addTask",
    component: () => import('@/views/publisher/addTask'),
    meta: {
      title: '发布任务',
      keepAlive: true
    }
  },
  {
    path: "/playground",
    name: "playground",
    component: () => import('@/views/common/tasksPlayground'),
    meta: {
      title: '任务广场',
      keepAlive: true
    }
  },
  {
    path: "/register",
    name: "register",
    component: () => import('@/views/public/Register'),
    meta: {
      title: '注册-COLLECT',
      keepAlive: false,
      needRouter:false
    }
  }, {
    path: "/taskInfo",
    name: "taskInfo",
    component: () => import('@/views/common/testPage'),
    meta: {
      title: '任务详情',
      keepAlive: true
    }
  }, {
    path: "/mydeliver",
    name: "mydeliver",
    component: () => import('@/views/common/testPage'),
    meta: {
      title: '任务详情',
      keepAlive: true
    }
  }
  , {
    path: "/myhistory",
    name: "myhistory",
    component: () => import('@/views/worker/taskListHistory'),
    meta: {
      title: '我完成的',
      keepAlive: true
    }
  }, {
    path: "/myProcessing",
    name: "myProcessing",
    component: () => import('@/views/worker/taskListProcessing'),
    meta: {
      title: '我在做的',
      keepAlive: true
    }
  },
  {
    path: "/myPublished",
    name: "myPublished",
    component: () => import('@/views/publisher/myTasks'),
    meta: {
      title: '我发布的',
      keepAlive: true
    }
  }, {
    path: "/reportList",
    name: "reportList",
    component: () => import('@/views/publisher/reportList'),
    meta: {
      title: '报告列表',
      keepAlive: true

    }
  },
  {
    path: "/reportPage",
    name: "reportPage",
    component: () => import('@/views/common/newReportPage'),
    meta: {
      title: '报告详情',
      keepAlive: true
    }
  },
  {
    path: "/allTasks",
    name: "allTasks",
    component: () => import('@/views/admin/allTask'),
    meta: {
      title: '全部任务',
      keepAlive: true
    }
  },
  {
    path: "/submitReport",
    name: "submitReport",
    component: () => import('@/views/worker/submitReport'),
    meta: {
      title: '提交众测报告',
      keepAlive: true
    }
  }, {
    path: "/messages",
    name: "messages",
    component: () => import('@/views/common/messages'),
    meta: {
      title: '消息中心',
      keepAlive: true
    }
  }, {
    path: "/userInfo",
    name: "userInfo",
    component: () => import('@/views/common/userInfo'),
    meta: {
      title: '用户信息',
      keepAlive: true
    }
  },
  {
    path: "/collaborateReportPage",
    name: "collaborateReportPage",
    component: () => import('@/views/worker/collaborateReportPage'),
    meta: {
      title: '报告协作页',
      keepAlive: true
    },
    beforeEnter(to,from,next){
      if (localStorage.getItem('userRole') === 'PUBLISHER') {    //权限控制的具体规则
        alert("没有权限访问这个页面哦")
      } else {
        next()
      }
    }
  },
  {
    path: '/additionalReportPage',
    name: 'additionalReportPage',
    component: () => import('@/views/common/additionalReportPage'),
    meta: {
      title: 'additionalReportDetail',
      keepAlive: true
    }
  },
  {
    path: '*',    // 此处需特别注意至于最底部
    name: '404',
    component: () => import('../views/static/404.vue'),
    meta: {
      title: '404-COLLECT',
      keepAlive: false
    }
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})
router.beforeEach((to,from,next)=>{
  if(localStorage.getItem('id')!=null||to.meta.needRouter==false){
    next()
  }else{
    alert('请先登录！')
    next({path:'/login'})
  }

})

export default router
