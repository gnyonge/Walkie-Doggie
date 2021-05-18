<template>
  <div class="" id="mainBox">
    <div class="">
      <div class="d-flex justify-space-between">
      <div>
      <v-icon @click="goback()">mdi-arrow-left</v-icon>
      </div>
      <div>
        지역 변경
      </div>
      <div style="width: 24px; background-color: white;">
      </div>
    </div>
      <div style="font-size:18px;" class="my-3">
      기존 주소: <b>{{getUser.u_location}}</b>
      </div>
      <DaumPostcode
      :on-complete=handleAddress
      v-model="addressChange"
    />
    </div>
    <div style="font-size:18px;" class="my-5">
      변경할 주소: <b>{{addressChange}}</b>
    </div>
      <div class="d-flex justify-center">
        <v-btn class="" id="mainBtn" style="width:150px; margin-top:20px; margin-bottom: 20px;"
        @click="changeLocation()"><b>변경</b></v-btn>
      </div>
    
  </div>
  

</template>

<script >
import DaumPostcode from 'vuejs-daum-postcode'
import { mapGetters, mapActions} from 'vuex'

export default {
  data () {
    return {
      addressChange: ''
    }
  },
  computed: {
    ...mapGetters(['getUser', 'getAddress', 'getDogInfo']),
  },
  components: {
    DaumPostcode
  },
  mounted() {
      let recaptchaScript = document.createElement('script')
      recaptchaScript.setAttribute('src', '//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js')
      document.head.appendChild(recaptchaScript)
    },
  methods: {
    ...mapActions(['setAddressInApi', 'setAddressInApi']),
    goback() {
      if (this.getDogInfo.pet.peid) {
        this.$router.push('/mypage')
      }
    },
    changeLocation() {
      this.setAddressInApi({
        add: this.addressChange,
        uid: this.getUser.uid
      }).then(() => {
        this.$router.push('/mypage')
      })
    },
    handleAddress(data) {
      let fullAddress = data.address
      let fAddress = [] // 띄어쓰기 기준으로 나누어 단어 저장
      let sAddress = [] // '동' 으로 끝나는 단어 저장 

  
      fullAddress = data.jibunAddress

      // 띄어쓰기 기준으로 나누어 fAddress에 저장
      fAddress = fullAddress.split(' ');
      // 예: ["광주", "광산구", "장덕동", "992-8"]
      
      // 리스트 돌면서 마지막 단어가 '동'인 부분 저장 
      // 주소의 동까지 저장 
      for (let i in fAddress) {
        // 인덱스
        // console.log(i)  
        let checkAddress = fAddress[i]
        if(checkAddress.charAt(checkAddress.length-1) !== '동'){ 
          sAddress.push(checkAddress)
        }else {
          sAddress.push(checkAddress)
          break
        }  
  }
  const finalAddress = sAddress.join(" ");
  this.addressChange = finalAddress
  // e.g. '서울 성동구 왕십리로2길 20 (성수동1가)' 주소 세번째 꺼로 가져오기 
    },
    setAddress() {
      console.log(this.address, '최종 어드레스')
      this.setAddressInApi({
        uid: this.getUser.uid,
        add: this.addressChange
      })
      .then(() => {
        this.$router.push('/dogregister')
      })
    },
  }
}
</script>
<style scoped>
.register-btn {
  width: 200px;
}
.txt_example {
  font-size: 30px !important;
}

</style>