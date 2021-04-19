<template>
  <v-app id="back">
    <TopNav />
    <router-view style="padding-top: 70px;"></router-view>
    <BottomNav />
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
#back {
  background-color: #FDF8F2;
}
</style>
