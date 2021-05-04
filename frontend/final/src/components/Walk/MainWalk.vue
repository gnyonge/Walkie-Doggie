<template>
  <div style="padding: 50%;">
    <div class="d-flex justify-center mb-6">
      <v-img
        height="158"
        class="rounded-circle"
        max-height="158"
        max-width="158"
        src="../../assets/dog.jpg">
      </v-img>
    </div>
    <div class="d-flex justify-center">
      <div>
      반려견의 산책정보를 기록합니다. 
      </div>
    </div>
    
    <div class="d-flex justify-center">
      <v-btn
        elevation="2"
        large
        id="mainBtn"
        style="color: #323232; width: 210px; margin:10px;"
        @click="startWalk">산책 시작하기
        <v-icon>mdi-walk</v-icon>
      </v-btn>
    </div>
    <div class="d-flex  justify-center">
    <v-btn
      elevation="2"
      large
      id="mainBtn"
      style="color: #323232; width: 210px; margin:10px;"
      @click="goToHotPlace"
    >멍플레이스 구경하기
      <v-icon>mdi-fire</v-icon>
    </v-btn>
    
    </div>
  </div>
</template>

<script>
export default {
  name: 'MainWalk',
  methods:{
    startWalk(){
      this.$router.push('/startwalk')
    },
    goToHotPlace(){
      this.$router.push('/hotplace')
    }
  }
}
</script>

<style>

</style>