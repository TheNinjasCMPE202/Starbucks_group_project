<template>
  <div id="app" class='container'>
   
    <nav-bar v-if="authenticated"></nav-bar>
     <div v-if="username"><strong>Welcome, {{ username }}!</strong></div>
     <br>
    <router-view></router-view>
  </div>
</template>

<script>
import NavBar from './components/NavBar.vue'
import { mapGetters, mapActions } from 'vuex'

export default {
  name: 'app',
  components: {
    NavBar
  },
  computed: {
    ...mapGetters({
      authenticated: 'authenticateStatus',
      username:'user'
    })
    },
  data () {
    return {
    }
  }
}
</script>