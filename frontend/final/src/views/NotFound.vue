<template>
  <div>
    <div id="mainBox">
    <h1>미쳤습니까 휴먼? 돌아가십시오</h1>
    <img 
      src="@/assets/human.jpeg" 
      alt="" 
      style=" width: 300px;
              height: 350px;">
    <v-btn
      large
      class="ml-16"
      @click="goToHome">홈으로 돌아가기</v-btn>
    </div>
  </div>
</template>

<script>
import { mapMutations } from 'vuex'

export default {
  name: 'NotFound',
  mounted(){
    this.setNowTab(1)
  },
  methods: {
    ...mapMutations([
      'setNowTab',
    ]),
    goToHome(){
      this.$router.push('/')
    },
  },
}
</script>

<style>

</style>