<template>
  <div>
    <div id="mainBox">
      <div class="d-flex justify-space-between">
        <div>
          <v-icon @click="goback()">mdi-arrow-left</v-icon>
        </div>
        <div>
          회원 탈퇴
        </div>
        <div style="width: 24px; background-color: white;">
        </div>
      </div>
      <div class="mt-10">
        <div style="margin-top: 60px; margin-bottom: 60px" class="d-flex justify-center">
          정말 탈퇴하시겠어요? 
        </div>
        <div class="d-flex justify-center" style="margin-bottom:30px;">
          <v-btn id="mainBtn" class="mr-3" style="background-color:#E97575;" @click="deleteUser()">탈퇴할래요</v-btn>
          <v-btn id="mainBtn" @click="goback()">아니오</v-btn>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {mapGetters, mapActions} from 'vuex'
export default {
  name: "DontleaveusKakao",
  computed: {
    ...mapGetters(['getUser']) 
  },
  created() {
    if (this.getUser == undefined) {
        alert('로그인 해주세요!')
        this.$router.push('/')
      } 
  },
  methods: {
    ...mapActions(['deleteUserInApi']),
    deleteUser() {
      this.deleteUserInApi({
        u_password: null,
        uid: this.getUser.uid,
      })
      .then(()=>{
        alert('다음에 또 이용해주세요!')
        this.$router.push('/loginmain')
      })
    },
    goback() {
      this.$router.push('/mypage')
    }
  }
}
</script>

<style>

</style>