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
          <v-text-field name="password" label="비밀번호" id="password" v-model="password" type="password" required color="#48B9A8" style="margin-top:20px;" class="font-change"></v-text-field>
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
import { rscApi } from '@/services/api';

export default {
  data () {
    return {
      email: '',
      password: '',
      id:"1bf3f0e4ba92eceb2527659918098b46",
      uri:"http://localhost:8080/login",
      type:"code",
      address:"https://kauth.kakao.com"
    }
  },
    created() {
        let code = this.getParameter('code');
    console.log(code);
    if(code != undefined) {
      rscApi.get(`login/kakao?code=${code}`)
      .then(({data}) => {
        console.log(data);
        // doggie_token을 cookie로 저장
        this.$router.push(`/calendar`)
      })
      .catch(()=>{
          this.$router.push(`/login`)

      }) 
    }   
  },
  methods: {
    ...mapActions(['loginNormalInApi']),
            getParameter(name) { 
      var ret; 
      var url = location.href; 
      // get 파라미터 값을 가져올 수 있는 ? 를 기점으로 slice 한 후 split 으로 나눔 
      var params = (url.slice(url.indexOf('?') + 1, url.length)).split('&'); 
      // 나누어진 값의 비교를 통해 paramName 으로 요청된 데이터의 값만 
      for (var i = 0; i < params.length; i++) { 
        var varName = params[i].split('=')[0]; 
        if (varName == name) {
            ret = params[i].split('=')[1];
            return ret;
        } 
      }
    },
    kakao(){
        //  window.Kakao.Auth.login({
        //             scope : 'account_email, profile',
        //             success: (auth) =>{
        //               console.log(auth.access_token)
        //             },
        //             fail : function(err) {
        //                console.log('에러', err);
        //             }
        //         });
      // axios
      // .get(`${this.address}/oauth/authorize`,{
      //   params:{
      //     client_id:this.id,
      //     redirect_uri:this.uri,
      //     response_type:this.type
      //   }
      // })
      // .then(res=>{
      //   console.log("hihihi");
      //   console.log(res.data);
      //   // this.$router.put("/calendar");
      // })
      // .catch(()=>{
      //   console.log("FAILURE")
      // })
      window.location.replace("http://localhost:8888/pet/login/oauth")
      // window.location.replace("https://kauth.kakao.com/oauth/authorize?client_id=1bf3f0e4ba92eceb2527659918098b46&redirect_uri=http://localhost:8080&response_type=code")
      // const URL = new URL("https://kauth.kakao.com/oauth/authorize?client_id=1bf3f0e4ba92eceb2527659918098b46&redirect_uri=http://localhost:8888/pet/login/kakao&response_type=code")
      // window.location.assign(URL);
      
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
.font-change {
  font-family: 'Courier New', Courier, monospace;
}
label {
  font-family: "Jeju Gothic", serif !important;
}
</style>