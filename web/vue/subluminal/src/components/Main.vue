<template>
  <div class="hello">
    <sub-header></sub-header>
    <h3>Social</h3>
    <ul>
      <li>
        <a
          href="http://twitter.com/subluminalgame"
          target="_blank"
        >
          Twitter
        </a>
      </li>
      <li>
        <a
          href="http://instagram.com/subluminalgame"
          target="_blank"
        >
          Instagram
        </a>
      </li>
      <li>
        <a
          href="https://www.reddit.com/r/subluminal/"
          target="_blank"
        >
          Reddit
        </a>
      </li>
    </ul>
    <h3>Preorder</h3>
    <ul>
      <li class="button-pixel">
        <a
          href="http://buy.subluminal.tech/store.steampowered.com/app/427520/Factorio/index.html"
          target="_blank"
        >
          Steam*
          <p class="mini-details">*not an actual steam page</p>
        </a>
      </li>

    </ul>
        <ul>
          <li class="button-pixel">
            <router-link to="/gamerules"><h2>Game Rules</h2></router-link>
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
    border: 3px solid transparent;
    padding: 10px 5px;
  }

  .button-pixel:hover{
    border: 3px red solid;
  }
</style>
