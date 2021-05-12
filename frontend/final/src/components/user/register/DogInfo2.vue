<template>
  <div class="" id="mainBox">
    <div>
        <v-icon @click="goback()">mdi-arrow-left</v-icon>
    </div>
    <h5 class="text-center mb-5">반려견 등록페이지</h5>
      <div class="d-flex justify-center">
        <div class="">
          <label for="myfile" class="circle">
            사진</label>
          <input type="file"
              id="photo" name="photo"
              accept="image/*"
              
            >
        </div> 
      </div>
      <div>
        <v-form>
        <v-flex class="ph-size">
          <v-text-field name="name" label="강아지이름" id="name" v-model="name" type="name" required color="#48B9A8"></v-text-field>
        </v-flex>
        <v-flex class="ph-size">
          <v-text-field name="age" label="나이(생일)" id="age" v-model="age" type="age" required color="#48B9A8"></v-text-field>
        </v-flex>
    
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
        <v-flex class="ph-size">
          <v-text-field name="weight" label="체중" id="weight" v-model="weight" type="weight" required color="#48B9A8"></v-text-field>
        </v-flex>
        <v-flex class="ph-size">
          <v-text-field name="disease" label="선천적 질병" id="disease" v-model="disease" type="disease" required color="#48B9A8"></v-text-field>
        </v-flex>
        <v-flex class="ph-size">
          <v-text-field name="trauma" label="트라우마" id="trauma" v-model="trauma" type="trauma" required color="#48B9A8"></v-text-field>
        </v-flex>
        <v-flex class="ph-size">
          <v-text-field name="allergy" label="알레르기" id="allergy" v-model="allergy" type="allergy" required color="#48B9A8"></v-text-field>
        </v-flex>
      </v-form>
      <div class="d-flex justify-center">
        <v-btn id="mainBtn" @click="registerNewDog">반려견 등록</v-btn>
      </div>
      </div>
      
  </div>

</template>

<script>
import { mapActions } from 'vuex'

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

      date: new Date().toISOString().substr(0, 10),
      menu: false,
      modal: false,
      menu2: false,
    }
  },
  methods: {
    ...mapActions(['dogRegisterInApi']),
    registerNewDog() {
      console.log('안녕하세용')
      this.dogRegisterInApi({
        pe_age: this.age,
        pe_brithday: this.date,
        pe_disease: this.disease,
        pe_flag: 0,
        pe_name: this.name,
        pe_trauma: this.trauma,
        pe_weight: this.weight,
        peid: "string",
        pr_profile_photo: this.photo,
        uid: "adminadmin123"
      })
      .then((res) => {
        console.log(res, '반려견 등록 성공 !')
        this.$router.push('/calendar')
        
      }).catch((error)=> {
        console.log(error)
      }) 
    },
    goback() {
        this.$router.push('/dogregister')
      }
  }
}
</script>
<style scoped>
.register-btn-pd{
  padding: 30px;
}
.circle {
  width: 150px;
  height: 150px;
  background: #DEB98B;
  border-radius: 50%;
  font-size: 24px;
  text-align:center;
  line-height: 150px;
  /* color: white; */
}
.btn-file{
  position: relative;
  overflow: hidden;
}
.filebox label {
  display: inline-block;
  color: #fff;
  background-color: #F2B75B;
  cursor: pointer;
  -webkit-transition: background-color 0.2s;
  transition: background-color 0.2s;
  width: 150px;
  height: 150px;
  border-radius: 50%;
  font-size: 24px;
  text-align:center;
  line-height: 150px;
}

.filebox label:hover {
  background-color: #cc9653;
}

.filebox label:active {
  background-color: #754812;
}

.filebox input[type="file"] {
  position: absolute;
  width: 1px;
  height: 1px;
  padding: 0;
  margin: -1px;
  overflow: hidden;
  clip: rect(0, 0, 0, 0);
  border: 0;
}

</style>