<template>
  <div class="" id="mainBox">
    <div class="">
      <div>
        <v-icon @click="goback()">mdi-arrow-left</v-icon>
      </div>
      <h1 style="font-size: 15px;" class="d-flex justify-center">지역 설정</h1>
      <p style="font-size:12px;" class="d-flex justify-center">주소를 입력해 주세요.</p>
      <DaumPostcode
      :on-complete=handleAddress
      v-model="addressChange"
    />
    </div>
    
      <div class="d-flex justify-center mt-5">
        <v-btn class="" id="mainBtn" style="width:250px; margin-top:20px; margin-bottom: 20px;"><b>변경</b></v-btn>
      </div>
    <div style="font-size:18px;">
      기존 주소: <b>{{addressChange}}</b>
    </div>
  </div>
  

</template>

<script>
import { mapGetters } from 'vuex'
import DaumPostcode from 'vuejs-daum-postcode'

export default {
    data () {
    return {
      addressChange: '',
    }
  },
  components: {
    DaumPostcode
  },
  computed: {
    ...mapGetters(['getUser','getAddress'])
  },
  mounted() {
      let recaptchaScript = document.createElement('script')
      recaptchaScript.setAttribute('src', '//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js')
      document.head.appendChild(recaptchaScript)
    },
    watch: {
    address(newVal) {
      console.log(newVal, '변경한다고 제발 ')
    }
  },
  methods: {
    handleAddress(data) {
      let fullAddress = data.address
      let fAddress = [] // 띄어쓰기 기준으로 나누어 단어 저장
      let sAddress = [] // '동' 으로 끝나는 단어 저장 

  
      fullAddress = data.jibunAddress

      // 띄어쓰기 기준으로 나누어 fAddress에 저장
      fAddress = fullAddress.split(' ');
      // console.log(fAddress, '주소 나누기 성공') 
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
    },
    goback() {
      this.$router.push('/mypage')
    }
  }
}
</script>
<style scoped>
.health-box {
  padding: 25px;
  
  height: 80%;
  background: #EFE8DE;
  margin: 10px;
}
p {
  color: black;
  font-size: 40px;
}
</style>