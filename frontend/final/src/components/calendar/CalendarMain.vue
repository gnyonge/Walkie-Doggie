<template>
<div>
    <div id="mybox">
      <b-calendar v-model="value" @context="onContext" locale="ko-kr"
      selected-variant="success"
      today-variant="success"
      nav-button-variant="success"
      block
      hide-header
      label-help
      ></b-calendar>
    </div>
    <div>
      <div v-if="value" class="d-flex justify-center my-2 font-weight-bold" style="height: 40px;"><h3>오늘의 날짜는?</h3></div>
      <div v-if="value" class="d-flex justify-center my-2 font-weight-bold" style="height: 40px;"><h3>{{context}}</h3></div>
      <div v-if="value" style="height: 56px;" class="d-flex justify-center align-center">
        <div><p class="my-0">오늘 일기 써주실거죠?</p></div>
        <WritePage />
      </div>
      <div v-if="value" style="height: 56px; margin-top: 10px;" class="d-flex justify-center align-center">
        <div><p class="my-0">산책 기록 보러가기</p></div>
        <DiaryDetail />
      </div>
    </div>
</div>
</template>

<script>
import { mapMutations } from 'vuex'
import WritePage from '@/components/calendar/WritePage'
import DiaryDetail from '@/components/calendar/DiaryDetail'

export default {
  name: "CalendarMain",
  components: { WritePage, DiaryDetail },
  data() {
    return {
      value: '',
      context: null
    }
  },
  methods: {
    ...mapMutations(['setSelectedDate']),
    onContext(ctx) {
      this.context = ctx.selectedFormatted
      this.setSelectedDate(this.context)
    }
  }
}
</script>

<style>
#mybox {
  background-color: white;
  padding-top: 10px;
  width: 100%;
}
.b-calendar-grid-body div.col {
  height: 60px;
}
</style>