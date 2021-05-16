<template>
  <div class="logo-center">
    <div class="login-btn-group">  
      <!-- 1. 서비스로고 -->
      <div class="logo" style="margin-top: 60px">
        <v-img src="../../../assets/images/서비스로고.png" class="logo-img" alt=""></v-img>
      </div>
      <!-- 2. 로그인 회원가입 버튼 --> 
      <div class="login-btn-pd">
        <v-btn class="login-btn" id="mainBtn" style="margin-top: 30px;margin-bottom:40px; width: 180px; height: 60px; border-radius: 50px; font-size: 25px" @click="goto('/login')">로그인</v-btn>
      </div>
      
      <div class="signup-btn-pd">
        <v-btn class="signup-btn" id="mainBtn" style="margin-bottom:40px;  width: 180px; height: 60px; border-radius: 50px; font-size: 25px" @click="goto('/signup')">회원가입</v-btn>
      </div>
      <!-- 선 넣기 !!!!!!!! -->
      <!-- 카카오로그인 --> 
      <div class="signup-btn-pd" >
        <v-img src="../../../assets/images/kakao_login_medium_narrow.png" style="border-radius: 12px;" class="logo-img" alt="" @click="kakao()" ></v-img>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    goto(path) {
      this.$router.push(path)
    }
  },
}
</script>
<style scoped>
.logo {
  width: 250px;
  height: 250px; 
  border-radius: 70%;
  overflow: hidden;
  
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
  /* padding: 30px; */
  margin-top: 30px;
}
.logo-center {
  display: flex;
  justify-content: center;
  align-items: center;
}
hr {
  color: black;
}
</style>