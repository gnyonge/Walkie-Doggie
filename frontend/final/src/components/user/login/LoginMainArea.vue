<template>
  <div class="logo-center">
    <div class="login-btn-group">  
      <!-- 1. 서비스로고 -->
      <div class="logo" style="margin-top: 60px">
        <v-img src="../../../assets/images/서비스로고.png" class="rounded-circle" alt=""></v-img>
      </div>
      <!-- 2. 로그인 회원가입 버튼 --> 
      <div class="login-btn-pd">
        <v-btn class="login-btn" id="mainBtn" style="margin-top: 30px;margin-bottom:40px; width: 180px; height: 42px; border-radius: 12px; font-size: 14px; font-family:'Noto Sans KR';" @click="goto('/login')"><b>로그인</b></v-btn>
      </div>
      
      <div class="signup-btn-pd">
        <v-btn class="signup-btn" id="mainBtn" style="margin-bottom:40px;  width: 180px; height: 42px; border-radius: 12px; font-size: 14px; font-family:'Noto Sans KR';" @click="goto('/signup')"><b>회원가입</b></v-btn>
      </div>
      <!-- 선 넣기 !!!!!!!! -->
      <!-- 카카오로그인 --> 
      <div class="signup-btn-pd" >
        <v-img src="../../../assets/images/kakao_login_medium_narrow.png" style="width: 180px; border-radius: 12px; box-shadow: 3px 3px 0 rgb(0,0,0,0.3);" class="logo-img" alt="" @click="kakao()" ></v-img>
      </div>
    </div>
  </div>
</template>

<script>
import { rscApi } from '@/services/api';
import { mapMutations, mapGetters, mapActions } from 'vuex'

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
  created() {
    this.setNowTab(1)
  },
  computed: {
    ...mapGetters(['getUser', 'getDogInfo']),
  },
  methods: {
    ...mapMutations(['setUser', 'setDogInfo', 'setNowTab']),
    ...mapActions(['getUserInfoInApi', 'showDogInfoInApi']),
    goto(path) {
      this.$router.push(path)
    },
    kakao(){
      window.Kakao.Auth.login({
        scope:'account_email, profile',
        success: this.GetMe,
      })
    },
    GetMe(){
      window.Kakao.API.request({
        url:'/v2/user/me',
        success:res=>{
          const kakao_account = res.kakao_account;
          const userInfo={
            nickname : kakao_account.profile.nickname,
            email:kakao_account.email,
          };
          this.user.u_email = userInfo.email;
          this.user.u_nickname = userInfo.nickname;
          rscApi.post(`login/ksign`,this.user)
                .then(({data}) => {
                  this.user = data.user;
                  sessionStorage.setItem('doggieToken',data.doggieToken);
                  this.setUser(this.user)
                  
                  if(this.user.u_location==null){
                    //지역 등록 안했으니까 지역 등록으로
                    this.$router.push("/register")
                  }else{
                    //지역 등록 했으니까
                    this.getUserInfoInApi(this.user.uid)

                  .then((res) => {
                    if (res.data.petList.length == 0) {
                      this.$router.push("/dogregister")
                    }else{
                      this.showDogInfoInApi(res.data.petList[0].peid)
                      .then((res) => {
                        this.setDogInfo(res.data)
                      this.$router.push('/mypage')
                      })
                    }
                  })
                }
            })
            .catch (()=>{    })
        }
      })
  },
  },
}
</script>
<style scoped>
@import url('https://fonts.googleapis.com/css?family=Noto+Sans+KR&display=swap');

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