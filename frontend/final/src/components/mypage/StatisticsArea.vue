<template>
  <div class="">
    <div class="m-3">
      <h1>통계</h1>
    </div>
    <div class="m-3">
      <h1>2021년 4월 4째주</h1>
    </div>
    <!-- 목록 --> 
    <div class="health-box d-flex align-items-center">  
      <div class=""><p></p></div>
    </div>
    <!-- 뒤로가기 버튼 --> 
    <div class="mt-5">
      <router-link to="/mypage" class="d-flex justify-center"><v-btn>뒤로가기</v-btn></router-link> 
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
</style>