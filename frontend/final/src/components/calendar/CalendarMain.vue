<template>
<div>
    <v-card class="m-2 p-2">
      <b-calendar v-model="value" @context="onContext" locale="ko-kr"
      selected-variant="warning"
      today-variant="warning"
      nav-button-variant="warning"
      block
      hide-header
      label-help
      ></b-calendar>
    </v-card>
    <v-card
    v-if="value"
    class="m-2 p-2"
    max-width="374"
    >
      <div id="date"><h4 class="mt-2">오늘의 날짜는?🐶</h4></div>
      <div id="date"><h5 class="mt-3">{{context}}</h5></div>
      <v-divider id="divider"></v-divider>
      <div v-if="value" id="writebtns">
        <div><p class="my-0">오늘 일기 써주실거죠?</p></div>
        <v-btn id="mainBtn" @click="goto('write')">일기쓰기</v-btn>
      </div>
      <div v-if="value" id="writebtns">
        <div><p class="my-0">산책 기록 보러가기</p></div>
        <v-btn id="mainBtn" @click="goto('todaywalk')">산책기록</v-btn>
      </div>
    </v-card>
</div>
</template>

<script>
import { mapMutations } from 'vuex'

export default {
  name: "CalendarMain",
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
    },
    goto(name) {
      this.$router.push(`/calendar/${name}`)
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
  height: 60px;
}
.btn-warning:not(:disabled):not(.disabled).active, .btn-warning:not(:disabled):not(.disabled):active, .show>.btn-warning.dropdown-toggle {
  background-color: #F2B75B !important;
}
#divider {
  margin: 9px;
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