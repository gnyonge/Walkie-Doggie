<template>
  <div>
    <div class="" id="mainBox">
      <div>
        <v-icon @click="goback()">mdi-arrow-left</v-icon>
      </div>
      <v-form>
        <h5 class="text-center mb-5 mt-5">비밀번호 설정</h5>
        <v-text-field name="password" label="기존 비밀번호" id="password" v-model="password" type="password" required color="#48B9A8"></v-text-field>
        <v-text-field name="newpassword" label="새로운 비밀번호" id="newpassword" v-model="newpassword" type="password" required color="#48B9A8"></v-text-field>
        <v-text-field name="newpasswordcheck" label="새로운 비밀번호 확인" id="newpasswordcheck" v-model="newpasswordcheck" type="password" required color="#48B9A8"></v-text-field>
      </v-form>
      <router-link to="/mypage">
        <div class="d-flex justify-center mt-5">
          <v-btn class="" id="mainBtn" style="width:250px; margin-top:20px; margin-bottom: 20px;" @click="changePassword()"><b>변경</b></v-btn>
        </div>
      </router-link>
    </div>
    <div class="d-flex justify-end mt-5">
      <v-btn style="background-color:#E97575; margin:20px;" id="mainBtn"><b>탈퇴하기</b></v-btn>
    </div>
  </div>

</template>

<script>
import { mapActions } from 'vuex'
export default {
  data () {
    return {
      password: '',
      newpassword: '',
      newpasswordcheck: '',

    }
  },
  methods: {
    ...mapActions(['loginNormalInApi']),
    goback() {
      this.$router.push('/mypage')
    },
    changePassword() {
      this.checkPasswordInApi({
        u_password: "string",
        uid: "string"
      })
    }
  }
}
</script>
<style scoped>

</style>