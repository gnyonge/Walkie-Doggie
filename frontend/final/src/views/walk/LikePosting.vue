<template>
  <div>
    <div id="mainBox" >
      <div class="d-flex justify-space-between">
      <div>
      <v-icon @click="goback()">mdi-arrow-left</v-icon>
      </div>
      <div>
        좋아요 진행중!
      </div>
      <div style="width: 24px; background-color: white;">
      </div>
    </div>
      <div style="text-align: center;" class="mt-5">
        <!-- 사진받기 -->
        <v-img
        id = "photoimg"
        height="330"
        max-height="330"
        :src = photo_url>
        </v-img>
        <br>
        <div class="filebox"> 
          <label for="ex_file">좋아요 사진추가!</label> 
          <input type="file" accept="image/*" capture="camera" required @click="addPhoto()" id="ex_file"> 
        </div>
        <!-- 사용자 의견 선택옵션 -->
        <div class="mt-3">
          <div class="mt-3">이유를 선택해 주세요!</div>
          <div class="mt-2">
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
    var camera = document.getElementById('ex_file')
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
    addPhoto() {
        var t = this
        var photo = document.getElementById('ex_file')
        photo.addEventListener('change', function(event) {
          const formData = new FormData()
          var file = event.target.files[0]
          formData.append('file', file)
          t.makeTempPhotoUrlInApi(formData)
          .then(() => {
            t.photo_url = t.getTempPhotoURL
          }).catch((error) => {
            console.error(error)
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
  .filebox label {
  display: inline-block; 
  padding: .5em .75em; 
  color: #323232; 
  font-size: inherit; 
  line-height: normal; 
  vertical-align: middle; 
  background-color: #BAF1E4; 
  cursor: pointer; 
  border: 1px solid #ebebeb; 
  border-bottom-color: #e2e2e2; 
  border-radius: .25em; 
  } 
.filebox input[type="file"] {
  /* 파일 필드 숨기기 */ 
  position: absolute; 
  width: 1px; 
  height: 1px; 
  padding: 0; 
  margin: -1px; 
  overflow: hidden; 
  clip:rect(0,0,0,0); 
  border: 0; 
}
</style>
