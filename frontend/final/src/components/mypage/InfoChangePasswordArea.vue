<template>
  <div>
    <div class="" id="mainBox">
      <div>
        <v-icon @click="goback()">mdi-arrow-left</v-icon>
      </div>
      <v-form>
        <h5 class="text-center mb-5 mt-5">비밀번호 설정</h5>
        <v-text-field name="password" label="기존 비밀번호" id="password" v-model="password" type="password" required color="#48B9A8" class="font-change"></v-text-field>
        <div style="color:red; font-size:14px" v-if="check">
          기존 비밀번호를 확인해주세요.
        </div>
        <v-text-field name="newpassword" label="새로운 비밀번호" id="newpassword" v-model="newpassword" type="password" required color="#48B9A8" class="font-change"></v-text-field>
        <v-text-field name="newpasswordcheck" label="새로운 비밀번호 확인" id="newpasswordcheck" v-model="newpasswordcheck" type="password" required color="#48B9A8" class="font-change"></v-text-field>
      </v-form>
        <div class="d-flex justify-center mt-5">
          <v-btn class="" id="mainBtn" style="width:250px; margin-top:20px; margin-bottom: 20px;" @click="changePassword()"><b>변경</b></v-btn>
        </div>
    </div>
    <div class="d-flex justify-end mt-5">
      <v-btn style="background-color:#E97575; margin:20px;" id="mainBtn" @click="goto()"><b>탈퇴하기</b></v-btn>
    </div>
  </div>

</template>

<script>
import { mapActions, mapGetters } from 'vuex'
export default {
  data () {
    return {
      password: '',
      newpassword: '',
      newpasswordcheck: '',
      check: false,

    }
  },
  computed: {
    ...mapGetters(['getUser'])
  },
  methods: {
    ...mapActions(['loginNormalInApi','checkPasswordInApi','changePasswordInApi']),
    goback() {
      this.$router.push('/mypage')
    },
    changePassword() {
      this.checkPasswordInApi({
        u_password: this.password,
        uid: this.getUser.uid
      }).then((res) => {
        // 비밀번호 틀림
        console.log(res.data,'제발베랄자랄')
        if (res.data.message == false) {
          this.check = true
          console.log('비밀번호 틀림')
        }else { // 비밀번호 맞음
          this.check = false
          this.changePasswordInApi({
          u_password: this.password,
          uid: this.getUser.uid,
          newPass: this.newpassword
          })
          .then((res) => {
            console.log(res, '비밀번호 변경 성공')
          })
        }
      })
      
    },
    goto() {
      this.$router.push('/dontleaveus')
    }
  }
}
</script>
<style scoped>
.font-change {
  font-family: 'Courier New', Courier, monospace;
}
</style>