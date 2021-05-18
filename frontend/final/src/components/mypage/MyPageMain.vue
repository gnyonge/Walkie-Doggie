<template>
  <div>
    <div>  
      <!-- 1. 프로필 박스 -->
      <div class="mx-3 mt-3" id="mainBox">
        <div class="d-flex">
          <!-- 1. 프로필 사진 --> 
          <div class="mt-10">
            <!-- 프사 있을 때 -->
            <v-img :src="pet.pe_profile_photo" 
            class="rounded-circle"
            height="130"
            width="130" alt="프로필사진" v-if="photo">
            </v-img>
            <!-- 프사 없을 때 -->
            <v-img src="@/assets/images/서비스로고.png" 
            class="rounded-circle"
            max-height="130"
            max-width="130" alt="프로필사진" v-else>
            </v-img>
          </div>
          <!-- 2. 인적사항 --> 
          <div class="mt-1 ml-3" style="width: 148px; word-break:break-all;">
            <p style="font-size: 20px; margin-top: 3px;">{{pet.pe_name}}</p>
            <p><b>나이:</b> {{pet.pe_age}}살</p>
            <p><b>몸무게:</b> {{pet.pe_weight}}kg</p>
            <p v-if="pet.pe_disease"><b>질병:</b> {{pet.pe_disease}}</p>
            <p v-else><b>질병:</b> 없음</p>
            <p v-if="pet.pe_trauma"><b>트라우마:</b> {{pet.pe_trauma}}</p>
            <p v-else><b>트라우마:</b> 없음</p>
          </div>
          <!-- 햄버거버튼 -->
          <div class="text-center">
            <v-menu offset-y>
              <template v-slot:activator="{ on, attrs }">
                <v-btn
                color="#BAF1E4"
                icon
                v-bind="attrs"
                v-on="on"
              >
                <v-icon size="40px">mdi-menu</v-icon>
              </v-btn>
              </template>
              <v-list>
                <v-list-item
                  v-for="(item, index) in items"
                  :key="index"
                >
                  <v-list-item-title @click="goto(`${item.path}`)">{{ item.title }}</v-list-item-title>
                </v-list-item>
              </v-list>
            </v-menu>
          </div>
        </div>
      </div>
        
      <!-- 버튼 그룹 --> 
      <div class="d-flex">
        <!-- 건강 버튼-->
        <div id="mainBox" style="width:40%">
          <router-link to="/health">
          <div class="d-flex justify-center">
            <v-icon size="80px" color="#BAF1E4">mdi-pill</v-icon> 
          </div>
          <div class="d-flex justify-center mt-3">
            <div style="color:#48B9A8; font-size:20px;">건강</div>
          </div>
          </router-link>
        </div>
        <!-- 통계 버튼 --> 
        <div id="mainBox" style="width:40%">
          <router-link to="/statistics">
          <div class="d-flex justify-center">
            <v-icon size="80px" color="#BAF1E4">mdi-poll</v-icon> 
          </div>
          <div class="d-flex justify-center mt-3">
            <div style="color:#48B9A8; font-size:20px;">통계</div>
          </div>
          </router-link>
        </div>
      </div>
    </div>
      <!-- 3.강아지 일기 관리 --> 
      <div class="ml-5 mt-3">
        <h5><b>강아지 계정 관리</b> </h5>
      </div>
        <div class="frame" >
          <div class="card-list">
            <div class="mt-5" id="dogBox" v-for="(my, idx) in myPetList" :key="idx" :class="{push: pet.peid == my.peid}">
              <div class="d-flex justify-end">
                <v-icon v-if="pet.peid == my.peid" @click="goto('dogchange')">mdi-cog-outline</v-icon>
                <v-icon v-else style="color: white;">mdi-radiobox-blank</v-icon>
              </div>
              <div>
                <v-img :src="my.pe_profile_photo" 
                  class="rounded-circle" height="158"
                  max-height="158"
                  max-width="158" alt="프로필"
                  @click="selectDog(my.peid)">
                  </v-img>
              </div>
              <div class="d-flex justify-center mt-9">
                <p style="color:#79704F; font-size:24px;">{{my.pe_name}}</p>
              </div>
              <div class="d-flex justify-center">
                <div style="color:#79704F; font-size:20px;">{{my.pe_age}}살</div>
              </div>
              
            </div>
            <div class="mt-3">
              <v-btn
              class="mx-2"
              fab
              dark
              color="#BAF1E4"
              @click="addDog()"
            >
                <v-icon>
                  mdi-plus
                </v-icon>
              </v-btn>
            </div>
            <div class="space"></div>
            </div>
          </div>
        </div>
</template>

<script>
import {mapGetters, mapActions, mapMutations} from 'vuex'

export default {
  name: "MyPageMain",
  data: () => ({
      pet: {},
      allergy: [],
      photo: false, // 프로필 사진 유무
      items: [
        { title: '지역 설정' , path: 'infochangelocation'},
        { title: '계정 관리' , path: 'infochangepw'},
        { title: '로그아웃', path:'logout' },
      ],
      myPetList: [],
    }),
  created() {
    this.getUserInfoInApi(this.getUser.uid) // 모든 펫 리스트 가져오기
    .then(() => {
      this.myPetList = this.getMyDogListInfo
    })
    this.showDogInfoInApi(this.getDogInfo.pet.peid) // 현재 펫 정보 가져오기
    .then(() => {
      this.pet = this.getDogInfo.pet
      if (this.getDogInfo.pet.pe_profile_photo.length != 0) {
        this.photo = true
      }
      this.allergy = this.getDogInfo.allergy
    })
  },
  computed: {
    ...mapGetters(['getDogInfo', 'getUser', 'getMyDogListInfo', 'getAddress']) 
  },
  methods: {
    ...mapMutations(['setUser', 'setDogInfo', 'setMyDogListInfo', 'setNowTab']),
    ...mapActions(['showDogInfoInApi', 'getUserInfoInApi']),
    goto(path) {
      if (path != 'logout') {
        this.$router.push(`/${path}`)
      } 
      else { // 로그아웃할때
        this.setUser(null)
        this.setDogInfo(null)
        this.setMyDogListInfo(null)
        sessionStorage.setItem('doggie_token','');
        this.$router.push('/')
        this.setNowTab(1)
      }
      },
    addDog() {
      if (this.getMyDogListInfo.length == 3) {
        alert('최대 세마리까지 등록 가능합니다!')
      } else {
        this.$router.push('/dogregister')
      }
    },
    selectDog(peid) {
      this.showDogInfoInApi(peid) // 선택한 펫 정보 가져오기
      .then(() => {
        this.pet = this.getDogInfo.pet
        if (this.getDogInfo.pet.pe_profile_photo.length != 0) {
          this.photo = true
        }
        this.allergy = this.getDogInfo.allergy
      })
    }
  }
  
}
</script>
<style scoped>
#dogBox {
  display: inline;
  width: 300px;
  height: 350px;
  box-shadow: 3px 3px 0 rgb(0,0,0,0.3);
  color: #323232;
  border-radius: 15px;
  background-color: white;
  margin: 20px;
  padding: 20px;
  cursor: pointer; 
  outline: none; 
}

.push {
  box-shadow: 1px 1px 0 rgb(0,0,0,0.3) !important;
  background-color: #BAF1E4 !important;
  position: relative; 
  top:2px; 
}

.frame {
  width: 360px;
  overflow: auto;
}

.card-list {
  display: flex;
  padding-left: 10px;
  padding-right: 10px;
}

.card {
  flex-shrink: 0;
  width: 200px;
  height: 300px;
  margin-right: 10px;
}

.space {
  flex-shrink: 0;
  width: 200px;
  height: 200px;
}
</style>