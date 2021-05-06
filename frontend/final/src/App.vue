<template>
  <v-app id="back">
    <TopNav id="top"/>
    <router-view style="padding-top: 70px;"></router-view>
    <BottomNav id="bottom"/>
  </v-app>
</template>

<script>
import TopNav from './components/TopNav';
import BottomNav from './components/BottomNav';

export default {
  name: 'App',

  components: {
    TopNav,
    BottomNav,
  },

  data: () => ({
    //
  }),
};
</script>
<style>
@import url(http://fonts.googleapis.com/earlyaccess/jejugothic.css);
#back {
  background-color: #FDF8F2;
  font-family: "Jeju Gothic", serif;
}
#top {
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  top: 0;
  width: 100%;
  background-color: white;
}
#bottom {
  position: fixed;
  bottom: 0;
  width: 100%;
  height: 70px !important;
  padding-top: 10px;
}
</style>