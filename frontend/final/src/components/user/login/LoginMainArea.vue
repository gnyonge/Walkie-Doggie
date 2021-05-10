<template>
  <div class="logo-center">
    <div class="login-btn-group">  
      <!-- 1. 서비스로고 -->
      <div class="logo mt-10">
        <v-img src="../../../assets/images/서비스로고.png" class="logo-img" alt=""></v-img>
      </div>
      <!-- 2. 로그인 회원가입 버튼 --> 
      <div class="login-btn-pd">
        <router-link to="/login"><v-btn class="login-btn" id="mainBtn" style="margin-top: 30px;margin-bottom:40px;">로그인</v-btn></router-link>
      </div>
      <div class="signup-btn-pd">
        <router-link to="/signup"><v-btn class="signup-btn" id="mainBtn">회원가입</v-btn></router-link> 
      </div>
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
  /* margin-top: ; */
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
  /* padding: 30px; */
  margin-top: 30px;
}
.signup-btn-pd{
  /* padding: 30px; */
}
.logo-center {
  display: flex;
  justify-content: center;
  align-items: center;
}

</style>