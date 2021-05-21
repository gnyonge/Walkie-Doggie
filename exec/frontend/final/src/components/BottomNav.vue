<template>
  <v-bottom-navigation
    id="nav"
    v-model="active_tab"
    color="#48B9A8"
    light
  >
    <v-btn id="btns" v-for="tab in tabs" :key="tab.idx" @click="goTo(tab.path, tab.idx)">
      <span id="span-in-nav"><b>{{tab.name}}</b></span>
      <v-icon>{{tab.icon}}</v-icon>
    </v-btn>
  </v-bottom-navigation>
</template>

<script>
import { mapGetters, mapMutations } from 'vuex'
export default {
  name: "BottomNav",
  data() {
    return {
      active_tab: "",
      tabs: [
        {idx: 0, name: "기록일지", icon: "mdi-calendar-month-outline", path: "calendar"},
        {idx: 1, name: "산책", icon: "mdi-dog", path: "walk"},
        {idx: 2, name: "성분분석", icon: "mdi-google-analytics", path: "food"},
        {idx: 3, name: "마이페이지", icon: "mdi-account", path: "mypage"},
      ]
    }
  },
  created() {
    if (this.$route.name == 'Home') {
      this.setNowTab(1)
      }
    this.active_tab = this.getNowTab
  },
  computed: {
    ...mapGetters(['getNowTab'])
  },
  watch: {
    getNowTab(newVal) {
      this.active_tab = newVal
    },
  },
  methods: {
    ...mapMutations(['setNowTab']),
    goTo(path, idx) {
      if (this.$route.path!==`/${path}`) this.$router.push(`/${path}`)
      this.active_tab = idx
      this.setNowTab(idx)
  }}
}
</script>
<style>
#span-in-nav {
  margin-top: 8px;
  color: #323232;
}
#btns {
  background-color: white;
  margin-top: 5px;
  border-radius: 30px;
}
.v-bottom-navigation {
  display: flex;
  justify-content: space-between !important;
  border-radius: 30px 30px 0px 0px;
}
</style>