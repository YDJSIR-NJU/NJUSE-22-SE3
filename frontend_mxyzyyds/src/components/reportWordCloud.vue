<template>
  <div>

    <el-row :gutter="20">
      <el-col :span="8">
        <span>聚类1</span><div  ref="wordcloud0" style="display: inline-block;height:15rem;width:100%; margin-top: 1rem" class="grid-content" ></div></el-col>
      <el-col :span="8"><span>聚类2</span><div  ref="wordcloud1" style="display: inline-block;height:15rem;width:100%; margin-top: 1rem" class="grid-content" ></div></el-col>
      <el-col :span="8"><span>聚类3</span><div  ref="wordcloud2" style="display: inline-block;height:15rem;width:100%; margin-top: 1rem" class="grid-content" ></div></el-col>
    </el-row>
  </div>
</template>

<script>
import {getReportWords} from "../api/report";

import * as echarts from 'echarts'
import "echarts-wordcloud/dist/echarts-wordcloud"
import "echarts-wordcloud/dist/echarts-wordcloud.min"

export default {
  props:{
    taskId:''
  },
  name: "reportWordCloud",
  data(){
    return {
      wordCloud0:[],
      wordCloud1:[],
      wordCloud2:[],
      wordList:[]
    }
  },
  mounted() {
    var that=this
    getReportWords(parseInt(localStorage.getItem('id')),3,10,this.taskId).then(res=>{
      console.log("获取报告聚类关键词")
      console.log(res.data.data)
      if(res.data.data.length==1){
        that.wordCloud0=res.data.data[0].wordcloud
      }else if(res.data.data.length==2){
        that.wordCloud0=res.data.data[0].wordcloud
        that.wordCloud1=res.data.data[1].wordcloud
      }else {
        that.wordCloud0 = res.data.data[0].wordcloud
        that.wordCloud1 = res.data.data[1].wordcloud
        that.wordCloud2 = res.data.data[2].wordcloud
      }
      //console.log(that.wordCloud0)

      var wordlist = that.wordCloud0
      // console.log(wordlist)
      for (let key in wordlist) {
        // console.log(key) // name
        // console.log(wordlist[key]) // value
        that.wordList.push({'name': key, 'value': parseInt(this.round2percent(wordlist[key]))})
      }
      that.initWordCloud(0)


      wordlist=that.wordCloud1
      that.wordList=[]
      for (let key in wordlist) {
        // console.log(key) // name
        // console.log(wordlist[key]) // value
        that.wordList.push({'name': key, 'value': parseInt(this.round2percent(wordlist[key]))})
      }
      that.initWordCloud(1)


      wordlist=that.wordCloud2
      that.wordList=[]
      for (let key in wordlist) {
        // console.log(key) // name
        // console.log(wordlist[key]) // value
        that.wordList.push({'name': key, 'value': parseInt(this.round2percent(wordlist[key]))})
      }
      that.initWordCloud(2)
    })
  },
  methods:{
    initWordCloud(index) {
      var myChart
      if(index===0){
        myChart=echarts.init(this.$refs.wordcloud0);
      }else if(index===1){
        myChart=echarts.init(this.$refs.wordcloud1);
      }else{
        myChart=echarts.init(this.$refs.wordcloud2);
      }
      // console.log(this.wordList)
      myChart.setOption({
        series: [
          {
            type: "wordCloud",
            gridSize: 10,
            sizeRange: [14, 25],
            rotationRange: [0, 0],
            textStyle: {
              color: function () {
                return (
                  "rgb(" +
                  Math.round(Math.random() * 210) +
                  ", " +
                  Math.round(Math.random() * 210) +
                  ", " +
                  Math.round(Math.random() * 210) +
                  ")"
                );
              },
              emphasis: {
                shadowBlur: 10,
                shadowColor: '#333'
              }
            },
            backgroundColor: 'rgba(105,105,105,.0)',
            //位置相关设置
            left: "center",
            top: "center",
            right: null,
            bottom: null,
            width: "200%",
            height: "200%",
            //数据
            data: this.wordList
          }
        ]
      })
    },
    round2percent(num) {
      num = Math.round(num * Math.pow(10, 4)) / Math.pow(10, 4)
      num = Number(num * 100).toFixed(2)
      return num
    },
  }
}
</script>

<style scoped>
.el-row {
  margin-bottom: 20px;
  &:last-child {
   margin-bottom: 0;
 }
}
.el-col {
  border-radius: 4px;
}
.bg-purple-dark {
  background: #99a9bf;
}
.bg-purple {
  background: #d3dce6;
}
.bg-purple-light {
  background: #e5e9f2;
}
.grid-content {
  border-radius: 4px;
  min-height: 150px;
}
.row-bg {
  padding: 10px 0;
  background-color: #f9fafc;
}

</style>
