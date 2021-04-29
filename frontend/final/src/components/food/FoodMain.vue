<template>
  <div>
    <div id="date"><h2 class="my-10">우리 아이 간식</h2></div>
    <div id="date" class="mt-9"><h2 class="mt-5">성분 분석 해드려요!</h2></div>
    <div id="date" class="mt-15"><FoodImageUpload /></div>

    <div class="mt-9 d-flex justify-center">
      <div id="tempimg">사진 업로드 하면 여기 띄워짐!</div>
    </div>
    <div class="mt-6 d-flex justify-center">
      <v-btn rounded @click="result()" id="btnstyle" style="width: 200px; height: 50px; font-size: 20px;" :class="{diary: isClicked}">분석 시작!</v-btn>
    </div>
  </div>
</template>

<script>
import { mapMutations } from 'vuex'
import FoodImageUpload from '@/components/food/FoodImageUpload'
export default {
  name: "FoodMain",
  components: {
    FoodImageUpload
  },
  data() {
    return {
      isClicked: false
    }
  },
  methods: {
    ...mapMutations(['setNowTab']), 
    result() {
      this.isClicked = true
      this.setNowTab(2)
      this.$router.push({name: "FoodResult"})
    }
  }
}
</script>

<style>
#tempimg {
  background-color: peachpuff;
  width: 300px;
  height: 300px;
  display: flex;
  justify-content: center;
  align-items: center;
}
.form-file-text {
  margin-top: 0px;
}
.diary {
  box-shadow: 1px 1px 0 rgb(0,0,0,0.5) !important;
  position: relative; 
  top:2px; 
}
</style>