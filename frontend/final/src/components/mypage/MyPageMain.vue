<template>
  <div class="">
    <div class="">  
      <!-- 1. 프로필 박스 -->
      <div class="d-flex ml-3 mr-3 mt-5 justify-center">
        <!-- 1. 프로필 사진 --> 
        <div class="mt-5">
          <v-img src="../../assets/images/서비스로고.png" 
          class="rounded-circle" height="158"
          max-height="120"
          max-width="120" alt="">
          </v-img>
        </div>
        <!-- 2. 인적사항 --> 
        <div class="mt-3 ml-4">
          <p style="font-size: 20px">이름</p>
          <p>나이: 2살</p>
          <p>몸무게: 2.2kg</p>
          <p>특이사항: 닭고기 알레르기</p>
        </div>
      </div>
      <!-- 버튼 그룹 --> 
      <div class="d-flex">
        <!-- 건강 버튼-->
        <div id="mainBox" style="width:40%">
          <router-link to="/health">
          <div class="d-flex justify-center">
            <v-icon size="80px" color="#BAF1E4">mdi-pill</v-icon> 
          </div>
          <div class="d-flex justify-center mt-3">
            <div style="color:#48B9A8; font-size:20px;">건강</div>
          </div>
          </router-link>
        </div>
        <!-- 통계 버튼 --> 
        <div id="mainBox" style="width:40%">
          <router-link to="/statistics">
          <div class="d-flex justify-center">
            <v-icon size="80px" color="#BAF1E4">mdi-poll</v-icon> 
          </div>
          <div class="d-flex justify-center mt-3">
            <div style="color:#48B9A8; font-size:20px;">통계</div>
          </div>
          </router-link>
        </div>
      </div>
    </div>
      <!-- 3.강아지 계정 변경 --> 
      <div class="ml-5 mt-3">
        <h5><b>강아지 계정 관리</b> </h5>
      </div>
        <div class="frame" >
          <div class="card-list">
            <div class="mt-5" id="mainBox" style="background-color:#BAF1E4;">
              <div class="d-flex justify-end">
                <v-icon>mdi-cog-outline</v-icon>
              </div>
              <div>
                <v-img src="../../assets/images/서비스로고.png" 
                  class="rounded-circle" height="158"
                  max-height="158"
                  max-width="158" alt="">
                  </v-img>
              </div>
              <div class="d-flex justify-center mt-2">
                <p style="color:#79704F; font-size:24px;">이름</p>
              </div>
              
            </div>
            <div class="mt-5" id="mainBox">
              <p>건강</p>
            </div>
            <div class="mt-5 card" id="mainBox">
              <p>건강</p>
            </div>
            <div class="space"></div>
          </div>
        </div>
      </div>
  

</template>

<script>
// import InfoChange from "../../views/mypage/InfoChange.vue"

export default {
  // components: { InfoChange },
  
}
</script>
<style scoped>
p {
  color: black;
}

.frame {
  width: 360px;
  overflow: auto;
}

.card-list {
  display: flex;
  padding-left: 10px;
  padding-right: 10px;
}

.card {
  flex-shrink: 0;
  width: 200px;
  height: 300px;
  margin-right: 10px;
}

.space {
  flex-shrink: 0;
  width: 200px;
  height: 200px;
/*   background-color: white; */
}
</style>