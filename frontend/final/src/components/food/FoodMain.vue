<template>
  <div>
    <div id="mainBox">
    <div id="date"><h3 class="my-5"><b>우리 아이 간식</b></h3></div>
    <div id="date" class="mt-9"><h3><b>성분 분석 해드려요!</b></h3></div>
    <div id="date">
      <div class="filebox mt-3"> 
        <label for="ex_file"><b>성분표 사진추가!</b></label> 
        <input type="file" accept="image/*" @click="addPhoto()" id="ex_file"> 
      </div>
    </div>
    <div id="contentBox" class="mt-8">
        <v-img
        v-if="!photoCheck"
          :src="photo_url"
          alt="프로필 사진" 
        >
        </v-img>
      <v-img
        v-else
        height="330"
        width="330"
        :src="photo_url">
      </v-img>
    </div>
    <div class="mt-6 d-flex justify-center">
      <v-btn rounded @click="result()" id="btnstyle" style="width: 200px; height: 50px; font-size: 20px;" :disabled="!photoCheck" :class="{diary: isClicked}"><b>분석 시작!</b></v-btn>
    </div>
    </div>

  </div>
</template>

<script>
import { mapMutations, mapActions, mapGetters } from 'vuex'
export default {
  name: "FoodMain",
  data() {
    return {
      isClicked: false,
      photo_url: require('@/assets/images/서비스로고.png'),
      file: {},
      photoCheck: false
    }
  },
  computed: {
    ...mapGetters(['getTempPhotoURL', 'getDogInfo'])
  },
  methods: {
    ...mapMutations(['setNowTab']), 
    ...mapActions(['makeTempPhotoUrlInApi', 'foodPhotoInApi']),
    result() {
      this.isClicked = true
      this.setNowTab(2)
      const formData = new FormData()
      formData.append('file', this.file)
      this.foodPhotoInApi({formData: formData, peid: this.getDogInfo.pet.peid})
      .then(() => {
        this.$router.push({name: "FoodResult"})
      })
    },
    addPhoto() {
        var t = this
        var photo = document.getElementById('ex_file')
        photo.addEventListener('change', function(event) {
          const formData = new FormData()
          var file = event.target.files[0]
          t.file = event.target.files[0]
          formData.append('file', file)
          t.makeTempPhotoUrlInApi(formData)
          .then(() => {
            t.photoCheck = true
            t.photo_url = t.getTempPhotoURL
          }).catch((error) => {
            console.error(error)
          })
        })
      },
  }
}
</script>

<style>
#tempimg {
  background-color: peachpuff;
  width: 300px;
  height: 300px;
  display: flex;
  justify-content: center;
  align-items: center;
}
.form-file-text {
  margin-top: 0px;
}
.diary {
  box-shadow: 1px 1px 0 rgb(0,0,0,0.5) !important;
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