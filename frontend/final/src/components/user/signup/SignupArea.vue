<template>
  <div class="">
    <!-- 로그인--> 
    <div class="login-center">
      <v-form>
        <v-flex class="ph-size">
          <h1 class="text-center mb-5">회원가입</h1>
          <v-text-field name="nickname" label="닉네임" id="nickname" v-model="nickname" type="nickname" required></v-text-field>
          <v-btn>중복확인</v-btn>
        </v-flex>
        <v-flex class="ph-size">
          <v-text-field name="email" label="이메일주소" id="email" v-model="email" type="email" required></v-text-field>
          <v-btn>인증번호 전송</v-btn>
        </v-flex>
        <v-flex class="ph-size">
          <v-text-field name="password" label="인증번호" id="code" v-model="code" type="code" required></v-text-field>
          <v-btn>확인</v-btn>
        </v-flex>
        <v-flex class="ph-size">
          <v-text-field name="password" label="비밀번호" id="password" v-model="password" type="password" required></v-text-field>
        </v-flex>
        <v-flex class="ph-size">
          <v-text-field name="passwordcheck" label="비밀번호 확인" id="passwordcheck" v-model="passwordcheck" type="passwordcheck" required></v-text-field>
        </v-flex>
      </v-form>
      <div class="login-btn-pd">
        <v-btn class="login-btn">회원가입</v-btn>
      </div>
    </div>
  </div>

</template>

<script>
export default {
  
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