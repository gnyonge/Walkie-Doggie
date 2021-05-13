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
    <div v-if="!diary">
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
      <div id="contentBox" v-if="photo_url">
        <v-img
          id="diaryBox"
          width="100%"
          height="100%"
          :src="photo_url"
        ></v-img>
      </div>
      <div class="d-flex justify-center" id="contentBox">
        <div class="mt-2 font-weight-bold" style="font-size: 22px;">{{getPrettyDate}}</div>
      </div>
      <v-divider></v-divider>
      <div id="contentBox">{{myDiary.Diary.d_memo}}</div>
      <div v-if="myDiary.Diary.d_special">
        <div id="contentBox" class="font-weight-bold" style="font-size: 17px;">특이사항</div>
        <div id="contentBox">
          {{myDiary.Diary.d_special}}
        </div>
      </div>
      <div v-if="myDiary.Health_list.length != 0">
        <div id="contentBox" class="font-weight-bold" style="font-size: 17px;">건강사항</div>
        <div id="contentBox">
          <v-chip
            id="chips"
            v-for="(hth, idx) in myDiary.Health_list"
            :key="idx"
            class="mx-1 my-1"
            color="#BAF1E4"
          >
            {{ hth.h_content }}
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
      myDiary: {},
      photo_url: false
    }
  },
  created() {
    if (this.getMyDiaryObject.Diary != null) {
          this.diary = true
          this.myDiary = this.getMyDiaryObject
          if (this.getMyDiaryObject.Diary.d_img) {
            this.photo_url = this.getMyDiaryObject.Diary.d_img
          }
        }
  },
  computed: {
      ...mapGetters(['getSelectedDate', 'getMyDiaryObject', 'getPrettyDate'])
    },
  methods: {
    ...mapActions(['getTodayDiaryInApi', 'deleteTodayDiaryInApi']),
    go(path) {
      this.$router.push(path)
    },
    deleteDiary() {
      const formData = new FormData()
      let diary = {}
      if (!this.photo_url) {
        diary = {
            d_date: this.getSelectedDate,
            d_flag: 0,
            d_img: '',
            d_memo: this.diaryContent,
            d_special: this.memoContent,
            d_walk: 0,
            peid: "petpetpetpet1"
          }
      } else {
        diary = {
            d_date: this.getSelectedDate,
            d_flag: 0,
            d_img: this.photo_url,
            d_memo: this.diaryContent,
            d_special: this.memoContent,
            d_walk: 0,
            peid: "petpetpetpet1"
          }
      }
      let health_list = []
          for (let i in this.healthArray) {
            health_list.push({
              h_content: this.healthArray[i],
              h_date: this.getSelectedDate,
              h_flag: 0,
              hid: 0,
              peid: "petpetpetpet1"
            })
          }
          formData.append(
            'diary',
            new Blob([JSON.stringify(diary)], { type: 'application/json' })
          )
          formData.append(
            'health_list',
            new Blob([JSON.stringify(health_list)], { type: 'application/json' })
          )
          this.deleteTodayDiaryInApi(formData).then(() => {
            this.snackbar = false
            this.$router.push('/calendar')
          })
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