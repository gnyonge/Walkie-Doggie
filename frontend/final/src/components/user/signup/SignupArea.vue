<template>
  <div id="mainBox">
    <div class="d-flex justify-space-between mb-8">
      <!-- 뒤로가기 버튼  -->
      <div>
        <v-icon @click="goback()">mdi-arrow-left</v-icon>
      </div>
      <div>
        <b>회원가입</b>
      </div>
      <div style="width: 24px; background-color: white;">
      </div>
    </div>
    <!-- 회원가입 폼 -->
    <v-form>
      <v-text-field :rules="rules1" name="nickname" label="닉네임" id="nickname" v-model="nickname" type="nickname" required color="#48B9A8">
        <v-btn slot="append" id="mainBtn" style="margin:5px; width:80px;" @click="checkNickname()"><b>중복 확인</b></v-btn>
      </v-text-field>

      <div v-if="nickCheck" style="font-size: 12px">
        <b>사용가능한 닉네임 입니다!</b>
      </div>

      <v-text-field :rules="rules1" name="email" label="이메일주소" id="email" v-model="email" type="email" required color="#48B9A8">
        <v-btn id="mainBtn" slot="append" style="margin:5px; width:120px;" @click="sendCheckEmail()" :disabled="sendEmailMsg == true"><b>인증메일 전송</b></v-btn>
      </v-text-field>
      <div v-if="sent" style="font-size: 12px; color:red;">
        <b>인증메일을 보냈습니다.</b>
      </div>
      <div v-if="emailCheck" style="font-size: 12px; color:red;">
        <b>이미 회원가입한 이메일입니다!</b>
      </div>

      <v-text-field :rules="rules1" name="code" label="인증번호" id="code" v-model="code" type="code" required color="#48B9A8">
        <v-btn id="mainBtn" slot="append" style="margin:5px; width:60px;" @click="checkAuthEmail()"><b>확인</b></v-btn>
      </v-text-field>
      <div style="color:red; font-size:12px" v-if="codeCheck">
          <b>인증 코드를 다시 확인해주세요.</b>
      </div>
      <div style="font-size:12px" v-if="codeSuccess">
          <b>인증되었습니다.</b>
      </div>
      <v-text-field :rules="rules1" name="password" label="비밀번호" id="password" v-model="password" type="password"
       required color="#48B9A8" class="font-change"></v-text-field>
       
      <v-text-field name="passwordcheck" label="비밀번호 확인" id="confirmPassword" v-model="confirmPassword" 
      :rules="rules" type="password" required color="#48B9A8" class="font-change"></v-text-field>
    
    </v-form>
    <!-- 회원가입 완료 버튼  -->
    <div class="d-flex justify-center">
      <v-btn id="mainBtn" style="margin-top: 30px;width: 180px; height: 60px; border-radius: 50px; font-size: 25px" @click="registerUser()" :disabled="confirmSignup == false"><b>회원가입</b></v-btn>
    </div>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import axios from 'axios';
export default {
  data () {
    return {
      email: '',
      password: '',
      code: '',
      confirmPassword: '',
      nickname: '',
      nickCheck: false,
      sendEmailMsg: false,
      emailCheck: false,
      sent: false,
      codeCheck: false,
      codeSuccess: false,
      rules: [
        ()=> (this.password=== this.confirmPassword) || '비밀번호가 일치하지 않아요'
      ],
      rules1: [
        value => !!value || '필수 입력사항입니다!',
        value => !!value.replace(/(\s*)/g, "") || '공백은 불가해요!',
        ],
      rules2: [
      value => !!value.replace(/(\s*)/g, "") || '공백은 불가해요!',
      // value => (value || '').length <= 20 || 'Max 20 characters',
      ],
    }
  },
  computed: {
    ...mapGetters(['getUser']),
    confirmSignup() {
        if (this.email.length < 1) {
          return false
        }
        if (this.password.length < 1) {
          return false
        }
        if (this.code.length < 1) {
          return false
        }
        if (this.confirmPassword.length < 1) {
          return false
        }
        if (this.nickname.length < 1) {
          return false
        }
        return true
      }
  },
  methods: {
    ...mapActions(['createUserInApi','sendEmailInApi','checkPasswordInApi','checkAuthEmailInApi','loginNormalInApi']),
    goback() {
        this.$router.push('/loginmain')
    },
    checkNickname() {
      return axios.get(`http://localhost:8888/pet/user/confirm/check/${this.nickname}`)
      .then((res) => {
        this.nickCheck = res.data.flag
      }).catch((error) => {
        console.error(error)
      })
    },

    // 회원가입 눌렀을 때 
    registerUser() {
      this.createUserInApi({
        u_email: this.email,
        u_flag: 0,
        u_location: "string",
        u_nickname: this.nickname,
        u_password: this.password,
        u_report: 0,
        uid: "string"
      })
      .then((res) => {
        this.loginNormalInApi({
          u_email: res.data.email,
          u_password: res.data.password,
        }).then((res) => {
          if (res === "로그인에 성공하였습니다.") { // 로그인 됨
            this.$router.push('/register') // 반려견 등록 페이지로 이동 
          } else { // 로그인 안됨
            alert('회원가입 후 로그인에 실패하였습니다.')
          }
        })
      })
    },

    // 인증메일 보내기 눌렀을 때
    // 한번 클릭하면 버튼이 안보이도록 수정해야한다. 
    sendCheckEmail() {
      this.sendEmailInApi({
        email: this.email,
      })
      .then((res) => {
        if(res.data.message ==="인증 메일을 보냈습니다. 확인해주십시오") {
          this.sendEmailMsg = true
          this.emailCheck = false
          this.sent = true
        }
        if(res.data.message ==="회원가입된 메일입니다.") {
          this.emailCheck = true
        }
      })
    },

    // 인증 메일 확인 
    checkAuthEmail() {
      this.checkAuthEmailInApi({
        auth: this.code,
        email: this.email,
      })
      .then((res) => {
        if(res.data.message ==="인증 번호가 틀렸습니다.") {
          this.codeCheck = true
        }else {
          this.codeSuccess = true
          this.codeCheck = false
        }
      })
    }
    
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
.login-btn-pd{
  padding: 30px;
  margin-top: 120px;
}
.klogin-btn {
  width: 250px;
  border-radius: 5px;
}
.ph-size {
  width: 250px;
}

</style>