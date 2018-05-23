<template>
  <div>
    <h1>{{ msg }}</h1>
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

