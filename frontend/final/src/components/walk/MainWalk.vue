<template>
  <div class="main">
    <div class="d-flex justify-center">
      <v-img
        height="158"
        class="rounded-circle"
        max-height="158"
        max-width="158"
        :src = getDogInfo.pet.pe_profile_photo>
      </v-img>
    </div>
    <div class="d-flex justify-center explain">
    <b>반려견의 산책정보를 기록합니다. </b>
    </div>
    <div class="d-flex justify-center">
      <v-btn
        elevation="2"
        large
        id="mainBtn"
        style="color: #323232; width: 210px; margin:10px;"
        @click="startWalk"><b>산책 시작하기</b>
        <v-icon>mdi-dog-side</v-icon>
      </v-btn>
    </div>
    <div class="d-flex justify-center">
      <v-btn
        elevation="2"
        large
        id="mainBtn"
        style="color: #323232; width: 210px; margin:10px;"
        @click="goToHotPlace"
      ><b>멍플레이스 구경하기</b>
        <v-icon>mdi-fire</v-icon>
      </v-btn> 
    </div>
    <div class="d-flex justify-center mt-5">
      <v-btn
          color="red" text width="50px"
          @click="goto()"
        >
          <b>산책에서 나가기</b>
        </v-btn>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapActions, mapMutations } from 'vuex'

export default {
  name: 'MainWalk',
  computed: {
    ...mapGetters(['getUser', 'getHotPlace', 'getDogInfo'])
  },
  mounted(){
    // 중복으로 넘어가면 setWid초기화
    this.setWid(Number)
    this.deletePostingWid()
  },
  methods:{
    ...mapActions(['getHotPlaceListInApi']),
    ...mapMutations(['setWid', 'deletePostingWid']),
    startWalk(){
      this.$router.push('/startwalk')
    },
    goto() {
        this.$router.push('/calendar')
      },
    goToHotPlace(){
      this.getHotPlaceListInApi({
        uid: this.getUser.uid,
        sort: "string",
        p_location: this.getUser.u_location
      })
      .then(() => {
        if (this.getHotPlace.length == 0) {
          alert('핫플레이스가 하나도 없어요...')
        } else {
          this.$router.push('/hotplace')
        }
      })
    },
  }
}
</script>

<style>
.main{
  margin-top: 45%;
  text-align: center;
}
.explain{
  margin: 6% ;
  color: #7c7b77;
}
</style>