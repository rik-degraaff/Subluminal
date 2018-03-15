<template>
  <div class="latex-comp">
      <h1>Game Rules</h1>
        <div class="sub-rules">
                {{rules}}
        </div>
        <markdown></markdown>
  </div>
</template>

<script>
import Markdown from '../assets/GameRules.md'

export default {
    components: {Markdown},
    data() {
        return {
            rules: "test"
        }
    },
    name: 'Markdown',
    mounted() {
      console.log(rules)
  }
}
</script>

<style>
    h2{
        color: white;
    }
    .sub-rules{
        color: white;
    }
</style>


