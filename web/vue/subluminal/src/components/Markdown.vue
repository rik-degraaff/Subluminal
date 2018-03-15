<template>
  <div class="latex-comp">
      <h1>Game Rules</h1>
        <div class="sub-rules">
            <vue-markdown>
                {{rules}}
            </vue-markdown>
        </div>
  </div>
</template>

<script>
import rules from '../assets/GameRules.md'
import markdown from 'vue-markdown'

export default {
    data() {
        return {
            rules: rules.source
        }
    },
    name: 'Markdown',
    mounted() {
      console.log(rules.source)
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


