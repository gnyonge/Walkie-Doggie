<template>
  <v-bottom-navigation
    id="nav"
    v-model="active_tab"
    color="#DEB98B"
    light
  >
    <v-btn id="btns" v-for="tab in tabs" :key="tab.idx" @click="goTo(tab.idx)">
      <span>{{tab.name}}</span>
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
        {idx: 0, name: "기록일지", icon: "mdi-calendar-month-outline"},
        {idx: 1, name: "산책", icon: "mdi-dog"},
        {idx: 2, name: "성분분석", icon: "mdi-google-analytics"},
        {idx: 3, name: "커뮤니티", icon: "mdi-forum-outline"},
        {idx: 4, name: "마이페이지", icon: "mdi-account"}
      ]
    }
  },
  created() {
    this.active_tab = this.getNowTab
    window.addEventListener('beforeunload', this.setNowTab(0))  
    console.log('오예')
  },
  computed: {
    ...mapGetters(['getNowTab'])
  },
  methods: {
    ...mapMutations(['setNowTab']),
    goTo(idx) {
      if (idx == 0) {
        if (this.$route.path!=='/calendar') this.$router.push('/calendar')
        this.active_tab = 0
        this.setNowTab(0)
      } else if (idx == 1) {
          if (this.$route.path!=='/walk') this.$router.push('/walk')
          this.active_tab = 1
          this.setNowTab(1)
      } else if (idx == 2) {
          if (this.$route.path!=='/food') this.$router.push('/food')
          this.active_tab = 2
          this.setNowTab(2)
      } else if (idx == 3) {
          if (this.$route.path!=='/community') this.$router.push('/community')
          this.active_tab = 3
          this.setNowTab(3)
      } else if (idx == 4) {
          if (this.$route.path!=='/mypage') this.$router.push('/mypage')
          this.active_tab = 4
          this.setNowTab(4)
      }
  }}
}
</script>
<style>
span {
  margin-top: 8px;
  color: black;
}
#btns {
  background-color: white;
  margin-top: 5px;
}
</style>