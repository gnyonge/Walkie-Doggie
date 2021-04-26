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
      <v-card style="background-color: #FDF8F2">
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
          <v-toolbar-title class="mx-12">마이펫다이어리</v-toolbar-title>
          <v-spacer></v-spacer>
        </v-toolbar>
        <v-list
          three-line
          subheader
          style="background-color: #FDF8F2"
        >
          <v-list-item>
            <v-list-item-content>
              <div class="d-flex justify-center">
              <v-btn class="d-inline" @click="todaydiary()">오늘의 일기</v-btn>
              <v-btn class="d-inline" @click="todaywalk()">산책 기록</v-btn>
              </div>
            </v-list-item-content>
          </v-list-item>
        </v-list>
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
      }
    },
  methods: {
    todaydiary() {
      if (this.$route.path!=='/calendar/todaydiary') this.$router.push({ name: "TodayDiary" })
    },
    todaywalk() {
      if (this.$route.path!=='/calendar/todaywalk') this.$router.push({ name: "TodayWalk" })
    }
  }
}
</script>

<style>

</style>