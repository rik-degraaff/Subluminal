import Vue from 'vue'
import Router from 'vue-router'
import Main from '@/components/Main'
import Markdown from '@/components/Markdown'
import Disclaimer from '@/components/Disclaimer'

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Main',
      component: Main
    },
    {
      path: '/gamerules',
      name: 'Markdown',
      component: Markdown

    },
    {
      path: '/disclaimer',
      name: 'Disclaimer',
      component: Disclaimer
    }
  ],
})
