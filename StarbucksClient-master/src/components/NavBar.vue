<template>
	<nav class="nav has-shadow" style='box-shadow: 0 1px 0 rgba(219,219,219,.3);'>
		<div class="nav-left">
	    <router-link to="/" class="nav-item">
	      <img src="../assets/starbucks.jpg" alt="Bulma logo">
	    </router-link>
	  </div>
	  <span class="nav-toggle">
	    <span></span>
	    <span></span>
	    <span></span>
	  </span>

	  <!-- This "nav-menu" is hidden on mobile -->
	  <!-- Add the modifier "is-active" to display it on mobile -->
	  <div class="nav-right nav-menu">
	    <router-link to="/products" class="nav-item is-tab" exact-active-class="is-active">
	      Products
	    </router-link>
			<router-link to="/mycards" class="nav-item is-tab" exact-active-class="is-active">
	      My Cards
	    </router-link>

	    <div class="nav-item is-tab" :class="{ 'active-bottom-border': $route.path === '/cart' }">
	      <div class="field is-grouped">
	        <p class="control">
	          <router-link to='/cart' class="button is-info">
	            <span class="icon">
	              <i class="fa fa-shopping-cart"></i>
	            </span>
	            <span>Checkout ({{itemsInCart}})</span>
	          </router-link>
	        </p>
	      </div>
	    </div>
	  </div>
	</nav>
</template>

<style lang="scss">
.nav {
	height: auto;
	margin-bottom: 2rem;
}

.nav-item img {
    max-height: 3.5rem;
}

.active-bottom-border {
	border-bottom: 3px solid  #668cff;
    color: #668cff;
    padding-bottom: calc(.75rem - 8px);
}
</style>

<script>
import { mapGetters } from 'vuex'

export default {
	computed: {
		itemsInCart(){
			let cart = this.$store.getters.cartProducts;
			return cart.reduce((accum, item) => accum + item.quantity, 0)
		}
	}
}
</script>