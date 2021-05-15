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
     <DaumPostcode
      :on-complete=handleAddress
      v-model="address"
    />
    <div class="mt-3">
      주소는 동단위까지 입력됩니다.
      <p style="font-size:14px">(예: '광주 광산구 장덕동')</p>
    </div>
    <div>
      {{address}}
    </div>
      
    <div class="register-btn-pd d-flex justify-center mt-5">
        <v-btn class="" id="mainBtn" @click="setAddress()">지역 설정</v-btn>
    </div>
    </v-flex>
  </div>
</div>
</template>

<script >
import DaumPostcode from 'vuejs-daum-postcode'
import { mapGetters, mapActions } from 'vuex'

// var handleAddress = (data) => {
//   let fullAddress = data.address
//   let fAddress = [] // 띄어쓰기 기준으로 나누어 단어 저장
//   let sAddress = [] // '동' 으로 끝나는 단어 저장 

  
//     fullAddress = data.jibunAddress

//     // 띄어쓰기 기준으로 나누어 fAddress에 저장
//     fAddress = fullAddress.split(' ');
//     // console.log(fAddress, '주소 나누기 성공') 
//     // 예: ["광주", "광산구", "장덕동", "992-8"]
    
//     // 리스트 돌면서 마지막 단어가 '동'인 부분 저장 
//     // 주소의 동까지 저장 
//     for (let i in fAddress) {
//       // 인덱스
//       // console.log(i)  
//       let checkAddress = fAddress[i]
//       if(checkAddress.charAt(checkAddress.length-1) !== '동'){ 
//         sAddress.push(checkAddress)
//       }else {
//         sAddress.push(checkAddress)
//         break
//       }  
//   }
//   // console.log(sAddress, '동까지 저장')
//   const finalAddress = sAddress.join(" ");
//   this.address = finalAddress
//   // let address = finalAddress
//   console.log(finalAddress, '전달할 최종 주소')
//   // console.log(fullAddress, '풀 주소') 
//   // e.g. '서울 성동구 왕십리로2길 20 (성수동1가)' 주소 세번째 꺼로 가져오기 
//   }

export default {
  data () {
    return {
      address: '',
    }
  },
  computed: {
    ...mapGetters(['getUser'])
  },
  watch: {
    address(newVal) {
      console.log(newVal, '여기용ㅁㄹㄴㅇㅁㄴㄹ')
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
    ...mapActions(['setAddressInApi']),
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
  // console.log(sAddress, '동까지 저장')
  const finalAddress = sAddress.join(" ");
  this.address = finalAddress
  // let address = finalAddress
  console.log(finalAddress, '전달할 최종 주소')
  // console.log(fullAddress, '풀 주소') 
  // e.g. '서울 성동구 왕십리로2길 20 (성수동1가)' 주소 세번째 꺼로 가져오기 
    },
    setAddress() {
      console.log(this.address, '최종 어드레스')
      this.setAddressInApi({
        uid: this.getUser.uid,
        add: this.address
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