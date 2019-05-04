<template>
  <div class="My-Cards">
    <div class = "label" v-if="amount>0">Your Total Amount Due is ${{ amount }}</div>
    <br>
    <h1 class="title">Add Card</h1>
    <new-card></new-card>
    <div v-if="cards" class="table-wrap">
        <br>
        <h2 class="title">My Cards</h2>
      <table class="table is-striped">
        <tr>
          <td>Card Number</td>
          <td>Card Code</td>
          <td>Balance</td>
          <td></td>
        </tr>
        <tr v-for="card in cards" :key="card.number">
          <td>{{ card.cardNumber }}</td>
          <td>{{ card.cardCode }}</td>
          <td>${{ card.balance }}</td>
          <td><button @click='pay(card)' class='button is-info'>Pay</button></td>
        </tr>
      </table>
    </div>


  </div>

</template>
<script>
import axios from 'axios'
import { mapGetters, mapActions } from 'vuex'
import NewCard from './NewCard';
export default {
  computed: {
    ...mapGetters({
      amount: 'totalAmount'
    })
    },
  name: 'cards',
  components: {
    'new-card': NewCard,
  },
  data () {
    return{
      cards:null
    }
  },
  beforeMount(){
    this.$store.commit('SET_STATUS')
 },
  mounted(){
       var authOptions = {
        method: 'POST',
        url: 'http://cmpe202-api.juntengtan.me/card/get_cards_by_username',
        data: {"username":"cmpe202"},
       headers: {
        'Content-Type': 'application/json'}
    };
      axios(authOptions).then((response) => {
         this.cards = response.data
       
});
},
  methods: {
    pay(card){
        var authOptions = {
        method: 'POST',
        url: 'http://cmpe202-api.juntengtan.me/card/update_card_by_card_number',
        data: {"card_number":card.cardNumber,"balance":card.balance-this.amount},
       headers: {
        'Content-Type': 'application/json'}
    };
      axios(authOptions).then((response) => {
         console.log(this.response);
         location.reload();
         this.$store.commit('SET_STATUS')
});
    }

  }
    // return {
    //   cards: [{
    //   number: "123456789",
    //   code: "123",
    //   balance:"20"
    // },{
    //   number: "987654321",
    //   code: "321",
    //   balance:"20"
    // }]
    // }
 
};
</script>