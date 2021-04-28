<template>
<div>
    <div v-if="diary">
      <div id="myimg"><img id="imgsize" src="@/assets/pet.png"></div>
      <div id="mydiv"><h3>{{getSelectedDate}}</h3></div>
      <div id="mydiv"><h3>아직 일기가 없어요 ㅠ_ㅠ</h3></div>
      <div class="d-flex justify-center align-center my-2" style="height: 40px;"><div>일기 써주실거죠? 멍멍!</div><div><WritePage /></div></div>
    </div>
  <v-card
    v-else
    class="mx-auto"
    max-width="374"
  >
    <template slot="progress">
      <v-progress-linear
        color="deep-purple"
        height="10"
        indeterminate
      ></v-progress-linear>
    </template>
    <div class="mx-2">
    <v-img
      width="100%"
      src="@/assets/diarydogs.jpg"
    ></v-img>
    </div>

    <v-card-title>{{ getSelectedDate }}</v-card-title>
    <!-- 일지 내용 -->
    <v-card-text>
      <div>친구들이랑 한강가서 놀았다 ㅎㅎ 친구들 귀욥</div>
    </v-card-text>

    <v-divider class="mx-4"></v-divider>
    <!-- 특이사항 -->
    <v-card-title>특이사항</v-card-title>

    <v-card-text>
      <li>기침함</li>
      <li>사회화 잘 되는중</li>
    </v-card-text>

    <v-divider class="mx-4"></v-divider>
    <!-- 건강사항 -->
    <v-card-title>건강사항</v-card-title>

    <v-card-text>
      <v-chip
          id="chips"
          v-for="(hth, idx) in healthArray"
          :key="idx"
          class="mx-1 my-1"
          color="#EDD9BF"
          text-color="white"
        >
          {{ hth }}
        </v-chip>
    </v-card-text>

    <v-card-actions class="d-flex justify-end">
      <v-btn
        color="#5EBC88"
        text
      >
        수정
      </v-btn>
      <v-btn
        color="#5EBC88"
        text
      >
        삭제
      </v-btn>
    </v-card-actions>
  </v-card>
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
</style>