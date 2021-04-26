<template>
  <div class="">
    <div class="m-3">
      <h1>지역</h1>
    </div>
    
    <div class="health-box d-flex align-items-center">  
      <div class=""><p>지역수정하는 칸</p></div>
      <v-btn class="login-btn">변경</v-btn>
    </div>
    <div class="m-3">
      <h1>비밀번호</h1>
    </div>
    
    <div class="health-box d-flex align-items-center">  
      <v-flex class="ph-size">
          <v-text-field name="password" label="비밀번호" id="password" v-model="password" type="password" required></v-text-field>
        </v-flex>
        <v-flex class="ph-size">
          <v-text-field name="passwordcheck" label="비밀번호 확인" id="passwordcheck" v-model="passwordcheck" type="passwordcheck" required></v-text-field>
        </v-flex>
      <v-btn class="login-btn">변경</v-btn>
    </div>
  </div>
  

</template>

<script>
export default {
  
}
</script>
<style scoped>
.health-box {
  padding: 25px;
  
  height: 80%;
  background: #EFE8DE;
  margin: 10px;
}
p {
  color: black;
  font-size: 40px;
}
</style>