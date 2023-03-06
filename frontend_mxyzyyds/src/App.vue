<template>
  <div id="app">
    <el-container v-if="$route.meta.keepAlive">
      <el-header>
        <NavigateMenu :key="$route.fullPath" class="navi"></NavigateMenu>
      </el-header>
    </el-container>
    <router-view/>
  </div>
</template>

<script>

import NavigateMenu from "./components/NavigateMenu";

export default {
  components: {NavigateMenu},
  data() {
    return {
      radio: '2'
    }
  }
}
</script>

<style>
.navi {
  z-index: 999999999;
  padding: 0;
  margin-left: -20px;
}
</style>
<style lang="scss">
@import './css/globalCSS.scss';

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  height: 100%;
}

#nav {
  padding: 30px;

  a {
    font-weight: bold;
    color: #2c3e50;

    &.router-link-exact-active {
      color: #42b983;
    }
  }
}
</style>
