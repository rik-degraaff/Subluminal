<template>
  <div class="hello">
    <sub-header></sub-header>
    <h1>Social</h1>
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
    <h1>Preorder</h1>
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
    <h1>Get The Game</h1>
    <li class="button-pixel">
            <div class="sub-button-top"></div>
            <div class="sub-button-left"></div>
              <a href="http://jar.subluminal.tech/Subluminal.jar" target="_blank">Download</a>
            <div class="sub-button-right"></div>
            <div class="sub-button-bottom"></div>
          </li>
    <li class="button-pixel">
      <div class="sub-button-top"></div>
      <div class="sub-button-left"></div>
        <a href="http://jar.subluminal.tech/Manual.pdf" target="_blank">Manual</a>
      <div class="sub-button-right"></div>
      <div class="sub-button-bottom"></div>
    </li>
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
  ul {
    list-style-type: none;
    padding: 0;
  }
  li {
    display: inline-block;
    margin: 0 10px;
  }
  a {
    color: #59FF00;
    text-decoration: none;
    font-size: 1.5rem;
  }
  .mini-details{
    font-size: 0.75rem;
  }

  .button-pixel{
    border: 2px solid transparent;
    padding: 10px 5px;
    position: relative;
  }

  .button-pixel:hover{
    border: 2px solid red;
    cursor:pointer;
  }

 .sub-button-top{
    position: absolute;
    top: 0;
    left: 10%;
    margin-top: -5px;
    width: 80%;
    height: 5px;
    background: #101429;
  }

  .sub-button-bottom{
    position: absolute;
    bottom: 0;
    right: 10%;
    margin-bottom: -5px;
    width: 80%;
    height: 5px;
    background: #101429;
  }

  .sub-button-left{
    position: absolute;
    margin-left: -5px;
    width: 5px;
    height: 70%;
    top: 15%;
    left: 0;
    background: #101429;
  }

  .sub-button-right{
    position: absolute;
    margin-right: -5px;
    width: 5px;
    height: 70%;
    top: 15%;
    right: 0;
    background: #101429;
  }
</style>
