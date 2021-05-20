<template>
  <div>
    <!-- 로그인--> 
    <div>
      <!-- <v-form @submit.prevent="onSignin"> -->
      <div id="mainBox">
        <div class="d-flex justify-space-between">
          <div>
          <v-icon @click="goback()">mdi-arrow-left</v-icon>
          </div>
          <div>
            <b>로그인</b>
          </div>
          <div style="width: 24px; background-color: white;">
          </div>
        </div>
        <v-form>
          <v-text-field label="이메일" v-model="email" type="email" required color="#48B9A8" style="margin-top:40px;"></v-text-field>
          <v-text-field label="비밀번호" v-model="password" type="password" required color="#48B9A8" style="margin-top:20px;"></v-text-field>
        </v-form>
        <div style="color:red; font-size:14px" v-if="check">
          아이디나 비밀번호를 확인해주세요.
        </div>
        <div class="d-flex justify-center">
          <v-btn class="login-btn" id="mainBtn" style="width:100%; margin-top:30px; margin-bottom: 30px; font-size: 20px; height:45px; border-radius: 5px;" @click="login()"><b>로그인</b></v-btn>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapMutations, mapGetters } from 'vuex'

export default {
  data () {
    return {
      email: '',
      password: '',
      user:{
        uid:"",
        u_email:"",
        u_location:"",
        u_nickname:"",
    },
      check: false,
    };

  },
  computed: {
    ...mapGetters(['getDogInfo', 'getUser', 'getMyDogListInfo'])
  },
    created() { 
     this.setNowTab(1)
  },
  methods: {
    ...mapActions(['loginNormalInApi', 'getUserInfoInApi', 'showDogInfoInApi']),
    ...mapMutations(['setNowTab']),
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
    goback() {
      this.$router.push('/')
    },
    login() {
      this.loginNormalInApi({
        u_email: this.email,
        u_password: this.password,
      })
      .then((res) => {
        if(res == "로그인에 성공하였습니다.") {
          this.getUserInfoInApi(this.getUser.uid)
          .then(() => {
            this.showDogInfoInApi(this.getMyDogListInfo[0].peid)
            .then(() => {
              this.$router.push('/calendar')
            })
          })
        }
        else {
          this.check = true
        }
      })
    },
  }
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