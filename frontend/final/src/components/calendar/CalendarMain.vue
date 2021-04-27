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
      <div v-if="value" id="date"><h4 class="my-5">오늘의 날짜는?🐶</h4></div>
      <div v-if="value" id="date"><h5 class="mt-5">{{context}}</h5></div>
      <v-divider id="divider"></v-divider>
      <div v-if="value" id="writebtns">
        <div><p class="my-0">오늘 일기 써주실거죠?</p></div>
        <WritePage />
      </div>
      <div v-if="value" id="writebtns">
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
  padding-top: 20px;
  width: 100%;
  border: 1px;
  border-color: red;
}
.b-calendar-grid-body div.col {
  height: 60px;
}
#divider {
  margin: 20px;
}
#date {
  display: flex;
  justify-content: center;
  font-weight: bold;
  height: 40px;
}
#writebtns {
  height: 56px; 
  margin-top: 15px;
  display: flex;
  justify-content: flex-end;
  align-items: center;
}

</style>