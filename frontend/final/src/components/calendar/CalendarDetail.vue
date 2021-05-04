<template>
  <div>
    <div class="d-flex justify-center my-5">
      <v-btn rounded @click="todaydiary()" id="btnstyle" :class="{diary: isDiary}">오늘의 일기</v-btn>
      <v-btn rounded @click="todaywalk()" id="btnstyle" :class="{diary: !isDiary}">산책 기록</v-btn>
    </div>
    
  </div>
</template>

<script>
import TodayDiary from '@/'
export default {
  name: "CalendarDetail",
  data () {
      return {
        isDiary: false,
      }
    },
  methods: {
    todaydiary() {
      this.isDiary = true
      if (this.$route.path!=='/calendar/todaydiary') this.$router.push({ name: "TodayDiary" })
    },
    todaywalk() {
      this.isDiary = false
      if (this.$route.path!=='/calendar/todaywalk') this.$router.push({ name: "TodayWalk" })
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