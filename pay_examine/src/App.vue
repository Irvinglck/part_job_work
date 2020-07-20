<template>
  <div id="app">
<!--    <img src="./assets/logo.png">-->
    <img src="./assets/bani_logo.png">
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 20px;

}
  #app img {
    margin-bottom: 20px;
  }
</style>
