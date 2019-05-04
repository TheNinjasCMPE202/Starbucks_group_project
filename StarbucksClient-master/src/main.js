import Vue from 'vue'
import VueRouter from 'vue-router'
import store from './store/index.js'
import App from './App.vue'
import Products from './components/Products.vue'
import MyCards from './components/MyCards.vue'
import Cart from './components/Cart.vue'
import Login from './components/Login.vue'

Vue.use(VueRouter)

// Define routes
const routes = [
  { path: '/', component: Login },
  { path: '/products', component: Products },
  { path: '/cart', component: Cart },
  { path: '/mycards', component: MyCards },
]

// Register routes
const router = new VueRouter({
  routes
})

new Vue({
  el: '#app',
  render: h => h(App),
  router,
  store
})