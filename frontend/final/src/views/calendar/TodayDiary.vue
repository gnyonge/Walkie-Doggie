<template>
  <div id="mainBox">
    <div class="d-flex justify-space-between">
      <div>
      <v-icon @click="go('/calendar')">mdi-arrow-left</v-icon>
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
      <div id="mydiv1" class="font-weight-bold"><div>{{getPrettyDate}}</div></div>
      <v-divider></v-divider>
      <div id="mydiv2" class="mt-8"><div>아직 일기가 없어요 ㅠ_ㅠ</div></div>
      <div class="d-flex justify-center align-center my-2" style="height: 40px;">
        <div><v-btn rounded @click="go('/calendar/write')" id="btnstyle" width="130px">일기 쓰러가기!</v-btn></div>
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
        <div class="mt-2 font-weight-bold" style="font-size: 22px;">{{getPrettyDate}}</div>
      </div>
      <v-divider></v-divider>
      <div id="contentBox">{{getMyDiaryObject.Diary.d_memo}}</div>
      <div v-if="getMyDiaryObject.Diary.d_special">
        <v-divider></v-divider>
        <div id="contentBox" class="font-weight-bold" style="font-size: 17px;">특이사항</div>
        <div id="contentBox">
          기침을 한다 좀 더 지켜보자!
        </div>
      </div>
      <div v-if="getMyDiaryObject.Health_list.length != 0">
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
      </div>
      <div class="d-flex justify-end mt-9">
        <v-btn color="#48B9A8" text width="50px" @click="go('/calendar/update')">수정</v-btn>
        <!-- 삭제 확인 창 -->
        <v-btn
          color="#48B9A8" text width="50px"
          @click="snackbar = true"
        >
          삭제
        </v-btn>

        <v-snackbar
          v-model="snackbar"
          :multi-line="multiLine"
        >
          정말 삭제하시겠어요?

          <template v-slot:action="{ attrs }">
            <v-btn
              color="red"
              text
              v-bind="attrs"
              @click="deleteDiary()"
            >
              YES
            </v-btn>
          </template>
        </v-snackbar>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'
export default {
  name: "TodayDiary",
  data() {
    return {
      multiLine: true,
      snackbar: false,
      diary: false,
      healthArray: ['예방접종 1차', '심장사상충 약'],
    }
  },
  // created() {
  //   this.getTodayDiaryInApi({
  //     date: this.getSelectedDate,
  //     peid: 'petpetpetpet1'}).then((res) => {
  //       console.log(res.data)
  //     })
  // },
  computed: {
      ...mapGetters(['getSelectedDate', 'getMyDiaryObject', 'getPrettyDate'])
    },
  methods: {
    ...mapActions(['getTodayDiaryInApi', 'deleteTodayDiaryInApi']),
    go(path) {
      this.$router.push(path)
    },
    deleteDiary() {
      this.deleteTodayDiaryInApi({
        d_date: this.getSelectedDate, 
        d_flag: 0,
        d_img: "string",
        d_memo: this.getMyDiaryObject.Diary.d_memo,
        d_special: this.getMyDiaryObject.Diary.d_special,
        d_walk: 0,
        peid: "petpetpetpet1",
      }).then((res) => {
        console.log(res)
      })
      this.snackbar = false
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
.filebox label {
  display: inline-block; 
  padding: .5em .75em; 
  color: #323232; 
  font-size: inherit; 
  line-height: normal; 
  vertical-align: middle; 
  background-color: #BAF1E4; 
  cursor: pointer; 
  border: 1px solid #ebebeb; 
  border-bottom-color: #e2e2e2; 
  border-radius: .25em; 
  } 
</style>