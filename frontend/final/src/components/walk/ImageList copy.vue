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
        <v-btn icon @click="likePost(i.lid)" class="mt-1" v-if="i.l_flag == 0 && !isLiked">
          <v-icon>mdi-heart-outline</v-icon>
        </v-btn>
        <v-btn icon @click="likePost(i.lid)" class="mt-1" v-else-if="i.l_flag == 0 && isLiked">
          <v-icon color="red">mdi-heart</v-icon>
        </v-btn>
        <v-btn icon @click="likePost(i.lid)" class="mt-1" v-else-if="i.l_flag != 0 && !isLiked">
          <v-icon color="red">mdi-heart</v-icon>
        </v-btn>
        <v-btn icon @click="likePost(i.lid)" class="mt-1" v-else-if="i.l_flag != 0 && isLiked">
          <v-icon color="red">mdi-heart-outline</v-icon>
        </v-btn>
        <div class="d-inline mr-3" style="font-size: 18px; margin-top: 10px;" v-if="i.l_flag == 0 && !isLiked">{{i.l_like}}</div>
        <div class="d-inline mr-3" style="font-size: 18px; margin-top: 10px;" v-else-if="i.l_flag == 0 && isLiked">{{i.l_like + 1}}</div>
        <div class="d-inline mr-3" style="font-size: 18px; margin-top: 10px;" v-else-if="i.l_flag != 0 && !isLiked">{{i.l_like}}</div>
        <div class="d-inline mr-3" style="font-size: 18px; margin-top: 10px;" v-else-if="i.l_flag != 0 && isLiked">{{i.l_like + 1}}</div>
        
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
    }
  },
  created() {
    this.hotPlaceList = this.getHotPlace
    if(this.hotPlaceList.length == 0){
      this.$router.push('/hotplace')
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
    ...mapGetters(['getSelectedItem', 'getHotPlace', 'getUser', 'getDogInfo'])
  },
  methods: {
    ...mapMutations(['setSelectedItem', 'setNowTab']),
    ...mapActions(['likePlaceInApi', 'getHotPlaceListInApi']),
    goback() {
      this.$router.push('/walk')
      this.setNowTab(1)
    },
    clickItem(i){
      this.setSelectedItem(i)
    },
    likePost(lid) {
      this.likePlaceInApi({
        lid: lid,
        uid: this.getUser.uid
      }).then(() => {
        if (this.isLiked) {
          this.isLiked = false
        } else {
          this.isLiked = true
        }
      })
      // .then(() => {
      //   this.getHotPlaceListInApi({
      //       uid: this.getUser.uid,
      //       sort: "pop",
      //       p_location: this.getUser.u_location
      //     })
      // })
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