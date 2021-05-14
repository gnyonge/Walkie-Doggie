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
      <!-- 사진추가 했을때 -->
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
      <div>
        <v-form>
        <!-- 이름  -->
        <v-flex class="ph-size">
          <v-text-field label="강아지이름" id="name" v-model="name" type="name" required color="#48B9A8"></v-text-field>
        </v-flex>
        <!-- 나이 -->
        <v-flex class="ph-size">
          <v-text-field label="나이(생일)" id="age" v-model="age" type="age" required color="#48B9A8"></v-text-field>
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
          <v-text-field label="체중(kg)" id="weight" v-model="weight" type="weight" required color="#48B9A8"></v-text-field>
        </v-flex>
        <!-- 질병 -->
        <v-flex class="ph-size">
          <v-text-field label="선천적 질병" id="disease" v-model="disease" type="disease" required color="#48B9A8"></v-text-field>
        </v-flex>
        <!-- 트라우마 -->
        <v-flex class="ph-size">
          <v-text-field label="트라우마" id="trauma" v-model="trauma" type="trauma" required color="#48B9A8"></v-text-field>
        </v-flex>

        <div id="contentBox">
        <v-checkbox
          v-model="alle"
          label="알러지"
          color="#48B9A8"
          hide-details
        ></v-checkbox>
        <v-text-field
          color="#48B9A8"
          @keydown.enter="addAllergy()"
          v-model="allergyContent"
          class="px-5"
          v-if="alle"
          label="입력 후 enter"
        ></v-text-field>
        <div class="mx-4" v-if="alle">
          <v-chip
            v-for="(alg, idx) in allergyArray"
            :key="idx"
            class="mx-1 my-1"
            close
            color="#BAF1E4"
            @click:close="deleteAllergy(alg)"
          >
            {{ alg }}
          </v-chip>
        </div>
      </div>
      </v-form>
      <!-- 반려견 등록 버튼  -->
      <div class="d-flex justify-center">
        <v-btn id="mainBtn" @click="registerNewDog()" :disabled="!file">반려견 등록</v-btn>
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
      photo: '',
      photo_url: require('../../../assets/images/서비스로고.png'),

      date: new Date().toISOString().substr(0, 10),
      menu: false,
      modal: false,
      menu2: false,
      file: null,

      alle: false,
      allergyContent: '',
      allergyArray: [],
    }
  },
  computed: {
    ...mapGetters(['getTempPhotoURL'])
  },
  watch: {
      alle(newVal) {
        if (!newVal) {
          this.allergyContent = ""
          this.allergyArray = []
        }
      },
  },
  methods: {
    ...mapActions(['dogRegisterInApi', 'makeTempPhotoUrlInApi']),
    goback() {
      this.$router.push('/dogregister')
    },
    addAllergy() {
      if (this.allergyContent.replace(/(\s*)/g, "").length > 0) {
        this.allergyArray.push(this.allergyContent);
        this.allergyContent = "";
        }
      },
    deleteAllergy(alg) {
        const index = this.allergyArray.indexOf(alg);
        this.allergyArray.splice(index, 1);
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
    registerNewDog() {
      console.log('가입눌렀음')
      const formData = new FormData()
      let pet = {
          pe_age: this.age,
          pe_brithday: this.date,
          pe_disease: this.disease,
          pe_flag: 0,
          pe_name: this.name,
          pe_trauma: this.trauma,
          pe_weight: this.weight,
          peid: "petpetpetpet1",
          pe_profile_photo: this.photo_url,
          uid: "adminadmin123",
        }
     
      let allergy = []
      for (let i in this.allergyArray) {
        allergy.push({
          aid: 0,
          peid: "petpetpetpet1",
          al_name: this.allergyArray[i],
          al_flag: 0
          })
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
        this.$router.push('/calendar')
      })
    }}
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