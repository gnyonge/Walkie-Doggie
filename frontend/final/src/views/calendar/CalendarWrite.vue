<template>
  <div>
    <div id="mainBox">
      <div class="d-flex justify-space-between">
        <div>
        <v-icon @click="goback()">mdi-arrow-left</v-icon>
        </div>
        <div>
          일기쓰기
        </div>
        <div style="width: 24px; background-color: white;">
        </div>
      </div>
      <div id="contentBox">
        {{getPrettyDate}}
      </div>
      <div id="contentBox">
        <v-textarea
          v-model="diaryContent"
          label="일기 내용"
          outlined
          rows="5"
          row-height="25"
          color="#48B9A8"
        ></v-textarea>
      </div>
      <div id="contentBox">
        <div class="filebox"> 
          <label for="ex_file">사진 추가</label> 
          <input type="file" @click="addPhoto()" id="ex_file"> 
        </div>
      </div>
      <v-divider></v-divider>
      <div id="contentBox">
        <v-checkbox
          v-model="memo"
          label="특이사항"
          color="#48B9A8"
          hide-details
        ></v-checkbox>
        <v-textarea
          class="px-5 mt-5"
          v-model="memoContent"
          v-if="memo"
          outlined
          rows="2"
          row-height="20"
          color="#48B9A8"
        ></v-textarea>
      </div>
      <div id="contentBox">
        <v-checkbox
          v-model="health"
          label="건강사항"
          color="#48B9A8"
          hide-details
        ></v-checkbox>
        <v-text-field
          color="#48B9A8"
          @keydown.enter="addHealth()"
          v-model="healthContent"
          class="px-5"
          v-if="health"
          label="입력 후 enter"
        ></v-text-field>
        <div class="mx-4" v-if="health">
          <v-chip
            v-for="(hth, idx) in healthArray"
            :key="idx"
            class="mx-1 my-1"
            close
            color="#BAF1E4"
            @click:close="deleteHealth(hth)"
          >
            {{ hth }}
          </v-chip>
        </div>
      </div>
      <div class="d-flex justify-end">
        <v-btn id="mainBtn" width="50px" @click="createDiary()">완료</v-btn>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'

export default {
  name: "CalendarWrite",
  data () {
      return {
        diaryContent: "",
        memo: false, // 특이사항 체크 or not
        health: false, // 건강사항 체크 or not
        memoContent: "", // 특이사항 내용
        healthContent: "", // 건강사항 내용 (1개)
        healthArray: [], // 건강사항 내용들 (전체)
        photo: {}
      }
    },
    computed: {
      ...mapGetters(['getSelectedDate', 'getPrettyDate'])
    },
    watch: {
      health(newVal) {
        if (!newVal) {
          this.healthContent = ""
          this.healthArray = []
        }
      },
      memo(newVal) {
        if(!newVal) {
          this.memoContent = ""
        }
      },
      photo(newVal) {
        console.log(newVal,'요기')
      },
    },
    methods: {
      ...mapMutations(['setSelectedDate']),
      ...mapActions(['createNoPhotoDiaryInApi', 'createTempURLInApi']),
      addHealth() {
      if (this.healthContent.replace(/(\s*)/g, "").length > 0) {
        this.healthArray.push(this.healthContent);
        this.healthContent = "";
        }
      },
      deleteHealth(hth) {
        const index = this.healthArray.indexOf(hth);
        this.healthArray.splice(index, 1);
      },
      goback() {
        this.$router.push('/calendar')
      },
      addPhoto() {
        var t = this
        var photo = document.getElementById('ex_file')
        
        photo.addEventListener('change', function(event) {
          const formData = new FormData()
          t.photo = event.target.files[0]
          console.log(t.photo,'t.photo 확인')
          formData.append('file', t.photo)
          t.createTempURLInApi(formData)
          .then((res) => {
            console.log(res,'성공입니다')
          }).catch((error) => {
            console.error(error)
          })
        })
      },




      createDiary() {
        var camera = document.getElementById('camera')
        // var t = this
        // const formData= new FormData()

        camera.addEventListener('change', function(e) {
        console.log(e, '요깅')
        // var file = e.target.files[0]
        // console.log(file, '요기요')
        // formData.append('file', file)

        // t.makeTempPhotoUrlInApi(
        //   form
        // ).then(()=> {
        //   console.log('업로드한 사진 미리보기 성공')
        // }).catch((error)=>{
        //   console.log(error)
        // })
        // 백엔드 서버에서 이미지 주소 받아서 넣기 
        // t.photo_url = t.getTempPhotoURL
      })
      
        // let diary = {
        //     d_date: this.getSelectedDate,
        //     d_flag: 0,
        //     d_img: "string",
        //     d_memo: this.diaryContent,
        //     d_special: this.memoContent,
        //     d_walk: 0,
        //     peid: "petpetpetpet1"
        //   }

        // let health_list = []
        // for (let i in this.healthArray) {
        //   health_list.push({
        //     h_content: this.healthArray[i],
        //     h_date: this.getSelectedDate,
        //     h_flag: 0,
        //     hid: 0,
        //     peid: "petpetpetpet1"
        //   })
        // }
        
        
        // formData.append(
        //   'diary',
        //   new Blob([JSON.stringify(diary)], { type: 'application/json' })
        // )
        // formData.append(
        //   'health_list',
        //   new Blob([JSON.stringify(health_list)], { type: 'application/json' })
        // )
        // this.createNoPhotoDiaryInApi(formData).then(() => {
        //   this.$router.push(`/calendar/detail/todaydiary/${this.getSelectedDate}`)
        // })
      }
    }
}
</script>

<style>
#contentBox {
  margin-top: 20px;
  margin-bottom: 10px;
}
.v-label, .v-chip__content {
  margin: 0px;
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