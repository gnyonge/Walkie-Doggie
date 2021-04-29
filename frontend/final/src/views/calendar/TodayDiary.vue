<template>
  <div>
    <div v-if="diary">
      <div id="myimg"><img id="imgsize" src="@/assets/pet.png"></div>
      <div id="mydiv"><h3>{{getSelectedDate}}</h3></div>
      <div id="mydiv"><h3>아직 일기가 없어요 ㅠ_ㅠ</h3></div>
      <div class="d-flex justify-center align-center my-2" style="height: 40px;"><div>일기 써주실거죠? 멍멍!</div><div><WritePage /></div></div>
    </div>
    <div v-else>
      <div id="mydiv"><h3>{{getSelectedDate}}</h3></div>
      <div class="mx-3"><img id="myimg2" src="@/assets/pet.png"></div>
      <div id="contentbox"><div id="contentbox">ssssss</div></div>
      <v-list-item-title id="contentbox2" class="font-weight-bold">특이사항</v-list-item-title>
      <div id="contentbox"><div id="contentbox">ssssss</div></div>
      <v-list-item-title id="contentbox2" class="font-weight-bold">건강사항</v-list-item-title>
      <div class="mx-4">
        <v-chip
          id="chips"
          v-for="(hth, idx) in healthArray"
          :key="idx"
          class="mx-1 my-1"
          color="#EDD9BF"
        >
          {{ hth }}
        </v-chip>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import WritePage from '@/components/calendar/WritePage'
export default {
  name: "TodayDiary",
  components: {
    WritePage,
  },
  data() {
    return {
      diary: false,
      healthArray: ['예방접종', '심장사상충 약']
    }
  },
  computed: {
      ...mapGetters(['getSelectedDate'])
    },
}
</script>

<style scoped>
#mydiv {
  display: flex;
  justify-content: center;
  font-weight: bold;
  height: 40px;
  margin-top: 4px;
  margin-bottom: 4px;
}
#myimg2 {
  width: 100%;
}
#contentbox {
  background-color: white;
  margin: 12px;
}
#contentbox2 {
  padding-left: 12px;
  padding-right: 12px;
  
}
#chips {
  color: white;
}

</style>