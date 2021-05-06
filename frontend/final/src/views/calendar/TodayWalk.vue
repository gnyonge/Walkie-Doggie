<template>
  <div id="mainBox">
    <div class="d-flex justify-space-between">
      <div>
      <v-icon @click="goback()">mdi-arrow-left</v-icon>
      </div>
      <div>
        산책 기록
      </div>
      <div style="width: 24px; background-color: white;">
      </div>
    </div>
    <div id="myimg"><img id="imgsize" src="@/assets/pet.png"></div>
    <div id="mydiv1" class="font-weight-bold"><div>{{getSelectedDate}}</div></div>
    <v-divider></v-divider>
    <div id="mydiv2" class="mt-8"><div><v-icon>mdi-check</v-icon>오늘 0번 산책했어요 !</div></div>
    <div id="mydiv2"><div><v-icon>mdi-check</v-icon>경로(캡쳐 사진)!</div></div>
    <div id="mydiv2"><div><v-icon>mdi-check</v-icon>오늘 0km 산책했어요 !</div></div>
    <div id="mydiv2"><div><v-icon>mdi-check</v-icon>오늘 0분 산책했어요 !</div></div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
export default {
  name: "TodayWalk",
  computed: {
      ...mapGetters(['getSelectedDate'])
    },
  methods: {
    goback() {
      this.$router.push('/calendar')
    }
  }
}
</script>

<style>
#mydiv1 {
  display: flex;
  justify-content: center;
  align-content: center;
  height: 25px;
  font-size: 20px;
}
#mydiv2 {
  display: flex;
  justify-content: center;
  align-content: center;
  height: 40px;
  margin-top: 4px;
  margin-bottom: 4px;
}
#myimg {
  display: flex;
  justify-content: center;
  margin-top: 4px;
  margin-bottom: 10px;
}
#imgsize {
  width: 200px;
  border-radius: 70%;
  overflow: hidden;
}
</style>