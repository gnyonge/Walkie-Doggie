<template>
  <div>
    <div>  
      <!-- 1. 프로필 박스 -->
      <div class="mx-3 mt-3">
        <div class="ml-3 d-flex">
          <!-- 1. 프로필 사진 --> 
          <div class="mt-5">
            <v-img :src="pet.pe_profile_photo" 
            class="rounded-circle"
            max-height="130"
            max-width="130" alt="프로필사진" v-if="photo">
            </v-img>
            <v-img src="@/assets/images/서비스로고.png" 
            class="rounded-circle"
            max-height="130"
            max-width="130" alt="프로필사진" v-else>
            </v-img>
          </div>
          <!-- 2. 인적사항 --> 
          <div class="mt-3 ml-5" style="width: 148px; word-break:break-all;">
            <p style="font-size: 20px">{{pet.pe_name}}</p>
            <p>나이: {{pet.pe_age}}살</p>
            <p>몸무게: {{pet.pe_weight}}kg</p>
            <p>특이사항: {{pet.pe_trauma}}, {{pet.pe_disease}}</p>
          </div>
          <!-- 햄버거버튼 -->
          <v-menu
            bottom
            left
            class="d-inline"
          >
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

            <v-list width="100px">
              <!-- <v-list-item
                v-for="(item, i) in items"
                :key="i"
              >

                <v-list-item-title @click="goto()">{{ item.title }}</v-list-item-title>
              </v-list-item> -->
              <v-list-item>
                <v-list-item-title @click="goto('infochangelocation')" style="margin-top:10px">지역 수정</v-list-item-title>
                <v-list-item-title @click="goto('infochangepw')">계정 설정</v-list-item-title>
                <v-list-item-title @click="goto('')">?A?A?A?A?A??A</v-list-item-title>
              </v-list-item>
            </v-list>
          </v-menu>
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
            <!-- 한 마리당 하나의 카드 -->
            <!-- 카드 클릭시 대표 강아지 설정 변경  -->
            <div class="mt-5" id="mainBox" style="background-color:#BAF1E4;">
              <div class="d-flex justify-end">
                <!-- 설정버튼 클릭 시 dogchange 페이지로 이동 -->
                <v-icon @click="goto('dogchange')">mdi-cog-outline</v-icon>
              </div>
              <div>
                <v-img src="../../assets/images/서비스로고.png" 
                  class="rounded-circle" height="158"
                  max-height="158"
                  max-width="158" alt="">
                  </v-img>
              </div>
              <div class="d-flex justify-center mt-2">
                <p style="color:#79704F; font-size:24px;">이름</p>
              </div>
              
            </div>
            <!-- 강아지 추가 페이지로 이동 -->
            <div class="mt-3">
              <v-btn
              class="mx-2"
              fab
              dark
              color="#BAF1E4"
              @click="goto('dogregister')"
            >
                <v-icon>
                  mdi-plus
                </v-icon>
              </v-btn>
            </div>
            <!-- <div class="space"></div> -->
          </div>
        </div>
      </div>
</template>

<script>
import {mapGetters,mapActions} from 'vuex'

export default {
  name: "MyPageMain",
  data: () => ({
      // items: [
      //   { title: '지역 수정' , m_url: 'infochangelocation'},
      //   { title: '계정 관리' ,m_url: 'infochangepassword' },
      //   { title: '로그아웃' ,m_url: ''},
      // ],
      pet: {},
      allergy: [],
      photo: false, // 프로필 사진 유무
    }),
  created() {
    // this.getUserInfoInApi(this.getUser.uid)
    this.showDogInfoInApi(this.getDogInfo.pet.peid)
    .then(() => {
      this.pet = this.getDogInfo.pet
      if (this.getDogInfo.pet.pe_profile_photo.length != 0) {
        this.photo = true
      }
      this.allergy = this.getDogInfo.allergy
    })
  },
  computed: {
    ...mapGetters(['getDogInfo','getUser'])
  },
  methods: {
    ...mapActions(['showDogInfoInApi','getUserInfoInApi','getAddressInApi']),
    goto(path) {
      this.$router.push(`/${path}`)
    },
  }
  
}
</script>
<style scoped>
p {
  color: black;
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
/*   background-color: white; */
}
</style>