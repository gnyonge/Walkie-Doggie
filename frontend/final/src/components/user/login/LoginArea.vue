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
          <v-text-field name="email" label="이메일" id="email" v-model="email" type="email" required color="#48B9A8" style="margin-top:40px;"></v-text-field>
          <v-text-field name="password" label="비밀번호" id="password" v-model="password" type="password" required color="#48B9A8" style="margin-top:20px;"></v-text-field>
        </v-form>
          <div class="d-flex justify-center">
            <v-btn class="login-btn" id="mainBtn" style="width:100%; margin-top:30px; margin-bottom: 30px; font-size: 20px; height:45px; border-radius: 5px;" @click="login()"><b>로그인</b></v-btn>
          </div>
        <!-- 카카오로그인 --> 
        <div class=" d-flex justify-center" style="background: #BDBDBD; border-radius: 5px; box-shadow: 1px 3px 0 rgb(0,0,0,0.3);">
          <v-img src="../../../assets/images/카카오로그인버튼.png" class="logo-img" alt="" @click="kakao()" ></v-img>
        </div>
      </div>
    </div>
  </div>

</template>

<script>
import { mapActions } from 'vuex'
export default {
  data () {
    return {
      email: '',
      password: '',
    }
  },
  methods: {
    ...mapActions(['loginNormalInApi']),
    kakao(){
      window.location.replace(
        "https://kauth.kakao.com/oauth/authorize?client_id=1bf3f0e4ba92eceb2527659918098b46&redirect_uri=http://localhost:8888/pet/login/kakao&response_type=code"
      );
    },
    goback() {
      this.$router.push('/')
    },
    login() {
      this.loginNormalInApi({
        u_email: this.email,
        u_flag: 0,
        u_location: "string",
        u_nickname: "string",
        u_password: this.password,
        u_report: 0,
        uid: "adminadmin123"
      })
      .then((res) => {
        console.log(res, '로그인버튼 눌렀을 때 성공 !')
        this.$router.push('/calendar')
      })
      .catch((error) => {
        console.log(error)
      })
       // 반려견 등록 페이지로 이동 
    },
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