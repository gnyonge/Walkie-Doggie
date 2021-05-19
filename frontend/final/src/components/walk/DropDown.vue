<template>
  <div>
    <b-dropdown id="dropdown-1" :text="Filter" variant="info">
      <b-dropdown-item @click="all()">최신순</b-dropdown-item>
      <b-dropdown-item @click="pop()">인기순</b-dropdown-item>
      <b-dropdown-item @click="top()">TOP 5</b-dropdown-item>
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
      this.Filter = "최신순"
      this.getHotPlaceListInApi({
        uid: this.getUser.uid,
        sort: "string",
        p_location: this.getUser.u_location
      })
    },
    top() {
      this.setFilter('TOP 5')
      this.Filter = "TOP 5"
      this.getTop5ListInApi({
        uid: this.getUser.uid,
        p_location: this.getUser.u_location
      })
    },
    pop() {
      this.setFilter('POP')
      this.Filter = "인기순"
      this.getHotPlaceListInApi({
        uid: this.getUser.uid,
        sort: "pop",
        p_location: this.getUser.u_location
      })
    },
  }

}
</script>

<style>
.dropdown-menu {
  padding-left: 5px;
  padding-right: 5px;
}
</style>