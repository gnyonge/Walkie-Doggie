<template>
  <div>
    <div class="d-flex justify-center mb-6">
    <v-img
      height="158"
      class="rounded-circle"
      max-height="158"
      max-width="158"
      src="../../assets/dog.jpg">
    </v-img>
    <!-- <div class="d-flex justify-center mb-6">반려견의 산책정보를 기록합니다. </div> -->
    </div>
    <div class="d-flex justify-center">
    <v-btn
      elevation="2"
      large
      @click="startWalk">산책 시작하기
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
    }
  }
}
</script>

<style>

</style>