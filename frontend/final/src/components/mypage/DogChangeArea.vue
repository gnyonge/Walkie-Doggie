<template>
  <div id="mainBox">
    <div class="d-flex justify-space-between mb-8">
      <!-- 뒤로가기 버튼  -->
      <div>
        <v-icon @click="goback()">mdi-arrow-left</v-icon>
      </div>
      <div>
        반려견 정보 수정
      </div>
      <div style="width: 24px; background-color: white;">
      </div>
    </div>
    <div class="d-flex justify-center" style="font-size: 30px;">{{getDogInfo.pet.pe_name}}</div>
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
          <label for="ex_file">업로드 <v-icon class="pt-1">mdi-camera</v-icon></label> 
          <input type="file" id="ex_file" accept="image/*" @click="getPhoto()" ref="imageInput"> 
        </div>
      </div> 
    
    <div>
      <v-form>
        <!-- 이름  -->
        <v-flex class="ph-size">
          <v-text-field label="이름" :rules="namerules" v-model="name" required color="#48B9A8"></v-text-field>
        </v-flex>
        <!-- 나이 -->
        <v-flex class="ph-size">
          <v-text-field label="나이" :rules="agerules" v-model.number="age" required color="#48B9A8"></v-text-field>
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
          <v-text-field label="체중(kg)" :rules="agerules" v-model.number="weight" required color="#48B9A8"></v-text-field>
        </v-flex>
        <!-- 질병 -->
        <v-flex class="ph-size">
          <v-text-field label="선천적 질병" v-model="disease" required color="#48B9A8"></v-text-field>
        </v-flex>
        <!-- 트라우마 -->
        <v-flex class="ph-size">
          <v-text-field label="트라우마" v-model="trauma" required color="#48B9A8"></v-text-field>
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
      <div class="d-flex justify-center mt-9">
        <v-btn id="mainBtn" @click="updateDog()">반려견 정보 수정</v-btn>
      </div>

    </div>
    <div class="d-flex justify-end mt-5">
        <!-- 삭제 확인 창 -->
        <v-btn
          color="red" text width="50px"
          @click="snackbar = true"
        >
          삭제
        </v-btn>

        <v-snackbar
          v-model="snackbar"
          :multi-line="multiLine"
        >
          정말 삭제하시겠어요?

          <template v-slot:action="{ attrs }">
            <v-btn
              color="red"
              text
              v-bind="attrs"
              @click="deletePet()"
            >
              YES
            </v-btn>
          </template>
        </v-snackbar>
      </div>

  </div>
  

</template>

<script>
import { mapActions, mapGetters } from 'vuex'
export default {
  data() {
    return {
      photo_url: require('@/assets/images/서비스로고.png'),
      photo: true,
      date: new Date().toISOString().substr(0, 10),
      menu: false,
      modal: false,
      menu2: false,
      file: null,

      multiLine: true,
      snackbar: false,

      name: '',
      age: '',
      weight: '',
      trauma: '',
      disease: '',
      
      alle: false,
      allergyContent: '',
      allergyArray: [],

      namerules: [
        value => !!value || '필수 입력사항입니다!',
        value => !!value.replace(/(\s*)/g, "") || '공백은 불가해요!',
        ],
      agerules: [
        value => Number.isInteger(value) || '숫자만 가능합니다!'
      ]
    }
  },
  created() {
    if (this.getDogInfo.pet.pe_profile_photo.length != 0) {
      this.photo_url = this.getDogInfo.pet.pe_profile_photo
    } else {
      this.photo = false
    }
    this.name = this.getDogInfo.pet.pe_name
    this.age = this.getDogInfo.pet.pe_age
    this.weight = this.getDogInfo.pet.pe_weight
    this.trauma = this.getDogInfo.pet.pe_trauma
    this.disease = this.getDogInfo.pet.pe_disease
    this.date = this.getDogInfo.pet.pe_birthday.substr(0, 10)
    if (this.getDogInfo.allergy.length != 0) {
      this.alle = true
      for (let i of this.getDogInfo.allergy) {
        this.allergyArray.push(i.al_name)
      }
    }
  },
  computed: {
    ...mapGetters(['getTempPhotoURL', 'getDogInfo', 'getMyDogListInfo', 'getUser'])
  },
  methods: {
    ...mapActions(['makeTempPhotoUrlInApi', 'updateDogInfoInApi', 'NoPhotoUpdateDogInfoInApi', 
    'deletePetInApi', 'getUserInfoInApi', 'showDogInfoInApi']),
    goback() {
        this.$router.push('/mypage')
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
    updateDog() {
      const formData = new FormData()
      // 사진 변경 안했을 때
      if (!this.file) {
        let pet
        // 프사없었고 추가도안함
        if (!this.photo) {
          pet = {
          pe_age: this.age,
          pe_birthday: this.date,
          pe_disease: this.disease,
          pe_flag: 0,
          pe_name: this.name,
          pe_trauma: this.trauma,
          pe_weight: this.weight,
          peid: this.getDogInfo.pet.peid,
          pe_profile_photo: '',
          uid: this.getDogInfo.pet.uid
          }
          console.log(this.photo_url,'2')
        } 
        // 프사있었는데 변경안함
        else {
          pet = {
          pe_age: this.age,
          pe_birthday: this.date,
          pe_disease: this.disease,
          pe_flag: 0,
          pe_name: this.name,
          pe_trauma: this.trauma,
          pe_weight: this.weight,
          peid: this.getDogInfo.pet.peid,
          pe_profile_photo: this.photo_url,
          uid: this.getDogInfo.pet.uid
          }
          console.log(this.photo_url,'3')
        }
        let allergy = []
        for (let i in this.allergyArray) {
            allergy.push({
            aid: 0,
            peid: this.getDogInfo.pet.peid,
            al_name: this.allergyArray[i],
            al_flag: 0,
          })
        }
        console.log(allergy, '확인', pet)
        formData.append(
            'pet',
            new Blob([JSON.stringify(pet)], { type: 'application/json' })
          )
          formData.append(
            'allergy',
            new Blob([JSON.stringify(allergy)], { type: 'application/json' })
          )

          this.NoPhotoUpdateDogInfoInApi(formData).then(() => {
            this.$router.push('/mypage')
            console.log('사진추가안함')
          })
      } 
      // 사진 추가함
      else {
        console.log(this.photo_url,'4')
        let pet = {
          pe_age: this.age,
          pe_birthday: this.date,
          pe_disease: this.disease,
          pe_flag: 0,
          pe_name: this.name,
          pe_trauma: this.trauma,
          pe_weight: this.weight,
          peid: this.getDogInfo.pet.peid,
          pe_profile_photo: this.photo_url,
          uid: this.getDogInfo.pet.uid
        }
        
        let allergy = []
        for (let i in this.allergyArray) {
            allergy.push({
            aid: 0,
            peid: this.getDogInfo.pet.peid,
            al_name: this.allergyArray[i],
            al_flag: 0,
          })
        }
        formData.append(
            'pet',
            new Blob([JSON.stringify(pet)], { type: 'application/json' })
          )
          formData.append(
            'allergy',
            new Blob([JSON.stringify(allergy)], { type: 'application/json' })
          )
          formData.append('file', this.file)

          this.updateDogInfoInApi(formData).then((res) => {
            console.log(res, '사진추가후 수정완료')
            this.$router.push('/mypage')
          })
      }
      
    },
    deletePet() {
      if (this.getMyDogListInfo.length == 1) {
        alert('최소 한마리는 있어야해요!')
      } else {
        const formData = new FormData()
      let pet = {}
      // 사진 없을 때
      if (!this.photo) {
        pet = {
          pe_age: this.age,
          pe_birthday: this.date,
          pe_disease: this.disease,
          pe_flag: 0,
          pe_name: this.name,
          pe_trauma: this.trauma,
          pe_weight: this.weight,
          peid: this.getDogInfo.pet.peid,
          pe_profile_photo: '',
          uid: this.getDogInfo.pet.uid
        }
      }
      // 사진 있을 때
      else {
        pet = {
          pe_age: this.age,
          pe_birthday: this.date,
          pe_disease: this.disease,
          pe_flag: 0,
          pe_name: this.name,
          pe_trauma: this.trauma,
          pe_weight: this.weight,
          peid: this.getDogInfo.pet.peid,
          pe_profile_photo: this.photo_url,
          uid: this.getDogInfo.pet.uid
        }
      }
      let allergy = []
        for (let i in this.getDogInfo.allergy) {
          allergy.push({
            aid: this.getDogInfo.allergy[i].aid,
            peid: this.getDogInfo.pet.peid,
            al_name: this.getDogInfo.allergy[i].al_name,
            al_flag: 0,
          })
        }
      formData.append(
        'pet',
        new Blob([JSON.stringify(pet)], { type: 'application/json' })
      )
      formData.append(
        'allergy',
        new Blob([JSON.stringify(allergy)], { type: 'application/json' })
      )

      this.deletePetInApi(formData).then(() => {
        this.getUserInfoInApi(this.getUser.uid)
        .then(() => {
          this.showDogInfoInApi(this.getMyDogListInfo[0].peid)
          .then(() => {
            this.snackbar = false
            this.$router.push('/mypage')
          })
          
        })
        
      })
      }
      
    }  
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
/* .v-input {
  padding-top: 0px;
  margin-top: 0px;
  max-width: 60%;
} */

</style>