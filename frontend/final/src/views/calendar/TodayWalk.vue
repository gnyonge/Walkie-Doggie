<template>
  <div id="mainBox">
    <div class="d-flex justify-space-between">
      <div>
      <v-icon @click="goback()">mdi-arrow-left</v-icon>
      </div>
      <div>
        산책기록
      </div>
      <div style="width: 24px; background-color: white;">
      </div>
    </div>
    <div id="myimg"><img id="imgsize" src="@/assets/pet.png"></div>
    <div id="mydiv1" class="font-weight-bold"><div>{{getPrettyDate}}</div></div>
    <v-divider></v-divider>
    <div v-if="getMyWalkObject.Walk_list[0].count != 0">
      <div id="mydiv2" class="mt-8"><div><v-icon>mdi-check</v-icon>오늘 {{getMyWalkObject.Walk_list[0].count}}번 산책했어요 !</div></div>
      <div id="mydiv2"><div><v-icon>mdi-check</v-icon>오늘 {{getMyWalkObject.Walk_list[0].distance}}km 산책했어요 !</div></div>
      <div id="mydiv2"><div><v-icon>mdi-check</v-icon>오늘 {{getMyWalkObject.Walk_list[0].time}}분 산책했어요 !</div></div>
    </div>
    <div v-else>
      <div id="mydiv2" class="mt-8"><div><v-icon>mdi-check</v-icon>오늘 산책을 안했어요 😢</div></div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
export default {
  name: "TodayWalk",
  data() {
    return {
      
    }
  },
  computed: {
      ...mapGetters(['getSelectedDate', 'getPrettyDate', 'getMyWalkObject'])
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