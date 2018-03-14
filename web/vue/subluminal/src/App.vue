<template>
  <div id="app">
    <pre><h1>{{ msg }}</h1></pre>
    <link href="https://fonts.googleapis.com/css?family=Press+Start+2P" rel="stylesheet">
    <img class="banner" src="./assets/subluminal_logo.png">
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'App',
  data () {
    return {
      msg: 'Welcome Traveler'
    }
  }
}
</script>

<style>
#app {
  font-family: 'Press Start 2P', cursive;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
  box-sizing: content-box;
}
html {
   background: black;
}
*{
  font-family: 'Press Start 2P', cursive;
}
.banner{
   width: 100%;
   height: auto;
}
</style>
