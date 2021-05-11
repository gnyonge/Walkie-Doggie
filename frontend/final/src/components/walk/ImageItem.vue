<template>
  <div id="mainBox">
    <div class="d-flex justify-space-between">
      <div style="width: 24px; background-color: white;">
      </div>
      <div style="padding-top: 1px;">
        멍 PHOTO
      </div>
      <div>
        <v-icon @click="goback()">mdi-close</v-icon>
      </div>
    </div>
    <div id="contentBox">
      <div class="d-flex justify-center mb-3">사진이 잘 나와요!</div>
      <v-img
        id="diaryBox"
        width="100%"
        src="@/assets/diarydogs.jpg"
      ></v-img>
      <div class="d-flex justify-end mt-1">
        <v-icon class="mt-2">mdi-heart-outline</v-icon><div class="d-inline mr-3" style="font-size: 18px; margin-top: 10px;">10220</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "ImageItem",
  methods: {
    goback() {
      this.$router.push('/hotplace')
    }
  }
}
</script>

<style scoped>
#contentbox {
  background-color: white;
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