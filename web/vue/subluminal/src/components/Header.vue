<template>
  <div>
    <pre><h1>{{ msg }}</h1></pre>
    <link href="https://fonts.googleapis.com/css?family=Press+Start+2P" rel="stylesheet">
    <img class="banner" src="../assets/subluminal_logo.png">
  </div>
</template>

<script>
export default {
  name: 'Header',
  data () {
    return {
      msg: 'Welcome Traveler'
    }
  }
}
</script>


<style>
    .banner{
        width: 100%;
        height: auto;
    }
</style>

