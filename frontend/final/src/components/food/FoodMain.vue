<template>
  <div>
    <div id="date"><h2 class="my-10">우리 아이 간식</h2></div>
    <div id="date" class="mt-9"><h2 class="mt-5">성분 분석 해드려요!</h2></div>
    <div id="date" class="mt-8">
      <div class="filebox mt-3"> 
        <label for="ex_file">성분표 사진추가!</label> 
        <input type="file" accept="image/*" @click="addPhoto()" id="ex_file"> 
      </div>
    </div>
    <div id="contentBox">
      <v-img
        v-if="photo_url"
        height="330"
        width="330"
        :src="photo_url">
      </v-img>
    </div>
    <div class="mt-6 d-flex justify-center">
      <v-btn rounded @click="result()" id="btnstyle" style="width: 200px; height: 50px; font-size: 20px;" :class="{diary: isClicked}">분석 시작!</v-btn>
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
      photo_url: "",
      file: {},
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
</style>