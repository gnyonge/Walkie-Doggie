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
      <v-card style="background-color: #FDF8F2">
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
          style="background-color: #FDF8F2"
        >
          <v-subheader>{{getSelectedDate}}</v-subheader>
          <v-list-item>
            <v-list-item-content>
              <v-list-item-title>일지 내용</v-list-item-title>
              <v-textarea
                background-color="amber lighten-4"
                color="orange orange-darken-4"
              ></v-textarea>
            </v-list-item-content>
          </v-list-item>
          <v-list-item>
            <v-list-item-content>
              <v-list-item-title>사진 첨부</v-list-item-title>
              <v-file-input
                multiple
                color="#5EBC88"
              ></v-file-input>
            </v-list-item-content>
          </v-list-item>
        </v-list>
        <v-list
          three-line
          subheader
          style="background-color: #FDF8F2"
        >

          <v-list-item>
            <v-list-item-content>
              <v-list-item-title> <v-checkbox
              v-model="memo"
              label="특이사항"
              color="success"
              hide-details
            ></v-checkbox></v-list-item-title>
            <v-text-field
              color="success"
              v-model="memoContent"
              class="px-5"
              v-if="memo"
            ></v-text-field>
            
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
            
            <div class="mx-4">
              <v-chip
                v-for="(hth, idx) in healthArray"
                :key="idx"
                class="mx-1 my-1"
                close
                color="#5EBC88"
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
        memo: false,
        health: false,
        memoContent: "",
        healthContent: "",
        healthArray: [],

      }
    },
    computed: {
      ...mapGetters(['getSelectedDate'])
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
.v-btn__content, .v-label, .v-chip__content {
  margin: 0px;
}

</style>