import Vue from 'vue'
import Vuex from 'vuex'
import * as types from './mutation-types'

Vue.use(Vuex)

const debug = process.env.NODE_ENV !== 'production'

// initial state
const state = {
  added: [],
  user:"",
  totalAmount: 0,
  authenticateStatus:false,
  all: [
    {
      id: '1',
      name: 'Cold Brew',
      description: 'Ice, Brewed Coffee, Cascara Cold Foam',
      price: 1.5
    },
    {
      id: '2',
      name: 'Caffè Vanilla Frappuccino',
      description: 'Ice, Milk, Coffee Frappuccino Syrup',
      price: 2
    },
    {
      id: '3',
      name: 'Nitro Lemon Fog',
      description: 'Lemon spiced cold foam,Lemonade,Vanilla Syrup',
      price: 3
    }
  ]
}

// getters
const getters = {
	allProducts: state => state.all, // would need action/mutation if data fetched async
	getNumberOfProducts: state => (state.all) ? state.all.length : 0,
  totalAmount: state => state.totalAmount,
  authenticateStatus: state => state.authenticateStatus,
  user:state => state.user,
  cartProducts: state => {
		return state.added.map(({ id, quantity }) => {
			const product = state.all.find(p => p.id === id)

			return {
				name: product.name,
				price: product.price,
				quantity
			}
		})
	}
}

// actions
const actions = {
	addToCart({ commit }, product){
		commit(types.ADD_TO_CART, {
			id: product.id
		})
  },
  checkout({ commit },amount){
    commit('SET_TOTAL_AMOUNT', amount);
  },
  setStatus ({commit}) {
    commit('SET_STATUS');
  }
}

// mutations
const mutations = {

	[types.ADD_TO_CART] (state, { id }) {
	    const record = state.added.find(p => p.id === id)

	    if (!record) {
	      state.added.push({
	        id,
	        quantity: 1
	      })
	    } else {
	      record.quantity++
	    }
    },
    SET_TOTAL_AMOUNT(state, value) {
      state.totalAmount = value;
    },
    SET_STATUS(state){
      state.authenticateStatus = true;
    },
}

// one store for entire application
export default new Vuex.Store({
	state,
	strict: debug,
	getters,
	actions,
	mutations
})