<template>
  <div class="">
    <!-- 로그인--> 
    <div class="">
      <div id="mainBox">
      <v-form>

          <h5 class="text-center mb-5 mt-5">회원가입</h5>
          <v-text-field name="nickname" label="닉네임" id="nickname" v-model="nickname" type="nickname" required color="#48B9A8"><v-btn slot="append" id="mainBtn" style="margin:5px; width:80px;">중복 확인</v-btn></v-text-field>


          <v-text-field name="email" label="이메일주소" id="email" v-model="email" type="email" required color="#48B9A8"><v-btn id="mainBtn" slot="append" style="margin:5px; width:120px;">인증번호 전송</v-btn></v-text-field>
          

   
          <v-text-field name="code" label="인증번호" id="code" v-model="code" type="code" required color="#48B9A8"><v-btn id="mainBtn" slot="append" style="margin:5px; width:60px;">확인</v-btn></v-text-field>
          


          <v-text-field name="password" label="비밀번호" id="password" v-model="password" type="password" required color="#48B9A8"></v-text-field>
          <v-text-field name="passwordcheck" label="비밀번호 확인" id="passwordcheck" v-model="passwordcheck" type="passwordcheck" required color="#48B9A8"></v-text-field>

      </v-form>
      <div class="">
        <router-link to="/register"><v-btn class="" id="mainBtn" style="width:150px;">회원가입</v-btn></router-link>
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
      code: '',
      passwordcheck: '',
      nickname: '',
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