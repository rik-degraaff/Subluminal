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
      msg: 'Welcome Traveler,'+ '\n' + 'You Just Discovered'
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
}
* {
   font-family: 'Press Start 2P', cursive;
   line-height: 1.5rem;
   font-size: 1.25rem;
   background: black;
}
.banner{
   width: 100%;
   height: auto;
}
</style>
