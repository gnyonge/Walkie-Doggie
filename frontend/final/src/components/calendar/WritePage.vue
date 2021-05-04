<template>
    <v-dialog
      v-model="dialog"
      fullscreen
      hide-overlay
      transition="dialog-bottom-transition"
    >
      <template v-slot:activator="{ on, attrs }">
        <v-btn
        class="mx-2 my-2"
        fab
        dark
        color="#5EBC88"
        v-bind="attrs"
        v-on="on"
      >
        <v-icon dark>
          mdi-pencil
        </v-icon>
      </v-btn>
      </template>
      <v-card>
        <v-toolbar
          dark
          color="#5EBC88"
        >
          <v-btn
            icon
            dark
            @click="dialog = false"
          >
            <v-icon>mdi-close</v-icon>
          </v-btn>
          <v-toolbar-title>일지 작성</v-toolbar-title>
          <v-spacer></v-spacer>
          <v-toolbar-items>
            <v-btn
              dark
              text
              @click="dialog = false"
            >
              완료
            </v-btn>
          </v-toolbar-items>
        </v-toolbar>
        <v-list
          three-line
          subheader
        >
          <v-subheader class="mt-4 font-weight-bold d-flex justify-center" style="font-size: 25px">{{getSelectedDate}}</v-subheader>
          <v-list-item>
            <v-list-item-content>
              <!-- <v-list-item-title>일지 내용</v-list-item-title> -->
              <v-textarea
                v-model="diaryContent"
                
                label="일지 내용"
                auto-grow
                outlined
                rows="5"
                row-height="25"
                color="#5EBC88"
              ></v-textarea>
            </v-list-item-content>
          </v-list-item>
        <v-divider></v-divider>
          <v-list-item>
            <v-list-item-content>
              <v-list-item-title style="font-size: 20px">사진 첨부</v-list-item-title>
              <v-file-input
                multiple
                color="#5EBC88"
              ></v-file-input>
            </v-list-item-content>
          </v-list-item>
        <v-divider></v-divider>

        </v-list>
        <v-list
          three-line
          subheader
        >

          <v-list-item>
            <v-list-item-content>
              <v-list-item-title> <v-checkbox
              v-model="memo"
              label="특이사항"
              color="success"
              hide-details
            ></v-checkbox></v-list-item-title>
            <v-textarea
                class="px-5 mt-5"
                v-model="memoContent"
                v-if="memo"
                auto-grow
                outlined
                rows="5"
                row-height="15"
                color="#5EBC88"
              ></v-textarea>
            
            </v-list-item-content>
            
          </v-list-item>
          <v-list-item>
            <v-list-item-content>
              <v-list-item-title> <v-checkbox
              v-model="health"
              label="건강사항"
              color="#5EBC88"
              hide-details
            ></v-checkbox></v-list-item-title>
            <v-text-field
              color="#5EBC88"
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
                color="#EDD9BF"
                @click:close="deleteHealth(hth)"
              >
                {{ hth }}
              </v-chip>
            </div>
            
            </v-list-item-content>
            
          </v-list-item>
        </v-list>
      </v-card>
    </v-dialog>
</template>

<script>
import { mapGetters, mapMutations } from 'vuex'
  export default {
    name: "WritePage",
    data () {
      return {
        dialog: false,
        diaryContent: "",
        memo: false, // 특이사항
        health: false, // 건강사항
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
    }
  }
</script>

<style>
/* .v-btn__content, .v-label, .v-chip__content {
  margin: 0px;
}
.v-text-field__details {
  display: none !important;
}
.v-list {
  padding-bottom: 0px !important;
}
.v-label {
  font-size: 20px !important;
} */
</style>