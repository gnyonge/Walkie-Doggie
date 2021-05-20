<template>
<div>
    <v-card class="m-2 p-2">
      <b-calendar v-model="value" @context="onContext" :date-info-fn="dateClass" locale="ko-kr"
      selected-variant="warning"
      today-variant="warning"
      nav-button-variant="warning"
      block
      hide-header
      label-help
      ></b-calendar>
    </v-card>
    
    <div
    id="mainBox"
    v-if="value"
    max-width="374"
    >
      <div id="date"><h4 class="mt-2"><b>오늘의 날짜는?🐶</b></h4></div>
      <div id="date"><h5 class="mt-3"><b>{{getPrettyDate}}</b></h5></div>
      <v-divider></v-divider>
      <div v-if="value" id="writebtns">
        <div><p class="my-0">오늘 일기 써주실거죠?</p></div>
        <v-btn id="mainBtn" width="100px" class="ml-3" @click="goto('write')"><b>일기쓰기</b></v-btn>
      </div>
      <div v-if="value" id="writebtns">
        <div><p class="my-0">산책기록 보실래요?</p></div>
        <v-btn id="mainBtn" width="100px" class="ml-3" @click="goto(`detail/todaywalk/${getSelectedDate}`)"><b>산책기록</b></v-btn>
      </div>
    </div>
</div>
</template>

<script>
import { mapMutations, mapGetters, mapActions } from 'vuex'

export default {
  name: "CalendarMain",
  data() {
    return {
      value: '',
      context: null,
    }
  },
  created(){
    this.setNowTab(0)
    this.allDiaryInApi(this.getDogInfo.pet.peid)
  },
  computed: {
    ...mapGetters(['getPrettyDate', 'getSelectedDate', 'getMyDiaryObject', 'getDogInfo', 'getAllDiaryList'])
  },
  methods: {
    ...mapMutations(['setSelectedDate', 'setPrettyDate', 'setDetailBtn', 'setNowTab']),
    ...mapActions(['getTodayDiaryInApi', 'getTodayWalkInApi', 'allDiaryInApi']),
    onContext(ctx) {
      this.context = ctx.activeYMD
      this.setSelectedDate(this.context)
      let prettyDate = this.context.slice(0,4) + '년 ' + this.context.slice(5,7) + '월 ' + this.context.slice(8,10) + '일'
      this.setPrettyDate(prettyDate)

      this.getTodayDiaryInApi({
      date: this.getSelectedDate,
      peid: this.getDogInfo.pet.peid}).then(()=> {
        if (this.getMyDiaryObject.Diary != null && ctx.selectedDate) {
          this.setDetailBtn('diary')
          this.$router.push(`/calendar/detail/todaydiary/${this.getSelectedDate}`)
        }
      })
      
      this.getTodayWalkInApi({
        date: this.getSelectedDate,
        peid: this.getDogInfo.pet.peid})
      
    },
    goto(name) {
      this.setDetailBtn('walk')
      this.$router.push(`/calendar/${name}`)
    },
    dateClass(ymd) {
        if (this.getAllDiaryList.includes(ymd)) {
          return ymd ? 'table-warning' : ''
        }
      }
  }
}
</script>

<style>
#mybox {
  background-color: white;
  margin: 10px;
  padding-top: 9px;
  width: 100%;
}
.b-calendar-grid-body div.col {
  display: flex;
  align-items: center;
  height: 60px;
}
.btn-warning:not(:disabled):not(.disabled).active, .btn-warning:not(:disabled):not(.disabled):active, .show>.btn-warning.dropdown-toggle {
  background-color: #F2B75B !important;
}
#date {
  display: flex;
  justify-content: center;
  font-weight: bold;
  height: 40px;
}
#writebtns {
  height: 56px; 
  margin-top: 10px;
  display: flex;
  justify-content: flex-end;
  align-items: center;
}
</style>