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
    />
    </div>
    <router-link to="/mypage">
      <div class="d-flex justify-center mt-5">
        <v-btn class="" id="mainBtn" style="width:250px; margin-top:20px; margin-bottom: 20px;"><b>변경</b></v-btn>
      </div>
    </router-link>
  </div>
  

</template>

<script>
import DaumPostcode from 'vuejs-daum-postcode'

var handleAddress = (data) => {
  let fullAddress = data.address
  let extraAddress = ''
  if (data.addressType === 'R') {
    if (data.bname !== '') {
      extraAddress += data.bname
    }
    if (data.buildingName !== '') {
      extraAddress += (extraAddress !== '' ? `, ${data.buildingName}` : data.buildingName)
    }
    fullAddress += (extraAddress !== '' ? ` (${extraAddress})` : '')
  }

  console.log(fullAddress) // e.g. '서울 성동구 왕십리로2길 20 (성수동1가)'
}
export default {
    data () {
    return {
      nickname: '',
      zip: '', addr1: '', addr2: ''
    }
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
    handleAddress,
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