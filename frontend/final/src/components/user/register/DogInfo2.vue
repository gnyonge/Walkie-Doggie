<template>
  <div id="mainBox">
    <div class="d-flex justify-space-between mb-8">
      <div>
      <v-icon @click="goback()">mdi-arrow-left</v-icon>
      </div>
      <div>
        반려견 등록 
      </div>
      <div style="width: 24px; background-color: white;">
      </div>
    </div>
      <!-- 프로필 사진 업로드 -->
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
          <input type="file" id="ex_file" accept="image/*" @click="getPhoto" ref="imageInput"> 
        </div>
      </div> 
      <div>
        <v-form>
        <!-- 이름  -->
        <v-flex class="ph-size">
          <v-text-field name="name" label="강아지이름" id="name" v-model="name" type="name" required color="#48B9A8"></v-text-field>
        </v-flex>
        <!-- 나이 -->
        <v-flex class="ph-size">
          <v-text-field name="age" label="나이(생일)" id="age" v-model="age" type="age" required color="#48B9A8"></v-text-field>
        </v-flex>
        <!-- 생일-달력 -->
        <v-dialog
          ref="dialog"
          v-model="modal"
          :return-value.sync="date"
          persistent
          width="290px"
        >
          <template v-slot:activator="{ on, attrs }">
            <v-text-field
              v-model="date"
              label="생일"
              readonly
              v-bind="attrs"
              v-on="on"
            ></v-text-field>
          </template>
          <v-date-picker
            v-model="date"
            scrollable
            locale="ko-kr"
            color="#48B9A8"
          >
            <v-spacer></v-spacer>
            <v-btn
              text
              color="#48B9A8"
              @click="modal = false"
            >
              취소
            </v-btn>
            <v-btn
              text
              color="#48B9A8"
              @click="$refs.dialog.save(date)"
            >
              확인
            </v-btn>
          </v-date-picker>
        </v-dialog>
        <!-- 체중 -->
        <v-flex class="ph-size">
          <v-text-field name="weight" label="체중(kg)" id="weight" v-model="weight" type="weight" required color="#48B9A8"></v-text-field>
        </v-flex>
        <!-- 질병 -->
        <v-flex class="ph-size">
          <v-text-field name="disease" label="선천적 질병" id="disease" v-model="disease" type="disease" required color="#48B9A8"></v-text-field>
        </v-flex>
        <!-- 트라우마 -->
        <v-flex class="ph-size">
          <v-text-field name="trauma" label="트라우마" id="trauma" v-model="trauma" type="trauma" required color="#48B9A8"></v-text-field>
        </v-flex>
        <!-- 알레르기 -->
        <v-flex class="ph-size">
          <v-text-field name="allergy" label="알레르기" id="allergy" v-model="allergy" type="allergy" required color="#48B9A8"></v-text-field>
        </v-flex>
      </v-form>
      <!-- 반려견 등록 버튼  -->
      <div class="d-flex justify-center">
        <v-btn id="mainBtn" @click="registerNewDog">반려견 등록</v-btn>
      </div>
      </div>
      
  </div>

</template>

<script>
import { mapActions, mapGetters } from 'vuex'

export default {
  data () {
    return {
      name: '',
      age: '',
      weight: '',
      trauma: '',
      allergy:'',
      disease:'',
      photo_url: require('../../../assets/images/서비스로고.png'),

      date: new Date().toISOString().substr(0, 10),
      menu: false,
      modal: false,
      menu2: false,
      file: '',
    }
  },
  computed: {
    ...mapGetters(['getTempPhotoURL'])
  },
  methods: {
    ...mapActions(['dogRegisterInApi', 'makeTempPhotoUrlInApi']),
    goback() {
      this.$router.push('/dogregister')
    },
    //사용자 업로드 사진 주소 백엔드 전송 후 보여주기 
    getPhoto(){
      var camera = document.getElementById('ex_file')
      var t = this
      camera.addEventListener('change', function(e) {
        const form = new FormData()
        var file = e.target.files[0]
        form.append('file', file)
        t.makeTempPhotoUrlInApi(
          form
        ).then(()=> {
          console.log('업로드한 사진 미리보기 성공')
        }).catch((error)=>{
          console.log(error)
        })
        // 백엔드 서버에서 이미지 주소 받아서 넣기 
        t.photo_url = t.getTempPhotoURL
      })
    },
    registerNewDog() {
      console.log('안녕하세용')
      let formData = new FormData()
      let pet = {
          pe_age: this.age,
          pe_brithday: this.date,
          pe_disease: this.disease,
          pe_flag: 0,
          pe_name: this.name,
          pe_trauma: this.trauma,
          pe_weight: this.weight,
          peid: "petpetpetpet1",
          pr_profile_photo: this.photo_url,
          uid: "adminadmin123",
        }
      // console.log(this.date)
      
      let allergy = {
          al_name: this.allergy,
          peid: "petpetpetpet1"
      }
      formData.append(
        'pet',
        new Blob([JSON.stringify(pet)], { type: 'application/json' })
      )
      formData.append('file', this.file);
      formData.append(
        'allergy',
        new Blob([JSON.stringify(allergy)], { type: 'application/json' })
      )
      this.dogRegisterInApi(formData).then(() => {
        // this.$router.push('/calendar')
      })
    },        
  }
}
</script>
<style scoped>
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