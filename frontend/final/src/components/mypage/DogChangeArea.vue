<template>
  <div class="" id="mainBox">
    <div>
        <v-icon @click="goback()">mdi-arrow-left</v-icon>
    </div>
    <div class="d-flex justify-center" style="font-size: 30px;">두부</div>
    <div class="d-flex justify-center mt-3">
      <v-img src="../../assets/images/서비스로고.png" 
        class="rounded-circle" height="158"
        max-height="158"
        max-width="158" alt="">
      </v-img>
      <div class="d-flex justify-end">
        <v-icon>mdi-camera-flip</v-icon>
      </div>
    </div>
    
    <div>
      <div class="d-flex justify-between mt-5 ml-5">나이</div>
      <div class="d-flex justify-between mt-5 ml-5">몸무게</div>
      <div class="mt-5 ml-5">특이사항</div>
    </div>
    
    <router-link to="/mypage">
      <div class="d-flex justify-center mt-5">
        <v-btn class="" id="mainBtn" style="width:250px; margin-top:20px; margin-bottom: 20px;"><b>변경</b></v-btn>
      </div>
    </router-link>
    <!-- <div>
       <v-btn
      class="mx-2"
      fab
      dark
      color="orange"
    >
      <v-icon dark>
        mdi-plus
      </v-icon>
    </v-btn>
    </div> -->
   

  </div>
  

</template>

<script>
export default {
  methods: {
    goback() {
        this.$router.push('/mypage')
      }
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
.profile-box {
  padding: 25px;
  width: 100%;
  height: 30%;
  background: #EFE8DE;
}
p {
  color: black;
  font-size: 40px;
}
</style>