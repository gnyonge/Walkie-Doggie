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
import { mapActions } from 'vuex'
export default {
  name: "DropDown",
  data() {
    return {
      Filter: "Filter",
      place: "장덕동",
    }
  },
  methods: {
    ...mapActions(['getHotPlaceListInApi', 'getTop5ListInApi']),
    all() {
      this.Filter = "All"
      this.getHotPlaceListInApi(this.place)
    },
    top() {
      this.Filter = "Top 5"
      this.getTop5ListInApi(this.place)
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