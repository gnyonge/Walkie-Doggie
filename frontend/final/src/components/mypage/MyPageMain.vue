<template>
  <div class="">
    <div class="login-btn-group profile-box">  
      <!-- 1. 프로필 박스 -->
      <router-link to="/infochange">
        <v-btn
          tile
        >
        <v-icon left>
          mdi-cog
        </v-icon>
        설정
      </v-btn>
      </router-link>
        
      <div class="mt-2">
        <v-img src="../../assets/images/서비스로고.png" 
        class="rounded-circle" height="158"
        max-height="158"
        max-width="158" alt="">
        </v-img>
      </div>
      <div>
        <p>나이: 2살</p>
        <p>몸무게: 2.2kg</p>
        <p>특이사항: 닭고기 알레르기</p>
      </div>
    </div>
      <!-- 2. 건강, 통계보기, 강아지 계정 변경 --> 
      <div class="mt-5 lst">
        <router-link to="/health" class="d-flex justify-center"><p>건강</p></router-link>
      </div>
      <div class="mt-5 lst">
        <router-link to="/statistics" class="d-flex justify-center"><p>통계보기</p></router-link> 
      </div>
      <div class="mt-5 lst">
        <router-link to="/dogchange" class="d-flex justify-center"><p>강아지 계정 변경</p></router-link> 
      </div>
    </div>
  

</template>

<script>
export default {
  
}
</script>
<style scoped>
.logo {
  width: 200px;
  height: 200px; 
  border-radius: 70%;
  overflow: hidden;
  border: solid 3px;
  /* display: flex; */
  /* justify-content: center; */
  /* align-items: center; */
  
}
.logo-img {
  width: 100%;
    height: 100%;
    object-fit: cover;
}
.login-btn-group {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.login-btn {
  width: 200px;
}
.signup-btn {
  width: 200px;
}
.login-btn-pd{
  padding: 30px;
  margin-top: 120px;
}
.signup-btn-pd{
  padding: 30px;
}
.mypage-list {
  /* display: block; */
  width: 100%;
  height: 50px;
  border-radius: 0;
  box-shadow: 0;
  background: #DEB98B;
}
.mypage-list-btn {
  box-shadow: 0;
  background: #DEB98B;
}
.profile-box {
  width: 100%;
  height: 40%;
  background: #EFE8DE;
}
.lst {
  width: 100%;
  height: 95px;
  background: #EFE8DE;
  font-size: 40px;
}
p {
  color: black;
}
</style>