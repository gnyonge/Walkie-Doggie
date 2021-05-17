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
      <div class="signup-btn-pd" >
        <v-img src="../../../assets/images/kakao_login_medium_narrow.png" style="border-radius: 12px;" class="logo-img" alt="" @click="kakaologout()" ></v-img>
      </div>
    </div>
  </div>
</template>

<script>
import { rscApi } from '@/services/api';

export default {
  data(){
    return{
      user:{
        uid:"",
        u_email:"",
        u_location:"",
        u_nickname:"",
    },
    }
  },
  methods: {
    goto(path) {
      this.$router.push(path)
    },
    kakao(){
            console.log(window.Kakao);
      // const params={
      //   redirectUri:"http://localhost:8080/login",
      // };
      window.Kakao.Auth.login({
        scope:'account_email, profile',
        success: this.GetMe,
        // fail:LogoutFailure,
      });
    },
        GetMe(authObj){
      console.log(authObj);
      window.Kakao.API.request({
        url:'/v2/user/me',
        success:res=>{
          const kakao_account = res.kakao_account;
          const userInfo={
            nickname : kakao_account.profile.nickname,
            email:kakao_account.email,
          };
          console.log(userInfo);
          this.user.u_email = userInfo.email;
          this.user.u_nickname = userInfo.nickname;
          rscApi.post(`login/ksign`,this.user)
                .then(({data}) => {
                  this.user = data.user;
                  sessionStorage.setItem('doggie_token',data.doggie_token);
                  if(this.user.u_location==null){
                    //지역 등록 안했으니까 지역 등록으로
                    this.$router.push("/register")
                  }else{
                    //지역 등록 했으니까
                    if(data.list==null){
                    this.$router.push("/dogregister")

                    }else{
                    this.$router.push('/calendar')
                    }
                  }
                  console.log(data);
                  
    //     console.log(data);
    //     // doggie_token을 cookie로 저장
    //     this.$router.push(`/calendar`)
      })
                .catch (()=>{    
          // this.$router.push(`/login`)

      })
          // this.$router.push('/calendar')
        }
      })
  },
  kakaologout(){
    window.Kakao.Auth.logout()
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