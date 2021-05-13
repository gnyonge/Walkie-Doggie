<template>
  <div>
    <div class="d-flex justify-center mt-3">
      <v-btn rounded @click="todaydiary()" id="btnstyle" :class="{diary: isDiary == 'diary'}">오늘의 일기</v-btn>
      <v-btn rounded @click="todaywalk()" id="btnstyle" :class="{diary: isDiary == 'walk'}">산책기록</v-btn>
    </div>
    <transition mode="out-in">
      <router-view></router-view>
    </transition>
    
  </div>
</template>

<script>
import { mapGetters, mapMutations } from 'vuex'
export default {
  name: "CalendarDetail",
  data () {
      return {
        isDiary: '',
      }
    },
  created() {
    this.isDiary = this.getDetailBtn
  }, 
  computed: {
    ...mapGetters(['getSelectedDate', 'getDetailBtn'])
  },
  methods: {
    ...mapMutations(['setDetailBtn']),
    todaydiary() {
      this.isDiary = 'diary'
      this.setDetailBtn('diary')
      if (this.$route.path!==`/calendar/detail/todaydiary/${this.getSelectedDate}`) this.$router.push(`/calendar/detail/todaydiary/${this.getSelectedDate}`)
    },
    todaywalk() {
      this.isDiary = 'walk'
      this.setDetailBtn('walk')
      if (this.$route.path!==`/calendar/detail/todaywalk/${this.getSelectedDate}`) this.$router.push(`/calendar/detail/todaywalk/${this.getSelectedDate}`)
    }
  }
}
</script>

<style>
#btnstyle {
  display: inline;
  margin-left: 2px;
  margin-right: 2px;
  width: 110px;
  background-color: #BAF1E4;
  color: #323232;
  cursor: pointer; 
  outline: none; 
  box-shadow: 1px 4px 0 rgb(0,0,0,0.3);
}
.diary {
  box-shadow: 1px 1px 0 rgb(0,0,0,0.3) !important;
  position: relative; 
  top:2px; 
}
</style>