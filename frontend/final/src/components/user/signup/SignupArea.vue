<template>
  <div id="mainBox">
    <div class="d-flex justify-space-between mb-8">
      <!-- 뒤로가기 버튼  -->
      <div>
        <v-icon @click="goback()">mdi-arrow-left</v-icon>
      </div>
      <div>
        회원가입
      </div>
      <div style="width: 24px; background-color: white;">
      </div>
    </div>
    <!-- 회원가입 폼 -->
    <v-form>
      <v-text-field name="nickname" label="닉네임" id="nickname" v-model="nickname" type="nickname" required color="#48B9A8"><v-btn slot="append" id="mainBtn" style="margin:5px; width:80px;">중복 확인</v-btn></v-text-field>
      <v-text-field name="email" label="이메일주소" id="email" v-model="email" type="email" required color="#48B9A8"><v-btn id="mainBtn" slot="append" style="margin:5px; width:120px;" @click="sendCheckEmail()">인증메일 전송</v-btn></v-text-field>
      <v-text-field name="code" label="인증번호" id="code" v-model="code" type="code" required color="#48B9A8"><v-btn id="mainBtn" slot="append" style="margin:5px; width:60px;">확인</v-btn></v-text-field>
      <v-text-field name="password" label="비밀번호" id="password" v-model="password" type="password" required color="#48B9A8" class="font-change"></v-text-field>
      <v-text-field name="passwordcheck" label="비밀번호 확인" id="confirmPassword" v-model="confirmPassword" :rules="rules" type="password" required color="#48B9A8" class="font-change"></v-text-field>
      <!-- <v-text-field name="password" label="비밀번호" id="password" v-model="password" :rules="passwordRules" type="password" required color="#48B9A8" class="font-change"></v-text-field> -->
      <!-- <v-text-field name="passwordcheck" label="비밀번호 확인" id="confirmPassword" v-model="confirmPassword" :rules="[confirmPasswordRules, passwordConfirmationRule]" type="password" required color="#48B9A8" class="font-change"></v-text-field> -->
    
    </v-form>
    <!-- 회원가입 완료 버튼  -->
    <!-- 버튼 비활성화 시켜놓고 password랑 confirmPassword가 같을 때만 활성화 되게  -->
    <div class="d-flex justify-center">
      <v-btn class="" id="mainBtn" style="margin-top: 30px;width: 180px; height: 60px; border-radius: 50px; font-size: 25px" @click="registerUser()">회원가입</v-btn>
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
      code: '',
      confirmPassword: '',
      nickname: '',
      // passwordRules: [v=> !!v || "Password is required"],
      // confirmPasswordRules: [v=> !!v || "Password is required"],
      rules: [
        ()=> (this.password=== this.confirmPassword) || '비밀번호가 일치하지 않아요'
      ]
    }
  },
  methods: {
    ...mapActions(['createUserInApi','sendEmailInApi','checkPasswordInApi']),
    goback() {
        this.$router.push('/loginmain')
    },
    
    // 회원가입 눌렀을 때 
    registerUser() {
      // this.checkPasswordInApi({
        // u_email: this.email,
        // u_flag: 0,
        // u_location: "string",
        // u_nickname: this.nickname,
        // u_password: this.password,
        // u_report: 0,
      //   uid: "string"
      // }).then((res) => {
      //   console.log(res, '비밀번호 확인 눌렀을 때 성공 !')
      // });
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
        console.log(res.data, '회원가입버튼 눌렀을 때 성공 !')
      })
      this.$router.push('/register') // 반려견 등록 페이지로 이동 
    },
    // 인증메일 보내기  눌렀을 때 
    // 한번 클릭하면 버튼이 안보이도록 수정해야한다. 
    sendCheckEmail() {
      this.sendEmailInApi({
        // auth: "string",
        email: this.email,
        // flag: 0
      })
      .then((res) => {
        console.log(this.email)
        console.log(res, '인증번호 메일 보내기 눌렀을 때 성공 !')
        //500에러 뜸 
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
.font-change {
  font-family: 'Courier New', Courier, monospace;
}
</style>