<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'App',
}
</script>

<style lang="scss">
@font-face {
    font-family: 'PxPlus IBM VGA9';
    src: url('~@/assets/PxPlus_IBM_VGA9.eot');
    src: url('~@/assets/PxPlus_IBM_VGA9.eot?#iefix') format('embedded-opentype'),
        url('~@/assets/PxPlus_IBM_VGA9.woff2') format('woff2'),
        url('~@/assets/PxPlus_IBM_VGA9.woff') format('woff'),
        url('~@/assets/PxPlus_IBM_VGA9.ttf') format('truetype');
    /*font-weight: normal;*/
    font-style: normal;
}
#app {
  font-family: 'PxPlus IBM VGA9', cursive;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: white;
  margin-top: 60px;
  box-sizing: content-box;
}
html {
   background: #101429;
}

</style>
