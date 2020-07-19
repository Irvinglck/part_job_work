import Vue from 'vue'
import Router from 'vue-router'
import PayList from '@/components/PayList'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'PayList',
      component: PayList
    }
  ]
})
