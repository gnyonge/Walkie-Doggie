<template>
  <div class="">
    <!-- 로그인--> 
    <div class="login-center">
      <!-- <v-form @submit.prevent="onSignin"> -->
      <v-form>
        <v-flex class="ph-size">
          <h1 class="text-center mb-5">로그인</h1>
          <v-text-field name="email" label="이메일" id="email" v-model="email" type="email" required></v-text-field>
        </v-flex>

        <v-flex class="ph-size">
          <v-text-field name="password" label="비밀번호" id="password" v-model="password" type="password" required></v-text-field>
        </v-flex>
      </v-form>
      <div class="login-btn-pd">
        <v-btn class="login-btn">로그인</v-btn>
      </div>
      <!-- 카카오로그인 --> 
      <div class="klogin-btn" style="background: #BDBDBD;">
        <v-img src="../../../assets/images/카카오로그인버튼.png" class="logo-img" alt=""></v-img>
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