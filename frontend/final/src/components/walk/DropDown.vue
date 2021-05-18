<template>
  <div>
    <b-dropdown id="dropdown-1" :text="Filter" variant="info">
      <b-dropdown-item @click="all()">All</b-dropdown-item>
      <b-dropdown-item @click="top()">Top 5</b-dropdown-item>
      <!-- <b-dropdown-item active>Active action</b-dropdown-item> -->
    </b-dropdown>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
export default {
  name: "DropDown",
  data() {
    return {
      Filter: "Filter",
    }
  },
  computed:{
    ...mapGetters(['getUser'])
  },
  methods: {
    ...mapActions(['getHotPlaceListInApi', 'getTop5ListInApi']),
    all() {
      this.Filter = "All"
      this.getHotPlaceListInApi({
        uid: this.getUser.uid,
        sort: "string",
        p_location: this.getUser.u_location
      })
    },
    top() {
      this.Filter = "Top 5"
      this.getTop5ListInApi({
        uid: this.getUser.uid,
        p_location: this.getUser.u_location
      })
    }
  }

}
</script>

<style>
.dropdown-menu {
  padding-left: 5px;
  padding-right: 5px;
}
</style>