<template>
  <v-app id="back">
    <TopNav id="top"/>
    <router-view style="padding-top: 70px; padding-bottom: 90px;"></router-view>
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
@import url(https://fonts.googleapis.com/earlyaccess/jejugothic.css);
#back {
  background-color: #F7FAFB;
  font-family: "Jeju Gothic", serif;
  color: #323232;
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
  box-shadow: 1px 3px 0 rgb(0,0,0,0.3);
  background-color: #BAF1E4;
  color: #323232;
}
span {
  margin-top: 0px;
}
</style>