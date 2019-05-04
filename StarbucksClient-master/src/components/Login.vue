<template>
    <div class="login">
        <br>
        <label class="label">Welcome to Starbucks Website, please login to order!</label>
        <br>
        <div class="msg">{{ errorMessage }}</div><br>
            <label class="label">User Name:</label>
            <input class="input is-info"  v-model="username" >
            <label class="label">User Pin:</label>
            <input class="input is-info" type = "password" v-model="password">
            <br><br>
      <button class='button is-info' @click='login(username,password)'>Login</button>

    </div>
</template>
<script>
import { mapGetters, mapActions } from 'vuex'
import axios from 'axios'
export default {
    name: 'login',
      data () {
    return{
      username:"",
      password:"",
      errorMessage:null
    }
  },
    methods: {
       login(username,password){
       //console.log(username);
       var authOptions = {
        method: 'POST',
        url: 'http://cmpe202-api.juntengtan.me/user/get_user_by_username',
        data: {"username":username},
       headers: {
        'Content-Type': 'application/json'}
    };
      axios(authOptions).then((response) => {
         //console.log(response);
         if(response.data){
         var name = response.data.username
         var pwd = response.data.pin
         if(name == username && pwd == password){
            this.$router.push('/products')
            //this.$store.state.authenticateStatus = true
            this.$store.commit('SET_STATUS');
            this.$store.state.user = name
            }
            else{
                this.errorMessage = "Invalid username/password"
            }
         }else{
             this.errorMessage = "User does not exist, please register a new user!"
         }
    
         

});
       }

   }
}
</script>
<style lang="scss">
.login input{
    height: 30px;
     width: 200px;
}
.msg {
   color: #FF0000; 
}
</style>