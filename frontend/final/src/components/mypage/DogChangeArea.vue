<template>
  <div id="mainBox">
    <div>
        <v-icon @click="goback()">mdi-arrow-left</v-icon>
    </div>
    <div class="d-flex justify-center" style="font-size: 30px;">두부</div>
    <div class="d-flex justify-center">
        <v-avatar width="100px" height="100px">
          <img
            :src="photo_url"
            alt="프로필 사진" 
          >
        </v-avatar>
      </div>
      <div class="d-flex justify-center">
        <div class="filebox mt-3"> 
          <label for="ex_file">업로드 <v-icon>mdi-camera</v-icon></label> 
          <input type="file" id="ex_file" accept="image/*" @click="getPhoto()" ref="imageInput"> 
        </div>
      </div> 
    <!-- <div class="d-flex justify-center mt-3">
      <v-img src="../../assets/images/서비스로고.png" 
        class="rounded-circle" height="158"
        max-height="158"
        max-width="158" alt="">
      </v-img>
      <div class="d-flex justify-end">
        <v-icon>mdi-camera-flip</v-icon>
      </div>
    </div> -->
    
    <div>
      <div class="d-flex justify-between mt-5 ml-5">나이</div>
      <div class="d-flex justify-between mt-5 ml-5">몸무게</div>
      <div class="d-flex justify-between mt-5 ml-5">트라우마</div>
      <div class="d-flex justify-between mt-5 ml-5">질병</div>
      <div class="d-flex justify-between mt-5 ml-5">생일</div>
      
    </div>
    
    <router-link to="/mypage">
      <div class="d-flex justify-center mt-5">
        <v-btn id="mainBtn" style="width:250px;"><b>변경</b></v-btn>
      </div>
    </router-link>
    <div class="d-flex justify-center mt-5">
        <v-btn id="mainBtn" style="width:250px;"><b>삭제</b></v-btn>
      </div>
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
import { mapActions, mapGetters } from 'vuex'
export default {
  data() {
    return {
      photo_url: require('@/assets/images/서비스로고.png'),
      file: null,
    }
  },
  computed: {
    ...mapGetters(['getTempPhotoURL'])
  },
  methods: {
    ...mapActions(['makeTempPhotoUrlInApi']),
    goback() {
        this.$router.push('/mypage')
      },
    getPhoto(){
      var camera = document.getElementById('ex_file')
      var t = this
      camera.addEventListener('change', function(e) {
        let formData = new FormData()
        var file = e.target.files[0]
        t.file = e.target.files[0]
        console.log(file,'파일')
        formData.append('file', t.file)
        t.makeTempPhotoUrlInApi(
          formData
        ).then (()=> {
          t.photo_url = t.getTempPhotoURL
        })    
      })
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