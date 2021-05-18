<template>
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
    <div>
      <div>

      </div>
      <div v-if="check" class="d-flex justify-center mt-10" >
        비밀번호를 확인해 주세요.
      </div>
      <div v-if="check">
        <v-form class="mt-10">
        <v-text-field name="password" label="기존 비밀번호" id="password" v-model="password" type="password" required color="#48B9A8" class="font-change">
          <v-btn @click="checkpw()" slot="append" style="margin:5px; width:60px;" id="mainBtn">확인</v-btn>
        </v-text-field>
      </v-form>
      <div style="color:red; font-size:14px" v-if="!pass">
          비밀번호가 틀렸습니다.
        </div>
      </div>
      
      <div v-if="!check" class="mt-10">
        <div style="margin-top: 60px; margin-bottom: 60px" class="d-flex justify-center">
          정말 탈퇴하시겠어요? 
        </div>
        <div class="d-flex justify-center" style="margin-bottom:30px;">
          <v-btn id="mainBtn" class="mr-3" style="background-color:#E97575;" @click="deleteUser()">탈퇴할래요</v-btn>
          <v-btn id="mainBtn" @click="goback()">아니오</v-btn>
        </div>
      </div>
      </div>
      
      <div style="width: 24px; background-color: white;">
      </div>
    </div>
</template>

<script>
import {mapGetters, mapActions} from 'vuex'
export default {
  name: "DontleaveusArea",
  data() {
    return {
     password: '',
     check: true,
     pass: true
    }
  },
  computed: {
    ...mapGetters(['getUser'])
  },
  methods: {
    ...mapActions(['deleteUserInApi', 'checkPasswordInApi']),
    deleteUser() {
      this.deleteUserInApi({
        u_password: this.password,
        uid: this.getUser.uid,
      })
      .then(()=>{
        this.$router.push('/loginmain')
      })
    },
    checkpw() {
      this.checkPasswordInApi({
        u_password: this.password,
        uid: this.getUser.uid
      })
      .then((res) => {
        if(!res.data.message) {
          // this.check = false
          this.pass = false
        } else {
          this.check = false
          this.pass = true
        }
      })
    },
    goback() {
      this.$router.push('/infochangepw')
    },
  }
}
</script>
<style scoped>
.logo {
  width: 150px;
  height: 150px; 
  border-radius: 70%;
  overflow: hidden;
  border: solid 3px;
  /* display: flex; */
  /* justify-content: center; */
  /* align-items: center; */
  
}
.mypage-list {
  width: 100%;
  height: 50px;
  border-radius: 0;
  box-shadow: 0;
  background: #DEB98B;
}
.mypage-list-btn {
  box-shadow: 0;
  background: #DEB98B;
}
.health-box {
  padding: 25px;
  height: 80%;
  background: #EFE8DE;
  margin: 10px;
}
.lst {
  width: 100%;
  height: 95px;
  background: #EFE8DE;
  font-size: 40px;
}
p {
  color: black;
  font-size: 40px;
}
#healthDiv {
  border-radius: 15px;
  background-color: white;
  border: 1px solid #E5E5E5;
  box-shadow: 3px 3px 3px #E5E5E5;
}

</style>