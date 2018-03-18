<template>
  <div class="hello">
    <sub-header></sub-header>
    <h3>Social</h3>
    <ul>
      <li class="button-pixel">
        <div class="sub-button-top"></div>
        <div class="sub-button-left"></div>
        <a
          href="http://twitter.com/subluminalgame"
          target="_blank"
        >
          Twitter
        </a>
        <div class="sub-button-right"></div>
        <div class="sub-button-bottom"></div>
      </li>
      <li class="button-pixel">
        <div class="sub-button-top"></div>
        <div class="sub-button-left"></div>
        <a
          href="http://instagram.com/subluminalgame"
          target="_blank"
        >
          Instagram
        </a>
        <div class="sub-button-right"></div>
        <div class="sub-button-bottom"></div>
      </li>
      <li class="button-pixel">
        <div class="sub-button-top"></div>
        <div class="sub-button-left"></div>
        <a
          href="https://www.reddit.com/r/subluminal/"
          target="_blank"
        >
          Reddit
        </a>
        <div class="sub-button-right"></div>
        <div class="sub-button-bottom"></div>
      </li>
    </ul>
    <h3>Preorder</h3>
    <ul>
      <li class="button-pixel">
        <div class="sub-button-top"></div>
        <div class="sub-button-left"></div>
        <a
          href="http://buy.subluminal.tech/store.steampowered.com/app/427520/Factorio/index.html"
          target="_blank"
        >
          Steam*
          <p class="mini-details">*not an actual steam page</p>
        </a>
        <div class="sub-button-right"></div>
        <div class="sub-button-bottom"></div>
      </li>

    </ul>
        <ul>
          <li class="button-pixel">
            <div class="sub-button-top"></div>
            <div class="sub-button-left"></div>
              <router-link to="/gamerules">Game Rules</router-link>
            <div class="sub-button-right"></div>
            <div class="sub-button-bottom"></div>
          </li>

        </ul>
    <h1>More Coming Soon</h1>
  </div>
</template>

<script>
import Header from './Header'
export default {
  name: 'HelloWorld',
  components: {
    'sub-header': Header
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  h1, h2 {
    font-weight: normal;
  }
  ul {
    list-style-type: none;
    padding: 0;
  }
  li {
    display: inline-block;
    margin: 0 10px;
  }
  a {
    color: #42b983;
    text-decoration: none;
  }
  .mini-details{
    font-size: 0.25rem;
  }

  .button-pixel{
    border: 1px solid transparent;
    padding: 10px 5px;
    position: relative;
  }

  .button-pixel:hover{
    border: 1px solid red;
  }

 .sub-button-top{
    position: absolute;
    top: 0;
    left: 10%;
    margin-top: -5px;
    width: 80%;
    height: 5px;
    background: black;
  }

  .sub-button-bottom{
    position: absolute;
    bottom: 0;
    right: 10%;
    margin-bottom: -5px;
    width: 80%;
    height: 5px;
    background: black;
  }

  .sub-button-left{
    position: absolute;
    margin-left: -5px;
    width: 5px;
    height: 70%;
    top: 15%;
    left: 0;
    background: black;
  }

  .sub-button-right{
    position: absolute;
    margin-right: -5px;
    width: 5px;
    height: 70%;
    top: 15%;
    right: 0;
    background: black;
  }
</style>
