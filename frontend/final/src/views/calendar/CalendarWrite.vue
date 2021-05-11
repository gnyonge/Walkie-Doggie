<template>
  <div>
    <div id="mainBox">
      <div class="d-flex justify-space-between">
        <div>
        <v-icon @click="goback()">mdi-arrow-left</v-icon>
        </div>
        <div>
          일기쓰기
        </div>
        <div style="width: 24px; background-color: white;">
        </div>
      </div>
      <div id="contentBox">
        {{getSelectedDate}}
      </div>
      <div id="contentBox">
        <v-textarea
          v-model="diaryContent"
          label="일기 내용"
          outlined
          rows="5"
          row-height="25"
          color="#48B9A8"
        ></v-textarea>
      </div>
      <div id="contentBox">
        사진 첨부
      </div>
      <div id="contentBox">
        <v-file-input
          multiple
          color="#48B9A8"
        ></v-file-input>
      </div>
      <v-divider></v-divider>
      <div id="contentBox">
        <v-checkbox
          v-model="memo"
          label="특이사항"
          color="#48B9A8"
          hide-details
        ></v-checkbox>
        <v-textarea
          class="px-5 mt-5"
          v-model="memoContent"
          v-if="memo"
          outlined
          rows="2"
          row-height="20"
          color="#48B9A8"
        ></v-textarea>
      </div>
      <div id="contentBox">
        <v-checkbox
          v-model="health"
          label="건강사항"
          color="#48B9A8"
          hide-details
        ></v-checkbox>
        <v-text-field
          color="#48B9A8"
          @keydown.enter="addHealth()"
          v-model="healthContent"
          class="px-5"
          v-if="health"
          label="입력 후 enter"
        ></v-text-field>
        <div class="mx-4" v-if="health">
          <v-chip
            v-for="(hth, idx) in healthArray"
            :key="idx"
            class="mx-1 my-1"
            close
            color="#BAF1E4"
            @click:close="deleteHealth(hth)"
          >
            {{ hth }}
          </v-chip>
        </div>
      </div>
      <div class="d-flex justify-end">
        <v-btn id="mainBtn" width="50px" @click="createDiary()">완료</v-btn>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'

export default {
  name: "CalendarWrite",
  data () {
      return {
        dialog: false,
        diaryContent: "",
        memo: false, // 특이사항 체크 or not
        health: false, // 건강사항 체크 or not
        memoContent: "", // 특이사항 내용
        healthContent: "", // 건강사항 내용 (1개)
        healthArray: [], // 건강사항 내용들 (전체)

      }
    },
    computed: {
      ...mapGetters(['getSelectedDate'])
    },
    watch: {
      health(newVal) {
        if (!newVal) {
          this.healthContent = ""
          this.healthArray = []
        }
      },
      memo(newVal) {
        if(!newVal) {
          this.memoContent = ""
        }
      },
      dialog(newVal) {
        if(!newVal) {
          this.health = false
          this.memo = false
          this.diaryContent = ""
        }
      }
    },
    methods: {
      ...mapMutations(['setSelectedDate']),
      ...mapActions(['createNoPhotoDiaryInApi']),
      addHealth() {
      if (this.healthContent.replace(/(\s*)/g, "").length > 0) {
        this.healthArray.push(this.healthContent);
        this.healthContent = "";
        }
      },
      deleteHealth(hth) {
        const index = this.healthArray.indexOf(hth);
        this.healthArray.splice(index, 1);
      },
      goback() {
        this.$router.push('/calendar')
      },
      createDiary() {
        this.createNoPhotoDiaryInApi({
          d_date: this.getSelectedDate,
          d_flag: 0,
          d_img: "string",
          d_memo: this.diaryContent,
          d_special: this.memoContent,
          d_walk: 0,
          peid: "petpetpetpet1"
        }).then((res) => {
          console.log(res)
          console.log('성공??')
        })
      }
    }
}
</script>

<style>
#contentBox {
  margin-top: 20px;
  margin-bottom: 10px;
}
.v-label, .v-chip__content {
  margin: 0px;
}
</style>