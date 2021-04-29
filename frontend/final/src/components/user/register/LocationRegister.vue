<template>
  <div class="register-btn-pd">
    <v-flex class="ph-size">
      <h1 class="text-center mb-5 mt-5">반려견 등록</h1>
      <!-- <v-text-field name="nickname" label="주소" id="nickname" v-model="nickname" type="nickname" required></v-text-field>
      <div class="d-flex justify-center">
        <router-link to="#"><v-btn class="register-btn">지역설정</v-btn></router-link> 
      </div> -->
      <!-- <div class="daummap"> <h1>우편번호: <span>{{ zip }}</span></h1> <h1>기본주소: <span>{{ addr1 }}</span></h1> <h1>상세주소: <span>{{ addr2 }}</span></h1> <div ref="embed"></div> <button @click="showApi">주소API 호출</button> </div> -->
    <DaumPostcode
      :on-complete=handleAddress
    />
    <!-- <div class="daummap"> 
      <h1>우편번호: <span>{{ zip }}</span></h1> 
      <h1>기본주소: <span>{{ addr1 }}</span></h1> 
      <h1>상세주소: <span>{{ addr2 }}</span></h1> 
      <div ref="embed"></div> 
      <button @click="showApi">주소API 호출</button> 
    </div> -->
    <div class="register-btn-pd">
        <router-link to="/dogregister"><v-btn class="register-btn" >지역 설정</v-btn></router-link>
    </div>
    </v-flex>
  </div>

</template>

<script >
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
    handleAddress 
  }
//   methods: { showApi() { 
//     new window.daum.Postcode({ 
//       oncomplete: (data) => { 
//         // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분. 
//         // 도로명 주소의 노출 규칙에 따라 주소를 조합한다. 
//         // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다. 
//         let fullRoadAddr = data.roadAddress; // 도로명 주소 변수 
//         let extraRoadAddr = ''; // 도로명 조합형 주소 변수 
//         // 법정동명이 있을 경우 추가한다. (법정리는 제외) 
//         // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다. 
//         if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){ 
//           extraRoadAddr += data.bname; 
//           }
//         // 건물명이 있고, 공동주택일 경우 추가한다. 
//         if(data.buildingName !== '' && data.apartment === 'Y'){ 
//           extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName); 
//           } 
//         // 도로명, 지번 조합형 주소가 있을 경우, 괄호까지 추가한 최종 문자열을 만든다. 
//         if(extraRoadAddr !== ''){ extraRoadAddr = ' (' + extraRoadAddr + ')'; } 
//         // 도로명, 지번 주소의 유무에 따라 해당 조합형 주소를 추가한다. 
//         if(fullRoadAddr !== ''){ fullRoadAddr += extraRoadAddr; } 
//         // 우편번호와 주소 정보를 해당 필드에 넣는다. 
//         this.zip = data.zonecode; 
//         //5자리 새우편번호 사용 
//         this.addr1 = fullRoadAddr; 
//         } 
//         }).embed(this.$refs.embed) 
//         }
// }
}
</script>
<style scoped>
.register-btn {
  width: 200px;
}
.register-btn-pd{
  /* padding: 30px; */
}
.txt_example {
  font-size: 30px !important;
}
</style>