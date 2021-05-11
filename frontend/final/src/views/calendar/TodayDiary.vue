<template>
  <div id="mainBox">
    <div class="d-flex justify-space-between">
      <div>
      <v-icon @click="goback()">mdi-arrow-left</v-icon>
      </div>
      <div>
        오늘의 일기
      </div>
      <div style="width: 24px; background-color: white;">
      </div>
    </div>
    <!-- 일지 안썼을 때-->
    <div v-if="diary">
      <div id="myimg"><img id="imgsize" src="@/assets/pet.png"></div>
      <div id="mydiv1" class="font-weight-bold"><div>{{getSelectedDate}}</div></div>
      <v-divider></v-divider>
      <div id="mydiv2" class="mt-8"><div>아직 일기가 없어요 ㅠ_ㅠ</div></div>
      <div class="d-flex justify-center align-center my-2" style="height: 40px;">
        <div><v-btn rounded @click="goToWrite()" id="btnstyle" width="130px">일기 쓰러가기!</v-btn></div>
      </div>
    </div>

    <!-- 일지 썼을 때-->
    <div v-else>
      <div id="contentBox">
        <v-img
          id="diaryBox"
          width="100%"
          src="@/assets/diarydogs.jpg"
        ></v-img>
      </div>
      <div class="d-flex justify-center" id="contentBox">
        <div class="mt-2 font-weight-bold" style="font-size: 22px;">{{getSelectedDate}}</div>
      </div>
      <v-divider></v-divider>
      <div id="contentBox">친구들이랑 한강가서 놀았다 ㅎㅎ 친구들 귀욥 친구들이랑 한강가서 놀았다 ㅎㅎ 친구들 귀욥친구들이랑 한강가서 놀았다 ㅎㅎ 친구들 귀욥</div>
      <v-divider></v-divider>
      <div id="contentBox" class="font-weight-bold" style="font-size: 17px;">특이사항</div>
      <div id="contentBox">
        기침을 한다 좀 더 지켜보자!
      </div>
      <v-divider></v-divider>
      <div id="contentBox" class="font-weight-bold" style="font-size: 17px;">건강사항</div>
      <div id="contentBox">
        <v-chip
          id="chips"
          v-for="(hth, idx) in healthArray"
          :key="idx"
          class="mx-1 my-1"
          color="#BAF1E4"
        >
          {{ hth }}
        </v-chip>
      </div>
      <div class="d-flex justify-end mt-9">
        <v-btn color="#48B9A8" text width="50px">수정</v-btn>
        <v-btn color="#48B9A8" text width="50px">삭제</v-btn>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
export default {
  name: "TodayDiary",
  data() {
    return {
      diary: false,
      healthArray: ['예방접종 1차', '심장사상충 약']
    }
  },
  computed: {
      ...mapGetters(['getSelectedDate'])
    },
  methods: {
    goToWrite() {
      this.$router.push('/calendar/write')
    },
    goback() {
      this.$router.push('/calendar')
    }
  }
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
#diaryBox {
  border-radius: 15px;
  background-color: white;
  box-shadow: 5px 5px 5px #E5E5E5;
  padding: 20px;
}

</style>