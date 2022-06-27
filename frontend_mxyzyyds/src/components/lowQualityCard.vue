<template>
  <div class="collaborateCardWrapper">
    <div class="collaborateTitle">
      {{ Title }}
    </div>
    <div class="reportScore">评分：{{score }}</div>
    <el-button :size="large" style="margin-left: 10px; padding: 10px" type="success">点击协作</el-button>
    <div class="collaborateDesc">
      {{ description }}

    </div>

  </div>
</template>

<script>
export default {
  name: "collaborateCard",
  props: {
    score:1,
    Title:'xxx',
    description:'aaa',
    id:-1
  },
  data() {
    return {}

  }
}
</script>

<style scoped>
.collaborateCardWrapper {
  margin-right: 20px;
  margin-left: 20px;
  height: auto;
  border-radius: 5px;
  border: 1px solid #BDBDBD;
  margin-bottom: 10px;
  text-align: left;
}

.collaborateTitle {
  margin-top: 10px;
  margin-left: 20px;
  text-align: left;
  margin-right: 20px;
}

.collaborateDesc {
  margin-left: 20px;
  text-align: left;
  margin-right: 20px;
  margin-bottom: 10px;
}

.reportScore {
  margin-left: 20px;
  color: #999999;
  text-align: left;
  margin-right: 20px;
}


</style>
