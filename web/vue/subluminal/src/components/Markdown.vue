<template>
  <div class="latex-comp">
      <h1>Game Rules</h1>
        <div class="sub-rules">
                {{rules}}
        </div>
  </div>
</template>

<script>
import rules from '../assets/GameRules.md'

export default {
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


