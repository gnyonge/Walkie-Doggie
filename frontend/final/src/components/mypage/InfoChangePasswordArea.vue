<template>
  <div>
    <div id="mainBox">
      <div class="d-flex justify-space-between">
        <div>
        <v-icon @click="goback()">mdi-arrow-left</v-icon>
        </div>
        <div>
          비밀번호 변경
        </div>
        <div style="width: 24px; background-color: white;">
        </div>
      </div>
      <v-form class="mt-7">
        <v-text-field name="password" label="기존 비밀번호" id="password" v-model="password" type="password" required color="#48B9A8" class="font-change"></v-text-field>
        <div style="color:red; font-size:14px" v-if="check">
          기존 비밀번호를 확인해주세요.
        </div>
        <v-text-field name="newpassword" label="새로운 비밀번호" id="newpassword" v-model="newpassword" type="password" required color="#48B9A8" class="font-change"></v-text-field>
        <v-text-field name="newpasswordcheck" label="새로운 비밀번호 확인" id="newpasswordcheck" v-model="newpasswordcheck" type="password" required color="#48B9A8" class="font-change"></v-text-field>
      </v-form>
      <div class="d-flex justify-center">
        <v-btn class="" id="mainBtn" style="width:250px; margin-top:20px;" @click="changePassword()"><b>비밀번호 변경</b></v-btn>
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
  created() {
    if (this.getUser == undefined) {
        alert('로그인 해주세요!')
        this.$router.push('/')
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
        if (res.data.message == false) {
          this.check = true
        }else { // 비밀번호 맞음
          this.check = false
          this.changePasswordInApi({
          u_password: this.password,
          uid: this.getUser.uid,
          newPass: this.newpassword
          })
          .then(() => {
            alert('변경되었습니다!')
            this.$router.push('/mypage')
          })
        }
      })
    },
    goto() {
      this.$router.push('/dontleaveus')
    },
  }
}
</script>
<style scoped>

</style>