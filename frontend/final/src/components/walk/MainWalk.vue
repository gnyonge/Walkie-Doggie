<template>
  <div class="main">
    <div class="d-flex justify-center">
      <v-img
        height="158"
        class="rounded-circle"
        max-height="158"
        max-width="158"
        src="../../assets/dog.jpg">
      </v-img>
    </div>
    <div class="d-flex justify-center explain">
    반려견의 산책정보를 기록합니다. 
    </div>
    <div class="d-flex justify-center">
      <v-btn
        elevation="2"
        large
        id="mainBtn"
        style="color: #323232; width: 210px; margin:10px;"
        @click="startWalk">산책 시작하기
        <v-icon>mdi-dog-side</v-icon>
      </v-btn>
    </div>
    <div class="d-flex justify-center">
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
.main{
  margin-top: 45%;
  text-align: center;
}
.explain{
  margin: 6% ;
  color: #7c7b77;
}
</style>