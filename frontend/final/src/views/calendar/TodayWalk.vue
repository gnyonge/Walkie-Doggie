<template>
  <div>
    <div>
      <div id="myimg"><img id="imgsize" src="@/assets/pet.png"></div>
      <div id="mydiv2" class="font-weight-bold mb-7"><h3>{{getSelectedDate}}</h3></div>
      <div id="mydiv2"><div><v-icon class="mb-1">mdi-check</v-icon>오늘 0번 산책했어요 !</div></div>
      <div id="mydiv2"><div><v-icon class="mb-1">mdi-check</v-icon>경로(캡쳐 사진)!</div></div>
      <div id="mydiv2"><div><v-icon class="mb-1">mdi-check</v-icon>오늘 0km 산책했어요 !</div></div>
      <div id="mydiv2"><div><v-icon class="mb-1">mdi-check</v-icon>오늘 0분 산책했어요 !</div></div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
export default {
  name: "TodayWalk",
  computed: {
      ...mapGetters(['getSelectedDate'])
    },
}
</script>

<style>
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
  margin-bottom: 30px;
}
#imgsize {
  width: 150px;
  border-radius: 70%;
  overflow: hidden;
}
</style>