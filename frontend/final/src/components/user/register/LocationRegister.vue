<template>
  <div class="register-btn-pd">
    <v-flex class="ph-size">
      <h1 class="text-center mb-5">반려견 등록</h1>
      <v-text-field name="nickname" label="주소" id="nickname" v-model="nickname" type="nickname" required></v-text-field>
      <div class="d-flex justify-center">
        <router-link to="#"><v-btn class="register-btn">지역설정</v-btn></router-link> 
      </div>
    </v-flex>
      
      
  </div>

</template>

<script>
export default {
  
}
</script>
<style scoped>
.register-btn {
  width: 200px;
}
.register-btn-pd{
  padding: 30px;
}
</style>