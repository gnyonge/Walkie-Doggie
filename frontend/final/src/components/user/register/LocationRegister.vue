<template>
<div id="mainBox">
    <div class="d-flex justify-center">
      <div>
        반려견 등록
      </div>
      <div style="width: 24px; background-color: white;">
      </div>
    </div>
  <div class="register-btn-pd">
    <v-flex class="ph-size">
      <!-- <v-text-field name="nickname" label="주소" id="nickname" v-model="nickname" type="nickname" required></v-text-field>
      <div class="d-flex justify-center">
        <router-link to="#"><v-btn class="register-btn">지역설정</v-btn></router-link> 
      </div> -->
      <!-- <div class="daummap"> <h1>우편번호: <span>{{ zip }}</span></h1> <h1>기본주소: <span>{{ addr1 }}</span></h1> <h1>상세주소: <span>{{ addr2 }}</span></h1> <div ref="embed"></div> <button @click="showApi">주소API 호출</button> </div> -->
    <DaumPostcode
      :on-complete=handleAddress
    />
    <div class="mt-3">
      지번 주소를 동까지 입력해주세요.
    </div>
      <v-text-field 
      label="(예: '광주 광산구 장덕동')" 
      id="address" 
      v-model="address" 
      type="address" required>
      </v-text-field>
    <div class="register-btn-pd d-flex justify-center mt-5">
        <v-btn class="" id="mainBtn" @click="setAddress()">지역 설정</v-btn>
    </div>
    </v-flex>
  </div>
</div>
</template>

<script >
import DaumPostcode from 'vuejs-daum-postcode'

var handleAddress = (data) => {
  let fullAddress = data.address
  let extraAddress = ''
  if (data.addressType === 'R') {
    fullAddress = data.jibunAddress
  }
  // 띄어쓰기 2후 마지막 글자가 동이이면 저장 
  // 아니면 -> 세번 후 저장!!! 
  // 그런데도 동이 없으면 면까지만?? 

  console.log(fullAddress) // e.g. '서울 성동구 왕십리로2길 20 (성수동1가)' 주소 세번째 꺼로 가져오기 

  console.log(extraAddress)
}
export default {
  data () {
    return {
      address: ''
    }
  },
  components: {
    DaumPostcode
  },
  created() {
    
  },
  mounted() {
      let recaptchaScript = document.createElement('script')
      recaptchaScript.setAttribute('src', '//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js')
      document.head.appendChild(recaptchaScript)
    },
  methods: {
    handleAddress,
    setAddress() {
      // address 
      // this.goto()
    },
    goto() {
      this.$router.push('/dogregister')
    }
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