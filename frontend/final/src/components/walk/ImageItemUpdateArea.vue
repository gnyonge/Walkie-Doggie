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
      <div class="d-flex justify-center mb-3">
        작성자 : {{getSelectedItem.pe_name}}
      </div>
      <div class="d-flex justify-center mb-3">
        {{getSelectedItem.l_desc}}
      </div>
      <v-img
        id="diaryBox"
        width="100%"
        :src = getSelectedItem.l_image 
      ></v-img>
      <div style="text-align: center;" class="mt-5">
        <div>왜 좋은가요?</div>
          <div>
            <v-btn text id="likeBox" @click="selectThis(option.name, idx)" :class="{clicked: optionValue == option.name}"
            v-for="option, idx in selectOptions" :key="idx">
              {{option.name}}
            </v-btn>
          </div>
      </div>
      
      
      <div id="contentBox"> 
      <div class="d-flex justify-end mt-9">
        <v-btn color="#48B9A8" text width="50px" @click="go()" v-if="userCheckBtn()">수정</v-btn>
        <!-- 삭제 확인 창 -->
        <!-- <v-btn
          color="#48B9A8" text width="50px"
          @click="snackbar = true"
          v-if="userCheckBtn()"
        >
          삭제
        </v-btn> -->
        <!-- <v-snackbar
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
        </v-snackbar> -->
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
      userCheck: false,
      optionValue: -1,
      selectOptions: [
        {idx: 0, name: '사진이 잘 나와요!'},
        {idx: 1, name: '휴식 공간이 있어요!'},
        {idx: 2, name: '마킹을 했어요!'},
        {idx: 3, name: '날씨가 좋아요!'},
      ]
    }
  },
  computed: {
    ...mapGetters(['getSelectedItem', 'getDogInfo', 'getUser']),
  },
  methods: {
    ...mapMutations(['setSelectedItem']),
    ...mapActions(['deleteMyPostingInApi', 'getHotPlaceListInApi','editMyPostingInApi']),
    go() {
      this.$router.push('/calendar/update')
    },
    goback() {
     this.setSelectedItem(null)
    },
    // 나의 게시글 수정
    update() {
      this.editMyPostingInApi({

      })

    },
    //나의 게시글 삭제 
    // deleteMyPosting(){
    //   let peid = this.getDogInfo.pet.peid
    //   let lid = this.getSelectedItem.lid
    //   this.deleteMyPostingInApi({peid, lid})
    //     .then(()=> {
    //       this.getHotPlaceListInApi(this.getUser.u_location)
    //       this.setSelectedItem(null)
          

    //     })
    // },
    userCheckBtn() {
      if(this.getSelectedItem.pe_name === this.getDogInfo.pet.pe_name){
        return this.userCheck = true
      }
    }
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
  .btn{
    opacity: 0;
  }
  .btn>label{
    padding: 5px 10px;
    border: 1px solid #ccc;
    border-radius: 3px;
  }
  input[type=radio]:checked + label 
  {
    background: #000000 ;
    color: rgb(255, 255, 255); 
  }
  #likeBox {
    border-radius: 15px;
    background-color: white;
    box-shadow: 5px 5px 5px #E5E5E5;
    border: 1px solid grey;
    padding: 20px;
    margin: 10px;
    width: 200px;
    cursor: pointer; 
    outline: none; 
    box-shadow: 1px 4px 0 rgb(0,0,0,0.3);
  }
  .clicked {
    box-shadow: 1px 1px 0 rgb(0,0,0,0.3) !important;
    background-color: #BAF1E4 !important;
    border: hidden !important;
    position: relative; 
    top:2px; 
  }
</style>