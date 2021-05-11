<template>
  <div id="mainBox">
    <!-- 뒤로가기, 멍 PHOTOS-->
    <div class="d-flex justify-space-between">
      <div>
      <v-icon @click="goback()">mdi-arrow-left</v-icon>
      </div>
      <div>
        멍 PHOTOS
      </div>
      <div style="width: 24px; background-color: white;">
      </div>
    </div>
    <!-- 멍 PHOTOS 반복 -->
    <div id="contentBox" v-for="i, idx in 3" :key="idx">
      <div class="d-flex justify-center mb-3">사진이 잘 나와요!</div>
      <v-img
        id="diaryBox"
        width="100%"
        src="@/assets/diarydogs.jpg"
      ></v-img>
      <div class="d-flex justify-end mt-1">
        <v-icon class="mt-2">mdi-heart-outline</v-icon><div class="d-inline mr-3" style="font-size: 18px; margin-top: 10px;">10220</div>
      </div>
      <v-divider></v-divider>
    </div>
  </div>
</template>

<script>
export default {
  name: "ImageList",
  methods: {
    goback() {
      this.$router.push('/walk')
    }
  }
}
</script>

<style scoped>
#contentbox {
  border: 1px solid grey;
  margin: 12px;
  border-radius: 15px;
}
#diaryBox {
  border-radius: 15px;
  background-color: white;
  box-shadow: 5px 5px 5px #E5E5E5;
  padding: 20px;
}
</style>