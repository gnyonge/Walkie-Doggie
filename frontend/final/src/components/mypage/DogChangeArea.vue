<template>
  <div class="">
    <div class="profile-box d-flex align-items-center">  
      <!-- 1. 프로필 박스 -->
      <div class="logo mt-1">
        <v-img src="../../assets/images/서비스로고예시.png" class="" alt=""></v-img>
      </div>
      <div class="ml-8"><p>찡꼬</p></div>
    </div>
    <div class="profile-box d-flex align-items-center mt-5">  
      <!-- 1. 프로필 박스 -->
      <div class="logo mt-1">
        <v-img src="../../assets/images/서비스로고예시.png" class="" alt=""></v-img>
      </div>
      <div class="ml-8"><p>두부</p></div>
    </div>
    <div class="profile-box d-flex align-items-center mt-5">  
      <!-- 1. 프로필 박스 -->
      <div class="logo mt-1">
        <v-img src="../../assets/images/서비스로고예시.png" class="" alt=""></v-img>
      </div>
      <div class="ml-8"><p>바니</p></div>
    </div>
    <div>
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
    </div>
   

    </div>
  

</template>

<script>
export default {
  
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