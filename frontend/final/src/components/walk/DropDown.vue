<template>
  <div>
    <b-dropdown id="dropdown-1" :text="Filter" variant="info">
      <b-dropdown-item @click="all()">모든 게시글</b-dropdown-item>
      <b-dropdown-item @click="top()">탑 5 인기글</b-dropdown-item>
      <!-- <b-dropdown-item active>Active action</b-dropdown-item> -->
    </b-dropdown>
  </div>
  
</template>

<script>
import { mapActions, mapGetters, mapMutations } from 'vuex'
export default {
  name: "DropDown",
  data() {
    return {
      Filter: "필터",
    }
  },
  computed:{
    ...mapGetters(['getUser'])
  },
  methods: {
    ...mapMutations(['setFilter']),
    ...mapActions(['getHotPlaceListInApi', 'getTop5ListInApi']),
    all() {
      this.setFilter('ALL')
      this.Filter = "All"
      this.getHotPlaceListInApi({
        uid: this.getUser.uid,
        sort: "string",
        p_location: this.getUser.u_location
      })
    },
    top() {
      this.setFilter('TOP5')
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