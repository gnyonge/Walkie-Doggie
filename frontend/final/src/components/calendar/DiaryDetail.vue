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
        color="#DEB98B"
        v-bind="attrs"
        v-on="on"
        @click="todaywalk()"
      >
        <v-icon dark>
          mdi-dog-service
        </v-icon>
      </v-btn>
      </template>
      <v-card>
        <v-toolbar
          dark
          color="#DEB98B"
        >
          <v-btn
            icon
            dark
            @click="dialog = false"
          >
            <v-icon>mdi-close</v-icon>
          </v-btn>
          <v-toolbar-title class="mx-16">My Pet Diary</v-toolbar-title>
          <v-spacer></v-spacer>
        </v-toolbar>
              <div class="d-flex justify-center my-5">
              <v-btn rounded @click="todaydiary()" id="btnstyle" :class="{diary: isDiary}">오늘의 일기</v-btn>
              <v-btn rounded @click="todaywalk()" id="btnstyle" :class="{diary: !isDiary}">산책 기록</v-btn>
              </div>
        <router-view></router-view>
      </v-card>
    </v-dialog>
</template>

<script>
export default {
  name: "DiaryDetail",
  data () {
      return {
        dialog: false,
        notifications: false,
        sound: true,
        widgets: false,
        isDiary: false,
      }
    },
  methods: {
    todaydiary() {
      this.isDiary = true
      if (this.$route.path!=='/calendar/todaydiary') this.$router.push({ name: "TodayDiary" })
    },
    todaywalk() {
      this.isDiary = false
      if (this.$route.path!=='/calendar/todaywalk') this.$router.push({ name: "TodayWalk" })
    }
  }
}
</script>

<style>
#btnstyle {
  display: inline;
  margin-left: 2px;
  margin-right: 2px;
  width: 110px;
  background-color: #5EBC88;
  color: white;
  cursor: pointer; 
  outline: none; 
  box-shadow: 1px 4px 0 rgb(0,0,0,0.5);
}
.diary {
  box-shadow: 1px 1px 0 rgb(0,0,0,0.5) !important;
  position: relative; 
  top:2px; 
}
</style>