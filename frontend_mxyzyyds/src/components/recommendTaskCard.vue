<template>
  <div class="Card">
    <div class="cardTitle">
      任务ID：{{ item.id }}
    </div>
    <div class="cardDescription">{{ item.taskDiscribe }}</div>
    <div class="cardCharacters">
      <el-tag>{{ item.deviceBrand }}</el-tag>&nbsp;
      <el-tag>{{ item.type }}</el-tag>&nbsp;
      <el-tag>{{ item.operatingSystem }}</el-tag>&nbsp;
      <el-tag type="success">{{ item.difficulty }}分</el-tag>
    </div>

  </div>

</template>

<script>
export default {
  name: "recommendTaskCard",
  props: {
    item: {}
  },
  data() {
    return {}
  }
}
</script>

<style scoped>
.Card {
  border-radius: 5px;
  border: 1px solid #BDBDBD;
  margin-left: 20px;
  margin-right: 20px;
  margin-bottom: 10px;
}

.cardTitle {
  margin-top: 5px;
  margin-left: 20px;
  font-weight: 500;
  text-align: left;
  font-size: 18px;

}

.cardDescription {
  text-align: left;
  margin-left: 20px;
  margin-right: 20px;
  margin-bottom: 5px;
}

.cardCharacters {
  text-align: left;
  font-size: 10px;
  margin-left: 20px;
  color: #999999;
  margin-bottom: 10px;
}

</style>
