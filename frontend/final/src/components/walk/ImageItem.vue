<template>
  <div id="mainBox">
    <div class="d-flex justify-space-between">
      <div style="width: 24px; background-color: white;">
      </div>
      <div style="padding-top: 1px;">
        멍 PHOTO
      </div>
      <div>
        <v-icon @click="goback()">mdi-close</v-icon>
      </div>
    </div>
      <div class="d-flex justify-center mb-3">작성자 : {{getSelectedItem.pe_name}}</div>
      <div class="d-flex justify-center mb-3">{{getSelectedItem.l_desc}}</div>
      <v-img
        id="diaryBox"
        width="100%"
        :src = getSelectedItem.l_image 
      ></v-img>
      <div id="contentBox"> 
      <div class="d-flex justify-end mt-9">
        <v-btn color="#48B9A8" text width="50px" @click="go('/calendar/update')">수정</v-btn>
        <!-- 삭제 확인 창 -->
        <v-btn
          color="#48B9A8" text width="50px"
          @click="snackbar = true"
        >
          삭제
        </v-btn>
        <v-snackbar
          v-model="snackbar"
          :multi-line="multiLine">
          정말 삭제하시겠어요?
          <template v-slot:action="{ attrs }">
            <v-btn
              color="red"
              text
              v-bind="attrs"
              @click="deleteMyPosting"
            >
              YES
            </v-btn>
          </template>
        </v-snackbar>
        <div>
          <v-icon class="mt-2">mdi-heart-outline</v-icon><div class="d-inline mr-3" style="font-size: 18px; margin-top: 10px;">10220</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
export default {
  name: "ImageItem",
  data() {
    return{
      item: '',
      multiLine: true,
      snackbar: false,
    }
  },
  computed: {
    ...mapGetters(['getSelectedItem', 'getDogInfo'])
  },
  methods: {
    ...mapMutations(['setSelectedItem']),
    ...mapActions(['deleteMyPostingInApi']),
    goback() {
     this.setSelectedItem(null)
    },
    //나의 게시글 삭제 
    deleteMyPosting(){
      let peid = this.getDogInfo.pet.peid
      let lid = this.getSelectedItem.lid
      this.deleteMyPostingInApi({peid, lid})
        .then((res)=> {
          console.log(res)
        })
    },
  },
}
</script>

<style scoped>
#contentbox {
  background-color: white;
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