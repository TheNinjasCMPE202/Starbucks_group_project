<template>
    <div class="new-card">
        <div class="msg">{{ errorMessage }}</div><br>
        <form action="#" method="post" v-on:submit.prevent="submitForm">
            <label class="label">Card Number:</label>
            <input class="input is-info"  type="number" v-model="cardNumber" maxlength="9" placeholder="9 digits">
            <label class="label">Card Code:</label>
            <input class="input is-info"  type="number" v-model="cardCode" placeholder="3 digits">
            <br><br>
            <button class='button is-info' @click='add(cardNumber,cardCode)'>Add</button>
        </form>
    </div>
</template>
<script>
import axios from 'axios'
export default {
  name: 'NewCard',
  data() {
    return {
      cardNumber: '',
      cardCode:'',
      errorMessage:null
    };
  },  
  beforeMount(){
    this.$store.commit('SET_STATUS')
 },
  methods: {
    submitForm() {
      if (this.cardNumber && this.cardCode ) {
        this.$store.commit('addCard', {
          number: this.cardNumber,
          code: this.cardCode
        });
        }
    },
    add(cardNumber,cardCode) {
      if(cardNumber.length == 9 && cardCode.length ==3){
        var authOptions = {
        method: 'POST',
        url: 'http://cmpe202-api.juntengtan.me/add_card',
        data: {"card_number":cardNumber,"card_code":cardCode,"balance":20,"username":"cmpe202"},
       headers: {
        'Content-Type': 'application/json'}
       };
       axios(authOptions).then((response) => {
         //console.log(this.response);
         location.reload();
         this.$store.commit('SET_STATUS')
});
    }else{
        this.errorMessage ="Wrong card number/pin"
    }
    }
  }
}
</script>
<style lang="scss">
.new-card input{
    height: 30px;
     width: 200px;
}
.msg {
   color: #FF0000; 
}

</style>
