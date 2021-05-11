<template>
  <div>
    <div id="mainBox" style="text-align: center;">
      <!-- 사진받기 -->
      <v-img
      id = "photoimg"
      height="330"
      max-height="330"
      :src = photo_url>
      </v-img>
      <input 
        type="file" 
        name="cameraFile" 
        id="camera" 
        accept="image/*" 
        capture="camera"
        @click= getPhoto
        required>
      <br>
      <!-- <v-btn  id="button">
        사진 업로드
      </v-btn> -->
      <!-- 사용자 의견 선택옵션 -->
      <div>
        <div>선정이유</div>
        <div>
          {{ optionvalue }}
          <div id="mainBox">
            <input 
              type="radio" 
              name="chk_info" 
              class="btn" 
              id="btn01" 
              value="1" 
              v-model="optionvalue"/> 
            <label for="btn01"> 마킹 
              <v-icon>mdi-lead-pencil</v-icon>
            </label>
          </div>
          <div id="mainBox">
            <input 
              type="radio" 
              name="chk_info" 
              class="btn" 
              id="btn02" 
              value="2" 
              v-model="optionvalue"/> 
              <label for="btn02"> 강아지 자랑 </label>
          </div>
          <div id="mainBox">
            <input 
              type="radio" 
              name="chk_info" 
              class="btn" 
              id="btn03" 
              value="3" 
              v-model="optionvalue"/> 
              <label for="btn03"> 휴식공간 </label>
          </div>
          <div id="mainBox">
            <input 
              type="radio" 
              name="chk_info"
              class="btn" 
              id="btn04" 
              value="4"  
              v-model="optionvalue"/> 
              <label for="btn04"> 날씨 좋음 </label>
          </div>
        </div>
      </div>
      <div>
        <v-btn
          large
          id="mainBtn"
          @click="posting">
          입력완료
        </v-btn>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'LikePosting', 
  data() {
    return {
      photo_url: '', 
      optionvalue: 0, 
    }
  },
  mounted(){
    // 클릭하자마자 사진 바로 받기 
    var camera = document.getElementById('camera')
    camera.click()
  },
  methods:{
    // 사진, 옵션 정보 백엔드 정보 전송 
    posting(){
      
      this.$router.push('/startwalk')
    },
    //사용자 업로드 사진 주소 백엔드 전송 후 보여주기 
    getPhoto(){
      var camera = document.getElementById('camera')
      
      camera.addEventListener('change', function(e) {
        // console.log(e.target.files[0])
        var file = e.target.files[0]
        console.log(URL.createObjectURL(file))
        // 백엔드 서버에서 이미지 주소 받아서 넣기 
        this.photo_url = file
      })
    },
    
  },
}
</script>

<style scoped>
  .btn{
    opacity: 0;
  }
  .btn>label{
    padding: 5px 10px;
    border: 1px solid #ccc;
    border-radius: 3px;
  }
  input[type=radio]:checked + label 
  {
    background: #000000 ;
    color: rgb(255, 255, 255); 
  }
</style>
