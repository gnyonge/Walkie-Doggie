<template>
  <div>
    <div id="mainBox" >
      <div>
        <v-icon @click="goback">mdi-arrow-left</v-icon>
      </div>
      <div style="text-align: center;">
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
  </div>
</template>

<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'

export default {
  name: 'LikePosting', 
  data() {
    return {
      photo_url: '', 
      optionValue: -1,
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
      ...mapGetters(['getNowLat', 'getNowLon', 'getAreaName', 'getTempPhotoURL', 'getDogInfo', 'getWid'])
  },
  methods:{
    ...mapMutations(['setNowLat', 'setNowLon', 'setTempPhotoURL']),
    ...mapActions(['sendNowPost', 'makeTempPhotoUrlInApi', 'sendNowPostInApi', ]),
    selectThis(name, idx) {
      this.optionValue = name
      this.isClicked = idx
    },
    //사용자 업로드 사진 주소 백엔드 전송 후 보여주기 
    getPhoto(){
      var camera = document.getElementById('camera')
      var t = this
      camera.addEventListener('change', function(e) {
        const form = new FormData()
        var file = e.target.files[0]
        form.append('file', file)
        t.makeTempPhotoUrlInApi(
          form
        ).then(()=> {
          t.photo_url = t.getTempPhotoURL
        }).catch((error)=>{
          console.log(error)
        })
        
      })
    },
    // 사진, 옵션 정보 백엔드 정보 전송 
    posting(){
      if(this.photo_url === ''){
        alert('사진을 업로드 해 주세요!')
      }else if (this.optionValue === -1){
        alert('선정이유를 선택해 주세요!')
      } else {
        this.sendNowPostInApi({
          wid: this.getWid, 
          p_latitude: this.getNowLat,
          p_longtitude: this.getNowLon,
          peid: this.getDogInfo.pet.peid,
          l_image: this.getTempPhotoURL,
          l_desc: this.optionValue,
          p_location: this.getAreaName
        }).then((res) =>{
          console.log(res, '잘왔을때')
          // 같은 장소에 핀 찍은 경우 
          if (res.status === 404){
            this.deleteAll()
            this.$router.push('/walk')
            alert('같은 장소에는 좋아요를 찍을 수 없습니다.')   

          }else if(res.status === 202){
            this.deleteAll()
            this.$router.push('/startwalk')
          }
          
        }).catch((error) =>{
          console.log(error.response)
        })
        
      }
    },

    goback(){
      this.deleteAll()
      this.$router.push('/startwalk')
    },
    deleteAll(){
      // 새로운 정보를 받을 수 있도록 값 초기화 
      this.setNowLat(0)
      this.setNowLon(0)
      this.setTempPhotoURL('')
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
