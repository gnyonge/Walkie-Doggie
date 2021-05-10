<template>
  <div>
    <div class="" id="mainBox">
      <v-form>
        <h5 class="text-center mb-5 mt-5">비밀번호 설정</h5>
        <v-text-field name="password" label="비밀번호" id="password" v-model="password" type="password" required color="#48B9A8"></v-text-field>
        <v-text-field name="passwordcheck" label="비밀번호 확인" id="passwordcheck" v-model="passwordcheck" type="passwordcheck" required color="#48B9A8"></v-text-field>
      </v-form>
      <router-link to="/mypage">
        <div class="d-flex justify-center mt-5">
          <v-btn class="" id="mainBtn" style="width:250px; margin-top:20px; margin-bottom: 20px;"><b>변경</b></v-btn>
        </div>
      </router-link>
    </div>
    <div class="d-flex justify-end mt-5">
      <v-btn style="background-color:#E97575; margin:20px;" id="mainBtn"><b>탈퇴하기</b></v-btn>
    </div>
  </div>

</template>

<script>

export default {

}
</script>
<style scoped>

</style>