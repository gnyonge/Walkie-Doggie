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
        <div>왜 좋은가요?</div>
        <div>
          <div v-if="optionValue">"{{ optionValue }}"</div>
          <v-btn text id="likeBox" @click="selectThis(option.name, idx)" :class="{clicked: optionValue == option.name}"
          v-for="option, idx in selectOptions" :key="idx">
            {{option.name}}
          </v-btn>
        </div>
      </div>
      <div>
        <v-btn
          class="mt-3"
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
import { mapGetters, mapMutations, mapActions } from 'vuex'

export default {
  name: 'LikePosting', 
  data() {
    return {
      photo_url: '', 
      optionValue: 0,
      selectOptions: [
        {idx: 0, name: '사진이 잘 나와요!'},
        {idx: 1, name: '휴식 공간이 있어요!'},
        {idx: 2, name: '마킹을 했어요!'},
        {idx: 3, name: '날씨가 좋아요!'},
      ]
    }
  },
  mounted(){
    // 클릭하자마자 사진 바로 받기 
    var camera = document.getElementById('camera')
    camera.click()
  },
  computed: {
      ...mapGetters(['getNowLat', 'getNowLon', 'getAreaName', 'getTempPhotoURL'])
  },
  methods:{
    ...mapMutations([]),
    ...mapActions(['sendNowPost', 'makeTempPhotoUInApi', 'sendNowPostInApi']),
    selectThis(name, idx) {
      this.optionValue = name
      this.isClicked = idx
    },
    // 사진, 옵션 정보 백엔드 정보 전송 
    posting(){
      this.makeTempPhotoUInApi({
        p_latitude: this.getNowLat,
        p_longtitude: this.getNowLat,
        peid: "string",
        l_image: this.getTempPhotoURL,
        l_desc: "string",
        p_location: this.getAreaName
      }).then((res) =>{
        console.log(res, '백엔드 전송 성공')
      }).catch((error) =>{
        console.log(error)
      })
      this.$router.push('/startwalk')
    },
    //사용자 업로드 사진 주소 백엔드 전송 후 보여주기 
    getPhoto(){
      var camera = document.getElementById('camera')
      
      camera.addEventListener('change', function(e) {
        // console.log(e.target.files[0])
        var file = e.target.files[0]
        this.makeTempPhotoUInApi({
          file: file
        }).then(()=> {
          console.log('업로드한 사진 미리보기 성공')
        }).catch((error)=>{
          console.log(error)
        })
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
  #likeBox {
    border-radius: 15px;
    background-color: white;
    box-shadow: 5px 5px 5px #E5E5E5;
    border: 1px solid grey;
    padding: 20px;
    margin: 10px;
    width: 200px;
    cursor: pointer; 
    outline: none; 
    box-shadow: 1px 4px 0 rgb(0,0,0,0.3);
  }
  .clicked {
    box-shadow: 1px 1px 0 rgb(0,0,0,0.3) !important;
    background-color: #BAF1E4 !important;
    border: hidden !important;
    position: relative; 
    top:2px; 
  }
</style>
