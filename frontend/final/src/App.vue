<template>
  <v-app id="back">
    <TopNav id="top"/>
    <transition mode="out-in">
      <router-view style="padding-top: 70px; padding-bottom: 90px;"></router-view>
    </transition>
    <BottomNav id="bottom" v-if="getNowTab!= 1"/>
  </v-app>
</template>

<script>
import TopNav from './components/TopNav';
import BottomNav from './components/BottomNav';
import { mapGetters } from 'vuex'

export default {
  name: 'App',

  components: {
    TopNav,
    BottomNav,
  },

  data: () => ({
    //
  }),
  computed: {
    ...mapGetters(['getNowTab'])
  },
  created() {
    document.title = "WALKIE DOGGIE"
  }

};
</script>
<style>
@import url(https://fonts.googleapis.com/earlyaccess/jejugothic.css);
@import url('https://fonts.googleapis.com/css?family=Noto+Sans&display=swap');

#back {
  background-color: #F7FAFB;
  font-family: 'Noto Sans';
  color: #323232;
  font-weight: bold;
}
#top {
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  z-index: 200;
  top: 0;
  width: 100%;
  background-color: #48B9A8;
  border-radius: 0px 0px 20px 20px;

}
#bottom {
  position: fixed;
  bottom: 0;
  width: 100%;
  height: 70px !important;
  padding-top: 10px;
}
/* 수정하지 마쉐요 직접 class나 style줘서 바꿔주십시오 */
#mainBox {
  border-radius: 15px;
  background-color: white;
  box-shadow: 5px 5px 5px #E5E5E5;
  margin: 20px;
  padding: 20px;
}
/* 수정하지 마쉐요 직접 class나 style줘서 바꿔주십시오 */
#mainBtn {
  display: inline;
  border-radius: 20px;
  padding: 10px;
  width: 130px;
  box-shadow: 3px 3px 0 rgb(0,0,0,0.3);
  background-color: #BAF1E4;
  color: #323232;
}
span {
  margin-top: 0px;
}
</style>