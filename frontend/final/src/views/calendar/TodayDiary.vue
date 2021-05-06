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
        {{getSelectedDate}}
      </div>
      <div id="contentBox">
        <v-img
          id="diaryBox"
          width="100%"
          src="@/assets/diarydogs.jpg"
        ></v-img>
      </div>
      <div id="contentBox">
        <div id="paper">
          <div id="pattern">
            <div id="content">
              친구들이랑 한강가서 놀았다 ㅎㅎ 친구들 귀욥
              <br>
              친구들이랑 한강가서 놀았다 ㅎㅎ 친구들 귀욥
            </div>
          </div>
        </div>
      </div>


    </div>


  <v-card
    
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
export default {
  name: "TodayDiary",
  data() {
    return {
      diary: false,
      healthArray: ['예방접종', '심장사상충 약']
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
#contentbox {
  background-color: white;
  margin: 12px;
}
#contentbox2 {
  padding-left: 12px;
  padding-right: 12px;
}
#diaryBox {
  border-radius: 15px;
  background-color: white;
  box-shadow: 5px 5px 5px #E5E5E5;
  padding: 20px;
}
#paper {
  position: relative;
  margin: 20px auto;
  padding-top: 20px;
  padding-bottom: 20px;
  background-color: white;
  box-shadow: 0px 0px 5px 0px #888;
}

/* styling red vertical line */
#paper::before {
  content: '';
  width: 2px;
  height: 100%;
  position: absolute;
  top: 0;
  left: 40px;
  background-color: rgba(255,0,0,0.6);
}

/* styling blue horizontal lines */
#pattern {
  height: 100%;
  background-image: repeating-linear-gradient(white 0px, white 24px, teal 25px);
}

/* styling text content */
#content {
  padding-top: 6px;
  padding-left: 56px;
  padding-right: 16px;
  line-height: 30px;
  font-size: 15px;
  letter-spacing: 1px;
  word-spacing: 5px;
}
</style>