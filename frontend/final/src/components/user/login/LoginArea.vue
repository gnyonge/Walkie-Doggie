<template>
  <div class="">
    <!-- 로그인--> 
    <div class="">
      <!-- <v-form @submit.prevent="onSignin"> -->
      <div id="mainBox">
        <div class="d-flex justify-space-between">
        <div>
        <v-icon @click="goback()">mdi-arrow-left</v-icon>
        </div>
        <div>
          로그인
        </div>
        <div style="width: 24px; background-color: white;">
        </div>
      </div>
        <v-form>
          <v-text-field name="email" label="이메일" id="email" v-model="email" type="email" required color="#48B9A8" style="margin-top:20px;"></v-text-field>
          <v-text-field name="password" label="비밀번호" id="password" v-model="password" type="password" required color="#48B9A8" style="margin-top:20px;"></v-text-field>
        </v-form>
        <router-link to="/calendar">
          <div class="d-flex justify-center">
            <v-btn class="login-btn" id="mainBtn" style="width:100%; margin-top:20px; margin-bottom: 20px;"><b>로그인</b></v-btn>
          </div>
        </router-link>
        <!-- 카카오로그인 --> 
        <div class=" d-flex justify-center" style="background: #BDBDBD;">
          <v-img src="../../../assets/images/카카오로그인버튼.png" class="logo-img" alt="" @click="kakao()" ></v-img>
        </div>
      </div>
    </div>
  </div>

</template>

<script>
export default {
  data () {
    return {
      email: '',
      password: '',
    }
  },
  methods: {
    kakao(){
      window.location.replace(
        "https://kauth.kakao.com/oauth/authorize?client_id=1bf3f0e4ba92eceb2527659918098b46&redirect_uri=http://localhost:8888/pet/login/kakao&response_type=code"
      );
    }
  },
}
</script>
<style scoped>
.login-center {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.login-btn {
  width: 250px;
}
.login-btn-pd{
  /* padding: 30px; */
  /* margin-top: 120px; */
}
.klogin-btn {
  width: 250px;
  border-radius: 5px;
}
.ph-size {
  width: 250px;
}

</style>