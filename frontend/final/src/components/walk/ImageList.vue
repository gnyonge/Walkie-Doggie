<template>
  <div id="mainBox" style="margin-top: 0px;">
    <!-- 뒤로가기, 멍 PHOTOS-->
    <div class="d-flex justify-space-between">
      <div>
      <v-icon @click="goback()">mdi-arrow-left</v-icon>
      </div>
      <div>
        멍 PHOTOS
      </div>
      <div style="width: 24px; background-color: white;">
      </div>
    </div>
    <!-- 멍 PHOTOS 반복 -->
    <div id="contentBox" v-for="i, idx in hotPlaceList" :key="idx">
      <div class="d-flex justify-center mb-3">{{i.l_desc}}</div>
      <div @click="clickItem(i)">
        <v-img
          id="diaryBox"
          width="100%"
          :src="i.l_image"
        ></v-img>
      </div>
      <div class="d-flex justify-end mt-1">
        <v-btn icon @click="likePost(i.lid)" class="mt-1" v-if="i.l_flag == 0">
          <v-icon>mdi-heart-outline</v-icon>
        </v-btn>
        <v-btn icon @click="likePost(i.lid)" class="mt-1" v-else>
          <v-icon color="red">mdi-heart</v-icon>
        </v-btn>
        <div class="d-inline mr-3" style="font-size: 18px; margin-top: 7px;">{{i.l_like}}</div>
      </div>
      <v-divider></v-divider>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'

export default {
  name: "ImageList",
  data() {
    return {
     hotPlaceList: [],
     isLiked: false,
     color: ""
    }
  },
  created() {
    if (this.getUser == undefined) {
        alert('로그인 해주세요!')
        this.$router.push('/')
      } else {
        this.hotPlaceList = this.getHotPlace
        if(this.hotPlaceList.length == 0){
          this.$router.push('/hotplace')
        }
      }
  },
  beforeDestroy() {

  },
  watch: {
    getHotPlace(newVal) {
      this.hotPlaceList = newVal
    }
  },
  computed: {
    ...mapGetters(['getSelectedItem', 'getHotPlace', 'getUser', 'getDogInfo', 'getFilter'])
  },
  methods: {
    ...mapMutations(['setSelectedItem', 'setNowTab']),
    ...mapActions(['likePlaceInApi', 'getHotPlaceListInApi', 'getTop5ListInApi']),
    goback() {
      this.$router.push('/walk')
      this.setNowTab(1)
    },
    clickItem(i){
      this.setSelectedItem(i)
    },
    likePost(lid) {
      if (this.getFilter == 'ALL') {
        this.likePlaceInApi({
          lid: lid,
          uid: this.getUser.uid
        }).then(() => {
          this.getHotPlaceListInApi({
              uid: this.getUser.uid,
              sort: "string",
              p_location: this.getUser.u_location
            })
        })
      } else if (this.getFilter == 'TOP 5') {
        this.likePlaceInApi({
          lid: lid,
          uid: this.getUser.uid
        }).then(() => {
          this.getTop5ListInApi({
            uid: this.getUser.uid,
            p_location: this.getUser.u_location
          })
        })
      } else {
        this.likePlaceInApi({
          lid: lid,
          uid: this.getUser.uid
        }).then(() => {
          this.getHotPlaceListInApi({
            uid: this.getUser.uid,
            sort: "pop",
            p_location: this.getUser.u_location
          })
        })
      }
      
    }
  }
}
</script>

<style scoped>
#contentbox {
  border: 1px solid grey;
  margin: 12px;
  border-radius: 15px;
}
#diaryBox {
  border-radius: 15px;
  background-color: white;
  box-shadow: 5px 5px 5px #E5E5E5;
  padding: 20px;
}
</style>